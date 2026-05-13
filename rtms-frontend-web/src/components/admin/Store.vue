<script setup>
import { ref } from "vue"
import { get, post } from "@/utils/request.js"
import { message } from "ant-design-vue"
import toMoney from "@/utils/money"
import imgUrl from "@/config/img"
import StoreDetail from "@/components/admin/StoreDetail.vue"

const [messageApi, contextHolder] = message.useMessage()

const columns = [
    {
        title: "店铺Logo",
        dataIndex: "logo",
        width: 100,
    },
    {
        title: "店铺名称",
        dataIndex: "name",
        width: 180,
    },
    {
      title: "店铺类型",
      dataIndex: "type",
      width: 120,
      filters: [
        {
          text: "水果果干",
          value: "水果果干",
        },
        {
          text: "中餐美食",
          value: "中餐美食",
        },
        {
          text: "名吃快餐",
          value: "名吃快餐",
        },
        {
          text: "火锅烧烤",
          value: "火锅烧烤",
        },
        {
          text: "汉堡炸鸡",
          value: "汉堡炸鸡",
        },
        {
          text: "蛋糕甜点",
          value: "蛋糕甜点",
        },
        {
          text: "奶茶饮品",
          value: "奶茶饮品",
        },
        {
          text: "超市便利",
          value: "超市便利",
        },
      ],
      onFilter: (value, record) => record.type.indexOf(value) === 0,
    },
  {
    title: "电话号码",
    dataIndex: "phone",
    width: 140,
  },
  {
    title: "店铺地址",
    dataIndex: "address",
    width: 280,
  },
  {
    title: "状态",
    dataIndex: "status",
    width: 120,
    filters: [
      {
        text: "正常",
        value: "正常",
      },
      {
        text: "审核中",
        value: "审核中",
      },
    ],
        onFilter: (value, record) => record.status.indexOf(value) === 0,
    },
    {
        title: "操作",
        dataIndex: "options",
    },
]

const data = ref([])
const getStores = async () => {
    const response = await get("/api/admin_select_stores")
    if (response.code === 200) {
        data.value = response.data
    }
}
getStores()

const formatDate = (dateString) => {
    const date = new Date(dateString)
    const formattedDate = date.toLocaleString("zh-CN", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
    })
    return formattedDate
}

// 下架/通过逻辑
const open = ref(false)
const selectedStoreId = ref(0)
const selectedAccountId = ref(0)
const selectedStatus = ref("正常")
const updateStore = (storeId, accountId, status) => {
    open.value = true
    selectedStoreId.value = storeId
    selectedAccountId.value = accountId
    selectedStatus.value = status
}

const updateStoreById = async () => {
    const response = await post("/api/update_store_status", {
        id: selectedStoreId.value,
        status: selectedStatus.value,
    })
    if (response.code === 200) {
        if (selectedStatus.value === "正常") {
            updateAccountRole("商家")
        } else {
            updateAccountRole("顾客")
        }
        getStores()
    }
}

const updateAccountRole = async (role) => {
    const response = await post("/api/update_account_role", {
        id: selectedAccountId.value,
        role: role,
    })
    if (response.code === 200) {
        message.success("审核成功")
        getRiders()
    }
}

const handleUpdateStore = () => {
    updateStoreById()
    open.value = false
}
</script>

<template>
    <div class="content">
        <context-holder />
        <a-table
            :columns="columns"
            :data-source="data"
            :row-key="(record) => record.id"
            :scroll="{ x: 800, y: 500 }"
        >
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'logo'">
                    <a-image
                        :width="50"
                        :src="imgUrl + record.logo"
                        alt="店铺Logo"
                    />
                </template>
                <template v-if="column.dataIndex === 'name'">
                    {{ record.name }}
                </template>
                <template v-if="column.dataIndex === 'role'">
                    {{ record.role }}
                </template>
                <template v-if="column.dataIndex === 'phone'">
                    {{ record.phone }}
                </template>
                <template v-if="column.dataIndex === 'address'">
                    {{ record.address }}
                </template>
                <template v-if="column.dataIndex === 'status'">
                    {{ record.status }}
                </template>
                <template
                    v-if="
                        column.dataIndex === 'options' &&
                        record.status === '正常'
                    "
                >
                    <a-button
                        type="primary"
                        size="small"
                        danger
                        @click="
                            updateStore(record.id, record.accountId, '审核中')
                        "
                        >下架</a-button
                    >
                </template>
                <template
                    v-if="
                        column.dataIndex === 'options' &&
                        record.status === '审核中'
                    "
                >
                    <a-button
                        type="primary"
                        size="small"
                        @click="
                            updateStore(record.id, record.accountId, '正常')
                        "
                        >通过</a-button
                    >
                </template>
                <template v-if="column.dataIndex === 'options'">
                    <StoreDetail type="default" size="small" :store="record" />
                </template>
            </template>
        </a-table>
        <div>
            <a-modal
                v-model:open="open"
                title="修改店铺"
                @ok="handleUpdateStore()"
            >
                <p v-if="selectedStatus == '正常'">确定通过审核吗？</p>
                <p v-if="selectedStatus == '审核中'">确定下架该店铺吗？</p>
            </a-modal>
        </div>
    </div>
</template>

<style scoped>
.content {
    margin-left: 220px;
    margin-top: 80px;
    width: 82%;
    padding: 20px;
    background: #ffffff;
}
</style>
