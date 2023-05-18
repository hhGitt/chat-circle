<template>
  <el-menu class="el-menu-demo" mode="horizontal" :ellipsis="false">
    <el-menu-item index="0" style="margin-left:10%" @click="goIndex">首页</el-menu-item>
    <div class="flex-grow" />
    <el-menu-item index="1" @click="chatRoom">聊天室</el-menu-item>
    <el-sub-menu index="2" style="margin-right:10%">
      <template #title>
        <el-avatar :size="40" :src="UserStore.user_info?.face_img_url"
          @error="errorHandler">
          <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
        </el-avatar>
      </template>
      <h2 class="pl10 pr10">{{UserStore.user_info?.username }}</h2>
      <p class="signature">{{ UserStore.user_info?.signature }}</p>
      <el-divider class="nm mt10 mb10"/>
      <el-menu-item v-for="item in dropdownList" :key="item.value" :command="item.value" :index="item.index"
        @click="handleCommand(item.value)">{{ item.label }}</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
import { ref, getCurrentInstance, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
import { UserStore } from "@/stores";
const errorHandler = () => true

const router = useRouter();
const dropdownList = reactive<any>([
  {
    label: "个人中心",
    value: "personalCenter",
    index: "2-1"
  },
  {
    label: "退出登录",
    value: "logout",
    index: "2-2"
  },
]);
const chatRoom = () => {
  router.replace({ path: "/chat" });
}

const logout = () => {
  UserStore.cleAll();
  router.replace({ path: "/login" });
  ElMessage({ type: "success", message: "退出成功", });
};

const goIndex = ()=>{
  router.replace({ path: "/" });
}

const handleCommand = (command: string | number | object) => {
  switch (command) {
    case "logout":
      ElMessageBox.confirm("确认退出吗？", "Warning", {
        type: "warning",
      })
        .then(() => {
          logout();
        })
        .catch(() => { });
      break;
    case "personalCenter":
      router.push({ path: "/personalCenter" })
      break;
    default:
      break;
  }
};

onMounted(() => {
});
</script>

<style lang="less" scoped>
.header {
  box-sizing: border-box;
  height: 60px;
  box-shadow: 0 2px 8px rgb(0 0 0 / 15%);
  line-height: 60px;
  
  .expand {
    width: 60px;
    text-align: center;
  }

  .item_style {
    float: right;
    height: 60px;
    text-align: center;
    line-height: 60px;
    text-align: center;
    cursor: pointer;
  }
}

.el-dropdown {
  line-height: 60px;
}

.flex-grow {
  flex-grow: 1;
}

.signature{
  padding: 10px 10px 0 10px;
  overflow:hidden;
  text-overflow:ellipsis; /* 加省略号 */
  white-space:nowrap; /* 强制不换行 */
}
</style>

