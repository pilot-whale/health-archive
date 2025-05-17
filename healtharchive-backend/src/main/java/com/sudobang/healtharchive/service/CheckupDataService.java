package com.sudobang.healtharchive.service;

import com.sudobang.healtharchive.vo.CheckupDataVO;
import com.sudobang.healtharchive.vo.EchartVO;

import java.util.List;


public interface CheckupDataService {
    List<CheckupDataVO> list(Integer itemId, Integer reportId);

    EchartVO listVisual(Integer indicatorId, String selectedPeriod);
}
