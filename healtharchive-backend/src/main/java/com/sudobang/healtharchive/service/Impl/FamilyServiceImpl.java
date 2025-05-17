package com.sudobang.healtharchive.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudobang.healtharchive.constant.MessageConstant;
import com.sudobang.healtharchive.constant.PasswordConstant;
import com.sudobang.healtharchive.context.BaseContext;
import com.sudobang.healtharchive.dto.FamilyDTO;
import com.sudobang.healtharchive.entity.Family;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;
import com.sudobang.healtharchive.exception.AccountNotFoundException;
import com.sudobang.healtharchive.mapper.FamilyMapper;
import com.sudobang.healtharchive.mapper.UserMapper;
import com.sudobang.healtharchive.service.FamilyService;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Family getById(Long id) {
        Family family = familyMapper.selectById(id);
        family.setPassword("******");
        return family;
    }

    @Override
    public void save(FamilyDTO familyDTO) {
        Family family = new Family();
        BeanUtils.copyProperties(familyDTO, family);
        family.setShareCode(UUID.randomUUID().toString());
        familyMapper.insert(family);
    }

    @Override
    public List<Integer> joinGroup(String shareCode, String password) {
        List<Integer> familyGroup = familyMapper.joinGroup(shareCode, password);
        if(familyGroup == null){
            throw new AccountNotFoundException("分享码或密码错误");
        }
        return familyGroup;
    }

    @Override
    public PageBean<User> list(Integer pageNum, Integer pageSize) {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        Long familyId = user.getFamilyGroup();

        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.list(familyId);
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        PageBean<User> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

    @Override
    public PageBean<Family> listAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Family> familyList = familyMapper.listAll();
        PageInfo<Family> pageInfo = new PageInfo<>(familyList);

        PageBean<Family> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }
}
