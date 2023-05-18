<template>
  <div class="chat_main">
    <div class="chose_title1">
      <div :class="choose_info.curRoomType == '0' ? 'active' : ''" @click="changeType('0', '热门')">热门</div>
      <!-- <div>收藏</div> -->
      <div :class="choose_info.curRoomType == item.id ? 'active' : ''" v-for="item in title_list.data"
        @click="changeType(item.id, item.name)">{{
          item.name }}</div>
    </div>
    <div class="chat_box">
      <div class="side_lis">
        <div class="chose_lis">
          <div @click="sideChoose.choose = 0" :class="sideChoose.choose == 0 ? 'active_chose' : ''">
            <el-icon>
              <p class="tip_msg" v-if="haveMsg.flag"></p>
              <ChatLineSquare />
            </el-icon>
            <span>聊天</span>
          </div>
          <div @click="sideChoose.choose = 1" :class="sideChoose.choose == 1 ? 'active_chose' : ''">
            <el-icon>
              <Connection />
            </el-icon>
            <span>好友</span>
          </div>
        </div>
        <msgList v-if="sideChoose.choose == 0" :chatList="chat_list" :myProp="choose_info" :turnV="lisOrChat"></msgList>
        <friendList v-else-if="sideChoose.choose == 1" :myProp="choose_info" :turnV="lisOrChat" :sideChoose="sideChoose">
        </friendList>
      </div>
      <div class="chose_info">
        <chatList v-if="lisOrChat.flag == 1" :myProp="choose_info" :turnV="lisOrChat" ref="chatListMethod"></chatList>
        <chatPage v-else-if="lisOrChat.flag == 2" :myProp="choose_info" :turnV="lisOrChat" :sideChoose="sideChoose">
        </chatPage>
      </div>
    </div>
  </div>
</template>

<script  lang='ts' setup>
import { onMounted, reactive, ref, onUnmounted, watch } from 'vue';
import { getChatType, getFriendChatList } from '@/request/api/chat';
import { ElMessage } from "element-plus";
import msgList from './msgList.vue';
import friendList from './friendList.vue';
import chatList from './chatList.vue';
import chatPage from './chatPage.vue';
import { createWebSocket, closeWebSocket, setUpdateChatList, setConfirmMsg } from '@/utils/websocket';
import type { chatMsg } from '@/utils/websocket';
import { FriendStore, UserStore } from '@/stores';
const userId = UserStore.user_info?.userId
const chatListMethod = ref<{ changeTypeInfo(): any; }>();
const haveMsg = reactive({ flag: false })
const sideChoose = ref({ choose: 0 });
const lisOrChat = ref({ flag: 1 }); // 1：聊天列表，2：聊天页面
const choose_info = ref({
  curRoomType: "0", // 房间类型
  TypeName: "",  // 类型名称
  chatId: "",  // 聊天室id
  chatObj: 0 // 聊天对象（1：聊天室，2：好友）
})
let chat_list = reactive({
  data: [
    {
      name: "",
      imgUrl: "",
      id: "",
      msg: "",
      time: "",
      msgNumber: 0
    }
  ]
})
let title_list = reactive({
  data:
    [
      {
        "id": "0",
        "name": "test"
      }
    ]
});


watch(() => {
  return chat_list.data.map(item => item.msgNumber).reduce((sum, value) => sum + value, 0);
}, (newValue) => {
  haveMsg.flag = newValue !== 0;
});

// 获取聊天室类型列表
const setTitleList = async () => {
  await getChatType().then((res: any) => {
    if (res.success) {
      title_list.data = res.data
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
  })
}

// 更改选择的类型
const changeType = (typeId: string, name: string) => {
  choose_info.value.TypeName = name;
  choose_info.value.curRoomType = typeId;
  choose_info.value.chatObj = 0;
  lisOrChat.value.flag = 1;
  if (!chatListMethod.value) return
  chatListMethod.value.changeTypeInfo()
}

// 从缓存获取聊天列表并获取相关信息
const setChatList = async () => {
  await getFriendChatList().then((res: any) => {
    if (res.success) {
      let array1 = res.data;
      let array2 = FriendStore.friendChats;
      console.log(array2);
      if (array1.length == 0 && array2.length == 0) {
        return
      } else if (array1.length == 0) {
        chat_list.data = array2;
        FriendStore.setFriendChats(array2)
      } else if (array2.length == 0) {
        chat_list.data = array1;
        FriendStore.setFriendChats(array1)
      }
      else {
        const ids1 = array1.map((obj: { id: any; }) => obj.id);
        const ids2 = array2.map((obj: { id: any; }) => obj.id);
        const intersectionIds = ids1.filter((id: any) => ids2.includes(id));
        const result = array1.filter((obj: { id: any; }) => intersectionIds.includes(obj.id));
        chat_list.data = result;
        FriendStore.setFriendChats(result)
      }
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
  })
}

// 更新聊天列表消息
const updateChatList = (chatMsg: chatMsg, receiveInfo: any, flag: boolean) => {
  const id = (chatMsg.senderId == userId ? chatMsg.receiverId : chatMsg.senderId);
  const index = chat_list.data.findIndex((obj: { id: string; }) => obj.id === id);
  if (index == -1) {
    let updatedObj;
    if (chatMsg.senderId == userId) {
      updatedObj = {
        name: receiveInfo.data.username,
        imgUrl: receiveInfo.data.imgUrl,
        id: receiveInfo.data.userId,
        msg: chatMsg.msg == null ? "" : chatMsg.msg,
        time: String(new Date(chatMsg.sendTime)),
        msgNumber: 0
      };
    } else {
      updatedObj = {
        name: chatMsg.senderName,
        imgUrl: chatMsg.senderImg,
        id: chatMsg.senderId,
        msg: chatMsg.msg == null ? "" : chatMsg.msg,
        time: String(new Date(chatMsg.sendTime)),
        msgNumber: 1
      };
    }
    if (updatedObj != null) chat_list.data.unshift(updatedObj)
  } else {
    let tmp = chat_list.data[index]
    tmp.msg = chatMsg.msg == null ? "" : chatMsg.msg;
    tmp.time = String(new Date(chatMsg.sendTime));
    if (chatMsg.senderId == userId || flag) tmp.msgNumber = 0;
    else tmp.msgNumber++;
    chat_list.data.splice(index, 1);
    chat_list.data.unshift(tmp);
  }
  FriendStore.setFriendChats(chat_list)
}

// 确认消息接收
const confirmMsg = (receiveInfo: any) => {
  const index = chat_list.data.findIndex((obj: { id: string; }) => obj.id === receiveInfo.data.userId);
  if (index != -1) {
    chat_list.data[index].msgNumber = 0
  }
}

onMounted(() => {
  title_list.data = [];
  chat_list.data = [];
  setTitleList();
  setChatList();
  setUpdateChatList(updateChatList);
  setConfirmMsg(confirmMsg);
  createWebSocket();
});

onUnmounted(() => {
  closeWebSocket()
})
</script>

<style lang='less' scoped>
.chat_main {
  width: 1200px;
  min-height: 800px;
  margin: auto;
  padding-top: 20px;
  padding-bottom: 100px;

  .chose_title1 {
    width: 100%;
    height: 150px;
    background-color: #fff;
    padding: 10px;
    display: flex;
    flex-wrap: wrap;
    box-sizing: border-box;
    border-bottom: 1px solid #cfcfcf;

    div {
      width: 150px;
      height: 50px;
      font-size: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 10px 9px;
      background-color: #F2F2F2;
      border-radius: 5px;
      color: #555555;
      font-weight: bold;
      cursor: pointer;
    }

    :hover {
      color: #FFFFFF;
      background-color: #555555;
    }

    .active {
      color: #FFFFFF;
      background-color: #555555;
    }
  }


  .chat_box {
    display: flex;
    margin: auto;
    width: 100%;
    box-sizing: border-box;
    height: 800px;

    .side_lis {
      width: 20%;
      background-color: #fff;
      border-right: 1px solid #cfcfcf;

      .chose_lis {
        height: 40px;
        display: flex;
        justify-content: space-around;

        div {
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;

          span {
            margin: 0 5px;
            color: #444444;
            position: relative;
          }

        }

        .tip_msg {
          width: 8px;
          height: 8px;
          background-color: rgb(219, 88, 88);
          border-radius: 50px;
          position: absolute;
          right: auto;
          right: 0;
          top: 0;
        }

        :hover {
          color: #0650c0;

          span {
            color: #0650c0;
          }
        }

        .active_chose {
          color: #0650c0;

          span {
            color: #0650c0;
          }
        }
      }


    }



    .chose_info {
      width: 80%;
      background-color: #fff;
      position: relative;
    }
  }


}
</style>