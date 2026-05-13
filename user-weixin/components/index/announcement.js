"use strict";
const common_vendor = require("../../common/vendor.js");
const stores_system = require("../../stores/system.js");
const _sfc_main = {
  __name: "announcement",
  setup(__props) {
    const systemStore = stores_system.useSystemStore();
    const translateValue = common_vendor.ref(80);
    setInterval(() => {
      if (translateValue.value == 0) {
        setTimeout(() => {
          translateValue.value -= 1;
        }, 1e3);
      } else if (translateValue.value > -80) {
        translateValue.value -= 1;
      } else {
        translateValue.value = 80;
      }
    }, 20);
    return (_ctx, _cache) => {
      return {
        a: common_vendor.t(common_vendor.unref(systemStore).announcement),
        b: "translateY(" + translateValue.value + "rpx)"
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-afbbbf37"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/index/announcement.vue"]]);
wx.createComponent(Component);
