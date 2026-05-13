"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
const stores_account = require("../../stores/account.js");
require("../../config.js");
const _sfc_main = {
  __name: "order-detail",
  setup(__props) {
    const order = common_vendor.ref({});
    const store = common_vendor.ref({});
    const rider = common_vendor.ref({});
    const hasReview = common_vendor.ref(false);
    const showMap = common_vendor.ref(false);
    const mapMarkers = common_vendor.ref([]);
    const mapPolyline = common_vendor.ref([]);
    const locationTimer = common_vendor.ref(null);
    const getRiderLocation = async () => {
      if (order.value.progress !== "配送中") return;
      const response = await utils_request.request.get("/get_rider_location_by_order_id/" + order.value.id);
      if (response.code === 200 && response.data) {
        const data = response.data;
        showMap.value = true;
        const markers = [];
        if (data.storeLatitude && data.storeLongitude) {
          markers.push({
            id: 1,
            latitude: data.storeLatitude,
            longitude: data.storeLongitude,
            title: data.storeName || "商家",
            width: 30,
            height: 30,
            callout: { content: data.storeName || "商家", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
        }
        if (data.deliveryLatitude && data.deliveryLongitude) {
          markers.push({
            id: 2,
            latitude: data.deliveryLatitude,
            longitude: data.deliveryLongitude,
            title: data.deliveryAddress || "收货地址",
            width: 30,
            height: 30,
            callout: { content: "收货点", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
        }
        if (data.riderLatitude && data.riderLongitude) {
          markers.push({
            id: 3,
            latitude: data.riderLatitude,
            longitude: data.riderLongitude,
            title: "骑手位置",
            width: 36,
            height: 36,
            callout: { content: "骑手", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
        }
        mapMarkers.value = markers;
        const points = [];
        if (data.storeLatitude && data.storeLongitude) {
          points.push({ latitude: data.storeLatitude, longitude: data.storeLongitude });
        }
        if (data.riderLatitude && data.riderLongitude) {
          points.push({ latitude: data.riderLatitude, longitude: data.riderLongitude });
        }
        if (data.deliveryLatitude && data.deliveryLongitude) {
          points.push({ latitude: data.deliveryLatitude, longitude: data.deliveryLongitude });
        }
        if (points.length >= 2) {
          mapPolyline.value = [{
            points: points,
            color: "#068cfa",
            width: 4,
            dottedLine: false,
            arrowLine: true
          }];
        }
      }
    };
    const getOrder = async (option) => {
      const response = await utils_request.request.get("/select_order_by_id/" + option.orderId);
      if (response.code === 200) {
        order.value = response.data;
        const getStore = async () => {
          const response2 = await utils_request.request.get("/select_store_by_store_id/" + order.value.storeId);
          if (response2.code === 200) {
            store.value = response2.data;
          }
        };
        const getRider = async () => {
          const response2 = await utils_request.request.get("/select_rider_by_id/" + order.value.riderId);
          if (response2.code === 200) {
            rider.value = response2.data;
          }
        };
        const checkReview = async () => {
          const response2 = await utils_request.request.get("/select_review_by_order_id/" + order.value.id);
          if (response2.code === 200 && response2.data) {
            hasReview.value = true;
          }
        };
        getStore();
        getRider();
        getOrderProducts(option.orderId);
        checkReview();
        if (order.value.progress === "配送中") {
          getRiderLocation();
          if (locationTimer.value) clearInterval(locationTimer.value);
          locationTimer.value = setInterval(() => {
            getRiderLocation();
          }, 5000);
        }
      }
    };
    const createTime = common_vendor.computed(() => {
      var date = new Date(order.value.createTime);
      var year = date.getFullYear();
      var month = ("0" + (date.getMonth() + 1)).slice(-2);
      var day = ("0" + date.getDate()).slice(-2);
      var hours = ("0" + date.getHours()).slice(-2);
      var minutes = ("0" + date.getMinutes()).slice(-2);
      var seconds = ("0" + date.getSeconds()).slice(-2);
      var formattedDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
      return formattedDate;
    });
    common_vendor.onLoad((option) => {
      getOrder(option);
    });
    const goStore = (storeId) => {
      common_vendor.index.navigateTo({
        url: "/pages/store/store?storeId=" + storeId
      });
    };
    const orderProducts = common_vendor.ref([]);
    const orderProductsImgs = common_vendor.ref([]);
    const getProduct = async (productId, productName, specification, number, totalPrice) => {
      const response = await utils_request.request.get("/select_product_by_id/" + productId);
      if (response.code === 200) {
        let product = {
          name: productName,
          specification,
          number,
          totalPrice,
          picture: response.data.picture
        };
        orderProductsImgs.value.push(product);
      }
    };
    const getOrderProducts = async (orderId) => {
      const response = await utils_request.request.get("/select_order_products_by_order_id/" + orderId);
      if (response.code === 200) {
        orderProducts.value = response.data;
        for (let i = 0; i < orderProducts.value.length; i++) {
          getProduct(
            orderProducts.value[i].productId,
            orderProducts.value[i].name,
            orderProducts.value[i].specification,
            orderProducts.value[i].number,
            orderProducts.value[i].totalPrice
          );
        }
      }
    };
    const accountStore = stores_account.useAccountStore();
    const payment = () => {
      console.log(accountStore.money);
      console.log(order.value.id);
      console.log(order.value.totalPrice);
      common_vendor.index.showModal({
        title: "支付订单",
        content: "确认要支付该订单吗？",
        success: function(res) {
          if (res.confirm) {
            if (accountStore.money < order.value.totalPrice) {
              common_vendor.index.showToast({
                title: "余额不足",
                icon: "error"
              });
            } else {
              const customerPayment = async (orderId) => {
                const response = await utils_request.request.post("/update_account_money", {
                  id: accountStore.id,
                  money: accountStore.money - order.value.totalPrice
                });
                if (response.code === 200) {
                  const response2 = await utils_request.request.post("/customer_payment_order_by_id/" + orderId);
                  if (response2.code === 200) {
                    accountStore.money -= order.value.totalPrice;
                    order.value.progress = "已支付";
                    common_vendor.index.showToast({
                      title: "支付成功",
                      icon: "success"
                    });
                  }
                }
              };
              customerPayment(order.value.id);
            }
          }
        }
      });
    };
    function add30Minutes(time) {
      const [hours, minutes] = time.split(":").map(Number);
      const totalMinutes = hours * 60 + minutes;
      const newTotalMinutes = totalMinutes + 30;
      const newHours = Math.floor(newTotalMinutes / 60);
      const newMinutes = newTotalMinutes % 60;
      const newTime = `${newHours.toString().padStart(2, "0")}:${newMinutes.toString().padStart(2, "0")}`;
      return newTime;
    }
    const confirmReceive = () => {
      common_vendor.index.showModal({
        title: "确认收货",
        content: "确认已收到商品吗？",
        success: async (res) => {
          if (res.confirm) {
            const response = await utils_request.request.post("/customer_completed_order_by_id/" + order.value.id);
            if (response.code === 200) {
              order.value.progress = "已完成";
              common_vendor.index.showToast({ title: "确认成功", icon: "success" });
            }
          }
        }
      });
    };
    const goReview = () => {
      common_vendor.index.navigateTo({
        url: `/pages/review/review?orderId=${order.value.id}&storeId=${order.value.storeId}`
      });
    };
    const goComplaint = () => {
      common_vendor.index.navigateTo({
        url: `/pages/complaint/complaint?orderId=${order.value.id}&storeId=${order.value.storeId}&storeName=${encodeURIComponent(store.value.name)}`
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(order.value.progress),
        b: order.value.progress === "待支付"
      }, order.value.progress === "待支付" ? {
        c: common_vendor.o(payment)
      } : {}, {
        d: order.value.progress === "已送达"
      }, order.value.progress === "已送达" ? {
        e: common_vendor.o(confirmReceive)
      } : {}, {
        f: order.value.progress === "配送中"
      }, order.value.progress === "配送中" ? {
        g: common_vendor.t(add30Minutes(order.value.expectedTime))
      } : {}, {
        h: common_vendor.t(common_vendor.unref(createTime)),
        i: common_vendor.t(store.value.phone),
        j: order.value.progress === "已完成"
      }, order.value.progress === "已完成" ? common_vendor.e({
        k: !hasReview.value
      }, !hasReview.value ? {
        l: common_vendor.o(goReview)
      } : {}, {
        m: common_vendor.o(goComplaint)
      }) : {}, {
        n: common_vendor.unref(utils_img.imgUrl) + store.value.logo,
        o: common_vendor.t(store.value.name),
        p: common_vendor.o(($event) => goStore(store.value.id)),
        q: common_vendor.f(orderProductsImgs.value, (orderProduct, index, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + orderProduct.picture,
            b: common_vendor.t(orderProduct.name),
            c: common_vendor.t(orderProduct.specification),
            d: common_vendor.t(orderProduct.number),
            e: common_vendor.t(orderProduct.totalPrice / 100),
            f: index
          };
        }),
        r: common_vendor.t(order.value.packagePrice / 100),
        s: common_vendor.t(order.value.deliveryPrice / 100),
        t: common_vendor.t(order.value.totalPrice / 100),
        v: order.value.riderId !== 0
      }, order.value.riderId !== 0 ? {
        w: common_vendor.t(rider.value.name),
        x: common_vendor.t(rider.value.gender),
        y: common_vendor.t(rider.value.phone)
      } : {}, {
        z: common_vendor.t(order.value.address),
        A: common_vendor.t(order.value.code),
        B: common_vendor.t(order.value.payment),
        C: order.value.progress === "待支付" || order.value.progress === "已取消"
      }, order.value.progress === "待支付" || order.value.progress === "已取消" ? {} : {}, {
        D: common_vendor.t(order.value.note),
        E: showMap.value,
        F: mapMarkers.value,
        G: mapPolyline.value
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-71729483"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/pages/order-detail/order-detail.vue"]]);
wx.createPage(MiniProgramPage);
