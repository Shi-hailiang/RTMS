<template>
    <a-button type="primary" @click="showDrawer" class="create-rider-button">
        <template #icon><PlusOutlined /></template>
        绑定骑手
    </a-button>
    <a-drawer
        title="绑定骑手"
        :width="500"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <a-form :model="form" :rules="rules" layout="vertical">
            <a-row :gutter="24">
                <a-col :span="24">
                    <a-form-item label="绑定账号" name="accountId">
                        <a-select
                            ref="select"
                            v-model:value="form.accountId"
                            style="width: 120px"
                            @focus="focus"
                            @change="handleChange"
                        >
                            <a-select-option
                                v-for="account in accounts"
                                :value="account.id"
                                :key="account.id"
                                >{{ account.username }}</a-select-option
                            >
                        </a-select>
                    </a-form-item>
                </a-col>
            </a-row>
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
import { PlusOutlined } from "@ant-design/icons-vue"
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"

const [messageApi, contextHolder] = message.useMessage()

const emit = defineEmits(["get-addresses"])

const form = reactive({
    accountId: "",
    name: "",
    gender: "",
    idNumber: "",
    phone: "",
})
const rules = {
    name: [
        {
            required: true,
            message: "请输入骑手姓名",
        },
    ],
    gender: [
        {
            required: true,
            message: "请设置骑手性别",
        },
    ],
    idNumber: [
        {
            required: true,
            message: "请输入骑手身份证号",
        },
    ],
    phone: [
        {
            required: true,
            message: "请输入骑手电话号码",
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
        createRider()
    }
}

const createRider = async () => {
    const response = await post("/api/create_rider", {
        accountId: form.accountId,
        name: form.name,
        gender: form.gender,
        idNumber: form.idNumber,
        phone: form.phone,
    })
    if (response.code === 200) {
        updateAccountRole()
    }
}

const updateAccountRole = async () => {
    const response = await post("/api/update_account_role", {
        id: form.accountId,
        role: "骑手",
    })
    if (response.code === 200) {
        message.success("新增成功")
        emit("get-riders")
    }
}

// 获取顾客账号列表
const accounts = ref([])
const getAccounts = async () => {
    const response = await get("/api/select_accounts")
    if (response.code === 200) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].role === "顾客") {
                accounts.value.push(response.data[i])
            }
        }
    }
}
getAccounts()
</script>
<style scoped>
.create-rider-button {
    margin: 0 0 20px 0;
}
</style>
