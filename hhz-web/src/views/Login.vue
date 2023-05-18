<template>
  <div class="view">
    <canvas id="snow"></canvas>
    <div class="content">
      <div class="login">
        <!-- <img class="logo" src="@/assets/logo.svg" alt="" /> -->
        <h2>登录</h2>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" class="demo-ruleForm" :size="formSize" status-icon>
        <el-form-item prop="name">
          <el-input v-model="ruleForm.account" placeholder="手机号/邮箱">
            <template #prefix>
              <el-icon class="el-input__icon">
                <user />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="nm">
          <el-input v-model="ruleForm.password" placeholder="密码" show-password @keyup.enter.native="login(ruleFormRef)">
            <template #prefix>
              <el-icon class="el-input__icon">
                <lock />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="register" class="register_t">
          <a @click.prevent="toRegister()">注册</a>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login(ruleFormRef)">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onUnmounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { useRouter } from "vue-router";
import { UserStore } from "@/stores";
import { ElMessage } from "element-plus";
import snow from "@/utils/snow";
import { checkUser } from "@/request/api/user";
import type { loginInfoParam } from "@/request/interface/user";
const timer = ref<any>(null);
const router = useRouter();
const formSize = ref("large");
const ruleFormRef = ref<FormInstance>();
interface userForm {
  account: string;
  password: string;
}
const ruleForm = reactive<userForm>({
  account: "",
  password: "",
});
const rules = reactive<FormRules>({
  account: [{ required: true, message: "请输入账号", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});
const login = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      const userInfo: loginInfoParam = {
        account: ruleForm.account,
        password: ruleForm.password
      };
      checkUser(userInfo).then((res: any) => {
        if (res.success) {
          ElMessage.success("登录成功");
          UserStore.setToken(res.data.token);
          UserStore.setUserInfos({ userId: res.data.userId, username: res.data.username, face_img_url: res.data.faceImg, signature: res.data.signature })
          router.push({ path: "/", });
        } else {
          ElMessage.error("账号密码有误,请重新输入")
        }
      });
    }
  });
};


const toRegister = () => {
  router.push({ path: "/register", name: 'register' });
}

onMounted(() => {
  //使用雪花飘落背景
  timer.value = snow().timer;
});
//组件销毁时 关闭,清楚定时器
onUnmounted(() => {
  clearInterval(timer.value);
  timer.value = null;
});
</script>

<style lang="less" scoped>
.view {
  background-color: #293c55;
  // background: url("../assets/snow.png") no-repeat;
  background-size: cover;
  height: 100vh;
}

.content {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -200px;
  margin-top: -200px;
  width: 320px;
  padding: 40px 40px 30px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 2px 3px 7px rgb(0 0 0 / 20%);

  .login {
    margin-bottom: 20px;
    display: flex;
    justify-content: center;
    align-items: center;

    .logo {
      width: 50px;
      margin-right: 10px;
    }
  }

  .register_t {
    margin: 0;

    a {
      margin-left: auto;
      color: #000;
      cursor: pointer;
    }
  }
}
</style>
