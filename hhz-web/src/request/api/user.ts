// 导入axios实例
import httpRequest from '../index'
import type { loginInfoParam, registerUserParam, VerificationCodeParam } from '../interface/user';

const BaseAPI = import.meta.env.VITE_API

// 用户登录
export function checkUser(param: loginInfoParam) {
    return httpRequest({
        url: BaseAPI + '/us/user/login',
        method: 'post',
        data: param,
    })
}

// 获取验证码
export function getVerificationCode(param: VerificationCodeParam) {
    return httpRequest({
        url: BaseAPI + '/us/user/verificationCode',
        method: 'post',
        data: param,
    })
}

// 用户注册
export function registerUser(param: registerUserParam) {
    return httpRequest({
        url: BaseAPI + '/us/user',
        method: 'post',
        data: param,
    })
}