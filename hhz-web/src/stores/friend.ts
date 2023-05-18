import { defineStore } from 'pinia'

const useFriendStore = defineStore('FriendStore', {
    state: (): any => ({
        friendChats: localStorage.getItem("_chat_friends") != null ? JSON.parse(localStorage.getItem("_chat_friends") as any) : [],
    }),
    getters: {
    },
    actions: {
        setFriendChats(friendChats: any) {
            localStorage.setItem('_chat_friends', JSON.stringify(friendChats))
            this.friendChats = friendChats
        },
        cleFriendChats() {
            localStorage.removeItem("_chat_friends")
            this.friendChats = []
        },
    },
})

export default useFriendStore;