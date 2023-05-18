import { UserStore } from '@/stores';
import { reactive, type ReactiveEffect, type Ref } from 'vue';
const wsUrl = "ws://127.0.0.1:8888/ws";  // websocket 默认连接地址
let websocket: any;  // 用于存储实例化后websocket
let isConnect = false;  // 连接标识 避免重复连接
let rec: any;  // 断线后重连，延迟5秒重新创建webSocket连接 rec用于存储延迟请求的代码
let addRoomMsg: Function // 添加房间消息
let resizeScreen: Function  // 调整屏幕位置
let updateChatList: Function //添加聊天列表信息
let confirmMsg:Function // 确认聊天信息
let receiveInfo = reactive({
    data: {
        username: "",
        imgUrl: "",
        userId: "",
        sendType: 0
    },
}) // 接收方信息
const userId = UserStore.user_info?.userId

export enum Actions {
    CONNECT = 1,    // "第一次(或重连)初始化连接"
    CHAT = 2,       // "聊天消息",	
    SIGNED = 3,     // "消息签收",
    KEEPALIVE = 4,  //"客户端保持心跳",
    PULL_FRIEND = 5,// "拉取好友";
    Enter_ChatRoom = 6, // "进入聊天室"
    Leave_ChatRoom = 7, // "离开聊天室"
}

export interface chatMsg {
    senderId: string | any; //发送者id
    senderImg: string | any; //发送者头像url
    senderName: string | any; //发送者姓名
    receiverId: string | any;  //接收者id
    msg: string | null;  //聊天内容
    sendTime: Date;
}

export interface chatInfo {
    action: Actions;
    chatMsg: chatMsg | null;
    expand: string | null;
}

function createWebSocket() {
    // 判断浏览器是否支持WebSocket
    if (window.WebSocket) {
        try {
            initWebSocket(); // 初始化websocket连接
        } catch (e) {
            console.log("尝试创建连接失败");
            reConnect(); // 如果无法连接上 webSocket 那么重新连接！可能会因为服务器重新部署，或者短暂断网等导致无法创建连接
        }
    } else {
        console.log("浏览器不支持websocket")
    }
}

function initWebSocket() {
    websocket = new WebSocket(wsUrl);
    console.log("websocket:", websocket)
    websocket.onopen = function (e: any) {
        websocketOpen(e);
        const msg: chatMsg = { senderId: userId, senderImg: null, senderName: null, receiverId: null, msg: null, sendTime: new Date() }
        const info: chatInfo = { action: Actions.CONNECT, chatMsg: msg, expand: null }
        websocketSend(info); // 发送连接信息
        setInterval( keepLive, 2 * 60 * 1000);  // 定时发送心跳
    }

    // 接收
    websocket.onmessage = function (e: any) {
        websocketOnMessage(e);
    };

    // 连接发生错误
    websocket.onerror = function () {
        console.log("WebSocket连接发生错误");
        isConnect = false; // 连接断开修改标识
        reConnect(); // 连接错误 需要重连
    };

    websocket.onclose = function (e: any) {
        websocketClose(e);
    };
}

// 定义重连函数
let reConnect = () => {
    console.log("尝试重连");
    if (isConnect) return; // 如果已经连上了
    rec && clearTimeout(rec);
    rec = setTimeout(function () {
        // 延迟5秒重连 避免过多次过频繁请求重连
        createWebSocket();
    }, 5000);
}

// 发送心跳
function keepLive() {
    const msg: chatMsg = { senderId: userId, senderImg: null, senderName: null, receiverId: null, msg: null, sendTime: new Date() }
    const info: chatInfo = { action: Actions.KEEPALIVE, chatMsg: msg, expand: null }
    websocketSend(info); // 发送连接信息
}

// 创建连接
function websocketOpen(e: any) {
    console.log("连接成功");
}

// 数据接收
async function websocketOnMessage(e: any) {
    console.log("接收数据:", e.data);
    let data = JSON.parse(e.data);
    console.log(receiveInfo);
    if (data.action == Actions.CHAT) { // 聊天类型返回消息
        if (receiveInfo.data.sendType == 1 ){
            if(data.chatMsg.receiverId == receiveInfo.data.userId){
                await addRoomMsg(data.chatMsg, "down");
                await resizeScreen();
                signMsg();
            }else{
                updateChatList(data.chatMsg, receiveInfo, false);
            }
        }else if(receiveInfo.data.sendType == 2){
            if ((data.chatMsg.receiverId == receiveInfo.data.userId && data.chatMsg.senderId == userId) || (data.chatMsg.receiverId == userId && data.chatMsg.senderId == receiveInfo.data.userId)) {
                await addRoomMsg(data.chatMsg, "down");
                await resizeScreen();
                signMsg();
                updateChatList(data.chatMsg, receiveInfo, true);
            } else {
                updateChatList(data.chatMsg, receiveInfo, false);
            }
        }
    } else if (data.action == Actions.Enter_ChatRoom) {
        await resizeScreen();
    }
}

// 关闭
function websocketClose(e: any) {
    console.log(e);
    isConnect = false; // 断开后修改标识
    console.log("connection closed (" + e.code + ")");
}

// 数据发送
function websocketSend(data: any) {
    console.log("发送数据:", data, JSON.stringify(data));
    websocket.send(JSON.stringify(data));
}

// 签收消息
function signMsg() {
    const msg: chatMsg = { senderId: userId, senderImg: null, senderName: null, receiverId: receiveInfo.data.userId, msg: null, sendTime: new Date() }
    const info: chatInfo = { action: Actions.SIGNED, chatMsg: msg, expand: null }
    websocketSend(info); // 发送签收消息
}

// 实际使用
// ========================================

// 发送
function sendWebSocket(data: chatInfo) {
    if (websocket.readyState === websocket.OPEN) { // 开启状态
        if (data.action == Actions.CHAT || data.action == Actions.Enter_ChatRoom || data.action == Actions.Leave_ChatRoom) {  // 发送消息
            websocketSend(data);
        }
    } else { // 若 未开启 / 正在开启 状态 ，则等待1s后重新调用
        setTimeout(function () {
            sendWebSocket(data);
        }, 1000);
    }
}

// 关闭
let closeWebSocket = () => {
    websocket.close();
    console.log("关闭连接")
};

function setReceiveInfo(info: any) {
    receiveInfo = info;
}

function setAddMsg(fc: Function) {
    addRoomMsg = fc;
}
function setResizeScreen(fc: Function) {
    resizeScreen = fc;
}
function setUpdateChatList(fc: Function) {
    updateChatList = fc;
}
function setConfirmMsg(fc:Function){
    confirmMsg = fc;
}
function confirmFriendMsg(){
    confirmMsg(receiveInfo)
}

export {
    sendWebSocket,
    createWebSocket,
    closeWebSocket,
    setReceiveInfo,
    setAddMsg,
    setResizeScreen,
    signMsg,
    setUpdateChatList,
    setConfirmMsg,
    confirmFriendMsg
}
