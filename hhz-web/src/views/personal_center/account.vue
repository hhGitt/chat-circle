<template>
  <div class="account-main">
    <h3>账号信息</h3>
    <el-divider />
    <div class="t_div">
      <div class="tx_dis">
        <div class="t_title">电子邮箱</div>
        <div class="t_info">{{ info.email }}</div>
      </div>
      <el-button type="primary" style="margin-left: auto;">修改</el-button>
    </div>
    <el-divider />
    <div class="t_div">
      <div class="tx_dis">
        <div class="t_title">手机号</div>
        <div class="t_info">{{ info.phone_number }}</div>
      </div>
      <el-button type="primary" style="margin-left: auto;">修改</el-button>
    </div>
    <el-divider />
    <div class="t_div">
      <div class="tx_dis">
        <div class="t_title">密码</div>
        <div class="t_info">*******</div>
      </div>
      <el-button type="primary" style="margin-left: auto;">修改</el-button>
    </div>
  </div>

</template>

<script  lang='ts' setup>
import { onMounted, reactive } from 'vue';

const info = reactive({
  email: "xxxxxx@qq.com",
  phone_number: "xxxx",
})

onMounted(() => { });
</script>

<style lang='less' scoped>
.account-main {
  background-color: rgb(255, 255, 255);
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 0 #0000, 0 0 #0000, 0px 1px 3px rgba(0, 0, 0, .04), 0px 2px 8px rgba(0, 0, 0, .08);
}

.t_div {
  display: flex;

  .tx_dis {
    display: flex;
    align-items: center;
    width: 80%;


    .t_title {
      width: 15%;
      margin-right: 20px;
    }
  }


}
</style>