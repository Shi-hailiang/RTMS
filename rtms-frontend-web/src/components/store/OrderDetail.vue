<script setup>
import {onMounted, reactive, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import emitter from "@/utils/emitter.js";
import getSpecificationsByProductId from "@/api/getSpecificationsByProductId.js";
import getOrderProductsByOrderId from "@/api/getOrderProductsByOrderId.js";
import toMoney from "../../utils/money.js";
import getAccountByAccountId from "@/api/getAccountByAccountId.js";

const [messageApi, contextHolder] = message.useMessage()

const props = defineProps(['order'])
const emit = defineEmits(['reload'])

const {orderProducts,load} = getOrderProductsByOrderId(props.order.id)
load()

const {account} = getAccountByAccountId(props.order.accountId)

onMounted(() => {
  setTimeout(() => {
    getAccountByAccountId(props.order.accountId).load()
  }, 200)
})

const form = reactive({
  orderId: props.order.id,
  type: '',
  name: ''
});

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
    详 情
  </a-button>
  <a-drawer
      title="订单详情"
      :width="420"
      :open="open"
      :body-style="{ paddingBottom: '80px' }"
      :footer-style="{ textAlign: 'right' }"
      @close="onClose"
  >
    <a-form :model="form" layout="vertical">
      <a-row :gutter="0">
        <a-col :span="24">
          <a-form-item label="订单编号" name="type">
            <p>{{props.order.code}}</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="24">
          <a-form-item label="配送地址" name="type">
            <p>{{props.order.address}}</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="12">
          <a-form-item label="配送时间" name="type">
            <p>{{props.order.expectedTime}}</p>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="顾客电话" name="type">
            <p>{{props.order.phone}}</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="12">
          <a-form-item label="支付方式" name="type">
            <p>{{props.order.payment}}</p>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="订单进度" name="type">
            <p>{{props.order.progress}}</p>
          </a-form-item>
        </a-col>
      </a-row>
      <div id="order-products">
      <table>
        <thead>
        <tr>
          <th>产品</th>
          <th>单价</th>
          <th>数量</th>
          <th>合计</th>
          <th>规格</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="orderProduct in orderProducts" :key="orderProduct.id">
          <td>{{orderProduct.name}}</td>
          <td>￥{{toMoney(orderProduct.price)}}</td>
          <td>{{orderProduct.number}}</td>
          <td>￥{{toMoney(orderProduct.totalPrice)}}</td>
          <td>{{orderProduct.specification}}</td>
        </tr>
        </tbody>
      </table>
      </div>
      <a-row :gutter="0">
        <a-col :span="24">
          <a-form-item label="订单备注" name="type">
            <p>{{props.order.note}}</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="0">
        <a-col :span="8">
          <a-form-item label="打包费用" name="type">
            <p>￥{{toMoney(props.order.packagePrice)}}</p>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="配送费用" name="type">
            <p>￥{{toMoney(props.order.deliveryPrice)}}</p>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="总费用" name="type">
            <p>￥{{toMoney(props.order.totalPrice)}}</p>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

    <template #extra>
      <a-space>
        <a-button type="primary" @click="onClose">确定</a-button>
      </a-space>
    </template>
  </a-drawer>
</template>

<style scoped>
table {
  margin-top: 10px;
  margin-bottom: 40px;
  width: 99%;
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