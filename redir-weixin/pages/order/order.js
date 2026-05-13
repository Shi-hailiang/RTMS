"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
const stores_rider = require("../../stores/rider.js");
require("../../config.js");
const _sfc_main = {
  __name: "order",
  setup(__props) {
    const riderStore = stores_rider.useRiderStore();
    const orders = common_vendor.ref([]);
    const store = common_vendor.ref([]);
    const orderCards = common_vendor.ref([]);
    const selectedMenuItem = common_vendor.ref(1);
    const changeMenuItem = (menuItem) => {
      selectedMenuItem.value = menuItem;
      switch (menuItem) {
        case 1: {
          getOrdersByRiderIdAndProgress(riderStore.id, "配送中");
          break;
        }
        case 2: {
          getOrdersByRiderIdAndProgress(riderStore.id, "已送达");
          break;
        }
        case 3: {
          getOrdersByRiderIdAndProgress(riderStore.id, "已完成");
          break;
        }
      }
    };
    const getOrdersByRiderIdAndProgress = async (riderId, progress) => {
      const response = await utils_request.request.get(
        "/select_orders_by_rider_id_and_progress/" + riderId + "/" + progress
      );
      if (response.code === 200) {
        orders.value = response.data;
        loadOrderCards();
      }
    };
    const getStoreByStoreId = async (storeId, i) => {
      const response = await utils_request.request.get("/select_store_by_store_id/" + storeId);
      if (response.code === 200) {
        store.value = response.data;
        const orderCard = {
          orderId: orders.value[i].id,
          storeName: store.value.name,
          storeAddress: store.value.address,
          storeLogo: store.value.logo,
          storePhone: store.value.phone,
          orderAddress: orders.value[i].address,
          expectedTime: orders.value[i].expectedTime
        };
        orderCards.value.push(orderCard);
      }
    };
    const loadOrderCards = () => {
      orderCards.value = [];
      for (let i = 0; i < orders.value.length; i++) {
        getStoreByStoreId(orders.value[i].storeId, i);
      }
    };
    const getOrderDetail = (orderId) => {
      common_vendor.index.navigateTo({
        url: "/pages/order-detail/order-detail?orderId=" + orderId
      });
    };
    common_vendor.onShow(() => {
      switch (selectedMenuItem.value) {
        case 1: {
          getOrdersByRiderIdAndProgress(riderStore.id, "配送中");
          break;
        }
        case 2: {
          getOrdersByRiderIdAndProgress(riderStore.id, "已送达");
          break;
        }
        case 3: {
          getOrdersByRiderIdAndProgress(riderStore.id, "已完成");
          break;
        }
      }
    });
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: selectedMenuItem.value === 1 ? 1 : "",
        b: common_vendor.o(($event) => changeMenuItem(1)),
        c: selectedMenuItem.value === 2 ? 1 : "",
        d: common_vendor.o(($event) => changeMenuItem(2)),
        e: selectedMenuItem.value === 3 ? 1 : "",
        f: common_vendor.o(($event) => changeMenuItem(3)),
        g: orderCards.value.length === 0
      }, orderCards.value.length === 0 ? {
        h: common_assets._imports_0$1
      } : {}, {
        i: common_vendor.f(orderCards.value, (orderCard, k0, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + orderCard.storeLogo,
            b: common_vendor.t(orderCard.storeName),
            c: common_vendor.t(orderCard.storePhone),
            d: common_vendor.t(orderCard.storeAddress),
            e: common_vendor.t(orderCard.orderAddress),
            f: common_vendor.t(orderCard.expectedTime),
            g: common_vendor.o(($event) => getOrderDetail(orderCard.orderId), orderCard.orderId),
            h: orderCard.orderId
          };
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-93207a4f"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-rider/pages/order/order.vue"]]);
wx.createPage(MiniProgramPage);
