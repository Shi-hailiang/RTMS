"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "review",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const orderId = common_vendor.ref(0);
    const storeId = common_vendor.ref(0);
    const rating = common_vendor.ref(5);
    const content = common_vendor.ref("");
    const images = common_vendor.ref([]);
    const uploading = common_vendor.ref(false);
    common_vendor.onLoad((option) => {
      orderId.value = option.orderId;
      storeId.value = option.storeId;
    });
    const setRating = (star) => {
      rating.value = star;
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
    const submitReview = async () => {
      if (!content.value.trim()) {
        common_vendor.index.showToast({ title: "请输入评价内容", icon: "none" });
        return;
      }
      const review = {
        orderId: orderId.value,
        accountId: accountStore.id,
        storeId: storeId.value,
        rating: rating.value,
        content: content.value,
        images: images.value.join(",")
      };
      const response = await utils_request.request.post("/create_review", review);
      if (response.code === 200) {
        common_vendor.index.showToast({ title: "评价成功", icon: "success" });
        setTimeout(() => {
          common_vendor.index.navigateBack();
        }, 1500);
      }
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.f(5, (star, k0, i0) => {
          return {
            a: star,
            b: star <= rating.value ? 1 : "",
            c: common_vendor.o(($event) => setRating(star), star)
          };
        }),
        b: content.value,
        c: common_vendor.o(($event) => content.value = $event.detail.value),
        d: common_vendor.f(images.value, (img, index, i0) => {
          return {
            a: "http://localhost:8090/api/download_img?img=" + img,
            b: common_vendor.o(($event) => removeImage(index), index),
            c: index
          };
        }),
        e: images.value.length < 3 && !uploading.value
      }, images.value.length < 3 && !uploading.value ? {
        f: common_vendor.o(chooseImage)
      } : {}, {
        g: uploading.value
      }, uploading.value ? {} : {}, {
        h: common_vendor.o(submitReview)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-7018a65d"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/review/review.vue"]]);
wx.createPage(MiniProgramPage);
