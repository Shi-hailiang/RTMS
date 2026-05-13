"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "store-type",
  emits: ["getStoresByType"],
  setup(__props, { emit: emits }) {
    const getStoresByType = (type) => {
      emits("getStoresByType", type);
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(($event) => getStoresByType("水果果干")),
        b: common_vendor.o(($event) => getStoresByType("中餐美食")),
        c: common_vendor.o(($event) => getStoresByType("名吃快餐")),
        d: common_vendor.o(($event) => getStoresByType("火锅烧烤")),
        e: common_vendor.o(($event) => getStoresByType("汉堡炸鸡")),
        f: common_vendor.o(($event) => getStoresByType("蛋糕甜点")),
        g: common_vendor.o(($event) => getStoresByType("奶茶饮品")),
        h: common_vendor.o(($event) => getStoresByType("超市便利"))
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-25cde9eb"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/index/store-type.vue"]]);
wx.createComponent(Component);
