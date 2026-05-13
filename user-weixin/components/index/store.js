"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_img = require("../../utils/img.js");
const _sfc_main = {
  __name: "store",
  props: ["store"],
  setup(__props) {
    const props = __props;
    const store = props.store;
    const logoUrl = utils_img.imgUrl + store.logo;
    function goStore(storeId) {
      common_vendor.index.navigateTo({
        url: "/pages/store/store?storeId=" + storeId
      });
    }
    return (_ctx, _cache) => {
      return {
        a: logoUrl,
        b: common_vendor.t(common_vendor.unref(store).name),
        c: common_vendor.t(common_vendor.unref(store).packagePrice / 100),
        d: common_vendor.t(common_vendor.unref(store).businessHour),
        e: common_vendor.t(common_vendor.unref(store).description),
        f: common_vendor.o(($event) => goStore(common_vendor.unref(store).id))
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-bf3e9253"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/index/store.vue"]]);
wx.createComponent(Component);
