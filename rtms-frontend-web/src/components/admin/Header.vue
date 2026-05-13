<script setup>
import { ref, computed } from "vue"
import { useAccountStore } from "@/stores/account.js"
import axios from "axios"
import router from "@/router/index.js"
import imgUrl from "@/config/img.js"

const accountStore = useAccountStore()

const avatarUrl = computed(() => {
    return imgUrl + accountStore.avatar
})

const open = ref(false)
const logout = () => {
    open.value = true
}

const handleOk = (e) => {
    console.log(e)
    axios.get("/api/logout")
    accountStore.$reset()
    router.push("/login")
    open.value = false
}
</script>

<template>
    <div id="header">
        <div id="avatar">
            <img :src="avatarUrl" alt="Avatar" />
        </div>
        <div id="nickname">
            <span>{{ accountStore.nickname }}</span>
        </div>
        <a-button
            id="logout"
            v-if="useAccountStore().nickname !== '未登录'"
            @click="logout"
            type="primary"
            size="small"
            danger
            >退出</a-button
        >
    </div>
    <div>
        <a-modal v-model:open="open" title="退出登录" @ok="handleOk">
            <p>确定要退出登录吗？</p>
        </a-modal>
    </div>
</template>

<style scoped>
#header {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    height: 60px;
    width: 100%;
    background: #ffffff;
    z-index: 1000;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
}

#avatar img {
    width: 40px;
    border-radius: 50%;
    margin-right: 5px;
}

#nickname {
    margin-right: 20px;
}

#logout {
    margin-right: 40px;
    border-radius: 4px;
    user-select: none;
    transition: 0.3s;
}
</style>
