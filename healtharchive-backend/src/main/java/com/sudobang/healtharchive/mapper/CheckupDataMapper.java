package com.sudobang.healtharchive.mapper;

import com.sudobang.healtharchive.entity.CheckupDataBean;
import com.sudobang.healtharchive.entity.CheckupEchartBean;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.vo.CheckupDataVO;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckupDataMapper {


    List<CheckupDataBean> list(
            @Param("itemId") Integer itemId,
            @Param("reportId") Integer reportId
    );


    List<CheckupEchartBean> listVisual(
            @Param("userId") Long userId,
            @Param("indicatorId") Integer indicatorId,
            @Param("selectedPeriod") Integer selectedPeriod
    );
}
