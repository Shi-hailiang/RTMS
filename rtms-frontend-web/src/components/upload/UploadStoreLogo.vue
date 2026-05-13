<template>
    <a-upload
        v-model:file-list="fileList"
        name="file"
        list-type="picture-card"
        class="avatar-uploader"
        :show-upload-list="false"
        action="http://localhost:8090/api/upload_img"
        :before-upload="beforeUpload"
        @change="handleChange"
    >
        <img class="picture" v-if="imageUrl" :src="imageUrl" alt="点击上传" />
        <div v-else>
            <loading-outlined v-if="loading"></loading-outlined>
            <plus-outlined v-else></plus-outlined>
            <div class="ant-upload-text">Upload</div>
        </div>
    </a-upload>
</template>
<script setup>
import { ref } from "vue"
import { message } from "ant-design-vue"
import emitter from "@/utils/emitter.js"
import { useStoreStore } from "@/stores/store.js"
import imgUrl from "@/config/img.js"

const storeStore = useStoreStore()

function getBase64(img, callback) {
    const reader = new FileReader()
    reader.addEventListener("load", () => callback(reader.result))
    reader.readAsDataURL(img)
}
const fileList = ref([])
const loading = ref(false)
const imageUrl = ref(imgUrl + storeStore.logo)
const handleChange = (info) => {
    if (info.file.status === "uploading") {
        loading.value = true
        return
    }
    if (info.file.status === "done") {
        getBase64(info.file.originFileObj, (base64Url) => {
            imageUrl.value = base64Url
            loading.value = false
        })
        emitter.emit("send-store-logo", info.file.name)
    }
    if (info.file.status === "error") {
        loading.value = false
        message.error("upload error")
    }
}
const beforeUpload = (file) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png"
    if (!isJpgOrPng) {
        message.error("只能上传图片文件（jpg/png）!")
    }
    const isLt2M = file.size / 1024 / 1024 < 2
    if (!isLt2M) {
        message.error("图片必须小于 2MB!")
    }
    return isJpgOrPng && isLt2M
}
</script>
<style scoped>
.avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
}
.ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
}

.picture {
    width: 100%;
}
</style>
