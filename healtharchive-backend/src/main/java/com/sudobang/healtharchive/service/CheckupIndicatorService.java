package com.sudobang.healtharchive.service;

import com.sudobang.healtharchive.entity.CheckupIndicator;
import com.sudobang.healtharchive.entity.CheckupItem;

import java.util.List;

public interface CheckupIndicatorService {
    List<CheckupIndicator> list(Integer id);
}
