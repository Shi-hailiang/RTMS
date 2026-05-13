"use strict";
const common_vendor = require("../common/vendor.js");
const useOrderStore = common_vendor.defineStore("order", {
  state: () => {
    return {
      accountId: "",
      storeId: "",
      note: "",
      expectedTime: "",
      address: "",
      phone: "",
      packagePrice: 0,
      deliveryPrice: 0,
      totalPrice: 0,
      payment: "余额支付",
      orderProducts: {}
    };
  },
  persist: {
    enabled: true
  }
});
exports.useOrderStore = useOrderStore;
