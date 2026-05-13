<script setup>
import { ref, onMounted, watch } from "vue"
import { useStoreStore } from "@/stores/store.js"
import { get, del } from "@/utils/request.js"
import { message } from "ant-design-vue"
import { StarFilled, DeleteOutlined } from "@ant-design/icons-vue"

const storeStore = useStoreStore()
const reviews = ref([])
const loading = ref(false)
const imgUrl = "http://localhost:8090/api/download_img?img="

// 获取评价列表
const getReviews = async () => {
    if (!storeStore.id) {
        console.log("storeStore.id is empty, waiting...")
        return
    }
    loading.value = true
    try {
        console.log("Fetching reviews for store:", storeStore.id)
        const response = await get("/api/select_reviews_by_store_id/" + storeStore.id)
        console.log("Reviews response:", response)
        if (response.code === 200) {
            reviews.value = response.data || []
            console.log("Reviews loaded:", reviews.value)
        }
    } catch (error) {
        console.error("Error fetching reviews:", error)
    } finally {
        loading.value = false
    }
}

// 删除评价
const deleteReview = async (id) => {
    const response = await del("/api/delete_review/" + id)
    if (response.code === 200) {
        message.success("删除成功")
        getReviews()
    }
}

// 格式化日期
const formatDate = (timestamp) => {
    const date = new Date(timestamp)
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`
}

onMounted(() => {
    getReviews()
})

// 监听 storeStore.id 变化
watch(() => storeStore.id, (newId) => {
    if (newId) {
        getReviews()
    }
}, { immediate: true })
</script>

<template>
    <div class="review-page">
        <div class="page-header">
            <h2>评价管理</h2>
            <span class="total">共 {{ reviews.length }} 条评价</span>
        </div>
        
        <div class="review-list" v-loading="loading">
            <div class="review-item" v-for="review in reviews" :key="review.id">
                <div class="review-header">
                    <div class="user-info">
                        <div class="avatar" :style="review.avatar ? { backgroundImage: 'url(' + imgUrl + review.avatar + ')' } : {}">
                            <span v-if="!review.avatar" class="avatar-placeholder">{{ (review.nickname || '匿名')[0] }}</span>
                        </div>
                        <div class="user-detail">
                            <span class="nickname">{{ review.nickname || '匿名用户' }}</span>
                            <div class="stars">
                                <StarFilled v-for="star in (review.rating || 0)" :key="star" class="star active" />
                                <StarFilled v-for="star in (5 - (review.rating || 0))" :key="'empty' + star" class="star" />
                            </div>
                        </div>
                    </div>
                    <div class="review-meta">
                        <span class="date">{{ formatDate(review.createTime) }}</span>
                        <a-popconfirm
                            title="确定删除这条评价吗？"
                            @confirm="deleteReview(review.id)"
                        >
                            <a-button type="link" danger size="small">
                                <DeleteOutlined /> 删除
                            </a-button>
                        </a-popconfirm>
                    </div>
                </div>
                <div class="review-content">{{ review.content }}</div>
                <div class="review-images" v-if="review.images">
                    <img 
                        v-for="(img, index) in review.images.split(',')" 
                        :key="index" 
                        :src="imgUrl + img"
                    />
                </div>
            </div>
            
            <div class="empty" v-if="reviews.length === 0 && !loading">
                <p>暂无评价</p>
            </div>
        </div>
    </div>
</template>

<style scoped>
.review-page {
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

.review-list {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
}

.review-item {
    padding: 20px 0;
    border-bottom: 1px solid #eee;
}

.review-item:last-child {
    border-bottom: none;
}

.review-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
}

.user-info {
    display: flex;
    align-items: center;
}

.avatar {
    width: 40px;
    height: 40px;
    min-width: 40px;
    border-radius: 50%;
    margin-right: 12px;
    background-color: #068cfa;
    background-size: cover;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
}

.avatar-placeholder {
    color: #fff;
    font-size: 16px;
    font-weight: 500;
}

.user-detail {
    display: flex;
    flex-direction: column;
}

.nickname {
    font-weight: 500;
    color: #333;
    font-size: 14px;
}

.stars {
    margin-top: 4px;
    display: flex;
}

.star {
    font-size: 14px;
    color: #ddd;
    margin-right: 2px;
}

.star.active {
    color: #068cfa;
}

.review-meta {
    display: flex;
    align-items: center;
    gap: 10px;
}

.date {
    color: #999;
    font-size: 13px;
}

.review-content {
    margin-top: 12px;
    color: #333;
    line-height: 1.6;
    font-size: 14px;
}

.review-images {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 12px;
}

.review-images img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 4px;
    cursor: pointer;
}

.empty {
    text-align: center;
    padding: 60px 0;
    color: #999;
}
</style>
