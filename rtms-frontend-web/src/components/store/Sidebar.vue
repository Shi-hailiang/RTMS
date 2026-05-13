<script setup>
import router from "@/router/index.js"
import { ref } from "vue"
import {
    FileDoneOutlined,
    HomeOutlined,
    InboxOutlined,
    SettingOutlined,
    TagOutlined,
    StarOutlined,
} from "@ant-design/icons-vue"

const activeItem = ref("1")

switch (router.currentRoute.value.fullPath) {
    case "/store/home":
        activeItem.value = "1"
        break
    case "/store/category":
        activeItem.value = "2"
        break
    case "/store/product":
        activeItem.value = "3"
        break
    case "/store/order":
        activeItem.value = "4"
        break
    case "/store/review":
        activeItem.value = "5"
        break
    case "/store/setting":
        activeItem.value = "6"
        break
}

function toHome() {
    activeItem.value = "1"
    router.push("/store/home")
}

function toCategory() {
    activeItem.value = "2"
    router.push("/store/category")
}

function toProduct() {
    activeItem.value = "3"
    router.push("/store/product")
}

function toOrder() {
    activeItem.value = "4"
    router.push("/store/order")
}

function toReview() {
    activeItem.value = "5"
    router.push("/store/review")
}

function toSetting() {
    activeItem.value = "6"
    router.push("/store/setting")
}
</script>

<template>
    <div id="sidebar">
        <div id="logo">
            <img src="@/assets/logo.png" alt="Logo" />
            <h2>商家管理平台</h2>
        </div>
        <div id="menu">
            <div
                :class="{ active: activeItem === '1' }"
                class="menu-item"
                @click="toHome"
            >
                <HomeOutlined />
                <span>我的主页</span>
            </div>
            <div
                :class="{ active: activeItem === '2' }"
                class="menu-item"
                @click="toCategory"
            >
                <TagOutlined />
                <span>分类管理</span>
            </div>
            <div
                :class="{ active: activeItem === '3' }"
                class="menu-item"
                @click="toProduct"
            >
                <InboxOutlined />
                <span>产品管理</span>
            </div>
            <div
                :class="{ active: activeItem === '4' }"
                class="menu-item"
                @click="toOrder"
            >
                <FileDoneOutlined />
                <span>订单管理</span>
            </div>
            <div
                :class="{ active: activeItem === '5' }"
                class="menu-item"
                @click="toReview"
            >
                <StarOutlined />
                <span>评价管理</span>
            </div>
            <div
                :class="{ active: activeItem === '6' }"
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
