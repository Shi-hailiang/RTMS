"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_request = require("../../utils/request.js");
const utils_img = require("../../utils/img.js");
require("../../config.js");
if (!Math) {
  cart();
}
const cart = () => "./cart.js";
const _sfc_main = {
  __name: "product",
  setup(__props) {
    const categories = common_vendor.ref([]);
    const products = common_vendor.ref([]);
    const specifications = common_vendor.ref([]);
    const selectedCategoryId = common_vendor.ref(0);
    const isSpecificationOpen = common_vendor.ref(false);
    const groupedSpecifications = common_vendor.ref([]);
    const orderProduct = common_vendor.ref({
      productId: 0,
      name: "",
      price: 0,
      number: 1,
      specification: "",
      totalPrice: 0
    });
    const Cart = common_vendor.ref(null);
    common_vendor.watchEffect(() => {
      orderProduct.value.totalPrice = orderProduct.value.number * orderProduct.value.price;
    });
    function findById(arr, id) {
      return arr.find((obj) => obj.id === id);
    }
    function groupByType(arr) {
      const result = arr.value.reduce((acc, obj) => {
        const { type, productId, id, name } = obj;
        const existingGroup = acc.find((group) => group.type === type && group.productId === productId);
        if (existingGroup) {
          existingGroup.specifications.push({ id, name });
        } else {
          acc.push({
            productId,
            type,
            specifications: [{ id, name }]
          });
        }
        return acc;
      }, []);
      return common_vendor.ref(result);
    }
    const getProducts = async () => {
      const response = await utils_request.request.get("/select_products_by_category_id/" + selectedCategoryId.value);
      if (response.code === 200) {
        products.value = response.data;
      }
    };
    const getSpecifications = async (productId) => {
      const product = findById(products.value, productId);
      orderProduct.value.productId = product.id;
      orderProduct.value.name = product.name;
      orderProduct.value.price = product.price;
      const response = await utils_request.request.get("/select_specification_by_product_id/" + productId);
      if (response.code === 200) {
        specifications.value = response.data;
        isSpecificationOpen.value = true;
        groupedSpecifications.value = groupByType(specifications).value;
      }
    };
    common_vendor.onLoad((option) => {
      const getCategory = async () => {
        const response = await utils_request.request.get("/select_categories_by_store_id/" + option.storeId);
        if (response.code === 200) {
          categories.value = response.data;
          selectedCategoryId.value = categories.value[0].id;
          getProducts();
        }
      };
      getCategory();
    });
    function selectProductsByCategory(categoryId) {
      selectedCategoryId.value = categoryId;
      getProducts();
    }
    function isSelected(categoryId) {
      return selectedCategoryId.value === categoryId ? true : false;
    }
    function addCart(e) {
      let i = 0;
      let formState = true;
      const keys = Object.keys(e.detail.value).reverse();
      for (i = 0; i < keys.length; i++) {
        const key = keys[i];
        if (e.detail.value[key] === "") {
          common_vendor.index.showToast({
            title: "请选择" + key,
            icon: "none",
            duration: 1e3
          });
          formState = false;
        }
      }
      if (formState) {
        const keys2 = Object.keys(e.detail.value);
        const values = Object.values(e.detail.value);
        orderProduct.value.specification = keys2.map((key, index) => `${key}: ${values[index]}`).join(", ");
        sendOrderProduct();
        common_vendor.index.showToast({
          title: "加入成功",
          icon: "success",
          duration: 1e3
        });
        isSpecificationOpen.value = false;
      }
    }
    function plus() {
      orderProduct.value.number += 1;
    }
    function minus() {
      if (orderProduct.value.number !== 1) {
        orderProduct.value.number -= 1;
      }
    }
    const sendOrderProduct = () => {
      common_vendor.nextTick$1(() => {
        const childComp = Cart.value;
        if (childComp) {
          const temp = {
            productId: orderProduct.value.productId,
            name: orderProduct.value.name,
            price: orderProduct.value.price,
            number: orderProduct.value.number,
            specification: orderProduct.value.specification,
            totalPrice: orderProduct.value.totalPrice
          };
          childComp.addOrderProduct(temp);
        } else {
          console.error("Child component is not yet initialized");
        }
      });
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.f(categories.value, (category, k0, i0) => {
          return {
            a: common_vendor.t(category.name),
            b: category.id,
            c: isSelected(category.id) ? 1 : "",
            d: common_vendor.o(($event) => selectProductsByCategory(category.id), category.id)
          };
        }),
        b: common_vendor.o((...args) => _ctx.test && _ctx.test(...args)),
        c: common_vendor.f(products.value, (product, k0, i0) => {
          return {
            a: common_vendor.unref(utils_img.imgUrl) + product.picture,
            b: common_vendor.t(product.name),
            c: common_vendor.t(product.description),
            d: common_vendor.t(product.price / 100),
            e: common_vendor.o(($event) => getSpecifications(product.id), product.id),
            f: product.id
          };
        }),
        d: common_vendor.sr(Cart, "dac756bf-0", {
          "k": "Cart"
        }),
        e: common_vendor.f(groupedSpecifications.value, (type, k0, i0) => {
          return {
            a: common_vendor.t(type.type),
            b: common_vendor.f(type.specifications, (specification, k1, i1) => {
              return {
                a: common_vendor.t(specification.name),
                b: specification.name,
                c: specification.id
              };
            }),
            c: common_vendor.o((...args) => _ctx.radioChange && _ctx.radioChange(...args), type.id),
            d: type.type,
            e: type.id
          };
        }),
        f: common_vendor.t(orderProduct.value.price / 100),
        g: common_vendor.o(minus),
        h: common_vendor.t(orderProduct.value.number),
        i: common_vendor.o(plus),
        j: common_vendor.t(orderProduct.value.totalPrice / 100),
        k: common_vendor.o(addCart),
        l: common_vendor.o(() => {
        }),
        m: isSpecificationOpen.value ? 1 : "",
        n: common_vendor.o(($event) => isSpecificationOpen.value = false)
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-dac756bf"], ["__file", "E:/bishe/waimai-plus/uniapp/rtms-frontend-wx-account/components/store/product.vue"]]);
wx.createComponent(Component);
