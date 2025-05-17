package com.sudobang.healtharchive.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudobang.healtharchive.constant.MessageConstant;
import com.sudobang.healtharchive.context.BaseContext;
import com.sudobang.healtharchive.dto.UserDTO;
import com.sudobang.healtharchive.dto.UserLoginDTO;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;
import com.sudobang.healtharchive.exception.AccountNotFoundException;
import com.sudobang.healtharchive.exception.PasswordErrorException;
import com.sudobang.healtharchive.mapper.UserMapper;
import com.sudobang.healtharchive.service.UserService;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String residentIdentityCardNumber = userLoginDTO.getResidentIdentityCardNumber();
        String password = userLoginDTO.getPassword();

//        1.根据身份证号在数据库中查找
        User user = userMapper.getByResidentIdentityCardNumber(residentIdentityCardNumber);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

//        密码比对
        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return user;
    }

    @Override
    public void save(UserDTO userDTO) {
        String residentIdentityCardNumber = userDTO.getResidentIdentityCardNumber();
        User user = userMapper.getByResidentIdentityCardNumber(residentIdentityCardNumber);

        if (user != null) {
            //账号已经存在，不能根据已有身份证号注册新账号
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_FOUND);
        }

        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        userMapper.insert(newUser);
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.selectById(id);
        user.setPassword("******");
        return user;
    }

    @Override
    public void update(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userMapper.update(user);
    }

    @Override
    public Boolean checkUserPassword(Long id, String originalPassword) {
        User user = userMapper.selectById(id);
        return originalPassword.equals(user.getPassword());
    }

    @Override
    public User getUserByCardNumber(String cardNumber) {
        return userMapper.getUserByCardNumber(cardNumber);
    }

    @Override
    public PageBean<User> listAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> User = userMapper.listAll();
        PageInfo<User> pageInfo = new PageInfo<>(User);

        PageBean<User> pb = new PageBean<>();
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

}
