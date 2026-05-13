<script setup>
import getCategoriesByStoreId from "@/api/getCategoriesByStoreId.js";
import {useStoreStore} from "@/stores/store.js";
import {ref} from "vue";
import axios from "axios";
import UpdateCategory from "@/components/store/UpdateCategory.vue";

const storeStore = useStoreStore()
const {categories,load} = getCategoriesByStoreId(storeStore.id)
load()

const open = ref(false);
let selectCategoryId = ref("")

const deleteCategory = (id) => {
  open.value = true;
  selectCategoryId = id
};

const handleOk = e => {
  axios.delete("/api/delete_category/" + selectCategoryId)
  open.value = false;
  setTimeout(() => {
    load()
  }, 200)
};
</script>

<template>
  <div id="category">
    <div id="create-category">
      <CreateCategory @reload="load"/>
    </div>
    <table>
      <thead>
        <tr>
          <th>序号</th>
          <th>分类名</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="category in categories" :key="category.id">
          <td>{{categories.indexOf(category)+1}}</td>
          <td>{{category.name}}</td>
          <td>
            <UpdateCategory @reload="load" :category="category"/>
            <a-button type="primary" size="small" danger @click="deleteCategory(category.id)">删除</a-button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div>
    <a-modal v-model:open="open" title="删除" @ok="handleOk(categoryId)">
      <p>确定要删除这个分类吗？</p>
    </a-modal>
  </div>
</template>

<style scoped>
#category {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1200px;
  background: #ffffff;
  margin-top: 70px;
  margin-left: 200px;
}

#create-category {
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