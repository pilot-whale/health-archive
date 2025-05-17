package com.sudobang.healtharchive.mapper;

import com.github.pagehelper.Page;
import com.sudobang.healtharchive.dto.HospitalPageQueryDTO;
import com.sudobang.healtharchive.entity.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HospitalMapper {

    /**
     * 医院分页查询
     * @param
     * @return
     */
    List<Hospital> list(
            @Param("state") String state,
            @Param("level") String level,
            @Param("jci") Boolean jci,
            @Param("cap") Boolean cap,
            @Param("grade") String grade);
}
