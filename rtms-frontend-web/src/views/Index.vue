<script setup>
import Header from "@/components/index/Header.vue"
import Banner from "@/components/index/Banner.vue"
</script>

<template>
    <Header></Header>
    <Banner></Banner>
    <div class="introduction">
        <div class="cards">
            <div class="card">
                <span class="title">快捷</span>
                <span class="text"
                    >平台提供便捷、快速的外卖服务。即使是交通不便的乡镇，也能让您随时随地享受美食。</span
                >
            </div>
            <div class="card">
                <span class="title">健康</span>
                <span class="text"
                    >平台严选入驻商家资质。提供新鲜、健康的食材和菜品，为您的饮食保驾护航。</span
                >
            </div>
            <div class="card">
                <span class="title">高效</span>
                <span class="text"
                    >平台采用高效的「配送点」方式。以乡镇为单位，设置多个配送地址，覆盖乡镇东西南北。</span
                >
            </div>
        </div>
        <img class="action" src="@/assets/action.png" />
     
    </div>
</template>

<style scoped>
.introduction {
    display: flex;
    width: 80vw;
    margin-left: 10vw;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.cards {
    width: 100%;
    height: 30vh;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.card {
    width: 25vw;
    height: 20vh;
    background: #ffffff;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: 0.5s;
}

.card:hover {
    width: 28vw;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card:hover .title {
    width: 120px;
}

.title {
    font-size: 28px;
    font-weight: 700;
    border-bottom: 5px solid #1677fc;
    transition: 0.5s;
    width: 65px;
    margin-bottom: 16px;
}

.action {
    width: 100%;
    border-radius: 20px 20px 0 0;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.app-footer {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100px;
    margin: 0 40px 0;
    background: #ffffff;
    color: #29292980;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
