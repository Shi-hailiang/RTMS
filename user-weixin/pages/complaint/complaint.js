"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "complaint",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const orderId = common_vendor.ref(0);
    const storeId = common_vendor.ref(0);
    const storeName = common_vendor.ref("");
    const complaintType = common_vendor.ref("");
    const content = common_vendor.ref("");
    const images = common_vendor.ref([]);
    const uploading = common_vendor.ref(false);
    const typeOptions = ["食品问题", "配送问题", "服务态度", "虚假宣传", "其他"];
    common_vendor.onLoad((option) => {
      orderId.value = option.orderId;
      storeId.value = option.storeId;
      storeName.value = decodeURIComponent(option.storeName || "");
    });
    const selectType = (type) => {
      complaintType.value = type;
    };
    const chooseImage = () => {
      common_vendor.index.chooseImage({
        count: 3 - images.value.length,
        sizeType: ["compressed"],
        sourceType: ["album", "camera"],
        success: (res) => {
          res.tempFilePaths.forEach((path) => {
            uploadImage(path);
          });
        }
      });
    };
    const uploadImage = (filePath) => {
      uploading.value = true;
      common_vendor.index.uploadFile({
        url: "http://localhost:8090/api/upload_img",
        filePath,
        name: "file",
        success: (res) => {
          const data = JSON.parse(res.data);
          if (data.code === 200) {
            images.value.push(data.data);
          }
        },
        complete: () => {
          uploading.value = false;
        }
      });
    };
    const removeImage = (index) => {
      images.value.splice(index, 1);
    };
    const submitComplaint = async () => {
      if (!complaintType.value) {
        common_vendor.index.showToast({ title: "请选择投诉类型", icon: "none" });
        return;
      }
      if (!content.value.trim()) {
        common_vendor.index.showToast({ title: "请输入投诉内容", icon: "none" });
        return;
      }
      const complaint = {
        orderId: orderId.value,
        accountId: accountStore.id,
        storeId: storeId.value,
        type: complaintType.value,
        content: content.value,
        images: images.value.join(",")
      };
      const response = await utils_request.request.post("/create_complaint", complaint);
      if (response.code === 200) {
        common_vendor.index.showToast({ title: "投诉提交成功", icon: "success" });
        setTimeout(() => {
          common_vendor.index.navigateBack();
        }, 1500);
      }
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(storeName.value),
        b: common_vendor.f(typeOptions, (type, k0, i0) => {
          return {
            a: common_vendor.t(type),
            b: type,
            c: complaintType.value === type ? 1 : "",
            d: common_vendor.o(($event) => selectType(type), type)
          };
        }),
        c: content.value,
        d: common_vendor.o(($event) => content.value = $event.detail.value),
        e: common_vendor.f(images.value, (img, index, i0) => {
          return {
            a: "http://localhost:8090/api/download_img?img=" + img,
            b: common_vendor.o(($event) => removeImage(index), index),
            c: index
          };
        }),
        f: images.value.length < 3 && !uploading.value
      }, images.value.length < 3 && !uploading.value ? {
        g: common_vendor.o(chooseImage)
      } : {}, {
        h: uploading.value
      }, uploading.value ? {} : {}, {
        i: common_vendor.o(submitComplaint)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-516bbc45"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/complaint/complaint.vue"]]);
wx.createPage(MiniProgramPage);
