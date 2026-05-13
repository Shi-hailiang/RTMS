<script setup>
import { computed, ref } from "vue"
import { get } from "@/utils/request.js"
import toMoney from "@/utils/money"

// 平台数据统计
const numberData = ref({
    accountNumber: 0,
    storeNumber: 0,
    riderNumber: 0,
    addressNumber: 0,
})

const getAccounts = async () => {
    const response = await get("/api/select_accounts")
    if (response.code === 200) {
        numberData.value.accountNumber = response.data.length
    }
}

const getStores = async () => {
    const response = await get("/api/admin_select_stores")
    if (response.code === 200) {
        numberData.value.storeNumber = response.data.length
    }
}

const getRiders = async () => {
    const response = await get("/api/select_riders")
    if (response.code === 200) {
        numberData.value.riderNumber = response.data.length
    }
}

const getAddresses = async () => {
    const response = await get("/api/select_addresses")
    if (response.code === 200) {
        numberData.value.addressNumber = response.data.length
    }
}

getAccounts()
getStores()
getRiders()
getAddresses()

const orders = ref([])
const dayOrders = ref([])
const monthOrders = ref([])
const getOrders = async () => {
    const response = await get("/api/select_completed_orders")
    if (response.code === 200) {
        orders.value = response.data
        for (let i = 0; i < orders.value.length; i++) {
            if (orders.value[i].updateTime.slice(0, 10) === day.value) {
                dayOrders.value.push(orders.value[i])
            }
            if (orders.value[i].updateTime.slice(0, 7) === month.value) {
                monthOrders.value.push(orders.value[i])
            }
        }
    }
}
getOrders()

// 今日交易数据统计
const day = computed(() => {
    let currentDate = new Date()
    let year = currentDate.getFullYear() // 获取当前年份
    let month = currentDate.getMonth() + 1 // 获取当前月份，并加1以匹配常规月份表示
    let date = currentDate.getDate() // 获取当前日期
    return (
        year +
        "-" +
        (month < 10 ? "0" + month : month) +
        "-" +
        (date < 10 ? "0" + date : date)
    )
})

const dayOrderNumber = computed(() => {
    return dayOrders.value.length
})
const dayOrderPrice = computed(() => {
    let price = 0
    for (let i = 0; i < dayOrders.value.length; i++) {
        price += dayOrders.value[i].totalPrice
    }
    return price
})

// 本月交易数据统计
const month = computed(() => {
    let currentDate = new Date()
    let year = currentDate.getFullYear() // 获取当前年份
    let month = currentDate.getMonth() + 1 // 获取当前月份，并加1以匹配常规月份表示
    return year + "-" + (month < 10 ? "0" + month : month)
})

const monthOrderNumber = computed(() => {
    return monthOrders.value.length
})
const monthOrderPrice = computed(() => {
    let price = 0
    for (let i = 0; i < monthOrders.value.length; i++) {
        price += monthOrders.value[i].totalPrice
    }
    return price
})
</script>

<template>
    <div class="content">
        <a-divider orientation="left">平台数据统计</a-divider>
        <a-row :gutter="8">
            <a-col span="6">
                <a-card title="账号数量" :bordered="true">
                    <h2>{{ numberData.accountNumber }}</h2>
                </a-card>
            </a-col>
            <a-col span="6">
                <a-card title="商家数量" :bordered="true">
                    <h2>{{ numberData.storeNumber }}</h2>
                </a-card>
            </a-col>
            <a-col span="6">
                <a-card title="骑手数量" :bordered="true">
                    <h2>{{ numberData.riderNumber }}</h2>
                </a-card>
            </a-col>
            <a-col span="6">
                <a-card title="地址数量" :bordered="true">
                    <h2>{{ numberData.addressNumber }}</h2>
                </a-card>
            </a-col>
        </a-row>
        <a-divider orientation="left">今日交易数据统计（{{ day }}）</a-divider>
        <a-row :gutter="8">
            <a-col span="12">
                <a-card title="订单数量" :bordered="true">
                    <h2>{{ dayOrderNumber }}</h2>
                </a-card>
            </a-col>
            <a-col span="12">
                <a-card title="交易总额" :bordered="true">
                    <h2>￥{{ toMoney(dayOrderPrice) }}</h2>
                </a-card>
            </a-col>
        </a-row>
        <a-divider orientation="left"
            >本月交易数据统计（{{ month }}）</a-divider
        >
        <a-row :gutter="8">
            <a-col span="12">
                <a-card title="订单数量" :bordered="true">
                    <h2>{{ monthOrderNumber }}</h2>
                </a-card>
            </a-col>
            <a-col span="12">
                <a-card title="交易总额" :bordered="true">
                    <h2>￥{{ toMoney(monthOrderPrice) }}</h2>
                </a-card>
            </a-col>
        </a-row>
    </div>
</template>

<style scoped>
.content {
    position: fixed;
    top: 70px;
    left: 220px;
    width: 1000px;
}
</style>
