import { defineStore } from "pinia"

export const useSystemStore = defineStore("system", {
    state: () => {
        return {
            name: "",
            announcement: "",
            deliveryPrice: 0,
            banner1: "",
            banner2: "",
            banner3: "",
        }
    },
    persist: {
        enabled: true,
    },
})
