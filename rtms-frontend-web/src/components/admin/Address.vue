<script setup>
import { ref, reactive } from "vue"
import { get, post, del } from "@/utils/request.js"
import { message } from "ant-design-vue"
import { SearchOutlined } from "@ant-design/icons-vue"
import CreateAddress from "@/components/admin/CreateAddress.vue"
import UpdateAddress from "@/components/admin/UpdateAddress.vue"

const [messageApi, contextHolder] = message.useMessage()

// 查询地址相关逻辑
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

// 地址表格逻辑
const columns = [
    {
        title: "地址",
        dataIndex: "address",
        width: 450,
        customFilterDropdown: true,
        onFilter: (value, record) =>
            record.address
                .toString()
                .toLowerCase()
                .includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
            if (visible) {
                setTimeout(() => {
                    searchInput.value.focus()
                }, 100)
            }
        },
    },
    {
        title: "创建时间",
        dataIndex: "createTime",
        width: 225,
    },
    {
        title: "更新时间",
        dataIndex: "updateTime",
        width: 225,
    },
    {
        title: "操作",
        dataIndex: "options",
    },
]

const data = ref([])
const getAddresses = async () => {
    const response = await get("/api/select_addresses")
    if (response.code === 200) {
        data.value = response.data
    }
}
getAddresses()
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

// 删除地址逻辑
const open = ref(false)
const selectedAddressId = ref(0)
const deleteAddress = (addressId) => {
    open.value = true
    selectedAddressId.value = addressId
}

const deleteAddressById = async (addressId) => {
    const response = await del("/api/delete_address/" + addressId)
    if (response.code === 200) {
        message.success("删除成功")
        getAddresses()
    }
}

const handleDeleteAddress = () => {
    deleteAddressById(selectedAddressId.value)
    open.value = false
}
</script>

<template>
    <div class="content">
        <context-holder />
        <CreateAddress @get-addresses="getAddresses"></CreateAddress>
        <a-table
            :columns="columns"
            :data-source="data"
            :row-key="(record) => record.id"
            :scroll="{ x: 800, y: 500 }"
        >
            <template #headerCell="{ column }">
                <template v-if="column.key === 'address'">
                    <span style="color: #1890ff">地址</span>
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
                        :placeholder="`搜索地址`"
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
                <template v-if="column.dataIndex === 'address'">
                    {{ record.address }}
                </template>

                <template v-if="column.dataIndex === 'createTime'">
                    {{ formatDate(record.createTime) }}
                </template>
                <template v-if="column.dataIndex === 'updateTime'">
                    {{ formatDate(record.updateTime) }}
                </template>
                <template v-if="column.dataIndex === 'options'">
                    <UpdateAddress
                        type="primary"
                        :address="record"
                        @get-addresses="getAddresses"
                    >
                        修改
                    </UpdateAddress>
                    <a-button
                        type="primary"
                        size="small"
                        danger
                        @click="deleteAddress(record.id)"
                        >删除</a-button
                    >
                </template>
            </template>
        </a-table>
        <div>
            <a-modal
                v-model:open="open"
                title="删除地址"
                @ok="handleDeleteAddress()"
            >
                <p>确定要删除该地址吗？</p>
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
