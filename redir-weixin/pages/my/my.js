"use strict";
const common_vendor = require("../../common/vendor.js");
const stores_account = require("../../stores/account.js");
const stores_rider = require("../../stores/rider.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
require("../../config.js");
const _sfc_main = {
  __name: "my",
  setup(__props) {
    const accountStore = stores_account.useAccountStore();
    const riderStore = stores_rider.useRiderStore();
    const orderCompleteNumber = common_vendor.ref([]);
    const handlePhone = (phone) => {
      const prefix = phone.substring(0, 3);
      const suffix = phone.substring(phone.length - 4);
      const maskedPhone = prefix + "****" + suffix;
      return maskedPhone;
    };
    const handleIdNumber = (idNumber) => {
      const prefix = idNumber.substring(0, 3);
      const suffix = idNumber.substring(idNumber.length - 4);
      const maskedIdNumber = prefix + "***********" + suffix;
      return maskedIdNumber;
    };
    const uploadAvatarAndUpdateAccount = (tempFilePath) => {
      common_vendor.index.uploadFile({
        url: "http://localhost:8090/api/upload_img",
        filePath: tempFilePath,
        name: "file",
        formData: {
          // 这里可以添加其他请求参数
        },
        success: function(res) {
          const data = JSON.parse(res.data);
          if (data.code === 200) {
            const updateAccountAvatar = async (avatar) => {
              const response = await utils_request.request.post(
                "/update_account_avatar",
                {
                  id: accountStore.id,
                  avatar
                }
              );
              if (response.code === 200) {
                accountStore.avatar = avatar;
                common_vendor.index.showToast({
                  title: "上传成功",
                  icon: "success",
                  duration: 2e3
                });
              }
            };
            updateAccountAvatar(data.data);
          }
        },
        fail: function(err) {
          console.log(err);
        }
      });
    };
    const uploadAvatar = () => {
      common_vendor.index.chooseImage({
        count: 1,
        sizeType: ["original"],
        sourceType: ["album"],
        success: function(res) {
          const tempFilePath = res.tempFilePaths[0];
          uploadAvatarAndUpdateAccount(tempFilePath);
        }
      });
    };
    const logout = () => {
      common_vendor.index.showModal({
        content: "确定要退出登录吗？",
        success: function(res) {
          if (res.confirm) {
            const logoutApi = async () => {
              const response = await utils_request.request.get("/logout");
              if (response.code === 200) {
                accountStore.$reset;
                common_vendor.index.showToast({
                  title: "退出成功",
                  icon: "none",
                  duration: 1e3
                });
                setTimeout(() => {
                  common_vendor.index.redirectTo({
                    url: "/pages/login/login"
                  });
                }, 300);
              }
            };
            logoutApi();
          }
        }
      });
    };
    common_vendor.onLoad(() => {
      const selectOrderCompleteNumber = async (riderId) => {
        const response = await utils_request.request.get(
          "/select_order_complete_number_by_rider_id/" + riderId
        );
        if (response.code === 200) {
          orderCompleteNumber.value = response.data;
        }
      };
      selectOrderCompleteNumber(riderStore.id);
    });
    return (_ctx, _cache) => {
      return {
        a: common_vendor.unref(utils_img.imgUrl) + common_vendor.unref(accountStore).avatar,
        b: common_vendor.o(uploadAvatar),
        c: common_vendor.t(common_vendor.unref(accountStore).nickname),
        d: common_vendor.t(handlePhone(common_vendor.unref(riderStore).phone)),
        e: common_vendor.t(common_vendor.unref(riderStore).name),
        f: common_vendor.t(handleIdNumber(common_vendor.unref(riderStore).idNumber)),
        g: common_vendor.t(orderCompleteNumber.value.dayNumber),
        h: common_vendor.t((orderCompleteNumber.value.dayIncome || 0) / 100),
        i: common_vendor.t(orderCompleteNumber.value.monthNumber),
        j: common_vendor.t((orderCompleteNumber.value.monthIncome || 0) / 100),
        k: common_vendor.o(logout)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-2f1ef635"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-rider/pages/my/my.vue"]]);
wx.createPage(MiniProgramPage);
