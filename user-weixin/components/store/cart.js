"use strict";
const common_vendor = require("../../common/vendor.js");
const stores_order = require("../../stores/order.js");
const _sfc_main = {
  setup() {
    const orderProducts = common_vendor.ref([]);
    const addOrderProduct = (product) => {
      orderProducts.value.push(product);
    };
    const totalPriceCart = common_vendor.computed(() => {
      let sum = 0;
      for (let i = 0; i < orderProducts.value.length; i++) {
        sum += orderProducts.value[i].totalPrice;
      }
      return sum;
    });
    const isCartOpen = common_vendor.ref(false);
    const openOrCloseCart = () => {
      isCartOpen.value = !isCartOpen.value;
    };
    const clearCart = () => {
      orderProducts.value = [];
    };
    const orderStore = stores_order.useOrderStore();
    const checkOut = () => {
      if (orderProducts.value.length === 0) {
        common_vendor.index.showToast({
          title: "购物车空空的~",
          icon: "none",
          duration: 1e3
        });
      } else {
        orderStore.orderProducts = orderProducts.value;
        orderStore.totalPrice = orderStore.packagePrice + totalPriceCart.value;
        common_vendor.index.navigateTo({
          url: "/pages/check/check"
        });
      }
    };
    return {
      orderProducts,
      addOrderProduct,
      totalPriceCart,
      isCartOpen,
      openOrCloseCart,
      clearCart,
      checkOut
    };
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $setup.isCartOpen,
    b: $setup.isCartOpen,
    c: common_vendor.o((...args) => $setup.clearCart && $setup.clearCart(...args)),
    d: $setup.isCartOpen,
    e: common_vendor.f($setup.orderProducts, (product, k0, i0) => {
      return {
        a: common_vendor.t(product.name),
        b: common_vendor.t(product.specification),
        c: common_vendor.t(product.number),
        d: common_vendor.t(product.price / 100),
        e: product.id
      };
    }),
    f: $setup.isCartOpen,
    g: $setup.isCartOpen,
    h: common_vendor.o((...args) => $setup.openOrCloseCart && $setup.openOrCloseCart(...args)),
    i: $setup.isCartOpen ? 1 : "",
    j: common_vendor.o((...args) => $setup.openOrCloseCart && $setup.openOrCloseCart(...args)),
    k: common_vendor.t($setup.totalPriceCart / 100),
    l: common_vendor.o((...args) => $setup.openOrCloseCart && $setup.openOrCloseCart(...args)),
    m: common_vendor.o((...args) => $setup.checkOut && $setup.checkOut(...args))
  };
}
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-ce1139e3"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/store/cart.vue"]]);
wx.createComponent(Component);
