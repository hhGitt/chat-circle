// 导入axios实例
import httpRequest from '../index'
import { UserStore } from "@/stores";
const BaseAPI = import.meta.env.VITE_API

// 查看用户列表
export function getFriendList() {
    return httpRequest({
        url: BaseAPI + '/us/friend/lis',
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 添加好友
export function sendFriendRequest(id:string) {
    return httpRequest({
        url: BaseAPI + `/us/friend/${id}`,
        method: 'post',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 对好友请求做出行动
export function actionFriendRequest(requestId:string,sendId:string,action:number){
    return httpRequest({
        url: BaseAPI + `/us/friend/${requestId}/${sendId}/${action}`,
        method: 'post',
        headers: {
            'token': UserStore.token,
        },
    })
}

// 获取好友请求列表
export function getFriendRequests(){
    return httpRequest({
        url: BaseAPI + `/us/friend`,
        method: 'get',
        headers: {
            'token': UserStore.token,
        },
    })
}