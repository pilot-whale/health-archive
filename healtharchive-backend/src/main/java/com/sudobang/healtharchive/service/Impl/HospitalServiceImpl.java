package com.sudobang.healtharchive.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudobang.healtharchive.entity.Hospital;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.mapper.HospitalMapper;
import com.sudobang.healtharchive.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;


    @Override
    public PageBean<Hospital> list(Integer pageNum, Integer pageSize, String state, String level, Boolean jci, Boolean cap, String grade) {
        PageHelper.startPage(pageNum, pageSize);
        List<Hospital> hospitalList = hospitalMapper.list(state, level, jci, cap, grade);
        PageInfo<Hospital> pageInfo = new PageInfo<>(hospitalList);

        PageBean<Hospital> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }
}
