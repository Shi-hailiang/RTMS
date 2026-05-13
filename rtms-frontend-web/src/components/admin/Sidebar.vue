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
    ExclamationCircleOutlined,
} from "@ant-design/icons-vue"

const activeItem = ref("1")

switch (router.currentRoute.value.fullPath) {
    case "/admin/home":
        activeItem.value = "1"
        break
    case "/admin/account":
        activeItem.value = "2"
        break
    case "/admin/store":
        activeItem.value = "3"
        break
    case "/admin/rider":
        activeItem.value = "4"
        break
    case "/admin/address":
        activeItem.value = "5"
        break
    case "/admin/complaint":
        activeItem.value = "6"
        break
    case "/admin/setting":
        activeItem.value = "7"
        break
}

function toHome() {
    activeItem.value = "1"
    router.push("/admin/home")
}

function toAccount() {
    activeItem.value = "2"
    router.push("/admin/account")
}

function toStore() {
    activeItem.value = "3"
    router.push("/admin/store")
}

function toRider() {
    activeItem.value = "4"
    router.push("/admin/rider")
}

function toAddress() {
    activeItem.value = "5"
    router.push("/admin/address")
}

function toComplaint() {
    activeItem.value = "6"
    router.push("/admin/complaint")
}

function toSetting() {
    activeItem.value = "7"
    router.push("/admin/setting")
}
</script>

<template>
    <div id="sidebar">
        <div id="logo">
            <img src="@/assets/logo.png" alt="Logo" />
            <h2>系统管理平台</h2>
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
                @click="toAccount"
            >
                <UserOutlined />
                <span>账号管理</span>
            </div>
            <div
                :class="{ active: activeItem === '3' }"
                class="menu-item"
                @click="toStore"
            >
                <ShopOutlined />
                <span>商家管理</span>
            </div>
            <div
                :class="{ active: activeItem === '4' }"
                class="menu-item"
                @click="toRider"
            >
                <ThunderboltOutlined />
                <span>骑手管理</span>
            </div>
            <div
                :class="{ active: activeItem === '5' }"
                class="menu-item"
                @click="toAddress"
            >
                <EnvironmentOutlined />
                <span>地址管理</span>
            </div>
            <div
                :class="{ active: activeItem === '6' }"
                class="menu-item"
                @click="toComplaint"
            >
                <ExclamationCircleOutlined />
                <span>违规处理</span>
            </div>
            <div
                :class="{ active: activeItem === '7' }"
                class="menu-item"
                @click="toSetting"
            >
                <SettingOutlined />
                <span>系统设置</span>
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
