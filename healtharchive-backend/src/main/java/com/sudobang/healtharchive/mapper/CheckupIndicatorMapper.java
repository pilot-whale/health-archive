package com.sudobang.healtharchive.mapper;

import com.sudobang.healtharchive.entity.CheckupIndicator;
import com.sudobang.healtharchive.entity.CheckupItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckupIndicatorMapper {

    @Select("select * from checkup_indicator where checkupItemId=#{id}")
    List<CheckupIndicator> list(Integer id);


    @Select("select * from checkup_indicator where id=#{id}")
    CheckupIndicator search(Integer id);
}
