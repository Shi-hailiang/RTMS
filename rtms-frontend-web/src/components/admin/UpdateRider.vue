<template>
    <a-button
        type="primary"
        @click="showDrawer"
        class="update-rider-button"
        size="small"
    >
        修改
    </a-button>
    <a-drawer
        title="修改骑手"
        :width="500"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <a-form :model="form" :rules="rules" layout="vertical">
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item label="骑手姓名" name="name">
                        <a-input
                            v-model:value="form.name"
                            placeholder="请输入骑手姓名"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item label="性别" name="gender">
                        <a-radio-group
                            v-model:value="form.gender"
                            name="gender"
                        >
                            <a-radio value="男">男</a-radio>
                            <a-radio value="女">女</a-radio>
                        </a-radio-group>
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item label="身份证号" name="idNumber">
                        <a-input
                            v-model:value="form.idNumber"
                            placeholder="请输入身份证号"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item ref="phone" label="电话号码" name="phone">
                        <a-input v-model:value="form.phone" type="text">
                            <template #suffix>
                                <a-space><PhoneOutlined /></a-space>
                            </template>
                        </a-input>
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
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"

const [messageApi, contextHolder] = message.useMessage()

const emit = defineEmits(["get-riders"])

const props = defineProps(["rider"])

const form = reactive({
    id: props.rider.id,
    accountId: props.rider.accountId,
    name: props.rider.name,
    gender: props.rider.gender,
    idNumber: props.rider.idNumber,
    phone: props.rider.phone,
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
    if (form.accountId === "") {
        message.info("请选择要绑定的账号")
    } else if (form.name === "") {
        message.info("骑手姓名不能为空")
    } else if (form.gender === "") {
        message.info("请设置骑手性别")
    } else if (form.idNumber === "") {
        message.info("骑手身份证号不能为空")
    } else if (form.phone === "") {
        message.info("骑手电话号码不能为空")
    } else {
        open.value = false
        updateRider()
    }
}

const updateRider = async () => {
    const response = await post("/api/update_rider", {
        id: form.id,
        accountId: form.accountId,
        name: form.name,
        gender: form.gender,
        idNumber: form.idNumber,
        phone: form.phone,
    })
    if (response.code === 200) {
        message.success("修改成功")
        emit("get-riders")
    }
}
</script>
<style scoped>
.update-rider-button {
    margin: 0 10px 0 0;
}
</style>
