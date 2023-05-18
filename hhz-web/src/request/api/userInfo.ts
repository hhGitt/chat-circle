// 导入axios实例
import httpRequest from '../index'
import { UserStore } from "@/stores";
import type { chatUserInfo, userImage, userInfoParam } from '../interface/userInfo';

const BaseAPI = import.meta.env.VITE_API

// 获取用户信息
export function getUserInfo() {
    return httpRequest({
        url: BaseAPI + '/us/userinfo',
        method: 'get',
        headers: {
            'token': UserStore.token,
        }
    })
}

// 上传头像
export function putFaceImage(param: userImage) {
    return httpRequest({
        url: BaseAPI + '/us/userinfo/uploadFaceBase64',
        method: 'post',
        headers: {
            'token': UserStore.token,
        },
        data: param
    })
}

// 获取用户头像
export function getBigHeadPortrait() {
    return httpRequest({
        url: BaseAPI + '/us/userinfo/bigHeadPortrait',
        method: 'get',
        headers: {
            'token': UserStore.token,
        }
    })
}

// 获取地区列表
export function getRegionList() {
    return httpRequest({
        url: BaseAPI + '/us/userinfo/region',
        method: 'get',
        headers: {
            'token': UserStore.token,
        }
    })
}

// 更新用户信息
export function updateUserInfo(param: userInfoParam) {
    return httpRequest({
        url: BaseAPI + '/us/userinfo',
        method: 'post',
        headers: {
            'token': UserStore.token,
        },
        data: param
    })
}

// 获取聊天用户信息
export function getChatUserInfo(param: chatUserInfo){
    return httpRequest({
        url: `${BaseAPI}/us/userinfo/ou/${param.userId}`,
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}