"use strict";
Object.defineProperty(exports, Symbol.toStringTag, { value: "Module" });
const common_vendor = require("./common/vendor.js");
if (!Math) {
  "./pages/login/login.js";
  "./pages/register/register.js";
  "./pages/index/index.js";
  "./pages/search/search.js";
  "./pages/store/store.js";
  "./pages/check/check.js";
  "./pages/order/order.js";
  "./pages/order-detail/order-detail.js";
  "./pages/my/my.js";
  "./pages/address/address.js";
  "./pages/review/review.js";
  "./pages/complaint/complaint.js";
  "./pages/my-complaint/my-complaint.js";
}
const _sfc_main = {
  onLaunch: function() {
    console.log("App Launch");
  },
  onShow: function() {
    console.log("App Show");
  },
  onHide: function() {
    console.log("App Hide");
  }
};
const App = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/App.vue"]]);
function createApp() {
  const app = common_vendor.createSSRApp(App);
  app.use(common_vendor.createPinia());
  return {
    app,
    Pinia: common_vendor.Pinia
    // 此处必须将 Pinia 返回
  };
}
createApp().app.mount("#app");
exports.createApp = createApp;
