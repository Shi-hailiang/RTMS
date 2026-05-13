"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_system = require("../../stores/system.js");
const stores_order = require("../../stores/order.js");
require("../../config.js");
if (!Math) {
  (banner + announcement + storeType + store)();
}
const banner = () => "../../components/index/banner.js";
const announcement = () => "../../components/index/announcement.js";
const store = () => "../../components/index/store.js";
const storeType = () => "../../components/index/store-type.js";
const _sfc_main = {
  __name: "index",
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
        systemStore.deliveryPrice = response.data.deliveryPrice;
        orderStore.deliveryPrice = response.data.deliveryPrice;
        console.log(orderStore.deliveryPrice);
      }
    };
    const stores = common_vendor.ref([]);
    const getStores = async () => {
      const response = await utils_request.request.get("/select_stores");
      if (response.code === 200) {
        stores.value = response.data;
      }
    };
    getSyetem();
    getStores();
    common_vendor.onShow(() => {
      getStores();
    });
    const getStoresByType = async (type) => {
      const response = await utils_request.request.get("/select_stores_by_type/" + type);
      if (response.code === 200) {
        stores.value = response.data;
      }
    };
    const goToSearch = () => {
      common_vendor.index.navigateTo({ url: "/pages/search/search" });
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(goToSearch),
        b: common_vendor.o(getStoresByType),
        c: common_vendor.o(getStores),
        d: common_vendor.f(stores.value, (store2, k0, i0) => {
          return {
            a: "1cf27b2a-3-" + i0,
            b: common_vendor.p({
              store: store2
            }),
            c: store2.id
          };
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
