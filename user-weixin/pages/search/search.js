"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
require("../../config.js");
const _sfc_main = {
  __name: "search",
  setup(__props) {
    const keyword = common_vendor.ref("");
    const products = common_vendor.ref([]);
    const loading = common_vendor.ref(false);
    const searched = common_vendor.ref(false);
    const searchProducts = async () => {
      if (!keyword.value.trim()) {
        common_vendor.index.showToast({ title: "请输入搜索关键词", icon: "none" });
        return;
      }
      loading.value = true;
      searched.value = true;
      try {
        const response = await utils_request.request.get("/search_products", { keyword: keyword.value });
        if (response.code === 200) {
          products.value = response.data || [];
        }
      } finally {
        loading.value = false;
      }
    };
    const goToStore = (storeId) => {
      common_vendor.index.navigateTo({ url: `/pages/store/store?storeId=${storeId}` });
    };
    const formatPrice = (price) => {
      return (price / 100).toFixed(2);
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.o(searchProducts),
        b: keyword.value,
        c: common_vendor.o(($event) => keyword.value = $event.detail.value),
        d: common_vendor.o(searchProducts),
        e: loading.value
      }, loading.value ? {} : searched.value && products.value.length === 0 ? {} : {
        g: common_vendor.f(products.value, (product, k0, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + product.picture,
            b: common_vendor.t(product.name),
            c: common_vendor.t(product.description),
            d: common_vendor.t(product.categoryName),
            e: common_vendor.t(formatPrice(product.price)),
            f: common_vendor.t(product.unit),
            g: product.id,
            h: common_vendor.o(($event) => goToStore(product.storeId), product.id)
          };
        })
      }, {
        f: searched.value && products.value.length === 0
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-c10c040c"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/search/search.vue"]]);
wx.createPage(MiniProgramPage);
