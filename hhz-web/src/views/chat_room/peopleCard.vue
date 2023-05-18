<template>
  <el-dialog v-model="dialogVisible" width="25%" align-center :modal="false">
    <div class="user_info">
      <el-avatar :size="80" :src="cardData.data.imgUrl" shape="square">
        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
      </el-avatar>
      <div class="user_i">
        <div style="display: flex; flex-direction: row;">
          <span :title="cardData.data.username">{{ cardData.data.username }}</span>
          <p style="padding:0 5px">{{ getYear2(cardData.data.birthday) }}后</p>
          <div class="sex_ic" v-if="cardData.data.sex == 2" title="女">
            <svg t="1681181597749" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="2684" width="200" height="200">
              <path
                d="M519.658935 673.068396c81.129762 0 157.397796-31.572066 214.762295-88.937588 118.423133-118.421087 118.423133-311.085084 0-429.507194C677.057755 97.259114 600.788697 65.668629 519.658935 65.668629c-81.128739 0-157.39882 31.590486-214.744899 88.954985-118.402667 118.42211-118.402667 311.086108 0 429.507194C362.279558 641.496329 438.548616 673.068396 519.658935 673.068396zM346.474594 196.183148c46.267766-46.266743 107.764376-71.734788 173.184341-71.734788 65.419965 0 126.937041 25.468045 173.203784 71.734788 95.499028 95.500051 95.499028 250.889097 0 346.389148-46.266743 46.245254-107.765399 71.713298-173.203784 71.713298-65.420989 0-126.916575-25.468045-173.184341-71.713298C250.995009 447.072245 250.995009 291.682175 346.474594 196.183148z"
                p-id="2685" fill="#e89abe"></path>
              <path
                d="M793.986861 800.462854 549.061592 800.462854l0.008186-71.2743c0-16.22452-13.164834-29.389354-29.391401-29.389354s-29.389354 13.163811-29.389354 29.389354l-0.007163 71.2743L245.349429 800.462854c-16.225543 0-29.389354 13.163811-29.389354 29.390377s13.163811 29.392424 29.389354 29.392424l244.925269 0-0.00614 71.236438c0 16.22452 13.163811 29.389354 29.390377 29.389354s29.391401-13.163811 29.391401-29.389354l0.007163-71.236438L793.986861 859.245655c16.225543 0 29.391401-13.165858 29.391401-29.392424S810.213427 800.462854 793.986861 800.462854z"
                p-id="2686" fill="#e89abe"></path>
            </svg>
          </div>
          <div class="sex_ic" v-else-if="cardData.data.sex == 1" title="男">
            <svg t="1681182119778" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="3363" width="200" height="200">
              <path
                d="M394.927119 361.941356c-65.98176 0-128.025838 25.692149-174.679391 72.346725-96.296183 96.311533-96.296183 253.015527 0.01535 349.310687 46.653553 46.654576 108.682281 72.332399 174.647669 72.332399 65.965387 0 127.994116-25.693172 174.664042-72.346725 96.29516-96.296183 96.29516-252.984828 0-349.295338C522.936585 387.634528 460.90888 361.941356 394.927119 361.941356zM535.774985 749.783615c-37.627992 37.627992-87.658456 58.341756-140.863215 58.341756-53.204759 0-103.220896-20.712741-140.847865-58.325383-77.667917-77.667917-77.667917-204.043163-0.016373-281.712103 37.627992-37.619806 87.659479-58.33971 140.879588-58.33971 53.204759 0 103.219873 20.719904 140.847865 58.33971C613.427552 545.755802 613.427552 672.131047 535.774985 749.783615z"
                p-id="3364" fill="#1296db"></path>
              <path
                d="M868.171444 135.676076c-4.482081-4.481058-10.565636-7.002484-16.899902-7.002484l-329.066621 0.01535c-13.196557 0-23.902386 10.706853-23.902386 23.902386 0 13.20372 10.706853 23.902386 23.902386 23.902386l305.164235-0.016373 0 305.179584c0 13.204743 10.705829 23.902386 23.902386 23.902386 13.196557 0 23.902386-10.698666 23.902386-23.902386L875.173928 152.575977C875.174951 146.234549 872.653525 140.158157 868.171444 135.676076z"
                p-id="3365" fill="#1296db"></path>
            </svg>
          </div>
          <div class="sex_ic" v-else title="保密">
            <svg t="1681182241776" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="3559" width="200" height="200">
              <path
                d="M545.18272 342.68672a234.21952 234.21952 0 1 0-275.79904 231.37792v300.0832a35.13344 35.13344 0 1 0 70.26688 0V576a235.13088 235.13088 0 0 0 205.53216-233.31328zM310.9632 507.29984a164.61824 164.61824 0 1 1 163.95264-164.61312 164.46976 164.46976 0 0 1-163.95264 164.61312z m434.47808-59.08992V165.14048a35.13344 35.13344 0 1 0-70.26688 0v284.48256a234.24512 234.24512 0 1 0 70.26688-1.41312z m-30.44864 397.72672a164.61312 164.61312 0 1 1 163.95776-164.61824 164.46464 164.46464 0 0 1-163.95776 164.61312z"
                fill="#8a8a8a" p-id="3560"></path>
              <path
                d="M502.2464 724.51072h-395.4176a35.28704 35.28704 0 0 1 0-70.56896h395.4176a35.28704 35.28704 0 0 1 0 70.56896z m420.01408-328.13568a34.95424 34.95424 0 0 1-24.42752-9.92256L710.10816 204.2368l-172.3904 174.08a35.21536 35.21536 0 1 1-49.85344-49.75104l187.008-188.84096a49.3312 49.3312 0 0 1 69.21728-0.67584l202.5984 196.67456a35.38432 35.38432 0 0 1 0.83968 49.88928 34.98496 34.98496 0 0 1-25.2672 10.76224z"
                fill="#8a8a8a" p-id="3561"></path>
            </svg>
          </div>
        </div>
        <div style="padding: 5px 0;">
          <el-cascader v-model="region" :options="regions" :props="prop_cascader" disabled />
        </div>
        <div class="user_signature">
          <p>{{ cardData.data.signature }}</p>
        </div>
      </div>
    </div>
    <template #footer v-if="cardData.data.userId != userId">
      <span class="dialog-footer">
        <el-button v-if="!cardData.data.friendIs" @click="addFriendRequest(cardData.data.userId)">加好友</el-button>
        <el-button v-else type="primary" @click="chatToFriend(cardData.data.userId)">发消息</el-button>
      </span>
    </template>
  </el-dialog>
</template>
    
<script lang='ts' setup>
import { onMounted, reactive, ref } from 'vue';
import { getChatUserInfo, getRegionList } from '@/request/api/userInfo';
import { sendFriendRequest } from '@/request/api/friend'
import { ElMessage } from 'element-plus';
import { UserStore } from '@/stores';
const cardData = reactive({
  data: {
    userId: "",
    username: "",
    imgUrl: "",
    signature: "",
    sex: 0,
    birthday: "",
    regionId: "",
    friendIs: false,
  }
})
let regions = ref([]);
let region: Array<string> = reactive([]);
interface regionInter {
  value: string,
  label: string,
  children: Array<regionInter>
}
const prop_cascader = {
  expandTrigger: 'hover' as const,
}
const props = defineProps({
  myProp: { type: Object, required: true },
  turnV: { type: Object, required: true },
  sideChoose: { type: Object, required: true },
})
const choose_info = props.myProp;
const turnV = props.turnV;
const sideChoose = props.sideChoose;
const dialogVisible = ref(false);
const userId = UserStore.user_info?.userId;

// 判断几0后
const getYear2 = (str: string) => {
  let date = new Date(str);
  const year = date.getFullYear();
  return String(year % 100)[0] + "0";
}

// 获取所在地区id列表
const showRegion = (regionId: string): Array<string> => {
  var lis: Array<string> = Array();
  for (var i in regions.value) {
    var r: regionInter = regions.value[i];
    if (r.value == regionId) return [regionId];
    lis.push(r.value);
    var flag = dfs(r.children, regionId, lis);
    if (flag) break;
    lis.pop();
  }
  return reactive(lis);
}

const dfs = (region: any, target: string, lis: Array<string>): boolean => {
  for (var i in region) {
    if (region[i].value == target) {
      lis.push(region[i].value)
      return true;
    }
    lis.push(region[i].value);
    var flag = dfs(region[i].children, target, lis)
    if (flag) return true;
    lis.pop();
  }
  return false;
}

// 获取地区相关信息
const getRegions = async () => {
  await getRegionList().then((res: any) => {
    if (res.success) {
      regions.value = res.data;
    }
  })
}

// 获取用户相关信息
const getOUInfo = async (id: string) => {
  await getChatUserInfo({ userId: id }).then((res: any) => {
    if (res.success) {
      cardData.data = res.data;
      region = showRegion(cardData.data.regionId);
    }
    else {
      ElMessage.error("获取失败，请稍后重试")
    }
  })
}

// 添加好友
const addFriendRequest = async (id: string) => {
  await sendFriendRequest(id).then((res: any) => {
    if (res.success) {
      ElMessage.success("发送成功")
    }
    else {
      ElMessage.warning(res.message)
    }
  })
}

// 查看其它用户信息
const getOtherUserInfo = async (id: string) => {
  dialogVisible.value = true;
  if (regions.value.length == 0) {
    await getRegions();
  }
  await getOUInfo(id);
}

// 清空页面
const clearV = async () => {
  turnV.flag = 0;
}

// 转到好友聊天页面
const changeV = async (id:string) => {
  turnV.flag = 2;
  choose_info.chatId = id;
  choose_info.chatObj = 2;
  dialogVisible.value = false;
}

// 与好友聊天
const chatToFriend = async(id: string) => {
  await clearV();
  await changeV(id);
  sideChoose.choose = 0;
}

defineExpose({ getOtherUserInfo })
onMounted(() => {

});
</script>
    
<style lang='less' scoped>
.user_info {
  display: flex;
  flex-direction: row;
  height: 110px;

  .user_i {
    width: 80%;
    height: 80px;
    margin-left: 20px;

    :deep(.el-cascader) {
      width: 70% !important;
    }
  }

  .sex_ic {
    height: 20px;
    width: 20px;
    box-sizing: border-box;
    padding: 2px;

    svg {
      width: 100%;
      height: 100%;
    }
  }

  span {
    display: block;
    font-size: 15px;
    font-weight: bold;
    max-width: 60%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .user_signature {
    height: 80%;
    box-sizing: border-box;
    background-color: #f2f2f2;
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
</style>
