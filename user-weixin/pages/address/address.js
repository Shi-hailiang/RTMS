"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "address",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const addresses = common_vendor.ref([]);
    const newAddress = common_vendor.ref("");
    const isAdding = common_vendor.ref(false);
    const getAddresses = async () => {
      const response = await utils_request.request.get("/select_addresses_by_account_id/" + accountStore.id);
      if (response.code === 200) {
        addresses.value = response.data || [];
      }
    };
    common_vendor.onShow(() => {
      getAddresses();
    });
    const addAddress = async () => {
      if (!newAddress.value.trim()) {
        common_vendor.index.showToast({ title: "请输入地址", icon: "none" });
        return;
      }
      const response = await utils_request.request.post("/create_address", {
        accountId: accountStore.id,
        address: newAddress.value
      });
      if (response.code === 200) {
        common_vendor.index.showToast({ title: "添加成功", icon: "success" });
        newAddress.value = "";
        isAdding.value = false;
        getAddresses();
      }
    };
    const deleteAddress = (id) => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定删除该地址吗？",
        success: async (res) => {
          if (res.confirm) {
            const response = await utils_request.request.del("/delete_address/" + id);
            if (response.code === 200) {
              common_vendor.index.showToast({ title: "删除成功", icon: "success" });
              getAddresses();
            }
          }
        }
      });
    };
    const showAddForm = () => {
      isAdding.value = true;
    };
    const cancelAdd = () => {
      isAdding.value = false;
      newAddress.value = "";
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: addresses.value.length > 0
      }, addresses.value.length > 0 ? {
        b: common_vendor.f(addresses.value, (item, k0, i0) => {
          return {
            a: common_vendor.t(item.address),
            b: common_vendor.o(($event) => deleteAddress(item.id), item.id),
            c: item.id
          };
        })
      } : {}, {
        c: isAdding.value
      }, isAdding.value ? {
        d: newAddress.value,
        e: common_vendor.o(($event) => newAddress.value = $event.detail.value),
        f: common_vendor.o(cancelAdd),
        g: common_vendor.o(addAddress)
      } : {}, {
        h: !isAdding.value
      }, !isAdding.value ? {
        i: common_vendor.o(showAddForm)
      } : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-40ca010a"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/address/address.vue"]]);
wx.createPage(MiniProgramPage);
