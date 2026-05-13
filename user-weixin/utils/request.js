"use strict";
const common_vendor = require("../common/vendor.js");
const config = require("../config.js");
const baseUrl = config.apiConfig.dev.baseUrl;
const request = (options = {}) => {
  return new Promise((resolve, reject) => {
    common_vendor.index.request({
      url: baseUrl + (options.url || ""),
      // Added parentheses to ensure correct concatenation
      method: options.method || "GET",
      data: options.data || {},
      header: options.header || { "Content-Type": "application/json" }
    }).then((res) => {
      let { data } = res;
      if (data.code === 200) {
        resolve(data);
      } else {
        common_vendor.index.showToast({
          title: data.message,
          icon: "none",
          duration: 2e3
        });
        reject(data);
      }
    }).catch((error) => {
      reject(error);
    });
  });
};
const get = (url, data, options = {}) => {
  options.method = "GET";
  options.data = data;
  options.url = url;
  return request(options);
};
const post = (url, data, options = {}) => {
  options.method = "POST";
  options.data = data;
  options.url = url;
  return request(options);
};
const put = (url, data, options = {}) => {
  options.method = "PUT";
  options.data = data;
  options.url = url;
  return request(options);
};
const del = (url, data, options = {}) => {
  options.method = "DELETE";
  options.data = data;
  options.url = url;
  return request(options);
};
const request$1 = { request, get, post, put, del };
exports.request = request$1;
