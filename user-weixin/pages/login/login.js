"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
const stores_system = require("../../stores/system.js");
const stores_order = require("../../stores/order.js");
require("../../config.js");
const _sfc_main = {
  __name: "login",
  setup(__props) {
    const systemStore = stores_system.useSystemStore();
    const orderStore = stores_order.useOrderStore();
    const getSyetem = async () => {
      const response = await utils_request.request.get("/select_system");
      if (response.code === 200) {
        systemStore.name = response.data.name;
        systemStore.announcement = response.data.announcement;
        systemStore.deliveryPrice = response.data.deliveryPrice;
        systemStore.banner1 = response.data.banner1;
        systemStore.banner2 = response.data.banner2;
        systemStore.banner3 = response.data.banner3;
        orderStore.deliveryPrice = response.data.deliveryPrice;
      }
    };
    common_vendor.onLoad(() => {
      getSyetem();
    });
    common_vendor.onShow(() => {
      getSyetem();
    });
    function goRegister() {
      common_vendor.index.navigateTo({
        url: "/pages/register/register"
      });
    }
    const accountStore = stores_account.useAccountStore();
    const form = common_vendor.reactive({
      username: "",
      password: ""
    });
    const data = common_vendor.ref([]);
    const login = async () => {
      const response = await utils_request.request.post("/login", form);
      if (response.code === 200) {
        if (response.data.status === "已封禁") {
          common_vendor.index.showToast({
            title: "账号已被封禁",
            icon: "error",
            duration: 2e3
          });
        } else {
          data.value = response.data;
          accountStore.id = data.value.id;
          accountStore.username = data.value.username;
          accountStore.role = data.value.role;
          accountStore.phone = data.value.phone;
          accountStore.nickname = data.value.nickname;
          accountStore.avatar = data.value.avatar;
          accountStore.money = data.value.money;
          common_vendor.index.showToast({
            title: "登录成功",
            icon: "success",
            duration: 2e3
          });
          setTimeout(() => {
            common_vendor.index.switchTab({
              url: "/pages/index/index"
            });
          }, 1e3);
        }
      }
    };
    const submitForm = (e) => {
      if (form.username === "") {
        common_vendor.index.showToast({
          title: "账号不能为空",
          icon: "error",
          duration: 2e3
        });
      } else if (form.password === "") {
        common_vendor.index.showToast({
          title: "密码不能为空",
          icon: "error",
          duration: 2e3
        });
      } else {
        login();
      }
    };
    return (_ctx, _cache) => {
      return {
        a: common_assets._imports_0,
        b: common_vendor.t(common_vendor.unref(systemStore).name),
        c: form.username,
        d: common_vendor.o(($event) => form.username = $event.detail.value),
        e: form.password,
        f: common_vendor.o(($event) => form.password = $event.detail.value),
        g: common_vendor.o(goRegister),
        h: common_vendor.o(submitForm)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e4e4508d"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
