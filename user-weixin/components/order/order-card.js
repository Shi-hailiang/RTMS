"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
require("../../config.js");
const _sfc_main = {
  __name: "order-card",
  props: ["order"],
  setup(__props) {
    const props = __props;
    const store = common_vendor.ref({});
    const getStore = async () => {
      const response = await utils_request.request.get("/select_store_by_store_id/" + props.order.storeId);
      if (response.code === 200) {
        store.value = response.data;
      }
    };
    getStore();
    const orderProducts = common_vendor.ref([]);
    const orderProductsImgs = common_vendor.ref([]);
    const getProduct = async (productId, productName) => {
      const response = await utils_request.request.get("/select_product_by_id/" + productId);
      if (response.code === 200) {
        let product = {
          name: productName,
          picture: response.data.picture
        };
        orderProductsImgs.value.push(product);
      }
    };
    const getOrderProducts = async () => {
      const response = await utils_request.request.get("/select_order_products_by_order_id/" + props.order.id);
      if (response.code === 200) {
        orderProducts.value = response.data;
        for (let i = 0; i < orderProducts.value.length; i++) {
          getProduct(orderProducts.value[i].productId, orderProducts.value[i].name);
        }
      }
    };
    getOrderProducts();
    const goStore = (storeId) => {
      common_vendor.index.navigateTo({
        url: "/pages/store/store?storeId=" + storeId
      });
    };
    const cancelOrder = (orderId) => {
      common_vendor.index.showModal({
        title: "取消订单",
        content: "确认要取消该订单吗？",
        success: function(res) {
          if (res.confirm) {
            const cancelOrderById = async (orderId2) => {
              const response = await utils_request.request.post("/cancel_order_by_id/" + orderId2);
              if (response.code === 200) {
                common_vendor.index.reLaunch({
                  url: "/pages/order/order"
                });
              }
            };
            cancelOrderById(orderId);
          }
        }
      });
    };
    const cancelOrder2 = (orderId) => {
      common_vendor.index.showModal({
        title: "取消订单",
        content: "确认要取消该订单吗？",
        success: function(res) {
          if (res.confirm) {
            const cancelOrderById = async (orderId2) => {
              const response = await utils_request.request.post("/cancel_order_by_id_and_money_back/" + orderId2);
              if (response.code === 200) {
                common_vendor.index.reLaunch({
                  url: "/pages/order/order"
                });
              }
            };
            cancelOrderById(orderId);
          }
        }
      });
    };
    const deleteOrder = (orderId) => {
      common_vendor.index.showModal({
        title: "删除订单",
        content: "确认要删除该订单吗？",
        success: function(res) {
          if (res.confirm) {
            const deleteOrderById = async (orderId2) => {
              const response = await utils_request.request.post("/customer_delete_order_by_id/" + orderId2);
              if (response.code === 200) {
                common_vendor.index.reLaunch({
                  url: "/pages/order/order"
                });
              }
            };
            deleteOrderById(orderId);
          }
        }
      });
    };
    const completeOrder = (orderId) => {
      common_vendor.index.showModal({
        title: "确认订单",
        content: "确认已经取到餐了吗？",
        success: function(res) {
          if (res.confirm) {
            const completeOrderByOrderId = async (orderId2) => {
              const response = await utils_request.request.post("/customer_completed_order_by_id/" + orderId2);
              if (response.code === 200) {
                await utils_request.request.post("/update_account_money_after_customer_completed/" + orderId2);
              }
            };
            completeOrderByOrderId(orderId);
            common_vendor.index.showToast({
              title: "订单已完成",
              icon: "success",
              mask: true
            });
            setTimeout(() => {
              common_vendor.index.reLaunch({
                url: "/pages/order/order"
              });
            }, 1e3);
          }
        }
      });
    };
    const goOrderDetail = (orderId) => {
      common_vendor.index.navigateTo({
        url: "/pages/order-detail/order-detail?orderId=" + orderId
      });
    };
    function add30Minutes(time) {
      const [hours, minutes] = time.split(":").map(Number);
      const totalMinutes = hours * 60 + minutes;
      const newTotalMinutes = totalMinutes + 30;
      const newHours = Math.floor(newTotalMinutes / 60);
      const newMinutes = newTotalMinutes % 60;
      const newTime = `${newHours.toString().padStart(2, "0")}:${newMinutes.toString().padStart(2, "0")}`;
      return newTime;
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.unref(utils_img.imgUrl) + store.value.logo,
        b: common_vendor.t(store.value.name),
        c: common_vendor.o(($event) => goStore(store.value.id)),
        d: __props.order.progress !== "配送中"
      }, __props.order.progress !== "配送中" ? {
        e: common_vendor.t(__props.order.progress)
      } : {
        f: common_vendor.t(add30Minutes(__props.order.expectedTime))
      }, {
        g: common_vendor.f(orderProductsImgs.value, (orderProduct, index, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + orderProduct.picture,
            b: common_vendor.t(orderProduct.name),
            c: index
          };
        }),
        h: common_vendor.t(__props.order.totalPrice / 100),
        i: common_vendor.t(orderProducts.value.length),
        j: common_vendor.o(($event) => goOrderDetail(__props.order.id)),
        k: __props.order.progress === "已完成" || __props.order.progress === "已取消",
        l: common_vendor.o(($event) => deleteOrder(__props.order.id)),
        m: __props.order.progress === "已完成",
        n: common_vendor.o(($event) => goStore(store.value.id)),
        o: __props.order.progress === "待支付",
        p: common_vendor.o(($event) => cancelOrder(__props.order.id)),
        q: __props.order.progress === "已支付",
        r: common_vendor.o(($event) => cancelOrder2(__props.order.id)),
        s: __props.order.progress === "已接单",
        t: common_vendor.o(($event) => cancelOrder2(__props.order.id)),
        v: common_vendor.o(($event) => goOrderDetail(__props.order.id)),
        w: __props.order.progress === "已送达",
        x: common_vendor.o(($event) => completeOrder(__props.order.id))
      });
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-a62a017a"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/order/order-card.vue"]]);
wx.createComponent(Component);
