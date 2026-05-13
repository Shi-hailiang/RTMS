"use strict";
const common_vendor = require("../../common/vendor.js");
const stores_system = require("../../stores/system.js");
const utils_img = require("../../utils/img.js");
const _sfc_main = {
  __name: "banner",
  setup(__props) {
    const systemStore = stores_system.useSystemStore();
    return (_ctx, _cache) => {
      return {
        a: common_vendor.unref(utils_img.imgUrl) + common_vendor.unref(systemStore).banner1,
        b: common_vendor.unref(utils_img.imgUrl) + common_vendor.unref(systemStore).banner2,
        c: common_vendor.unref(utils_img.imgUrl) + common_vendor.unref(systemStore).banner3
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-fb69ceba"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/index/banner.vue"]]);
wx.createComponent(Component);
