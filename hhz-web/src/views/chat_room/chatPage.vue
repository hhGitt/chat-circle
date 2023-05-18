<template>
  <div class="room_title">
    <el-icon class="back" @click="goChatRoom">
      <ArrowLeft />
    </el-icon>
    <p>{{ choose_info.chatObj == 1 ? chatRoom.data.chatName : friendInfo.data.username }}</p>
    <div class="people_number" v-if="choose_info.chatObj == 1">
      <el-icon color="#1e8e29">
        <Avatar />
      </el-icon>
      <span>{{ chatRoom.data.chatPeople }}</span>
    </div>
  </div>
  <div id="info_main" v-loading="loading" ref="resultScroll">
    <div v-for="item in msgList.data" :class="item.senderId == userId ? 'my_lists' : 'friend_lists'">
      <div class="head_img" @click="showUserInfo(item.senderId)">
        <img :src="item.senderImg">
      </div>
      <div class="msg-wrapper">
        <p class="user_name">{{ item.senderName }}</p>
        <div class="msg">
          <p>{{ item.msg }}</p>
        </div>
      </div>
    </div>
  </div>
  <peopleCard ref="peopleC" :myProp="choose_info" :turnV="turnV" :sideChoose="sideChoose"></peopleCard>
  <div class="message_control">
    <div>
      <el-input v-model="message" placeholder="输入你想发送的信息" @keyup.enter.native="handleWatchEnter" :disabled=loading />
      <el-button type="primary" @click="sendMessage" :disabled=loading>发送</el-button>
    </div>
  </div>
</template>

<script  lang='ts' setup>
import { sendWebSocket, Actions, setAddMsg, setResizeScreen, signMsg, setReceiveInfo, confirmFriendMsg } from '@/utils/websocket';
import { onMounted, reactive, ref, onUnmounted, type Ref } from 'vue';
import type { chatMsg, chatInfo } from '@/utils/websocket';
import { UserStore } from '@/stores';
import { getRoomInfo, getMsg } from '@/request/api/chat';
import { ElMessage } from 'element-plus';
import { SendObj, type chatDateMsg } from '@/request/interface/chat'
import { DateUtils } from '@/utils/DateUtils';
import peopleCard from './peopleCard.vue';
import { getChatUserInfo } from '@/request/api/userInfo';
let chatRoom = reactive({
  data: {
    chatName: "",
    chatPeople: 0
  }
})
const friendInfo = reactive({
  data: {
    username: "",
    imgUrl: "",
    userId: "",
    sendType: 0
  },
})
const msgList = reactive({
  data: [
    {
      id: "",
      msg: "",
      msgId: "",
      receiverId: "",
      sendTime: "",
      senderId: "",
      senderImg: "",
      senderName: ""
    }
  ]
})
const loading = ref(true);
const chatType = ref("");
const chatId = ref("");
const message = ref("");
const userId = UserStore.user_info?.userId;
const userImg = UserStore.user_info?.face_img_url;
const userName = UserStore.user_info?.username;
const currDate = ref(String(new Date()));
const resultScroll: Ref<HTMLDivElement | null> = ref(null)
const isTop = ref(false)
const preMsgId = ref(null)
const props = defineProps({
  myProp: { type: Object, required: true },
  turnV: { type: Object, required: true },
  sideChoose: { type: Object, required: true },
})
const choose_info = props.myProp
const turnV = props.turnV
const sideChoose = props.sideChoose;
const peopleC = ref<{ getOtherUserInfo(str: string): undefined; }>();
// 查看用户信息
const showUserInfo = (id: string) => {
  if (!peopleC.value) return
  peopleC.value.getOtherUserInfo(id)
}

// 发送消息
const sendMessage = () => {
  if (message.value == "") {
    return
  }  
  const msg: chatMsg = { senderId: userId, senderImg: userImg, senderName: userName, receiverId: chatId.value, msg: message.value, sendTime: new Date() }
  const info: chatInfo = { action: Actions.CHAT, chatMsg: msg, expand: null }
  sendWebSocket(info)
  message.value = ""
}

// 监听键盘
const handleWatchEnter = (e: any) => {
  var key = window.event ? e.keyCode : e.which;
  if (key === 13) {
    // 执行发送
    sendMessage();
  }
}

// 返回聊天室选项
const goChatRoom = () => {
  turnV.flag = 1;
}

// 获取房间相关信息
const getInfo = async (chatId: string) => {
  await getRoomInfo(chatId).then((res: any) => {
    if (res.success) {
      chatRoom.data.chatName = res.data.name;
      chatRoom.data.chatPeople = res.data.people;
      friendInfo.data.userId = chatId;
      friendInfo.data.username = res.data.name;
      setReceiveInfo(friendInfo);
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
    loading.value = false;
  })
}

// 获取好友相关信息
const getFriendInfo = async (id: string) => {
  await getChatUserInfo({ userId: id }).then((res: any) => {
    if (res.success) {
      friendInfo.data.username = res.data.username;
      friendInfo.data.imgUrl = res.data.imgUrl;
      friendInfo.data.userId = res.data.userId;
      setReceiveInfo(friendInfo)
      confirmFriendMsg()
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
    loading.value = false;
  })
}

// 获取历史聊天信息
const getMsgs = async () => {
  const data: chatDateMsg = {
    acceptId: chatId.value,
    size: 10,
    date: DateUtils.formateDateTime(currDate.value),
    id: preMsgId.value,
  }
  const obj = choose_info.chatObj == 1 ? SendObj.room : SendObj.people;
  await getMsg(data, obj).then((res: any) => {
    if (res.success) {
      const msgList = res.data;
      if (msgList.length == 0) {
        isTop.value = true;
        console.log("到头了");
      } else {
        for (var i = 0; i < msgList.length; i++) {
          let msg = msgList[i];
          if (i == msgList.length - 1) {
            currDate.value = msg.sendTime;
            preMsgId.value = msg.id;
          }
          addMsg(msg, "up");
        }
      }
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
  })
}

// 在聊天框添加消息
async function addMsg(msg: any, upOrDown: string) {
  if (upOrDown == "up") {
    msgList.data.unshift(msg);
  } else {
    msgList.data.push(msg);
  }
}

//重新调整聊天窗口
async function resizeScreen() {
  //获取聊天列表容器对象
  var areaMsgList = document.getElementById("info_main");
  //设置聊天记录进入页面的时候自动滚动到最后一条
  if (areaMsgList != null) areaMsgList.scrollTop = areaMsgList.scrollHeight + areaMsgList.offsetHeight;
}

// 获取历史消息
const handleScroll = () => {
  if (!resultScroll.value) return
  const st = resultScroll.value.scrollTop; // 滚动条距离顶部的距离
  if (st == 0 && !isTop.value) {
    getMsgs();
  }
}

onMounted(async () => {
  msgList.data = [];
  chatId.value = choose_info.chatId;
  chatType.value = choose_info.curRoomType;
  setAddMsg(addMsg);
  setResizeScreen(resizeScreen);
  if (choose_info.chatObj == 1) {
    friendInfo.data.sendType = 1;
    getInfo(chatId.value);
    sendWebSocket({ action: Actions.Enter_ChatRoom, chatMsg: { senderId: userId, senderImg: null, senderName: null, receiverId: choose_info.chatId, msg: null, sendTime: new Date() }, expand: null })
  } else if (choose_info.chatObj == 2) {
    friendInfo.data.sendType = 2;
    getFriendInfo(chatId.value);
  }
  if (resultScroll.value) {
    resultScroll.value.addEventListener('scroll', handleScroll)
  }
  await getMsgs();
  await resizeScreen();
  signMsg();
});

onUnmounted(() => {
  if (choose_info.chatObj == 1) {
    sendWebSocket({ action: Actions.Leave_ChatRoom, chatMsg: { senderId: userId, senderImg: null, senderName: null, receiverId: choose_info.chatId, msg: null, sendTime: new Date() }, expand: null })
  }
})
</script>

<style lang='less' scoped>
.room_title {
  height: 40px;
  display: flex;
  align-items: center;

  p {
    font-size: 20px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-grow: 1;

  }

  .back {
    width: 5%;
    cursor: pointer;
  }

  .people_number {
    height: 100%;
    width: 5%;
    display: flex;
    align-items: center;
  }
}

.message_control {
  height: 60px;
  position: absolute;
  bottom: 0;
  box-sizing: border-box;
  padding: 10px;
  width: 100%;

  div {
    display: flex;

    .el-input {
      margin-right: 10px;
    }
  }

}


#info_main {
  height: calc(100% - 100px);
  background-color: #F2F2F2;
  box-sizing: border-box;
  overflow: auto;
  padding: 8px;
  padding-top: 0px;

  // 滚动条整体部分
  &::-webkit-scrollbar {
    width: 6px; //对垂直方向滚动条
    height: 6px; //对水平方向滚动条
  }

  //滚动的滑块
  &::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: #ccc //滚动条的颜色
  }

  //内层滚动槽
  &::-webkit-scrollbar-track-piece {
    background-color: #cccccc75;
  }

  .friend_lists {
    margin-top: 8px;
    display: flex;
    flex-direction: row;

    .head_img {
      width: 50px;
      height: 50px;
      margin-right: 5px;

      img {
        width: 50px;
        height: 50px;
        border-radius: 50px;
      }
    }

    .msg-wrapper {
      max-width: 80%;

      .user_name {
        font-size: 15px;
        font-weight: bold;
        height: 28px;
      }

      .msg {
        display: flex;
        flex-direction: row;

        p {
          box-sizing: border-box;
          padding: 8px;
          background-color: #FFF;
          max-width: 100%;
          min-height: 40px;
          border-radius: 5px;
          word-wrap: break-word;
          word-break: break-all;
          display: inline-block;
        }
      }
    }
  }

  .my_lists {
    margin-top: 8px;
    display: flex;
    flex-direction: row-reverse;

    .head_img {
      width: 50px;
      height: 50px;
      margin-left: 5px;

      img {
        width: 50px;
        height: 50px;
        border-radius: 50px;
      }
    }

    .msg-wrapper {
      max-width: 80%;

      .user_name {
        font-size: 15px;
        font-weight: bold;
        height: 28px;
        text-align: right;
      }

      .msg {
        display: flex;
        flex-direction: row-reverse;

        p {
          box-sizing: border-box;
          padding: 8px;
          background-color: #FFF;
          max-width: 100%;
          min-height: 40px;
          border-radius: 5px;
          word-wrap: break-word;
          word-break: break-all;
          display: inline-block;
        }
      }
    }
  }

}
</style>