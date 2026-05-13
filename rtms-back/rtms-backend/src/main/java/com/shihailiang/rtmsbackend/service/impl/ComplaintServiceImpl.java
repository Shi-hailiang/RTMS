package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.AccountMapper;
import com.shihailiang.rtmsbackend.mapper.ComplaintMapper;
import com.shihailiang.rtmsbackend.mapper.StoreMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Account;
import com.shihailiang.rtmsbackend.pojo.entity.Complaint;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import com.shihailiang.rtmsbackend.pojo.vo.ComplaintVO;
import com.shihailiang.rtmsbackend.service.ComplaintService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.shihailiang.constant.StatusConstant.BANNED;

@Slf4j
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private StoreMapper storeMapper;

    @Override
    public CommonResponse createComplaint(Complaint complaint, HttpServletRequest request) {
        complaint.setStatus("待处理");
        complaintMapper.insert(complaint);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectComplaintsByAccountId(Long accountId, HttpServletRequest request) {
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        queryWrapper.orderByDesc("create_time");
        List<Complaint> complaints = complaintMapper.selectList(queryWrapper);
        
        List<ComplaintVO> voList = new ArrayList<>();
        for (Complaint complaint : complaints) {
            ComplaintVO vo = new ComplaintVO();
            BeanUtils.copyProperties(complaint, vo);
            // 查询商家信息
            Store store = storeMapper.selectById(complaint.getStoreId());
            if (store != null) {
                vo.setStoreName(store.getName());
                vo.setStoreLogo(store.getLogo());
            }
            voList.add(vo);
        }
        return ResponseUtils.success(voList);
    }

    @Override
    public CommonResponse selectAllComplaints(HttpServletRequest request) {
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Complaint> complaints = complaintMapper.selectList(queryWrapper);
        
        List<ComplaintVO> voList = new ArrayList<>();
        for (Complaint complaint : complaints) {
            ComplaintVO vo = new ComplaintVO();
            BeanUtils.copyProperties(complaint, vo);
            // 查询用户信息
            Account account = accountMapper.selectById(complaint.getAccountId());
            if (account != null) {
                vo.setNickname(account.getNickname());
                vo.setAvatar(account.getAvatar());
            }
            // 查询商家信息
            Store store = storeMapper.selectById(complaint.getStoreId());
            if (store != null) {
                vo.setStoreName(store.getName());
                vo.setStoreLogo(store.getLogo());
            }
            voList.add(vo);
        }
        return ResponseUtils.success(voList);
    }

    @Override
    public CommonResponse handleComplaint(Long id, String result, String reply, HttpServletRequest request) {
        // 更新投诉状态
        UpdateWrapper<Complaint> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("result", result);
        updateWrapper.set("reply", reply);
        updateWrapper.set("status", "已处理");
        updateWrapper.set("handle_time", new Date());
        complaintMapper.update(null, updateWrapper);
        
        // 如果处理结果是禁用，则禁用商家账号
        if ("禁用".equals(result)) {
            Complaint complaint = complaintMapper.selectById(id);
            if (complaint != null) {
                Store store = storeMapper.selectById(complaint.getStoreId());
                if (store != null) {
                    UpdateWrapper<Account> accountWrapper = new UpdateWrapper<>();
                    accountWrapper.eq("id", store.getAccountId());
                    accountWrapper.set("status", BANNED);
                    accountMapper.update(null, accountWrapper);
                }
            }
        }
        
        return ResponseUtils.success();
    }
}
