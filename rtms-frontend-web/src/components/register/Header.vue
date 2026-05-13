<script setup>

import router from "@/router/index.js";
</script>

<template>
  <div id="header">
    <div id="container">
      <div id="header-left">
        <div id="logo" @click="router.push('/')">
          <img src="@/assets/hero.png" alt="logo">
          <span>舌尖急先锋</span>
        </div>
      </div>
      <div id="header-right">
        <div id="menu">
          <div class="menu-item" @click="router.push('/')">首页页</div>
          <div class="menu-item" @click="router.push('/login')">登录</div>
          <div class="menu-item active" @click="router.push('/register')">注册</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#header {
  width: 100%;
  height: 60px;
  background: rgba(255, 255, 255, 0.90);
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

#container {
  width: 1200px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#header-left {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#header-right {
  width: 50%;
  height: 100%;
}

#logo {
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  user-select: none;
}

#logo:hover {
  cursor: pointer;
}

#logo img {
  height: 35px;
  width: 35px;
  margin-right: 5px;
}

#logo span {
  font-size: 18px;
  font-weight: 500;
}

#menu {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.menu-item {
  margin-left: 10px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 10px;
  font-size: 18px;
  border-bottom: 5px solid rgba(255, 255, 255, 0);
  user-select: none;
}

.menu-item:hover {
  cursor: pointer;
  font-weight: 500;
}

.active {
  border-bottom: 5px solid #068cfa;
  font-weight: 500;
}
</style>