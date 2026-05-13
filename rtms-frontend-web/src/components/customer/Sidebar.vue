<script setup>
import router from "@/router/index.js"
import { ref } from "vue"
import {
    FileDoneOutlined,
    HomeOutlined,
    UserOutlined,
    ShopOutlined,
    ThunderboltOutlined,
    EnvironmentOutlined,
    SettingOutlined,
} from "@ant-design/icons-vue"

const activeItem = ref("1")

switch (router.currentRoute.value.fullPath) {
    case "/customer/home":
        activeItem.value = "1"
        break
    case "/customer/open_store":
        activeItem.value = "2"
        break
}

function toHome() {
    activeItem.value = "1"
    router.push("/customer/home")
}

function toOpenStore() {
    activeItem.value = "2"
    router.push("/customer/open_store")
}
</script>

<template>
    <div id="sidebar">
        <div id="logo">
            <img src="@/assets/logo.png" alt="Logo" />
            <h2>顾客Web端</h2>
        </div>
        <div id="menu">
            <div
                :class="{ active: activeItem === '1' }"
                class="menu-item"
                @click="toHome"
            >
                <HomeOutlined />
                <span>平台主页</span>
            </div>
            <div
                :class="{ active: activeItem === '2' }"
                class="menu-item"
                @click="toOpenStore"
            >
                <ShopOutlined />
                <span>申请开店</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
#sidebar {
    display: flex;
    flex-direction: column;
    position: fixed;
    top: 0;
    left: 0;
    width: 190px;
    height: 100%;
    background: #031528;
    z-index: 2000;
}

#logo {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 60px;
}

#logo img {
    width: 40px;
    margin-right: 5px;
}

#logo h2 {
    margin: 0;
    font-size: 16px;
    color: #ffffff;
}

#menu {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    width: 100%;
}

.menu-item {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 90%;
    height: 40px;
    color: #ffffff;
    border-radius: 10px;
    user-select: none;
    transition: 0.3s;
    font-size: 14px;
    margin-top: 5px;
}

.menu-item span {
    margin-left: 10px;
}

.menu-item:hover {
    background: #068cfa;
    cursor: pointer;
}

.active {
    background: #068cfa;
}
</style>
