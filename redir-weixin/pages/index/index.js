"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
const stores_account = require("../../stores/account.js");
const stores_rider = require("../../stores/rider.js");
require("../../config.js");
const _sfc_main = {
  __name: "index",
  setup(__props) {
    stores_account.useAccountStore();
    const riderStore = stores_rider.useRiderStore();
    const orders = common_vendor.ref([]);
    const store = common_vendor.ref([]);
    const orderCards = common_vendor.ref([]);
    const getStore = async (storeId, i) => {
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
    const getOrders = async () => {
      const response = await utils_request.request.get("/select_to_be_delivered_orders");
      if (response.code === 200) {
        orders.value = response.data;
        for (let i = 0; i < orders.value.length; i++) {
          getStore(orders.value[i].storeId, i);
        }
      }
    };
    const riderAcceptOrderById = async (orderId) => {
      const response = await utils_request.request.post(
        "/rider_accept_order_by_id/" + orderId + "/" + riderStore.id
      );
      if (response.code === 200) {
        orderCards.value = [];
        getOrders();
        common_vendor.index.showToast({
          title: "任务接受成功，请尽快完成配送",
          icon: "none"
        });
      }
    };
    const riderAcceptOrder = (orderId) => {
      common_vendor.index.showModal({
        title: "接受任务",
        content: "确认要接受该任务吗？",
        success: function(res) {
          if (res.confirm) {
            riderAcceptOrderById(orderId);
          }
        }
      });
    };
    getOrders();
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: orderCards.value.length === 0
      }, orderCards.value.length === 0 ? {
        b: common_assets._imports_0$1
      } : {}, {
        c: common_vendor.f(orderCards.value, (orderCard, k0, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + orderCard.storeLogo,
            b: common_vendor.t(orderCard.storeName),
            c: common_vendor.t(orderCard.storePhone),
            d: common_vendor.t(orderCard.storeAddress),
            e: common_vendor.t(orderCard.orderAddress),
            f: common_vendor.t(orderCard.expectedTime),
            g: common_vendor.o(($event) => riderAcceptOrder(orderCard.orderId), orderCard.orderId),
            h: orderCard.orderId
          };
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-rider/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
