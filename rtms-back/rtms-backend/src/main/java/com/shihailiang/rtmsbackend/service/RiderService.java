package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Rider;
import jakarta.servlet.http.HttpServletRequest;

public interface RiderService {

    /**
     * 查询骑手列表
     */
    CommonResponse selectRiders(HttpServletRequest request);

    /**
     * 根据id查询骑手
     */
    CommonResponse selectRiderById(Long id, HttpServletRequest request);

    /**
     * 根据账号id查询骑手
     */
    CommonResponse selectRiderByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 新增骑手
     */
    CommonResponse createRider(Rider rider, HttpServletRequest request);

    /**
     * 修改骑手
     */
    CommonResponse updateRider(Rider rider, HttpServletRequest request);

    /**
     * 删除骑手
     */
    CommonResponse deleteRider(Long id, HttpServletRequest request);
}
