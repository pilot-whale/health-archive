package com.sudobang.healtharchive.service.Impl;

import com.sudobang.healtharchive.entity.CheckupIndicator;
import com.sudobang.healtharchive.entity.CheckupItem;
import com.sudobang.healtharchive.mapper.CheckupIndicatorMapper;
import com.sudobang.healtharchive.mapper.CheckupItemMapper;
import com.sudobang.healtharchive.service.CheckupIndicatorService;
import com.sudobang.healtharchive.service.CheckupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckupIndicatorServiceImpl implements CheckupIndicatorService {

    @Autowired
    private CheckupIndicatorMapper checkupIndicatorMapper;

    @Override
    public List<CheckupIndicator> list(Integer id) {
        return checkupIndicatorMapper.list(id);
    }
}
