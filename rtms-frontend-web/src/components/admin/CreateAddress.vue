<template>
    <a-button type="primary" @click="showDrawer" class="create-address-button">
        <template #icon><PlusOutlined /></template>
        新增地址
    </a-button>
    <a-drawer
        title="新增地址"
        :width="500"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <a-form :model="form" :rules="rules" layout="vertical">
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item label="地址" name="address">
                        <a-input
                            v-model:value="form.address"
                            placeholder="请输入地址"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
        <template #extra>
            <a-space>
                <a-button @click="onClose">取消</a-button>
                <a-button type="primary" @click="onSubmit">确定</a-button>
            </a-space>
        </template>
    </a-drawer>
</template>
<script setup>
import { reactive, ref } from "vue"
import { PlusOutlined } from "@ant-design/icons-vue"
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"

const [messageApi, contextHolder] = message.useMessage()

const emit = defineEmits(["get-addresses"])

const form = reactive({
    address: "",
})
const rules = {
    address: [
        {
            required: true,
            message: "请输入地址",
        },
    ],
}
const open = ref(false)
const showDrawer = () => {
    open.value = true
}
const onClose = () => {
    open.value = false
}
const onSubmit = () => {
    if (form.address === "") {
        message.info("地址不能为空")
    } else {
        open.value = false
        createAddress()
    }
}

const createAddress = async () => {
    const response = await post("/api/create_address", {
        address: form.address,
    })
    if (response.code === 200) {
        message.success("新增成功")
        emit("get-addresses")
    }
}
</script>
<style scoped>
.create-address-button {
    margin: 0 0 20px 0;
}
</style>
