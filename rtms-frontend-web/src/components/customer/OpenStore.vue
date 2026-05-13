<script setup>
import UploadStoreLogo from "@/components/upload/UploadStoreLogo.vue"
import { reactive, ref, toRaw, watchEffect } from "vue"
import { PhoneOutlined } from "@ant-design/icons-vue"
import axios from "axios"
import { message } from "ant-design-vue"
import emitter from "@/utils/emitter.js"
import { useAccountStore } from "@/stores/account"

const accountStore = useAccountStore()

const [messageApi, contextHolder] = message.useMessage()
const formRef = ref()
const labelCol = {
    span: 5,
}
const wrapperCol = {
    span: 10,
}
const formState = reactive({
    accountId: accountStore.id,
    name: "",
    logo: "",
    phone: "",
    address: "",
    businessHour: "",
    businessLicense: "",
    description: "",
    packagePrice: 0,
    type: "",
})
const rules = {
    name: [
        {
            required: true,
            message: "请输入店铺名称",
            trigger: "change",
        },
    ],
    logo: [
        {
            required: false,
            message: "请设置店铺Logo",
            trigger: "change",
        },
    ],
    phone: [
        {
            required: true,
            message: "请设置店铺电话",
            trigger: "change",
        },
    ],
    address: [
        {
            required: true,
            message: "请设置店铺地址",
            trigger: "change",
        },
    ],
    businessHour: [
        {
            required: true,
            message: "请设置营业时间",
            trigger: "change",
        },
    ],
    businessLicense: [
        {
            required: false,
            message: "请设置营业执照",
            trigger: "change",
        },
    ],
    packagePrice: [
        {
            required: true,
            message: "请设置打包费用",
            trigger: "change",
        },
    ],
    description: [
        {
            required: true,
            message: "请输入店铺简介",
            trigger: "change",
        },
    ],
    type: [
      {
        required: true,
        message: "请设置店铺类型",
        trigger: "change",
      },
    ],
}
const onSubmit = () => {
    formRef.value
        .validate()
        .then(() => {
            axios
                .post(
                    "/api/open_store",
                    {
                        accountId: accountStore.id,
                        name: formState.name,
                        logo: formState.logo,
                        phone: formState.phone,
                        address: formState.address,
                        businessHour: formState.businessHour,
                        businessLicense: formState.businessLicense,
                        description: formState.description,
                        packagePrice: formState.packagePrice * 100,
                        type: formState.type,
                    },
                    {
                        headers: {
                            "Content-Type": "application/json",
                        },
                        withCredentials: true,
                    }
                )
                .then(({ data }) => {
                    if (data.code === 200) {
                        messageApi.success("申请成功")
                        open.value = false
                    } else {
                        messageApi.error(data.message)
                    }
                })
        })
        .catch((error) => {
            console.log("error", error)
        })
}
const resetForm = () => {
    formRef.value.resetFields()
}

const phoneRegex = /^\d{11}$/
const isPhoneValid = ref(true)

const validatePhone = () => {
    isPhoneValid.value = phoneRegex.test(formState.phone)
}

watchEffect(() => {
    validatePhone()
})

emitter.on("send-store-logo", (picture) => {
    formState.logo = picture
})

emitter.on("send-business-license", (picture) => {
    formState.businessLicense = picture
})
</script>

<template>
    <context-holder />
    <div id="setting">
        <span class="title">申请开店</span>
        <a-form
            ref="formRef"
            :model="formState"
            :rules="rules"
            :label-col="labelCol"
            :wrapper-col="wrapperCol"
        >
            <a-form-item ref="logo" label="店铺Logo" name="logo">
                <UploadStoreLogo />
            </a-form-item>
            <a-form-item ref="name" label="店铺名称" name="name">
                <a-input v-model:value="formState.name" />
            </a-form-item>
          <a-form-item ref="type" label="店铺类型" name="type">
            <a-select
                ref="type"
                v-model:value="formState.type"
                style="width: 120px"
                @focus="focus"
                @change="handleChange"
            >
              <a-select-option value="水果果干">水果果干</a-select-option>
              <a-select-option value="中餐美食">中餐美食</a-select-option>
              <a-select-option value="名吃快餐">名吃快餐</a-select-option>
              <a-select-option value="火锅烧烤">火锅烧烤</a-select-option>
              <a-select-option value="汉堡炸鸡">汉堡炸鸡</a-select-option>
              <a-select-option value="蛋糕甜点">蛋糕甜点</a-select-option>
              <a-select-option value="奶茶饮品">奶茶饮品</a-select-option>
              <a-select-option value="超市便利">超市便利</a-select-option>
            </a-select>
          </a-form-item>
            <a-form-item ref="phone" label="店铺电话" name="phone">
                <a-input v-model:value="formState.phone" type="text">
                    <template #suffix>
                        <a-space><PhoneOutlined /></a-space>
                    </template>
                </a-input>
            </a-form-item>
            <a-form-item ref="address" label="店铺地址" name="address">
                <a-input v-model:value="formState.address" />
            </a-form-item>
            <a-form-item
                ref="businessHour"
                label="营业时间"
                name="businessHour"
            >
                <a-input v-model:value="formState.businessHour" />
            </a-form-item>
            <a-form-item
                ref="businessLicense"
                label="营业执照"
                name="businessLicense"
            >
                <UploadBusinessLicense />
            </a-form-item>
            <a-form-item label="打包费用" name="packagePrice">
                <a-input-number
                    prefix="￥"
                    v-model:value="formState.packagePrice"
                    style="width: 200px"
                    :min="0"
                    :step="0.01"
                    string-mode
                />
            </a-form-item>
            <a-form-item label="店铺简介" name="description">
                <a-textarea v-model:value="formState.description" />
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                <a-button type="primary" @click="onSubmit">保存</a-button>
                <a-button style="margin-left: 10px" @click="resetForm"
                    >重置</a-button
                >
            </a-form-item>
        </a-form>
    </div>
</template>

<style scoped>
#setting {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 1200px;
    background: #ffffff;
    padding: 40px;
    margin-top: 90px;
    margin-left: 220px;
}

.title {
    margin-left: 100px;
    margin-bottom: 40px;
    font-size: 18px;
    font-weight: 500;
}
</style>
