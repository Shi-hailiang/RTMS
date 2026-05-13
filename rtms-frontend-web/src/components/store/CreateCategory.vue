<script setup>
import { reactive, ref } from 'vue';
import axios from "axios";
import {useStoreStore} from "@/stores/store.js";
import {message} from "ant-design-vue";

const storeStore = useStoreStore()

const [messageApi, contextHolder] = message.useMessage()

const emit = defineEmits(['reload'])

const form = reactive({
  name: '',
});
const rules = {
  name: [
    {
      required: true,
      message: '请输入分类名称',
    },
  ],
};
const open = ref(false);
const showDrawer = () => {
  open.value = true;
};
const onClose = () => {
  open.value = false;
};
const onSubmit = () => {
  axios.post(
      '/api/create_category',
      {
        storeId: storeStore.id,
        name: form.name,
      },
      {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true
      }).then(({data}) => {
    if (data.code === 200) {
      messageApi.success('新增成功')
      emit('reload')
      open.value = false
    } else {
      messageApi.error(data.message)
    }
  })
};
</script>

<template>
  <context-holder />
  <a-button type="primary" @click="showDrawer">
    <template #icon><PlusOutlined /></template>
    新增分类
  </a-button>
  <a-drawer
      title="新增分类"
      :width="420"
      :open="open"
      :body-style="{ paddingBottom: '80px' }"
      :footer-style="{ textAlign: 'right' }"
      @close="onClose"
  >
    <a-form :model="form" :rules="rules" layout="vertical">
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="分类名称" name="name">
            <a-input v-model:value="form.name" placeholder="请输入分类名称" />
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