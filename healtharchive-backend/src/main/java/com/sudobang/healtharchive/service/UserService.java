package com.sudobang.healtharchive.service;


import com.sudobang.healtharchive.dto.UserDTO;
import com.sudobang.healtharchive.dto.UserLoginDTO;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;


public interface UserService {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param userDTO
     */
    void save(UserDTO userDTO);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 编辑用户信息
     * @param userDTO
     */
    void update(UserDTO userDTO);

    Boolean checkUserPassword(Long id, String originalPassword);

    User getUserByCardNumber(String cardNumber);

    PageBean<User> listAll(Integer pageNum, Integer pageSize);
}
