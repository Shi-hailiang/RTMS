<script setup>
import { SmileTwoTone } from "@ant-design/icons-vue"
</script>

<template>
    <div class="content">
        <a-result title="请使用顾客小程序端进行外卖购买服务！">
            <template #icon>
                <SmileTwoTone />
            </template>
        </a-result>
    </div>
</template>

<style scoped>
.content {
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 24px;
    font-weight: 700;
    color: #545454;
}
</style>
