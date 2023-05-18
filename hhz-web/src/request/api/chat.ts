import httpRequest from '../index'
import type { SendObj, chatDateMsg, chatTypeParam } from '../interface/chat'
import { UserStore } from "@/stores";

const BaseAPI = import.meta.env.VITE_API

// 获取房间类型
export function getChatType() {
    return httpRequest({
        url: BaseAPI + '/cs/chat',
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 获取热门房间号
export function getHotRoom() {
    return httpRequest({
        url: BaseAPI + '/cs/chat/host',
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 获取指定类型房间号(分页)
export function getRoomByType(param: chatTypeParam) {
    if(param.roomName == ""){
        return httpRequest({
            url: `${BaseAPI}/cs/chat/${param.roomType}/${param.page}/${param.size}`,
            method: 'get',
            headers: {
                'token': UserStore.token,
            },
        })
    }else{
        return httpRequest({
            url: `${BaseAPI}/cs/chat/${param.roomType}/${param.roomName}/${param.page}/${param.size}`,
            method: 'get',
            headers: {
                'token': UserStore.token,
            },
        })
    }

}

// 获取指定房间信息
export function getRoomInfo(roomId: string) {
    return httpRequest({
        url: `${BaseAPI}/cs/chatroom/${roomId}`,
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 获取日期前的消息
export function getMsg(param: chatDateMsg,sendObj:SendObj) {    
    return httpRequest({
        url: `${BaseAPI}/cs/chatmsg/${sendObj}`,
        method: 'post',
        data: param,
        headers: {
            'token': UserStore.token,
        },
    })
}

// 获取聊天列表里的信息
export function getFriendChatList(){
    return httpRequest({
        url: `${BaseAPI}/cs/chatmsg/chatList`,
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}