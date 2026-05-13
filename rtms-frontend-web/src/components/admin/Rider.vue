<script setup>
import { ref, reactive } from "vue"
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"
import { SearchOutlined } from "@ant-design/icons-vue"
import CreateRider from "@/components/admin/CreateRider.vue"
import UpdateRider from "@/components/admin/UpdateRider.vue"

const [messageApi, contextHolder] = message.useMessage()

// 查询骑手相关逻辑
const state = reactive({
    searchText: "",
    searchedColumn: "",
})
const searchInput = ref()
const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm()
    state.searchText = selectedKeys[0]
    state.searchedColumn = dataIndex
}
const handleReset = (clearFilters) => {
    clearFilters({
        confirm: true,
    })
    state.searchText = ""
}

// 骑手表格逻辑
const columns = [
    {
        title: "骑手姓名",
        dataIndex: "name",
        width: 200,
        customFilterDropdown: true,
        onFilter: (value, record) =>
            record.name.toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
            if (visible) {
                setTimeout(() => {
                    searchInput.value.focus()
                }, 100)
            }
        },
    },
    {
        title: "性别",
        dataIndex: "gender",
        width: 100,
        filters: [
            {
                text: "男",
                value: "男",
            },
            {
                text: "女",
                value: "女",
            },
        ],
        onFilter: (value, record) => record.gender.indexOf(value) === 0,
    },
    {
        title: "身份证号",
        dataIndex: "idNumber",
        width: 200,
    },
    {
        title: "电话号码",
        dataIndex: "phone",
        width: 200,
    },
    {
        title: "状态",
        dataIndex: "status",
        width: 100,
    },
    {
        title: "操作",
        dataIndex: "options",
    },
]

const data = ref([])
const getRiders = async () => {
    const response = await get("/api/select_riders")
    if (response.code === 200) {
        data.value = response.data
    }
}
getRiders()

// 删除骑手逻辑
const open = ref(false)
const selectedRiderId = ref(0)
const selectedAccountId = ref(0)
const unbindRider = (riderId, accountId) => {
    open.value = true
    selectedRiderId.value = riderId
    selectedAccountId.value = accountId
}

const unbindRiderById = async (riderId) => {
    const response = await del("/api/delete_rider/" + riderId)
    if (response.code === 200) {
        updateAccountRole()
    }
}

const updateAccountRole = async () => {
    const response = await post("/api/update_account_role", {
        id: selectedAccountId.value,
        role: "顾客",
    })
    if (response.code === 200) {
        message.success("解绑成功")
        getRiders()
    }
}

const handleUnbindRider = () => {
    unbindRiderById(selectedRiderId.value)
    open.value = false
}
</script>

<template>
    <div class="content">
        <context-holder />
        <CreateRider @get-riders="getRiders"></CreateRider>
        <a-table
            :columns="columns"
            :data-source="data"
            :row-key="(record) => record.id"
            :scroll="{ x: 800, y: 500 }"
        >
            <template #headerCell="{ column }">
                <template v-if="column.key === 'name'">
                    <span style="color: #1890ff">骑手姓名</span>
                </template>
            </template>
            <template
                #customFilterDropdown="{
                    setSelectedKeys,
                    selectedKeys,
                    confirm,
                    clearFilters,
                    column,
                }"
            >
                <div style="padding: 8px">
                    <a-input
                        ref="searchInput"
                        :placeholder="`搜索骑手`"
                        :value="selectedKeys[0]"
                        style="width: 188px; margin-bottom: 8px; display: block"
                        @change="
                            (e) =>
                                setSelectedKeys(
                                    e.target.value ? [e.target.value] : []
                                )
                        "
                        @pressEnter="
                            handleSearch(
                                selectedKeys,
                                confirm,
                                column.dataIndex
                            )
                        "
                    />
                    <a-button
                        type="primary"
                        size="small"
                        style="width: 90px; margin-right: 8px"
                        @click="
                            handleSearch(
                                selectedKeys,
                                confirm,
                                column.dataIndex
                            )
                        "
                    >
                        <template #icon><SearchOutlined /></template>
                        搜索
                    </a-button>
                    <a-button
                        size="small"
                        style="width: 90px"
                        @click="handleReset(clearFilters)"
                    >
                        重置
                    </a-button>
                </div>
            </template>
            <template #customFilterIcon="{ filtered }">
                <SearchOutlined
                    :style="{ color: filtered ? '#108ee9' : undefined }"
                />
            </template>
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'name'">
                    {{ record.name }}
                </template>
                <template v-if="column.dataIndex === 'options'">
                    <UpdateRider
                        type="primary"
                        :rider="record"
                        @get-riders="getRiders"
                    >
                        修改
                    </UpdateRider>
                    <a-button
                        type="primary"
                        size="small"
                        danger
                        @click="unbindRider(record.id, record.accountId)"
                        >解绑</a-button
                    >
                </template>
            </template>
        </a-table>
        <div>
            <a-modal
                v-model:open="open"
                title="解绑骑手"
                @ok="handleUnbindRider()"
            >
                <p>确定要解绑骑手吗？</p>
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
