"use strict";
const apiConfig = {
  dev: {
    baseUrl: "http://localhost:8090/api"
  },
  prod: {
    baseUrl: "http://:8090/api"
  },
  // 配置默认充值金额
  addMoney: 50
};
exports.apiConfig = apiConfig;
