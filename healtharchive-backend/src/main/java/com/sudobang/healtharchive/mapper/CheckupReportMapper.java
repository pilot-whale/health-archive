package com.sudobang.healtharchive.mapper;

import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckupReportMapper {


    List<CheckupReportVO> list(Long userId);

    @Delete("delete from checkup_report where id=#{id}")
    void delete(Integer id);
}
