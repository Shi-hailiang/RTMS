<script setup>
import { UserOutlined, LockOutlined } from "@ant-design/icons-vue"
import { reactive } from "vue"
import { message } from "ant-design-vue"
import router from "@/router/index.js"
import axios from "axios"
import { useAccountStore } from "@/stores/account.js"

const accountStore = useAccountStore()

const [messageApi, contextHolder] = message.useMessage()

const form = reactive({
    username: "",
    password: "",
})

const login = () => {
    if (form.username === "") {
        messageApi.info("账号不能为空")
    } else if (form.password === "") {
        messageApi.info("密码不能为空")
    } else {
        axios
            .post(
                "/api/login",
                {
                    username: form.username,
                    password: form.password,
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
                    if (data.data.status === "已封禁") {
                        messageApi.error("账号已被封禁")
                    } else {
                        accountStore.id = data.data.id
                        accountStore.username = data.data.username
                        accountStore.role = data.data.role
                        accountStore.phone = data.data.phone
                        accountStore.nickname = data.data.nickname
                        accountStore.avatar = data.data.avatar
                        accountStore.money = data.data.money

                        if (data.data.role === "管理员") {
                            messageApi.success("登录成功")
                            setTimeout(() => {
                                router.push("/admin/home")
                            }, 800)
                        }
                        if (data.data.role === "商家") {
                            messageApi.success("登录成功")
                            setTimeout(() => {
                                router.push("/store/home")
                            }, 800)
                        }
                        if (data.data.role === "顾客") {
                            messageApi.success("登录成功")
                            setTimeout(() => {
                                router.push("/customer/home")
                            }, 800)
                        }
                        if (data.data.role === "骑手") {
                            messageApi.info("骑手账号请使用移动端登录")
                        }
                    }
                } else {
                    messageApi.error(data.message)
                }
            })
    }
}
</script>

<template>
    <context-holder />
    <div id="login-card">
        <div>
            <h2>登录</h2>
            <div id="login-subtitle">嗨！欢迎使用舌尖急先锋</div>
        </div>
        <a-form :model="form">
            <a-input
                v-model:value="form.username"
                type="text"
                style="margin-bottom: 10px"
                placeholder="账号"
            >
                <template #prefix>
                    <a-space><UserOutlined /></a-space>
                </template>
            </a-input>
            <a-input
                v-model:value="form.password"
                type="password"
                style="margin-bottom: 30px"
                placeholder="密码"
            >
                <template #prefix>
                    <a-space><LockOutlined /></a-space>
                </template>
            </a-input>
        </a-form>
        <div>
            <a-button @click="login()" type="primary">立即登录</a-button>
        </div>
        <div>
            <a-button @click="router.push('/register')" plain
                >注册账号</a-button
            >
        </div>
    </div>
</template>

<style scoped>
#login-card {
    background: #ffffff;
    width: 350px;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

#login-subtitle {
    margin-top: 20px;
    margin-bottom: 30px;
}

button {
    width: 100%;
    margin-bottom: 10px;
}
</style>
