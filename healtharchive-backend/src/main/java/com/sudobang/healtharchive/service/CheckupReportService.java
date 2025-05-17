package com.sudobang.healtharchive.service;

import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.springframework.stereotype.Service;

public interface CheckupReportService {
    PageBean<CheckupReportVO> list(Integer pageNum, Integer pageSize);

    void delete(Integer id);

    PageBean<CheckupReportVO> listByNumber(Integer pageNum, Integer pageSize, String residentIdentityCardNumber);
}
