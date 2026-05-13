<script setup>
import { ref, onMounted } from "vue"
import { get, post } from "@/utils/request.js"
import { message } from "ant-design-vue"

const complaints = ref([])
const loading = ref(false)
const imgUrl = "http://localhost:8090/api/download_img?img="

// 处理弹窗
const handleModalVisible = ref(false)
const currentComplaint = ref(null)
const handleResult = ref("")
const handleReply = ref("")

const resultOptions = ["驳回", "警告", "禁用"]

// 获取投诉列表
const getComplaints = async () => {
    loading.value = true
    try {
        const response = await get("/api/select_all_complaints")
        if (response.code === 200) {
            complaints.value = response.data || []
        }
    } finally {
        loading.value = false
    }
}

// 打开处理弹窗
const openHandleModal = (complaint) => {
    currentComplaint.value = complaint
    handleResult.value = ""
    handleReply.value = ""
    handleModalVisible.value = true
}

// 处理投诉
const handleComplaint = async () => {
    if (!handleResult.value) {
        message.warning("请选择处理结果")
        return
    }
    const response = await post("/api/handle_complaint", {
        id: currentComplaint.value.id,
        result: handleResult.value,
        reply: handleReply.value
    })
    if (response.code === 200) {
        message.success("处理成功")
        handleModalVisible.value = false
        getComplaints()
    }
}

// 格式化日期
const formatDate = (timestamp) => {
    if (!timestamp) return "-"
    const date = new Date(timestamp)
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`
}

// 获取状态颜色
const getStatusColor = (status) => {
    return status === '待处理' ? 'orange' : 'green'
}

// 获取结果颜色
const getResultColor = (result) => {
    if (result === '驳回') return 'default'
    if (result === '警告') return 'warning'
    if (result === '禁用') return 'error'
    return 'default'
}

onMounted(() => {
    getComplaints()
})
</script>

<template>
    <div class="complaint-page">
        <div class="page-header">
            <h2>违规处理</h2>
            <span class="total">共 {{ complaints.length }} 条投诉</span>
        </div>
        
        <a-table 
            :dataSource="complaints" 
            :loading="loading"
            :pagination="{ pageSize: 10 }"
            rowKey="id"
        >
            <a-table-column title="投诉用户" key="user">
                <template #default="{ record }">
                    <div class="user-cell">
                        <div class="avatar" :style="record.avatar ? { backgroundImage: 'url(' + imgUrl + record.avatar + ')' } : {}">
                            <span v-if="!record.avatar">{{ (record.nickname || '匿名')[0] }}</span>
                        </div>
                        <span>{{ record.nickname || '匿名用户' }}</span>
                    </div>
                </template>
            </a-table-column>
            
            <a-table-column title="被投诉商家" key="store">
                <template #default="{ record }">
                    <div class="store-cell">
                        <img v-if="record.storeLogo" :src="imgUrl + record.storeLogo" class="store-logo" />
                        <span>{{ record.storeName }}</span>
                    </div>
                </template>
            </a-table-column>
            
            <a-table-column title="投诉类型" dataIndex="type" key="type" />
            
            <a-table-column title="投诉内容" key="content" width="200">
                <template #default="{ record }">
                    <a-tooltip :title="record.content">
                        <span class="content-cell">{{ record.content }}</span>
                    </a-tooltip>
                </template>
            </a-table-column>
            
            <a-table-column title="证据图片" key="images">
                <template #default="{ record }">
                    <div class="images-cell" v-if="record.images">
                        <a-image-preview-group>
                            <a-image 
                                v-for="(img, index) in record.images.split(',')" 
                                :key="index"
                                :width="40"
                                :src="imgUrl + img"
                            />
                        </a-image-preview-group>
                    </div>
                    <span v-else>-</span>
                </template>
            </a-table-column>
            
            <a-table-column title="提交时间" key="createTime">
                <template #default="{ record }">
                    {{ formatDate(record.createTime) }}
                </template>
            </a-table-column>
            
            <a-table-column title="状态" key="status">
                <template #default="{ record }">
                    <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
                </template>
            </a-table-column>
            
            <a-table-column title="处理结果" key="result">
                <template #default="{ record }">
                    <a-tag v-if="record.result" :color="getResultColor(record.result)">{{ record.result }}</a-tag>
                    <span v-else>-</span>
                </template>
            </a-table-column>
            
            <a-table-column title="操作" key="action">
                <template #default="{ record }">
                    <a-button 
                        v-if="record.status === '待处理'"
                        type="primary" 
                        size="small"
                        @click="openHandleModal(record)"
                    >处理</a-button>
                    <a-tooltip v-else :title="'回复: ' + (record.reply || '无')">
                        <a-button type="link" size="small">查看</a-button>
                    </a-tooltip>
                </template>
            </a-table-column>
        </a-table>
        
        <!-- 处理弹窗 -->
        <a-modal 
            v-model:open="handleModalVisible" 
            title="处理投诉"
            @ok="handleComplaint"
        >
            <div class="handle-form" v-if="currentComplaint">
                <div class="form-item">
                    <label>投诉用户：</label>
                    <span>{{ currentComplaint.nickname }}</span>
                </div>
                <div class="form-item">
                    <label>被投诉商家：</label>
                    <span>{{ currentComplaint.storeName }}</span>
                </div>
                <div class="form-item">
                    <label>投诉类型：</label>
                    <span>{{ currentComplaint.type }}</span>
                </div>
                <div class="form-item">
                    <label>投诉内容：</label>
                    <p>{{ currentComplaint.content }}</p>
                </div>
                <div class="form-item">
                    <label>处理结果：</label>
                    <a-radio-group v-model:value="handleResult">
                        <a-radio v-for="opt in resultOptions" :key="opt" :value="opt">{{ opt }}</a-radio>
                    </a-radio-group>
                </div>
                <div class="form-item">
                    <label>回复内容：</label>
                    <a-textarea v-model:value="handleReply" placeholder="请输入回复内容" :rows="3" />
                </div>
            </div>
        </a-modal>
    </div>
</template>

<style scoped>
.complaint-page {
    position: fixed;
    top: 70px;
    left: 220px;
    width: calc(100% - 250px);
    height: calc(100vh - 90px);
    overflow-y: auto;
    padding: 20px;
}

.page-header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.page-header h2 {
    margin: 0;
    margin-right: 20px;
}

.total {
    color: #999;
    font-size: 14px;
}

.user-cell {
    display: flex;
    align-items: center;
    gap: 8px;
}

.avatar {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background-color: #068cfa;
    background-size: cover;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 14px;
}

.store-cell {
    display: flex;
    align-items: center;
    gap: 8px;
}

.store-logo {
    width: 32px;
    height: 32px;
    border-radius: 4px;
}

.content-cell {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.images-cell {
    display: flex;
    gap: 4px;
}

.handle-form .form-item {
    margin-bottom: 16px;
}

.handle-form label {
    display: block;
    font-weight: 500;
    margin-bottom: 8px;
    color: #333;
}

.handle-form p {
    margin: 0;
    color: #666;
}
</style>
