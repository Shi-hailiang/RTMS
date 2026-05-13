"use strict";
const common_vendor = require("../common/vendor.js");
const useAccountStore = common_vendor.defineStore("account", {
  state: () => {
    return {
      id: "",
      username: "",
      role: "",
      phone: "",
      nickname: "未登录",
      avatar: "default_avatar.png",
      money: 0
    };
  },
  persist: {
    enabled: true
  }
});
exports.useAccountStore = useAccountStore;
