<script setup>
import { ref, reactive } from "vue"
import { get, post } from "@/utils/request.js"
import { message } from "ant-design-vue"
import UploadBanner1 from "@/components/upload/UploadBanner1.vue"
import UploadBanner2 from "@/components/upload/UploadBanner2.vue"
import UploadBanner3 from "@/components/upload/UploadBanner3.vue"
import { useSystemStore } from "@/stores/system.js"
import emitter from "@/utils/emitter.js"

const [messageApi, contextHolder] = message.useMessage()

// 查询系统信息相关逻辑
const system = useSystemStore()

const getSystem = async () => {
    const response = await get("/api/select_system")
    if (response.code === 200) {
        system.name = response.data.name
        system.deliveryPrice = response.data.deliveryPrice / 100
        system.announcement = response.data.announcement
        system.banner1 = response.data.banner1
        system.banner2 = response.data.banner2
        system.banner3 = response.data.banner3
    }
}

emitter.on("send-banner1", (picture) => {
    system.banner1 = picture
})

emitter.on("send-banner2", (picture) => {
    system.banner2 = picture
})

emitter.on("send-banner3", (picture) => {
    system.banner3 = picture
})

getSystem()

const onSubmit = async () => {
    const response = await post("/api/update_system", {
        name: system.name,
        deliveryPrice: system.deliveryPrice * 100,
        announcement: system.announcement,
        banner1: system.banner1,
        banner2: system.banner2,
        banner3: system.banner3,
    })
    if (response.code === 200) {
        messageApi.success("保存成功")
        getSystem()
    } else {
        messageApi.error(data.message)
    }
}
</script>

<template>
    <div class="content">
        <context-holder />
        <div class="title">系统设置</div>
        <div class="text-row">
            <span>系统名称：</span>
            <a-input v-model:value="system.name" placeholder="请设置系统名称" />
        </div>
        <div class="text-row">
            <span>系统公告：</span>
            <a-input
                v-model:value="system.announcement"
                placeholder="请设置系统公告"
            />
        </div>
        <div class="text-row">
            <span>平台配送费用：</span>
            <a-input-number
                v-model:value="system.deliveryPrice"
                prefix="￥"
                min="0"
                precision="2"
            />
        </div>
        <div class="text-row">
            <span>顾客端轮播图设置：</span>
        </div>
        <div class="banner-row">
            <UploadBanner1 />
            <UploadBanner2 />
            <UploadBanner3 />
        </div>
        <div class="text-row">
            <a-button type="primary" @click="onSubmit">保存</a-button>
        </div>
    </div>
</template>

<style scoped>
.content {
    margin-left: 220px;
    margin-top: 80px;
    width: 82%;
    padding: 40px 60px;
    background: #ffffff;
    display: flex;
    flex-direction: column;
}

.title {
    font-size: 18px;
    font-weight: 700;
    margin-bottom: 20px;
}

.text-row {
    font-size: 16px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 500px;
    margin-bottom: 20px;
}

.text-row span {
    margin-bottom: 6px;
}

.banner-row {
    font-size: 16px;
    display: flex;
    align-items: flex-start;
    margin-bottom: 40px;
}
</style>
