<script setup>
import UpdateProduct from "@/components/store/UpdateProduct.vue";
import {useStoreStore} from "@/stores/store.js";
import {ref} from "vue";
import axios from "axios";
import getProductsByStoreId from "@/api/getProductsByStoreId.js";
import toMoney from "@/utils/money.js";
import imgUrl from "@/config/img.js";
import UpdateSpecification from "@/components/store/UpdateSpecification.vue";

const storeStore = useStoreStore()
const {products,load} = getProductsByStoreId(storeStore.id)
load()

const open = ref(false);
let selectProductId = ref("")

const deleteProduct = (productId) => {
  open.value = true;
  selectProductId = productId
};

const handleOk = e => {
  axios.delete("/api/delete_product/" + selectProductId)
  open.value = false;
  setTimeout(() => {
    load()
  }, 200)
};
</script>

<template>
  <div id="product">
    <div id="create-product">
      <CreateProduct @reload="load"/>
    </div>
    <table>
      <thead>
      <tr>
        <th>序号</th>
        <th>图片</th>
        <th>名称</th>
        <th>分类</th>
        <th>单价</th>
        <th>单位</th>
        <th>简介</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in products" :key="product.id">
        <td>{{products.indexOf(product)+1}}</td>
        <td>
          <a-image
              :width="100"
              :src="imgUrl + product.picture"
          />
        </td>
        <td>{{product.name}}</td>
        <td>{{product.categoryName}}</td>
        <td>￥{{toMoney(product.price)}}</td>
        <td>{{product.unit}}</td>
        <td>{{product.description}}</td>
        <td>{{product.status}}</td>
        <td width="100px">
          <UpdateProduct @reload="load" :product="product"/>
          <UpdateSpecification @reload="load" :product="product"/>
          <a-button type="primary" size="small" danger @click="deleteProduct(product.id)">删除</a-button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div>
    <a-modal v-model:open="open" title="删除" @ok="handleOk(productId)">
      <p>确定要删除这个产品吗？</p>
    </a-modal>
  </div>
</template>

<style scoped>
#product {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1200px;
  background: #ffffff;
  margin-top: 70px;
  margin-left: 200px;
}

#create-product {
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
  text-align: center;
}

tr:hover {
  background-color: #fafafa;
}
</style>