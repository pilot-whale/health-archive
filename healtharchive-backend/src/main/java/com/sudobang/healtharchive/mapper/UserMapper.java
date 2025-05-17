package com.sudobang.healtharchive.mapper;


import com.sudobang.healtharchive.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据身份证号查询用户
     * @param residentIdentityCardNumber
     * @return
     */
    @Select("select * from user where residentIdentityCardNumber = #{residentIdentityCardNumber}")
    User getByResidentIdentityCardNumber(String residentIdentityCardNumber);

    /**
     * 插入新用户
     * @param user
     */
    @Insert("insert into user(userName, residentIdentityCardNumber, password, familyGroup, name, permission)" +
            " values(#{userName}, #{residentIdentityCardNumber}, #{password}, #{familyGroup}, #{name}, 2)")
    void insert(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectById(Long id);

    /**
     * 编辑用户信息
     * @param user
     */
    void update(User user);

    @Select("select * from user where familyGroup=#{familyId}")
    List<User> list(Long familyId);

    @Select("select * from user where residentIdentityCardNumber=#{cardNumber}")
    User getUserByCardNumber(String cardNumber);

    @Select("select * from user")
    List<User> listAll();
}
