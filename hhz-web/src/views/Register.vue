<template>
    <div class="view">
        <canvas id="snow" style="position:absolute;z-index: 0;"></canvas>
        <div class="content">
            <el-button circle @click="toLogin"><el-icon>
                    <ArrowLeft />
                </el-icon></el-button>
            <div class="register">
                <h2>注册</h2>
            </div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" class="demo-ruleForm" :size="formSize"
                status-icon>
                <el-switch class="choice_bt" v-model="choice" inline-prompt active-text="手机号注册" inactive-text="邮箱注册"
                    width="100px" />
                <el-form-item prop="phone_number" v-if="choice">
                    <el-input v-model="ruleForm.phone_number" placeholder="手机号">
                        <template #prepend>+86</template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email" v-if="!choice">
                    <el-input v-model="ruleForm.email" placeholder="邮箱" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="ruleForm.password" placeholder="密码" show-password />
                </el-form-item>
                <el-form-item prop="password2">
                    <el-input v-model="ruleForm.password2" placeholder="确认密码" show-password />
                </el-form-item>
                <el-form-item prop="verification_code">
                    <el-input v-model="ruleForm.verification_code" placeholder="验证码">
                        <template #append>
                            <el-button type="primary" size="default" @click="getCode"
                                v-if="count_down_time === 0">获取验证码</el-button>
                            <el-button type="primary" size="default" @click="" v-if="count_down_time != 0">{{
        count_down_time
}}秒后可重新发送</el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="re_button" type="primary" @click="register(ruleFormRef)">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, onUnmounted, reactive, ref, watch } from "vue";
import { CheckUtils } from "@/utils/checkUtils";
import type { FormInstance, FormRules } from "element-plus";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import snow from "@/utils/snow";
import { getVerificationCode, registerUser } from "@/request/api/user";
import type { registerUserParam ,VerificationCodeParam} from '@/request/interface/user';

const timer = ref<any>(null);
const router = useRouter();
const formSize = ref("large");
const ruleFormRef = ref<FormInstance>();
const choice = ref<boolean>(true);
const count_down_time = ref<number>(0);
interface userForm {
    email: string;
    phone_number: string;
    password: string;
    password2: string;
    verification_code: string;
}
const ruleForm = reactive<userForm>({
    email: "",
    phone_number: "",
    password: "",
    password2: "",
    verification_code: ""
});

const validatePhone = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入手机号'));
    } else if (!CheckUtils.isPhone(value)) {
        callback(new Error('请输入正确手机号'));
    } else {
        callback();
    }
}

const validateEmail = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入邮箱'));
    } else if (!CheckUtils.isEmail(value)) {
        callback(new Error('请输入正确邮箱'));
    } else {
        callback();
    }
}

const validatePass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        if (ruleForm.password !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}

const validatePass2 = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请确认密码'))
    } else if (value !== ruleForm.password) {
        callback(new Error("两次输入密码不一致，请检查!"))
    } else {
        callback()
    }
}

const validateCode = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入验证码'))
    } else {
        callback()
    }
}

const rules = reactive<FormRules>({
    phone_number: [{ validator: validatePhone, trigger: 'blur', required: choice.value }],
    email: [{ validator: validateEmail, trigger: 'blur', required: !choice.value }],
    password: [{ validator: validatePass, trigger: 'blur' }],
    password2: [{ validator: validatePass2, trigger: 'blur' }],
    verification_code: [{ validator: validateCode, trigger: 'blur' }],
});

const getCode = () => {
    const userInfo:VerificationCodeParam = {
        email: ruleForm.email,
        phone_number: ruleForm.phone_number
    }
    count_down_time.value = 60;

    getVerificationCode(userInfo).then((res: any) => {
        if (res.success) {
            ElMessage.success("验证码发送成功,请注意接收");
        } else {
            ElMessage.error(res.message)
        }
    })
}

watch(count_down_time, async (newQuestion, oldQuestion) => {
    if (count_down_time.value > 0) {
        setTimeout(() => {
            if (count_down_time.value > 0) {
                count_down_time.value--;
            }
        }, 1000)
    }
})

const register = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    await formEl.validate(async (valid, fields) => {
        if (valid) {
            const userInfo :registerUserParam = {
                email: ruleForm.email,
                phone_number: ruleForm.phone_number,
                password: ruleForm.password,
                code: ruleForm.verification_code
            };
            registerUser(userInfo).then((res: any) => {
                if (res.success) {
                    ElMessage.success("注册成功");
                    toLogin();
                } else {
                    ElMessage.error(res.message)
                }
            });
        }
    });
};

const toLogin = () => {
    router.push({ name: "login" });
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

<style lang='less' scoped>
.view {
    background-color: #293c55;
    // background: url("../assets/snow.png") no-repeat;
    background-size: cover;
    height: 100vh;
    display: flex;
}

.content {
    margin: auto;
    width: 500px;
    padding: 20px 40px 40px 40px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 2px 3px 7px rgb(0 0 0 / 20%);
    justify-content: center;
    align-items: center;

    .register {
        margin-bottom: 20px;
        text-align: center;
    }
}

.re_button {
    width: 100%;
    margin: auto;
}

.choice_bt {
    --el-switch-on-color: #3bcf7e;
    --el-switch-off-color: #3d69fa;
}
</style>