package com.sudobang.healtharchive.mapper;

import com.sudobang.healtharchive.entity.Family;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FamilyMapper {

    /**
     * 根据id查询家庭组
     * @param id
     * @return
     */
    @Select("select * from family where id=#{id}")
    Family selectById(Long id);

    /**
     * 创建新的家庭组
     * @param family
     */
    @Insert("insert into family(id, familyName, password, shareCode)" +
            "values(#{id}, #{familyName}, #{password}, #{shareCode})")
    void insert(Family family);


    /**
     * 根据shareCode和password查询家庭组
     * @param shareCode password
     * @return
     */
    @Select("select id from family where shareCode=#{shareCode} and " +
            "password=#{password}")
    List<Integer> joinGroup(
            @Param("shareCode") String shareCode,
            @Param("password") String password
    );

    @Select("select * from family")
    List<Family> listAll();
}
