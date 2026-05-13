<script setup>
import {useStoreStore} from "@/stores/store.js";
import getOrdersByStoreId from "@/api/getOrdersByStoreId.js";
import toMoney from "../../utils/money.js";
import OrderDetail from "@/components/store/OrderDetail.vue";
import UpdateCategory from "@/components/store/UpdateCategory.vue";
import axios from "axios";
import {ref} from "vue";

const storeStore = useStoreStore()
const {orders,load} = getOrdersByStoreId(storeStore.id)
load()

// 当前选中的订单id
const selectOrderId = ref(0);
// 接单
// 确认接单弹窗
const acceptOpen = ref(false);
const acceptOrder = (orderId) => {
  selectOrderId.value = orderId
  acceptOpen.value = !acceptOpen.value
}
const handleAcceptOrder = async (orderId) => {
  try {
    await axios.post("/api/store_accept_order_by_id/" + orderId)
    load()
    acceptOpen.value = !acceptOpen.value
  } catch (error) {
    console.log(error)
  }
}

// 出单
// 确认出单弹窗
const outOpen = ref(false);
const outOrder = (orderId) => {
  selectOrderId.value = orderId
  outOpen.value = !outOpen.value
}
const handleOutOrder = async (orderId) => {
  try {
    await axios.post("/api/store_out_order_by_id/" + orderId)
    load()
    outOpen.value = !outOpen.value
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <div id="order">
    <table>
      <thead>
      <tr>
        <th>编号</th>
        <th>总计</th>
        <th>进度</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="order in orders" :key="order.id">
        <td>{{order.code}}</td>
        <td>￥{{toMoney(order.totalPrice)}}</td>
        <td>{{order.progress}}</td>
        <td>
          <a-button v-if="order.progress === '已支付'" type="primary" size="small" @click="acceptOrder(order.id)">接单</a-button>
          <a-button v-if="order.progress === '已接单'" type="primary" size="small" @click="outOrder(order.id)">出单</a-button>
          <OrderDetail @reload="load" :order="order"/>
        </td>
      </tr>
      </tbody>
    </table>
    <a-modal v-model:open="acceptOpen" title="接单" @ok="handleAcceptOrder(selectOrderId)">
      <p>确定接单吗？</p>
    </a-modal>
    <a-modal v-model:open="outOpen" title="出餐" @ok="handleOutOrder(selectOrderId)">
      <p>确定出单吗？订单状态将变更为“待配送”</p>
    </a-modal>
  </div>
</template>

<style scoped>
#order {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1200px;
  background: #ffffff;
  margin-top: 70px;
  margin-left: 200px;
}

#create-order {
  margin-top: 20px;
  margin-bottom: 10px;
  width: 90%;
  display: flex;
  justify-content: flex-end;
}

table {
  margin-top: 10px;
  margin-bottom: 10px;
  width: 90%;
  height: 650px;
  color: #393939;
  table-layout: auto;
  text-align: center;
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

tbody td {
  text-align: center;
}

tr {
  display: table;
  transition: 0.5s;
}

th {
  padding-left: 10px;
  background-color: #fafafa;
  border: 1px solid #ebebeb;
  border-top: none;
  border-left:none;
  border-right:none;
  height: 47px;
}

td {
  padding-left: 10px;
  border: 1px solid #ebebeb;
  border-left:none;
  border-right:none;
  height: 47px;
}

tr:hover {
  background-color: #fafafa;
}
</style>