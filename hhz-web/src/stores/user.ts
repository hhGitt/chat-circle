import { defineStore } from 'pinia'
import type { UserState } from './interface/user'

const useUserStore = defineStore('GlobalStore', {
  state: (): UserState => ({
    // 所有这些属性都将自动推断其类型
    token: localStorage.getItem("_vue3_token") != null ? localStorage.getItem("_vue3_token") : "",
    user_info: localStorage.getItem("_u_inf") != null ? JSON.parse(localStorage.getItem("_u_inf") as string) : "",
  }),
  getters: {

  },
  actions: {
    setToken(token: string) {
      localStorage.setItem('_vue3_token', token)
      this.token = token
    },
    cleToken() {
      localStorage.removeItem("_vue3_token")
      this.token = ""
    },
    setUserInfos(user_info: any) {
      localStorage.setItem('_u_inf', JSON.stringify(user_info));
      this.user_info = user_info;
    },
    updateUserInfos(user_info: any){
      localStorage.setItem('_u_inf', JSON.stringify(user_info));
    }
    ,
    cleUser_info() {
      localStorage.removeItem("_u_inf")
      this.user_info = null;
    },
    cleAll() {
      this.cleToken();
      this.cleUser_info();
    }
  },
})

export default useUserStore;