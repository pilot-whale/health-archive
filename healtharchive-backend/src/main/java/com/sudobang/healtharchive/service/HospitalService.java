package com.sudobang.healtharchive.service;

import com.sudobang.healtharchive.dto.HospitalPageQueryDTO;
import com.sudobang.healtharchive.entity.Hospital;
import com.sudobang.healtharchive.entity.PageBean;

public interface HospitalService {

    /**
     * 医院分页查询
     * @param
     * @return
     */
    PageBean<Hospital> list(Integer pageNum, Integer pageSize, String state, String level, Boolean jci, Boolean cap, String grade);
}
