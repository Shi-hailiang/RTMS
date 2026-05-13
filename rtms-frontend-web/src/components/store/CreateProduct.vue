<script setup>
import {onMounted, onUpdated, reactive, ref} from 'vue';
import axios from "axios";
import {useStoreStore} from "@/stores/store.js";
import {message} from "ant-design-vue";
import getCategoriesByStoreId from "@/api/getCategoriesByStoreId.js";
import UploadProductPicture from "@/components/upload/UploadProductPicture.vue";
import emitter from "@/utils/emitter.js";

const storeStore = useStoreStore()
const {categories,load} = getCategoriesByStoreId(storeStore.id)
load()

const [messageApi, contextHolder] = message.useMessage()

const emit = defineEmits(['reload'])

const form = reactive({
  categoryId: '',
  name: '',
  price: '',
  unit: '',
  description: '',
  picture: '',
});
const rules = {
  name: [
    {
      required: true,
      message: '请输入产品名称',
      trigger: 'blur',
    },
  ],
  categoryId: [
    {
      required: true,
      message: '请选择产品分类',
      trigger: 'blur',
    },
  ],
  price: [
    {
      required: true,
      message: '请输入产品单价',
      trigger: 'blur',
    },
  ],
  unit: [
    {
      required: true,
      message: '请输入产品单位',
      trigger: 'blur',
    },
  ],
  description: [
    {
      required: true,
      message: '请输入产品简介',
      trigger: 'blur',
    },
  ],
  picture: [
    {
      required: true,
      message: '请上传产品图像',
      trigger: 'blur',
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
      '/api/create_product',
      {
        storeId: storeStore.id,
        categoryId: form.categoryId,
        name: form.name,
        price: form.price * 100,
        unit: form.unit,
        description: form.description,
        picture: form.picture
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

const popupScroll = () => {
  console.log('popupScroll');
};

emitter.on('send-picture', (picture) => {
  form.picture = picture
})
</script>

<template>
  <context-holder />
  <a-button type="primary" @click="showDrawer">
    <template #icon><PlusOutlined /></template>
    新增产品
  </a-button>
  <a-drawer
      title="新增产品"
      :width="420"
      :open="open"
      :body-style="{ paddingBottom: '80px' }"
      :footer-style="{ textAlign: 'right' }"
      @close="onClose"
  >
    <a-form :model="form" :rules="rules" layout="vertical">
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品名称" name="name">
            <a-input v-model:value="form.name" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品分类" name="categoryId">
            <a-select
                v-model:value="form.categoryId"
                :size="middle"
                style="width: 200px"
            >
              <a-select-option v-for="category in categories" :value="category.id">{{category.name}}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品单价" name="price">
            <a-input-number
                prefix="￥"
                v-model:value="form.price"
                style="width: 200px"
                :min="0"
                :step="0.01"
                string-mode
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品单位" name="unit">
            <a-input v-model:value="form.unit" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品简介" name="description">
            <a-textarea
                v-model:value="form.description"
                :auto-size="{ minRows: 3, maxRows: 5 }"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="产品图片" name="picture">
            <UploadProductPicture />
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