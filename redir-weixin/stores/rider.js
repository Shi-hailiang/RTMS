"use strict";
const common_vendor = require("../common/vendor.js");
const useRiderStore = common_vendor.defineStore("rider", {
  state: () => {
    return {
      id: "",
      accountId: "",
      name: "",
      idNumber: "",
      gender: "",
      phone: ""
    };
  },
  persist: {
    enabled: true
  }
});
exports.useRiderStore = useRiderStore;
