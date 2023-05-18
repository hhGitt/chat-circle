<template>
  <div class="list_title">
    <p>{{ choose_info.TypeName }}{{ '(' + curTotal + '个)' }}</p>
    <div class="c_list_tool">
      <el-input v-model="input" size="small" placeholder="输入查找的房间" :prefix-icon="Search"
        @keyup.enter.native="handleWatchEnter" />
      <el-icon size="24" title="添加房间" @click="ElMessage.info('未开放')">
        <Edit />
      </el-icon>
    </div>
  </div>
  <div class="list_card" ref="resultScroll" v-loading="loading">
    <div class="c_card" v-for="item in chat_cards.data">
      <div class="c_card_1">
        <div class="m-10">
          <el-avatar :size="80" :src="item.roomImgUrl" shape="square">
            <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
          </el-avatar>
        </div>
        <div>
          <p>{{ item.name }}</p>
          <div>
            <el-icon color="#1e8e29">
              <Avatar />
            </el-icon>{{ item.people }}
          </div>
        </div>
        <div style="position:relative">
          <el-button type="info" circle @click="goChat(item.id)">进入
          </el-button>
          <div class="num_c">{{ item.nums >= 100 ? "99+" : item.nums }}</div>
        </div>
      </div>
      <div class="c_card_2">
        <p>
          {{ item.brief }}
        </p>
      </div>
    </div>
  </div>
</template>

<script  lang='ts' setup>
import { onMounted, reactive, ref, type Ref } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { getHotRoom, getRoomByType } from '@/request/api/chat';
import { ElMessage } from 'element-plus';

const input = ref('')
const props = defineProps({
  myProp: { type: Object, required: true },
  turnV: { type: Object, required: true }
})
const choose_info = props.myProp
const turnV = props.turnV
const allPage = ref(0)
const curPage = ref(0)
const curSize = ref(10)
const curTotal = ref(0)
const selectName = ref("")
const resultScroll: Ref<HTMLDivElement | null> = ref(null)
const loading = ref(false)

let chat_cards = reactive({
  data: [
    {
      id: "",  // 房间id
      name: "",   // 房间名称
      brief: "",   // 房间简介
      typeId: "",   // 房间类型id
      roomImgUrl: "",  // 图片url
      people: "",  // 在线人数
      nums: 0, // 未接收消息条数
    }
  ]
})

const goChat = (Id: string) => {
  choose_info.chatId = Id;
  turnV.flag = 2;
  choose_info.chatObj = 1;
}

// 监听键盘
const handleWatchEnter = (e: any) => {
  var key = window.event ? e.keyCode : e.which;
  if (key === 13) {
    chat_cards.data = [];
    curPage.value = 0;
    selectName.value = input.value;
    getChatRoom();
  }
}

// 获取热门
const setHotChatCards = async () => {
  await getHotRoom().then((res: any) => {
    if (res.success) {
      chat_cards.data = res.data;
      curTotal.value = res.data.length;
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
  })
}

// 获取相关类型房间
const getChatRoom = async () => {
  console.log({ page: curPage.value + 1, size: curSize.value, roomType: choose_info.curRoomType, roomName: selectName.value })
  await getRoomByType({ page: curPage.value + 1, size: curSize.value, roomType: choose_info.curRoomType, roomName: selectName.value }).then((res: any) => {
    loading.value = true;
    if (res.success) {
      for (let i = 0; i < res.data.records.length; i++) {
        chat_cards.data.push(res.data.records[i]);
      }
      curTotal.value = res.data.total;
      curPage.value = res.data.current;
      allPage.value = res.data.pages;
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
    loading.value = false;
  })
}

// 监听页面滚动条
const handleScroll = () => {
  if (!resultScroll.value) return
  const sh = resultScroll.value.scrollHeight // 滚动条高度
  const st = resultScroll.value.scrollTop // 滚动条距离顶部的距离
  const ch = resultScroll.value.clientHeight // 滚动条外容器的高度

  if (st + ch >= sh && choose_info.curRoomType != "0") {
    //到底了-业务逻辑
    if (curPage.value + 1 <= allPage.value) {
      getChatRoom()
    } else {
      console.log("到底了")
    }
  }
}

// 改变类型
const changeTypeInfo = () => {
  chat_cards.data = [];
  clearPage();
  if (choose_info.curRoomType == '0') {
    setHotChatCards();
  } else {
    getChatRoom();
  }
}

// 重置页面数据
const clearPage = () => {
  allPage.value = 0;
  curPage.value = 0;
  curSize.value = 10;
  curTotal.value = 0;
  selectName.value = "";
  input.value = "";
  choose_info.chatId = "";
}

defineExpose({ changeTypeInfo })
onMounted(() => {
  clearPage();
  changeTypeInfo();
  if (resultScroll.value) {
    resultScroll.value.addEventListener('scroll', handleScroll)
  }
});
</script>

<style lang='less' scoped>
.list_title {
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;

  p {
    font-size: 20px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-grow: 1;
    margin-left: 204px;
  }


  .c_list_tool {
    width: 204px;
    flex-grow: 0;
    display: flex;
    align-items: center;

    .el-icon {
      width: 24px;
      height: 24px;
      margin-right: 20px;
      margin-left: 10px;
    }

    .el-input {
      width: 150px;
      height: 24px;
    }

  }
}

.list_card {
  width: 100%;
  height: 760px;
  padding: 15px 30px;
  box-sizing: border-box;
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
  align-content: flex-start;

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


  .c_card {
    width: 340px;
    height: 180px;
    background-color: #F2F2F2;
    border-radius: 10px;
    margin: 15px 50px;

    .c_card_1 {
      display: flex;
      align-items: center;

      div:nth-child(1) {
        width: 80px;
      }

      div:nth-child(2) {
        width: 180px;
        height: 80px;
        flex-direction: row;

        p {
          width: 100%;
          font-size: 18px;
          font-weight: bold;
          color: #333333;
        }

        div {
          height: 40px;
          display: flex;
          align-items: center;
        }
      }

      .el-button {
        width: 40px;
        height: 40px;
        margin: 0 10px;
      }

      .num_c {
        border-radius: 50px;
        width: 20px !important;
        height: 20px !important;
        background-color: #D7D7D7;
        color: #7F7F7F;
        position: absolute;
        top: 0;
        right: 0;
        text-align: center;
        line-height: 20px;
        font-size: 12px;
      }
    }

    .c_card_2 {
      width: 320px;
      height: 66px;
      margin: 0 10px;
      box-sizing: border-box;
      background-color: #ffffffb7;
      border-radius: 5px;
      padding: 5px;
      overflow: auto;


      p {
        word-wrap: break-word;
        word-break: break-all;
        font-size: 14px;
        color: #333333;
      }

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
    }
  }
}
</style>