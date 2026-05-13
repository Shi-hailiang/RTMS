<script setup>
import Sidebar from "@/components/store/Sidebar.vue"
import Header from "@/components/store/Header.vue"
import getStoreByAccountId from "@/api/getStoreByAccountId.js"
import { useAccountStore } from "@/stores/account.js"
import { useStoreStore } from "@/stores/store.js"
import { onMounted, watch } from "vue"

const accountStore = useAccountStore()
const storeStore = useStoreStore()

const { store, load } = getStoreByAccountId(accountStore.id)

// 在组件加载时调用 load 函数
onMounted(() => {
    load()
})

// 监听 store.value 变化
watch(store, (newValue) => {
    storeStore.id = store.value.id
    storeStore.accountId = store.value.accountId
    storeStore.name = store.value.name
    storeStore.logo = store.value.logo
    storeStore.description = store.value.description
    storeStore.phone = store.value.phone
    storeStore.address = store.value.address
    storeStore.businessHour = store.value.businessHour
    storeStore.businessLicense = store.value.businessLicense
    storeStore.packagePrice = store.value.packagePrice
    storeStore.status = store.value.status
    storeStore.type = store.value.type
})
</script>

<template>
    <Header></Header>
    <Sidebar />
    <router-view />
</template>

<style scoped></style>
