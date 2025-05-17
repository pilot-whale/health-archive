package com.sudobang.healtharchive.service.Impl;

import com.sudobang.healtharchive.context.BaseContext;
import com.sudobang.healtharchive.entity.CheckupDataBean;
import com.sudobang.healtharchive.entity.CheckupEchartBean;
import com.sudobang.healtharchive.entity.CheckupIndicator;
import com.sudobang.healtharchive.mapper.CheckupDataMapper;
import com.sudobang.healtharchive.mapper.CheckupIndicatorMapper;
import com.sudobang.healtharchive.service.CheckupDataService;
import com.sudobang.healtharchive.vo.CheckupDataVO;
import com.sudobang.healtharchive.vo.EchartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CheckupDataServiceImpl implements CheckupDataService {

    @Autowired
    private CheckupDataMapper checkupDataMapper;

    @Autowired
    private CheckupIndicatorMapper checkupIndicatorMapper;

    @Override
    public List<CheckupDataVO> list(Integer itemId, Integer reportId) {
        List<CheckupDataVO> cdVOlist = new ArrayList<>();
        List<CheckupDataBean> cdlist = checkupDataMapper.list(itemId, reportId);
        for (CheckupDataBean cd : cdlist) {
            CheckupDataVO cdVO = new CheckupDataVO().builder()
                    .name(cd.getName())
                    .result(cd.getValue())
                    .reference(getReference(cd.getReferenceLow(), cd.getReferenceHigh()))
                    .unit(cd.getUnit())
                    .exception(getException(cd.getValue(), cd.getReferenceLow(), cd.getReferenceHigh()))
                    .build();
            cdVOlist.add(cdVO);
        }
        return cdVOlist;
    }

    private String getReference(Double referenceLow, Double referenceHigh){
        if(referenceLow == null && referenceHigh != null){
            return ("<" + referenceHigh);
        }
        if(referenceLow != null && referenceHigh == null){
            return (">" + referenceLow);
        }
        if(referenceLow != null && referenceHigh != null){
            return (referenceLow + "-" + referenceHigh);
        }
        return null;
    }

    private String getException(String value, Double referenceLow, Double referenceHigh) {
        if(referenceLow == null && referenceHigh == null){
            return null;
        }
        double actualLow;
        double actualHigh;
        if(value.contains("-")){
            String[] parts = value.split("-");
            actualLow = Double.parseDouble(parts[0]);
            actualHigh = Double.parseDouble(parts[1]);
        }
        else{
            actualLow = Double.parseDouble(value);
            actualHigh = Double.parseDouble(value);
        }
        if (referenceHigh != null && actualHigh > referenceHigh) {
            return "偏高";
        }
        if (referenceLow != null && actualLow < referenceLow) {
            return "偏低";
        }
        return null;
    }



    @Override
    public EchartVO listVisual(Integer indicatorId, String selectedPeriod) {
        CheckupIndicator checkupIndicator = checkupIndicatorMapper.search(indicatorId);
        Double referenceHigh = checkupIndicator.getReferenceHigh();
        Double referenceLow = checkupIndicator.getReferenceLow();
        String unit = checkupIndicator.getUnit();

        Long userId = BaseContext.getCurrentId();
        List<CheckupEchartBean> cdlist = checkupDataMapper.listVisual(userId, indicatorId, periodParser(selectedPeriod));

        List<String> checkupResultList = new ArrayList<>();
        List<String> hospitalNameList = new ArrayList<>();
        List<String> createDateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (CheckupEchartBean cd : cdlist) {
            checkupResultList.add(cd.getCheckupResult());
            hospitalNameList.add(cd.getHospitalName());
            createDateList.add(sdf.format(cd.getCreateDate()));
        }

        EchartVO echartVO = new EchartVO().builder()
                .referenceHigh(referenceHigh)
                .referenceLow(referenceLow)
                .unit(unit)
                .checkupResultList(checkupResultList)
                .hospitalNameList(hospitalNameList)
                .createDateList(createDateList)
                .build();

        return echartVO;
    }

    private Integer periodParser(String selectedPeriod){
        if(selectedPeriod.equals("十次")){
            return 10;
        }
        if(selectedPeriod.equals("五次")){
            return 5;
        }
        if(selectedPeriod.equals("三次")){
            return 3;
        }
        if(selectedPeriod.equals("两次")){
            return 2;
        }
        if(selectedPeriod.equals("一次")){
            return 1;
        }
        return null;
    }
}
