<script setup>
import { ref } from "vue"
import { get, post } from "@/utils/request.js"
import { message } from "ant-design-vue"
import toMoney from "@/utils/money"
import imgUrl from "@/config/img"

const columns = [
    {
        title: "头像",
        dataIndex: "avatar",
        width: 100,
    },
    {
        title: "账号",
        dataIndex: "username",
        width: 150,
    },
    {
        title: "角色",
        dataIndex: "role",
        width: 80,
        filters: [
            {
                text: "顾客",
                value: "顾客",
            },
            {
                text: "商家",
                value: "商家",
            },
            {
                text: "骑手",
                value: "骑手",
            },
        ],
        onFilter: (value, record) => record.role.indexOf(value) === 0,
    },
    {
        title: "电话号码",
        dataIndex: "phone",
        width: 150,
    },
    {
        title: "昵称",
        dataIndex: "nickname",
    },
    {
        title: "余额",
        dataIndex: "money",
        sorter: (a, b) => a.money - b.money,
    },
    {
        title: "注册时间",
        dataIndex: "createTime",
    },
    {
        title: "状态",
        dataIndex: "status",
        width: 80,
        filters: [
            {
                text: "正常",
                value: "正常",
            },
            {
                text: "封禁",
                value: "封禁",
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
const getAccounts = async () => {
    const response = await get("/api/select_accounts")
    if (response.code === 200) {
        data.value = response.data
    }
}
getAccounts()
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

// 封禁账号逻辑
const open = ref(false)
const selectedAccountId = ref(0)
const blockedAccount = (accounId) => {
    open.value = true
    selectedAccountId.value = accounId
}

const [messageApi, contextHolder] = message.useMessage()

const blockedAccountById = async (accountId) => {
    const response = await post("/api/blocked_account/" + accountId)
    if (response.code === 200) {
        message.success("封禁成功")
        getAccounts()
    }
}

const handleBlockedAccount = () => {
    blockedAccountById(selectedAccountId.value)
    open.value = false
}

// 解封账号逻辑
const unOpen = ref(false)
const unBlockedAccount = (accounId) => {
    unOpen.value = true
    selectedAccountId.value = accounId
}

const unBlockedAccountById = async (accountId) => {
    const response = await post("/api/un_blocked_account/" + accountId)
    if (response.code === 200) {
        message.success("解封成功")
        getAccounts()
    }
}

const handleUnBlockedAccount = () => {
    unBlockedAccountById(selectedAccountId.value)
    unOpen.value = false
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
                <template v-if="column.dataIndex === 'avatar'">
                    <a-image
                        :width="50"
                        :src="imgUrl + record.avatar"
                        alt="账号头像"
                    />
                </template>
                <template v-else-if="column.dataIndex === 'username'">
                    {{ record.username }}
                </template>
                <template v-else-if="column.dataIndex === 'role'">
                    {{ record.role }}
                </template>
                <template v-else-if="column.dataIndex === 'phone'">
                    {{ record.phone }}
                </template>
                <template v-else-if="column.dataIndex === 'nickname'">
                    {{ record.nickname }}
                </template>
                <template v-else-if="column.dataIndex === 'money'">
                    ￥ {{ toMoney(record.money) }}
                </template>
                <template v-else-if="column.dataIndex === 'createTime'">
                    {{ formatDate(record.createTime) }}
                </template>
                <template v-else-if="column.dataIndex === 'status'">
                    {{ record.status }}
                </template>
                <template
                    v-else-if="
                        column.dataIndex === 'options' &&
                        record.status === '正常'
                    "
                >
                    <a-button
                        type="primary"
                        size="small"
                        danger
                        @click="blockedAccount(record.id)"
                        >封禁</a-button
                    >
                </template>
                <template v-else>
                    <a-button
                        type="primary"
                        size="small"
                        @click="unBlockedAccount(record.id)"
                        >解封</a-button
                    >
                </template>
            </template>
        </a-table>
        <div>
            <a-modal
                v-model:open="open"
                title="封禁账号"
                @ok="handleBlockedAccount()"
            >
                <p>确定要封禁该账号吗？</p>
            </a-modal>
        </div>
        <div>
            <a-modal
                v-model:open="unOpen"
                title="解封账号"
                @ok="handleUnBlockedAccount()"
            >
                <p>确定要解封该账号吗？</p>
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
