package com.sudobang.healtharchive.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudobang.healtharchive.context.BaseContext;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;
import com.sudobang.healtharchive.mapper.CheckupReportMapper;
import com.sudobang.healtharchive.mapper.CheckupResultMapper;
import com.sudobang.healtharchive.mapper.UserMapper;
import com.sudobang.healtharchive.service.CheckupReportService;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckupReportServiceImpl implements CheckupReportService {

    @Autowired
    private CheckupReportMapper checkupReportMapper;

    @Autowired
    private CheckupResultMapper checkupResultMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean<CheckupReportVO> list(Integer pageNum, Integer pageSize) {
        Long userId = BaseContext.getCurrentId();
        PageHelper.startPage(pageNum, pageSize);
        List<CheckupReportVO> checkupReportVOList = checkupReportMapper.list(userId);
        PageInfo<CheckupReportVO> pageInfo = new PageInfo<>(checkupReportVOList);

        PageBean<CheckupReportVO> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        checkupReportMapper.delete(id);
        checkupResultMapper.delete(id);
    }

    @Override
    public PageBean<CheckupReportVO> listByNumber(Integer pageNum, Integer pageSize, String residentIdentityCardNumber) {
        User user = userMapper.getUserByCardNumber(residentIdentityCardNumber);
        Long userId = user.getId();
        PageHelper.startPage(pageNum, pageSize);
        List<CheckupReportVO> checkupReportVOList = checkupReportMapper.list(userId);
        PageInfo<CheckupReportVO> pageInfo = new PageInfo<>(checkupReportVOList);

        PageBean<CheckupReportVO> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

}
