<template>
  <div class="common-header">
    <el-header>
      <Header></Header>
    </el-header>
  </div>
  <div class="common-layout">
    <el-container>
      <el-main>
        <router-view v-slot="{ Component, route }">
          <transition appear name="fade-transform" mode="out-in">
            <component :is="Component" :key="route.path" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </div>
</template>
  
<script lang="ts" setup>
import { RouterView } from "vue-router";
import Header from "./header/index.vue";

</script>



<style lang="less" scoped>
.common-header {
  box-sizing: border-box;
  width: calc(99.3vw);
}

.el-aside {
  width: auto;
}

.common-layout {
  height: 100vh !important;
  padding-left: calc(100vw - 100%);
  box-sizing: border-box;
}

.el-header {
  padding: 0 !important;
}

.el-main {
  padding: 0 !important;
  background-color: rgb(247, 248, 250);
}
</style>