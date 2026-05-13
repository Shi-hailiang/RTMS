"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
const stores_order = require("../../stores/order.js");
require("../../config.js");
const _sfc_main = {
  __name: "check",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const orderStore = stores_order.useOrderStore();
    const orderProduct = common_vendor.ref([]);
    const addresses = common_vendor.ref([]);
    const isAddressesOpen = common_vendor.ref(false);
    orderProduct.value = orderStore.orderProducts;
    const getSyetem = async () => {
      const response = await utils_request.request.get("/select_system");
      if (response.code === 200) {
        orderStore.deliveryPrice = response.data.deliveryPrice;
        orderStore.totalPrice = orderStore.totalPrice + orderStore.deliveryPrice;
      }
    };
    getSyetem();
    common_vendor.onLoad((option) => {
      const getAddresses = async () => {
        const response = await utils_request.request.get("/select_addresses_by_account_id/" + accountStore.id);
        if (response.code === 200) {
          addresses.value = response.data;
        }
      };
      getAddresses();
    });
    const openOrCloseAddresses = () => {
      isAddressesOpen.value = !isAddressesOpen.value;
    };
    const changeAddress = (address) => {
      orderStore.address = address;
      openOrCloseAddresses();
    };
    const setExpectedTime = (event) => {
      orderStore.expectedTime = event.detail.value;
    };
    const submitOrder = () => {
      if (orderStore.accountId === "") {
        common_vendor.index.showToast({
          title: "未登录，请先登录",
          icon: "none",
          duration: 1e3
        });
      } else if (orderStore.address === "") {
        common_vendor.index.showToast({
          title: "请选择配送地点",
          icon: "none",
          duration: 1e3
        });
      } else if (orderStore.expectedTime === "") {
        common_vendor.index.showToast({
          title: "请选择配送时间",
          icon: "none",
          duration: 1e3
        });
      } else {
        let newOrder = {
          accountId: orderStore.accountId,
          storeId: orderStore.storeId,
          note: orderStore.note,
          expectedTime: orderStore.expectedTime,
          address: orderStore.address,
          phone: orderStore.phone,
          packagePrice: orderStore.packagePrice,
          deliveryPrice: orderStore.deliveryPrice,
          totalPrice: orderStore.totalPrice,
          payment: orderStore.payment
        };
        const createOrderProduct = async (orderId) => {
          let newOrderProduct = {
            orderId,
            productId: 0,
            name: "",
            price: 0,
            number: 0,
            specification: "",
            totalPrice: 0
          };
          let i = 0;
          console.log(orderStore.orderProducts.length);
          for (i = 0; i < orderStore.orderProducts.length; i++) {
            console.log(i);
            newOrderProduct.productId = orderStore.orderProducts[i].productId;
            newOrderProduct.name = orderStore.orderProducts[i].name;
            newOrderProduct.price = orderStore.orderProducts[i].price;
            newOrderProduct.number = orderStore.orderProducts[i].number;
            newOrderProduct.specification = orderStore.orderProducts[i].specification;
            newOrderProduct.totalPrice = orderStore.orderProducts[i].totalPrice;
            await utils_request.request.post("/create_order_product", newOrderProduct);
            console.log("订单产品数据创建成功：" + newOrderProduct);
          }
          orderStore.$reset();
          common_vendor.index.switchTab({
            url: "/pages/order/order"
          });
        };
        const createOrder = async () => {
          const response = await utils_request.request.post("/create_order", newOrder);
          if (response.code === 200) {
            common_vendor.index.showToast({
              title: "提交成功",
              icon: "success",
              duration: 1e3
            });
            let orderId = response.data;
            createOrderProduct(orderId);
          }
        };
        createOrder();
      }
    };
    function getCurrentTime() {
      const now = /* @__PURE__ */ new Date();
      let hours = now.getHours();
      let minutes = now.getMinutes();
      hours = hours < 10 ? "0" + hours : hours;
      minutes = minutes < 10 ? "0" + minutes : minutes;
      const currentTime = hours + ":" + minutes;
      return currentTime;
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.unref(orderStore).address !== ""
      }, common_vendor.unref(orderStore).address !== "" ? {
        b: common_vendor.t(common_vendor.unref(orderStore).address)
      } : {}, {
        c: common_vendor.o(openOrCloseAddresses),
        d: common_vendor.f(addresses.value, (address, k0, i0) => {
          return {
            a: common_vendor.t(address.address),
            b: common_vendor.o(($event) => changeAddress(address.address), address.id),
            c: address.id
          };
        }),
        e: isAddressesOpen.value ? 1 : "",
        f: common_vendor.unref(orderStore).expectedTime === ""
      }, common_vendor.unref(orderStore).expectedTime === "" ? {
        g: getCurrentTime(),
        h: getCurrentTime(),
        i: common_vendor.o(setExpectedTime)
      } : {
        j: common_vendor.t(common_vendor.unref(orderStore).expectedTime),
        k: common_vendor.unref(orderStore).expectedTime,
        l: getCurrentTime(),
        m: common_vendor.o(setExpectedTime)
      }, {
        n: common_vendor.f(common_vendor.unref(orderStore).orderProducts, (orderProduct2, k0, i0) => {
          return {
            a: common_vendor.t(orderProduct2.name),
            b: common_vendor.t(orderProduct2.specification),
            c: common_vendor.t(orderProduct2.number),
            d: common_vendor.t(orderProduct2.price / 100),
            e: common_vendor.t(orderProduct2.totalPrice / 100),
            f: orderProduct2.id
          };
        }),
        o: common_vendor.t(common_vendor.unref(orderStore).packagePrice / 100),
        p: common_vendor.t(common_vendor.unref(orderStore).deliveryPrice / 100),
        q: common_vendor.t(common_vendor.unref(orderStore).totalPrice / 100),
        r: common_vendor.unref(orderStore).note,
        s: common_vendor.o(($event) => common_vendor.unref(orderStore).note = $event.detail.value),
        t: common_vendor.o(submitOrder)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-d5c0dd20"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/check/check.vue"]]);
wx.createPage(MiniProgramPage);
