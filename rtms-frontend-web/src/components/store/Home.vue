<script setup>
import { ref } from "vue"
import { useAccountStore } from "@/stores/account.js"
import { useStoreStore } from "@/stores/store.js"
import axios from "axios"

const accountStore = useAccountStore()
const storeStore = useStoreStore()
const homeData = ref([])
const getData = async () => {
    try {
        const { data } = await axios(
            "/api/get_home_data_by_store_id/" + storeStore.id
        )
        homeData.value = data.data
    } catch (error) {
        console.log(error)
    }
}
const interval = setInterval(() => {
    if (storeStore.id !== "") {
        getData()
        clearInterval(interval)
    }
}, 100)
</script>

<template>
    <div id="home">
        <a-divider orientation="left">营销数据统计</a-divider>
        <a-row :gutter="8">
            <a-col span="8">
                <a-card title="今日订单总数" :bordered="true">
                    <h2>{{ homeData.dayOrders || 0 }}</h2>
                    <h4>
                        较昨日：{{
                            homeData.lastDayOrdersRate > 0
                                ? "+" + homeData.lastDayOrdersRate
                                : homeData.lastDayOrdersRate || 0
                        }}
                    </h4>
                </a-card>
            </a-col>
            <a-col span="8">
                <a-card title="今日销售总额" :bordered="true">
                    <h2>￥{{ homeData.daySales / 100 || 0 }}</h2>
                    <h4 v-if="homeData.lastDaySalesRate > 0">
                        较昨日：+{{ homeData.lastDaySalesRate / 100 || 0 }}
                    </h4>
                    <h4 v-else>
                        较昨日：{{ homeData.lastDaySalesRate / 100 || 0 }}
                    </h4>
                </a-card>
            </a-col>
            <a-col span="8">
                <a-card title="今月销售总额" :bordered="true">
                    <h2>￥{{ homeData.monthSales / 100 || 0 }}</h2>
                    <h4 v-if="homeData.lastMonthSalesRate > 0">
                        较上月：+{{ homeData.lastMonthSalesRate / 100 || 0 }}
                    </h4>
                    <h4 v-else>
                        较上月：{{ homeData.lastMonthSalesRate / 100 || 0 }}
                    </h4>
                </a-card>
            </a-col>
        </a-row>
        <a-divider orientation="left">店铺数据统计</a-divider>
        <a-row :gutter="8">
            <a-col span="8">
                <a-card title="分类数量" :bordered="true">
                    <h2>{{ homeData.categoryNumber || 0 }}</h2>
                </a-card>
            </a-col>
            <a-col span="8">
                <a-card title="产品数量" :bordered="true">
                    <h2>{{ homeData.productNumber || 0 }}</h2>
                </a-card>
            </a-col>
            <a-col span="8">
                <a-card title="订单数量" :bordered="true">
                    <h2>{{ homeData.orderNumber || 0 }}</h2>
                </a-card>
            </a-col>
        </a-row>
        <a-divider orientation="left">余额信息</a-divider>
        <a-row :gutter="8">
            <a-col span="8">
                <a-card title="店铺余额" :bordered="true">
                    <h2>￥{{ accountStore.money / 100 || 0 }}</h2>
                    <a-button type="primary" size="small">充值</a-button>
                    <a-button type="default" size="small">提现</a-button>
                </a-card>
            </a-col>
        </a-row>
    </div>
</template>

<style scoped>
#home {
    position: fixed;
    top: 70px;
    left: 220px;
    width: 1000px;
}
</style>
