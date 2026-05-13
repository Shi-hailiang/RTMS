import {defineStore} from "pinia";

export const useAccountStore = defineStore('account',  {
    state: () => {
        return {
            id: "",
            username: "",
            role: "",
            phone: "",
            nickname: "未登录",
            avatar: "default_avatar.png",
            money: 0,
        }
    },
    persist: {
        enabled: true
    }
})