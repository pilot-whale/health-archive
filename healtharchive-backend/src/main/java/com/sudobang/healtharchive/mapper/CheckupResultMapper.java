package com.sudobang.healtharchive.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckupResultMapper {


    @Delete("delete from checkup_result where reportId=#{id}")
    void delete(Integer id);
}
