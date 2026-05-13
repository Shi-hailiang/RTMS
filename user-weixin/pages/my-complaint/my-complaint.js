"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "my-complaint",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const complaints = common_vendor.ref([]);
    const imgUrl = "http://localhost:8090/api/download_img?img=";
    common_vendor.onShow(() => {
      getComplaints();
    });
    const getComplaints = async () => {
      const response = await utils_request.request.get("/select_complaints_by_account_id/" + accountStore.id);
      if (response.code === 200) {
        complaints.value = response.data || [];
      }
    };
    const formatDate = (timestamp) => {
      const date = new Date(timestamp);
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
    };
    const getStatusColor = (status) => {
      return status === "待处理" ? "#068cfa" : "#52c41a";
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.f(complaints.value, (item, k0, i0) => {
          return common_vendor.e({
            a: item.storeLogo
          }, item.storeLogo ? {
            b: imgUrl + item.storeLogo
          } : {}, {
            c: common_vendor.t(item.storeName),
            d: common_vendor.t(item.status),
            e: getStatusColor(item.status),
            f: common_vendor.t(item.type),
            g: common_vendor.t(item.content),
            h: item.images
          }, item.images ? {
            i: common_vendor.f(item.images.split(","), (img, index, i1) => {
              return {
                a: index,
                b: imgUrl + img,
                c: common_vendor.o(($event) => _ctx.uni.previewImage({
                  urls: item.images.split(",").map((i) => imgUrl + i),
                  current: imgUrl + img
                }), index)
              };
            })
          } : {}, {
            j: common_vendor.t(formatDate(item.createTime)),
            k: item.status === "已处理"
          }, item.status === "已处理" ? common_vendor.e({
            l: common_vendor.t(item.result),
            m: item.reply
          }, item.reply ? {
            n: common_vendor.t(item.reply)
          } : {}, {
            o: common_vendor.t(formatDate(item.handleTime))
          }) : {}, {
            p: item.id
          });
        }),
        b: complaints.value.length === 0
      }, complaints.value.length === 0 ? {} : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-35c67724"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/my-complaint/my-complaint.vue"]]);
wx.createPage(MiniProgramPage);
