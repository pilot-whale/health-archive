package com.sudobang.healtharchive.service.Impl;

import com.sudobang.healtharchive.entity.CheckupItem;
import com.sudobang.healtharchive.mapper.CheckupItemMapper;
import com.sudobang.healtharchive.service.CheckupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckupItemServiceImpl implements CheckupItemService {

    @Autowired
    private CheckupItemMapper  checkupItemMapper;

    @Override
    public List<CheckupItem> list() {
        return checkupItemMapper.list();
    }
}
