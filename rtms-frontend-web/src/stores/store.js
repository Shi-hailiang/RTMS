import {defineStore} from "pinia";

export const useStoreStore = defineStore('store',  {
    state: () => {
        return {
            id: "",
            accountId: "",
            name: "",
            logo: "",
            description: "",
            phone: "",
            address: "",
            businessHour: "",
            businessLicense: "",
            packagePrice: 0,
            status: "",
            type: "",
        }
    },
    persist: {
        enabled: true
    }
})