<script setup>
import { reactive, ref } from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import emitter from "@/utils/emitter.js";
import getSpecificationsByProductId from "@/api/getSpecificationsByProductId.js";

const [messageApi, contextHolder] = message.useMessage()

const props = defineProps(['product'])
const emit = defineEmits(['reload'])

const {specifications,load} = getSpecificationsByProductId(props.product.id)
load()

const form = reactive({
  productId: props.product.id,
  type: '',
  name: ''
});

const rules = {
  type: [
    {
      required: true,
      message: '请输入规格类型',
      trigger: 'blur',
    },
  ],
  name: [
    {
      required: true,
      message: '请输入规格名称',
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
      '/api/create_specification',
      {
        productId: props.product.id,
        type: form.type,
        name: form.name
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
      setTimeout(() => {
        load()
      }, 200)
    } else {
      messageApi.error(data.message)
    }
  })
};

emitter.on('send-picture', (picture) => {
  form.picture = picture
})

const deleteOpen = ref(false);
let selectSpecificationId = ref('')

const deleteSpecification = (specificationId) => {
  deleteOpen.value = true;
  selectSpecificationId = specificationId
};

const handleOk = e => {
  axios.delete("/api/delete_specification/" + selectSpecificationId)
  deleteOpen.value = false;
  setTimeout(() => {
    load()
  }, 200)
};
</script>

<template>
  <context-holder />
  <a-button type="default" @click="showDrawer" size="small">
    <template #icon><PlusOutlined /></template>
    规 格
  </a-button>
  <a-drawer
      title="规格管理"
      :width="420"
      :open="open"
      :body-style="{ paddingBottom: '80px' }"
      :footer-style="{ textAlign: 'right' }"
      @close="onClose"
  >
    <a-form :model="form" :rules="rules" layout="vertical">
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="规格类型" name="type">
            <a-input v-model:value="form.type" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="20">
          <a-form-item label="规格名称" name="name">
            <a-input v-model:value="form.name" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <div id="specifications">
      <table>
        <thead>
        <tr>
          <th>序号</th>
          <th>规格类型</th>
          <th>规格名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="specification in specifications" :key="specification.id">
          <td>{{specifications.indexOf(specification)+1}}</td>
          <td>{{specification.type}}</td>
          <td>{{specification.name}}</td>
          <td width="100px">
            <a-button type="primary" size="small" danger @click="deleteSpecification(specification.id)">删除</a-button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <template #extra>
      <a-space>
        <a-button @click="onClose">取消</a-button>
        <a-button type="primary" @click="onSubmit">确定</a-button>
      </a-space>
    </template>
  </a-drawer>
  <div>
    <a-modal v-model:open="deleteOpen" title="删除" @ok="handleOk(specificationId)">
      <p>确定要删除这个规格吗？</p>
    </a-modal>
  </div>
</template>

<style scoped>
table {
  margin-top: 10px;
  margin-bottom: 10px;
  width: 90%;
  height: 650px;
  color: #393939;
  table-layout: auto;
  border-collapse: collapse;
  display: table;
}

thead tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}

tbody {
  display: block;
  height: 80%;
  overflow-y: scroll;
}

tbody tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}

tr {
  display: table;
  transition: 0.5s;
}

th {
  background-color: #fafafa;
  border: 1px none #ebebeb;
  border-bottom-style: solid;
  height: 47px;
}

td {
  padding-left: 10px;
  border: 1px solid #ebebeb;
  border-left:none;
  border-right:none;
  height: 47px;
  text-align: center;
}

tr:hover {
  background-color: #fafafa;
}
</style>