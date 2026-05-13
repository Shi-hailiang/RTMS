"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "register",
  setup(__props) {
    function goLogin() {
      common_vendor.index.navigateBack({
        url: "/pages/login/login"
      });
    }
    stores_account.useAccountStore();
    const form = common_vendor.reactive({
      username: "",
      password: "",
      checkPassword: "",
      role: "",
      nickname: "",
      phone: ""
    });
    common_vendor.ref([]);
    const register = async () => {
      const response = await utils_request.request.post("/register", form);
      if (response.code === 200) {
        common_vendor.index.showToast({
          title: "注册成功",
          icon: "success",
          duration: 2e3
        });
        setTimeout(() => {
          common_vendor.index.navigateBack({
            url: "/pages/login/login"
          });
        }, 1e3);
      }
    };
    const phoneRegex = /^\d{11}$/;
    const isPhoneValid = common_vendor.ref(false);
    const validatePhone = () => {
      isPhoneValid.value = phoneRegex.test(form.phone);
    };
    common_vendor.watchEffect(() => {
      validatePhone();
    });
    const submitForm = (e) => {
      if (form.username === "") {
        common_vendor.index.showToast({
          title: "账号不能为空",
          icon: "error",
          duration: 2e3
        });
      } else if (form.password === "") {
        common_vendor.index.showToast({
          title: "密码不能为空",
          icon: "error",
          duration: 2e3
        });
      } else if (form.checkPassword === "") {
        common_vendor.index.showToast({
          title: "确认密码不能为空",
          icon: "none",
          duration: 2e3
        });
      } else if (form.password !== form.checkPassword) {
        common_vendor.index.showToast({
          title: "两次密码不一致",
          icon: "none",
          duration: 2e3
        });
      } else if (form.nickname === "") {
        common_vendor.index.showToast({
          title: "昵称不能为空",
          icon: "none",
          duration: 2e3
        });
      } else if (form.phone === "") {
        common_vendor.index.showToast({
          title: "手机号码不能为空",
          icon: "none",
          duration: 2e3
        });
      } else if (isPhoneValid.value === false) {
        common_vendor.index.showToast({
          title: "手机号码应为11位数字",
          icon: "none",
          duration: 2e3
        });
      } else {
        register();
      }
    };
    return (_ctx, _cache) => {
      return {
        a: form.username,
        b: common_vendor.o(($event) => form.username = $event.detail.value),
        c: form.password,
        d: common_vendor.o(($event) => form.password = $event.detail.value),
        e: form.checkPassword,
        f: common_vendor.o(($event) => form.checkPassword = $event.detail.value),
        g: form.nickname,
        h: common_vendor.o(($event) => form.nickname = $event.detail.value),
        i: form.phone,
        j: common_vendor.o(($event) => form.phone = $event.detail.value),
        k: common_vendor.o(goLogin),
        l: common_vendor.o(submitForm)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-bac4a35d"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/register/register.vue"]]);
wx.createPage(MiniProgramPage);
