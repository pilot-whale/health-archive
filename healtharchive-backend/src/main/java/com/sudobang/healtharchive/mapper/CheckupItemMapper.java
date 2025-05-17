package com.sudobang.healtharchive.mapper;

import com.sudobang.healtharchive.entity.CheckupItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckupItemMapper {

    @Select("select * from checkup_item")
    List<CheckupItem> list();
}
