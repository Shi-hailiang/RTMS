"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
require("../../config.js");
const _sfc_main = {
  __name: "order-detail",
  setup(__props) {
    const order = common_vendor.ref([]);
    const orderProducts = common_vendor.ref([]);
    const store = common_vendor.ref([]);
    const customer = common_vendor.ref([]);
    const showMap = common_vendor.ref(false);
    const mapLatitude = common_vendor.ref(null);
    const mapLongitude = common_vendor.ref(null);
    const storeLatitude = common_vendor.ref(null);
    const storeLongitude = common_vendor.ref(null);
    const deliveryLatitude = common_vendor.ref(null);
    const deliveryLongitude = common_vendor.ref(null);
    const mapMarkers = common_vendor.ref([]);
    const mapPolyline = common_vendor.ref([]);
    const locationReportTimer = common_vendor.ref(null);
    const fetchLocationData = async () => {
      const response = await utils_request.request.get("/get_rider_location_by_order_id/" + order.value.id);
      if (response.code === 200 && response.data) {
        const data = response.data;
        const markers = [];
        if (data.storeLatitude && data.storeLongitude) {
          storeLatitude.value = data.storeLatitude;
          storeLongitude.value = data.storeLongitude;
          markers.push({
            id: 1,
            latitude: data.storeLatitude,
            longitude: data.storeLongitude,
            title: data.storeName || "取餐点",
            width: 30,
            height: 30,
            callout: { content: "取餐", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
        }
        if (data.deliveryLatitude && data.deliveryLongitude) {
          deliveryLatitude.value = data.deliveryLatitude;
          deliveryLongitude.value = data.deliveryLongitude;
          markers.push({
            id: 2,
            latitude: data.deliveryLatitude,
            longitude: data.deliveryLongitude,
            title: data.deliveryAddress || "配送点",
            width: 30,
            height: 30,
            callout: { content: "配送点", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
        }
        mapMarkers.value = markers;
      }
    };
    const updatePolyline = (lat, lng) => {
      const points = [];
      if (storeLatitude.value && storeLongitude.value) {
        points.push({ latitude: storeLatitude.value, longitude: storeLongitude.value });
      }
      points.push({ latitude: lat, longitude: lng });
      if (deliveryLatitude.value && deliveryLongitude.value) {
        points.push({ latitude: deliveryLatitude.value, longitude: deliveryLongitude.value });
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
    };
    const reportLocation = () => {
      common_vendor.wx$1.getLocation({
        type: "gcj02",
        success: function(res) {
          mapLatitude.value = res.latitude;
          mapLongitude.value = res.longitude;
          showMap.value = true;
          const markers = mapMarkers.value.filter(function(m) { return m.id !== 3; });
          markers.push({
            id: 3,
            latitude: res.latitude,
            longitude: res.longitude,
            title: "我的位置",
            width: 36,
            height: 36,
            callout: { content: "我", fontSize: 12, padding: 5, display: "ALWAYS", borderRadius: 4 }
          });
          mapMarkers.value = markers;
          updatePolyline(res.latitude, res.longitude);
          utils_request.request.post("/report_rider_location", {
            orderId: order.value.id,
            riderId: order.value.riderId,
            latitude: res.latitude,
            longitude: res.longitude
          });
        },
        fail: function(err) {
          console.log("获取位置失败", err);
        }
      });
    };
    const startReportingLocation = () => {
      fetchLocationData();
      if (locationReportTimer.value) clearInterval(locationReportTimer.value);
      locationReportTimer.value = setInterval(function() {
        reportLocation();
      }, 3000);
    };
    const getOrder = async (orderId) => {
      const response = await utils_request.request.get("/select_order_by_id/" + orderId);
      if (response.code === 200) {
        order.value = response.data;
        getStore(order.value.storeId);
        getCustomer(order.value.accountId);
        if (order.value.progress === "配送中") {
          startReportingLocation();
        }
      }
    };
    const getOrderProducts = async (orderId) => {
      const response = await utils_request.request.get(
        "/select_order_products_by_order_id/" + orderId
      );
      if (response.code === 200) {
        orderProducts.value = response.data;
      }
    };
    const getStore = async (storeId) => {
      const response = await utils_request.request.get("/select_store_by_store_id/" + storeId);
      if (response.code === 200) {
        store.value = response.data;
      }
    };
    const getCustomer = async (accountId) => {
      const response = await utils_request.request.get(
        "/select_account_by_account_id/" + accountId
      );
      if (response.code === 200) {
        customer.value = response.data;
      }
    };
    const riderDeliveredOrderByOrderId = async (orderId) => {
      const response = await utils_request.request.post(
        "/rider_delivered_order_by_id/" + orderId
      );
      if (response.code === 200) {
        getOrder(orderId);
        getOrderProducts(orderId);
        common_vendor.index.showToast({
          title: "送达成功",
          icon: "success",
          mask: true
        });
      }
    };
    const riderDeliveredOrder = (orderId) => {
      common_vendor.index.showModal({
        title: "确认送达",
        content: "确定外卖已送至【" + order.value.address + "】吗？",
        success: function(res) {
          if (res.confirm) {
            riderDeliveredOrderByOrderId(orderId);
          }
        }
      });
    };
    common_vendor.onLoad((option) => {
      getOrder(option.orderId);
      getOrderProducts(option.orderId);
    });
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: order.value.progress === "配送中"
      }, order.value.progress === "配送中" ? {
        b: common_vendor.t(store.value.address),
        c: common_vendor.t(order.value.address)
      } : {}, {
        d: order.value.progress === "已送达"
      }, order.value.progress === "已送达" ? {
        e: common_vendor.t(order.value.address)
      } : {}, {
        f: order.value.progress === "已完成"
      }, order.value.progress === "已完成" ? {
        g: common_vendor.t(order.value.address)
      } : {}, {
        h: order.value.progress === "配送中"
      }, order.value.progress === "配送中" ? {} : {}, {
        i: order.value.progress === "已送达"
      }, order.value.progress === "已送达" ? {} : {}, {
        j: order.value.progress === "已完成"
      }, order.value.progress === "已完成" ? {} : {}, {
        k: common_vendor.unref(utils_img.imgUrl) + store.value.logo,
        l: common_vendor.t(store.value.name),
        m: common_vendor.t(store.value.phone),
        n: common_vendor.t(store.value.address),
        o: common_vendor.t(order.value.address),
        p: common_vendor.t(order.value.expectedTime),
        q: common_vendor.t(order.value.code),
        r: common_vendor.t(order.value.note),
        s: common_vendor.unref(utils_img.imgUrl) + customer.value.avatar,
        t: common_vendor.t(customer.value.nickname),
        v: common_vendor.t(customer.value.phone),
        w: order.value.progress === "配送中"
      }, order.value.progress === "配送中" ? {
        x: common_vendor.o(($event) => riderDeliveredOrder(order.value.id))
      } : {}, {
        y: showMap.value,
        z: mapMarkers.value,
        A: mapPolyline.value,
        B: mapLatitude.value,
        C: mapLongitude.value
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-71729483"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-rider/pages/order-detail/order-detail.vue"]]);
wx.createPage(MiniProgramPage);
