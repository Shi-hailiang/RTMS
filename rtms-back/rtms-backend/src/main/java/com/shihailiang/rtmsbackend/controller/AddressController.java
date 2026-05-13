package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Address;
import com.shihailiang.rtmsbackend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Address", description = "Address相关接口")
public class AddressController {

    @Resource
    AddressService addressService;

    @PostMapping("/api/create_address")
    @Operation(summary = "新增地址接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createAddress(@RequestBody Address address, HttpServletRequest request) {
        log.info("CreateAddress API is requested");
        return addressService.createAddress(address, request);
    }

    @GetMapping("/api/select_addresses")
    @Operation(summary = "查询地址列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectAddresses(HttpServletRequest request) {
        log.info("SelectAddresses API is requested");
        return addressService.selectAddresses(request);
    }

    @GetMapping("/api/select_addresses_by_account_id/{accountId}")
    @Operation(summary = "根据用户id查询地址列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectAddressesByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectAddressesByAccountId API is requested");
        return addressService.selectAddressesByAccountId(accountId, request);
    }

    @PostMapping("/api/update_address")
    @Operation(summary = "更新地址接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateAddress(@RequestBody Address address, HttpServletRequest request) {
        log.info("UpdateAddress API is requested");
        return addressService.updateAddress(address, request);
    }

    @DeleteMapping("/api/delete_address/{id}")
    @Operation(summary = "删除地址接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteAddress(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteAddress API is requested");
        return addressService.deleteAddress(id, request);
    }

}
