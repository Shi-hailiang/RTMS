<script setup>
import {UserOutlined, LockOutlined, SmileOutlined, PhoneOutlined} from "@ant-design/icons-vue";
import {reactive, watchEffect} from "vue";
import { message } from 'ant-design-vue';
const [messageApi, contextHolder] = message.useMessage();
import router from "@/router/index.js";
import axios from "axios";
import { ref } from 'vue';

const form = reactive({
  username: '',
  password: '',
  checkPassword: '',
  role: '',
  nickname: '',
  phone: '',
})

const phoneRegex = /^\d{11}$/;
const isPhoneValid = ref(true);

const validatePhone = () => {
  isPhoneValid.value = phoneRegex.test(form.phone);
};

// 在form.phone变化时触发电话号码校验
watchEffect(() => {
  validatePhone();
});

const register = () => {
  if(form.username === '') {
    messageApi.info('账号不能为空')
  } else if (form.password === '') {
    messageApi.info('密码不能为空')
  } else if (form.checkPassword === '') {
    messageApi.info('请再次确认密码')
  } else if (form.password !== form.checkPassword) {
    messageApi.info('两次密码不一致')
  } else if (form.nickname === '') {
    messageApi.info('昵称不能为空')
  } else if (form.phone === '') {
    messageApi.info('电话号码不能为空')
  } else if (!isPhoneValid.value) {
    messageApi.info('电话号码应为11位数字');
  } else {
    axios.post(
        '/api/register',
        {
          username: form.username,
          password: form.password,
          checkPassword: form.checkPassword,
          role: form.role,
          nickname: form.nickname,
          phone: form.phone,
        },
        {
          headers: {
            'Content-Type': 'application/json'
          },
          withCredentials: true
        }).then(({data}) => {
      if (data.code === 200) {
        messageApi.success('注册成功')
        setTimeout(() => {router.push('/login')}, 800)
      } else {
        messageApi.error(data.message)
      }
    })
  }
}
</script>

<template>
  <context-holder />
  <div id="register-card">
    <div>
      <h2>加入我们</h2>
      <div id="register-subtitle">注册账号</div>
    </div>
    <a-form :model="form">
      <a-input v-model:value="form.username"
               type="text"
               style="margin-bottom: 10px;"
               placeholder="账号">
        <template #prefix>
          <a-space><UserOutlined /></a-space>
        </template>
      </a-input>
      <a-input v-model:value="form.password"
               type="password"
               style="margin-bottom: 10px;"
               placeholder="密码">
        <template #prefix>
          <a-space><LockOutlined /></a-space>
        </template>
      </a-input>
      <a-input v-model:value="form.checkPassword"
               type="password"
               style="margin-bottom: 10px;"
               placeholder="确认密码">
        <template #prefix>
          <a-space><LockOutlined /></a-space>
        </template>
      </a-input>
      <a-input v-model:value="form.nickname"
               type="text"
               style="margin-bottom: 10px;"
               placeholder="昵称">
        <template #prefix>
          <a-space><SmileOutlined /></a-space>
        </template>
      </a-input>
      <a-input v-model:value="form.phone"
               type="text"
               style="margin-bottom: 30px;"
               placeholder="电话号码">
        <template #prefix>
          <a-space><PhoneOutlined /></a-space>
        </template>
      </a-input>
    </a-form>
    <div>
      <a-button @click="register()" type="primary">立即注册</a-button>
    </div>
    <div>
      <a-button @click="router.push('/login')" plain>已有账号</a-button>
    </div>
  </div>
</template>

<style scoped>

#register-card {
  background: #ffffff;
  width: 350px;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 10px 10px rgba(0,0,0,0.1);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

#register-subtitle {
  margin-top: 20px;
  margin-bottom: 30px;
}

button {
  width: 100%;
  margin-bottom: 10px;
}
</style>