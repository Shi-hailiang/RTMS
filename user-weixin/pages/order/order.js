"use strict";
const common_vendor = require("../../common/vendor.js");
const stores_account = require("../../stores/account.js");
const utils_request = require("../../utils/request.js");
require("../../config.js");
if (!Math) {
  OrderCard();
}
const OrderCard = () => "../../components/order/order-card.js";
const _sfc_main = {
  __name: "order",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const orders = common_vendor.ref([]);
    const selectedMenuItem = common_vendor.ref(1);
    const getOrders = async () => {
      switch (selectedMenuItem.value) {
        case 1: {
          const response = await utils_request.request.get(
            "/select_orders_by_account_id/" + accountStore.id
          );
          if (response.code === 200) {
            orders.value = response.data;
          }
          break;
        }
        case 2: {
          const response = await utils_request.request.get(
            "/select_pending_payment_orders_by_account_id/" + accountStore.id
          );
          if (response.code === 200) {
            orders.value = response.data;
          }
          break;
        }
        case 3: {
          const response = await utils_request.request.get(
            "/select_delivering_orders_by_account_id/" + accountStore.id
          );
          if (response.code === 200) {
            orders.value = response.data;
          }
          break;
        }
        case 4: {
          const response = await utils_request.request.get(
            "/select_completed_orders_by_account_id/" + accountStore.id
          );
          if (response.code === 200) {
            orders.value = response.data;
          }
          break;
        }
      }
      for (let i = 0; i < orders.value.length; i++) {
        const order = orders.value[i];
        if (order.status.includes("顾客")) {
          orders.value.splice(i, 1);
          i--;
        }
      }
    };
    const getAllOrders = () => {
      selectedMenuItem.value = 1;
      getOrders();
    };
    const getPendingPaymentOrders = () => {
      selectedMenuItem.value = 2;
      getOrders();
    };
    const getDeliveringOrders = () => {
      selectedMenuItem.value = 3;
      getOrders();
    };
    const getCompletedOrders = () => {
      selectedMenuItem.value = 4;
      getOrders();
    };
    common_vendor.onLoad(() => {
      getOrders();
    });
    common_vendor.onShow(() => {
      getOrders();
    });
    return (_ctx, _cache) => {
      return {
        a: selectedMenuItem.value === 1 ? 1 : "",
        b: common_vendor.o(getAllOrders),
        c: selectedMenuItem.value === 2 ? 1 : "",
        d: common_vendor.o(getPendingPaymentOrders),
        e: selectedMenuItem.value === 3 ? 1 : "",
        f: common_vendor.o(getDeliveringOrders),
        g: selectedMenuItem.value === 4 ? 1 : "",
        h: common_vendor.o(getCompletedOrders),
        i: common_vendor.f(orders.value, (order, k0, i0) => {
          return {
            a: order.id,
            b: "93207a4f-0-" + i0,
            c: common_vendor.p({
              order
            })
          };
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-93207a4f"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/order/order.vue"]]);
wx.createPage(MiniProgramPage);
