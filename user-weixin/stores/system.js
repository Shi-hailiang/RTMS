"use strict";
const common_vendor = require("../common/vendor.js");
const useSystemStore = common_vendor.defineStore("system", {
  state: () => {
    return {
      name: "",
      deliveryPrice: 0,
      announcement: "",
      banner1: "",
      banner2: "",
      banner3: ""
    };
  },
  persist: {
    enabled: true
  }
});
exports.useSystemStore = useSystemStore;
