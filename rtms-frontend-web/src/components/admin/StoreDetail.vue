<script setup>
import { reactive, ref } from "vue"
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"
import imgUrl from "@/config/img"
import toMoney from "@/utils/money"

const props = defineProps(["store"])

const store = props.store

const open = ref(false)
const showDrawer = () => {
    open.value = true
}
const onClose = () => {
    open.value = false
}
</script>

<template>
    <a-button
        type="default"
        @click="showDrawer"
        class="store-detail-button"
        size="small"
    >
        详情
    </a-button>
    <a-drawer
        title="店铺详情"
        :width="500"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <div class="text-logo">
            <span class="text-logo-key">店铺Logo：</span>
            <a-image
                :src="imgUrl + store.logo"
                alt="店铺Logo"
                width="100px"
            ></a-image>
        </div>
        <div class="text-row">
            <span class="text-row-key">店铺名称：</span>
            <span class="text-row-value">{{ store.name }}</span>
        </div>
        <div class="text-row">
          <span class="text-row-key">店铺类型：</span>
          <span class="text-row-value">{{ store.type }}</span>
        </div>
        <div class="text-row">
            <span class="text-row-key">店铺简介：</span>
            <a-typography-paragraph>
                <pre>{{ store.description }}</pre>
            </a-typography-paragraph>
        </div>
        <div class="text-line">
            <div class="text-row">
                <span class="text-row-key">营业时间：</span>
                <a-typography-paragraph
                    >{{ store.businessHour }}
                </a-typography-paragraph>
            </div>
            <div class="text-row">
                <span class="text-row-key">打包费用：</span>
                <a-typography-paragraph
                    >￥ {{ toMoney(store.packagePrice) }}
                </a-typography-paragraph>
            </div>
        </div>
        <div class="text-line">
            <div class="text-row">
                <span class="text-row-key">电话号码：</span>
                <a-typography-paragraph copyable
                    ><span class="text-row-value">{{ store.phone }}</span>
                </a-typography-paragraph>
            </div>
            <div class="text-row">
                <span class="text-row-key">店铺地址：</span>
                <a-typography-paragraph copyable
                    ><span class="text-row-value">{{ store.address }}</span>
                </a-typography-paragraph>
            </div>
        </div>
        <div class="text-business-license">
            <span class="text-logo-key">营业执照：</span>
            <a-image
                :src="imgUrl + store.businessLicense"
                alt="营业执照"
                width="150px"
            ></a-image>
        </div>
    </a-drawer>
</template>

<style scoped>
.store-detail-button {
    margin: 0 0 0 10px;
}

.text-logo {
    display: flex;
    flex-direction: column;
}

.text-logo-key {
    font-weight: 500;
    margin-bottom: 10px;
}

.text-row {
    margin: 20px 50px 20px 0;
}

.text-row-key {
    font-weight: 500;
    margin-bottom: 20px;
}

.text-row-value {
    color: #1677fc;
}

.text-line {
    display: flex;
    justify-content: flex-start;
}

.text-business-license {
    display: flex;
    flex-direction: column;
}
</style>
