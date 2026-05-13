"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
const stores_account = require("../../stores/account.js");
const stores_order = require("../../stores/order.js");
require("../../config.js");
if (!Math) {
  product();
}
const product = () => "../../components/store/product.js";
const _sfc_main = {
  __name: "store",
  setup(__props) {
    const logoUrl = common_vendor.ref("");
    const businessLicenseUrl = common_vendor.ref("");
    const isBusinessLicenseShow = common_vendor.ref(false);
    const store = common_vendor.ref([]);
    const categories = common_vendor.ref([]);
    const reviews = common_vendor.ref([]);
    const showReviews = common_vendor.ref(false);
    const accountStore = stores_account.useAccountStore();
    const orderStore = stores_order.useOrderStore();
    common_vendor.ref([]);
    common_vendor.onLoad((option) => {
      const getStore = async () => {
        const response = await utils_request.request.get("/select_store_by_store_id/" + option.storeId);
        if (response.code === 200) {
          store.value = response.data;
          orderStore.$reset();
          orderStore.accountId = accountStore.id;
          orderStore.phone = accountStore.phone;
          orderStore.storeId = store.value.id;
          orderStore.packagePrice = store.value.packagePrice;
          common_vendor.index.setNavigationBarTitle({
            title: store.value.name
          });
          logoUrl.value = utils_img.imgUrl + store.value.logo;
          businessLicenseUrl.value = utils_img.imgUrl + store.value.businessLicense;
          getReviews(store.value.id);
        }
      };
      const getCategory = async () => {
        const response = await utils_request.request.get("/select_categories_by_store_id/" + option.storeId);
        if (response.code === 200) {
          categories.value = response.data;
        }
      };
      getStore();
      getCategory();
    });
    function getBusinessLicense() {
      isBusinessLicenseShow.value = !isBusinessLicenseShow.value;
    }
    const getReviews = async (storeId) => {
      const response = await utils_request.request.get("/select_reviews_by_store_id/" + storeId);
      if (response.code === 200) {
        reviews.value = response.data || [];
      }
    };
    const toggleReviews = () => {
      showReviews.value = !showReviews.value;
    };
    const formatDate = (timestamp) => {
      const date = new Date(timestamp);
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")}`;
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: logoUrl.value,
        b: common_vendor.t(store.value.name),
        c: common_vendor.t(store.value.businessHour),
        d: common_vendor.t(store.value.phone),
        e: common_vendor.t(store.value.address),
        f: common_vendor.o(getBusinessLicense),
        g: common_vendor.t(reviews.value.length),
        h: common_vendor.o(toggleReviews),
        i: common_vendor.p({
          categories: categories.value
        }),
        j: isBusinessLicenseShow.value,
        k: common_vendor.o(getBusinessLicense),
        l: businessLicenseUrl.value,
        m: showReviews.value
      }, showReviews.value ? common_vendor.e({
        n: common_vendor.t(reviews.value.length),
        o: common_vendor.o(toggleReviews),
        p: common_vendor.f(reviews.value, (review, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.unref(utils_img.imgUrl) + review.avatar,
            b: common_vendor.t(review.nickname),
            c: common_vendor.f(5, (star, k1, i1) => {
              return {
                a: star,
                b: star <= review.rating ? 1 : ""
              };
            }),
            d: common_vendor.t(formatDate(review.createTime)),
            e: common_vendor.t(review.content),
            f: review.images
          }, review.images ? {
            g: common_vendor.f(review.images.split(","), (img, index, i1) => {
              return {
                a: index,
                b: common_vendor.unref(utils_img.imgUrl) + img,
                c: common_vendor.o(($event) => _ctx.uni.previewImage({
                  urls: review.images.split(",").map((i) => common_vendor.unref(utils_img.imgUrl) + i),
                  current: common_vendor.unref(utils_img.imgUrl) + img
                }), index)
              };
            })
          } : {}, {
            h: review.id
          });
        }),
        q: reviews.value.length === 0
      }, reviews.value.length === 0 ? {} : {}, {
        r: common_vendor.o(() => {
        }),
        s: common_vendor.o(toggleReviews)
      }) : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-c1a2745a"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/store/store.vue"]]);
wx.createPage(MiniProgramPage);
