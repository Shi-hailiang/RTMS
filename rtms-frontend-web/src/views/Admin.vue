<script setup>
import Sidebar from "@/components/admin/Sidebar.vue"
import Header from "@/components/admin/Header.vue"
import { useAccountStore } from "@/stores/account.js"
import { onMounted, watch } from "vue"

const accountStore = useAccountStore()

// 在组件加载时调用 load 函数
onMounted(() => {})
</script>

<template>
    <Header></Header>
    <Sidebar></Sidebar>
    <router-view />
</template>

<style scoped></style>
