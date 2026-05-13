"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
const stores_rider = require("../../stores/rider.js");
require("../../config.js");
const _sfc_main = {
  __name: "login",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const riderStore = stores_rider.useRiderStore();
    const form = common_vendor.reactive({
      username: "",
      password: ""
    });
    const data = common_vendor.ref([]);
    const login = async () => {
      const response = await utils_request.request.post("/login", form);
      if (response.code === 200) {
        data.value = response.data;
        if (response.data.status === "已封禁") {
          common_vendor.index.showToast({
            title: "账号已被封禁",
            icon: "error",
            duration: 2e3
          });
        } else {
          if (data.value.role !== "骑手") {
            common_vendor.index.showToast({
              title: "非骑手账号请使用顾客小程序端登录",
              icon: "none",
              duration: 2e3
            });
          } else {
            accountStore.id = data.value.id;
            accountStore.username = data.value.username;
            accountStore.role = data.value.role;
            accountStore.phone = data.value.phone;
            accountStore.nickname = data.value.nickname;
            accountStore.avatar = data.value.avatar;
            accountStore.money = data.value.money;
            const getRiderByAccountId = async () => {
              const response2 = await utils_request.request.get(
                "/select_rider_by_account_id/" + accountStore.id
              );
              if (response2.code === 200) {
                riderStore.id = response2.data.id;
                riderStore.accountId = response2.data.accountId;
                riderStore.name = response2.data.name;
                riderStore.idNumber = response2.data.idNumber;
                riderStore.gender = response2.data.gender;
                riderStore.phone = response2.data.phone;
                common_vendor.index.showToast({
                  title: "登录成功",
                  icon: "success",
                  duration: 2e3
                });
                setTimeout(() => {
                  common_vendor.index.switchTab({
                    url: "/pages/index/index"
                  });
                }, 1e3);
              }
            };
            getRiderByAccountId();
          }
        }
      }
    };
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
      } else {
        login();
      }
    };
    return (_ctx, _cache) => {
      return {
        a: common_assets._imports_0,
        b: form.username,
        c: common_vendor.o(($event) => form.username = $event.detail.value),
        d: form.password,
        e: common_vendor.o(($event) => form.password = $event.detail.value),
        f: common_vendor.o(submitForm)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e4e4508d"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-rider/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
