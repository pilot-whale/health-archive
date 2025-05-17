package com.sudobang.healtharchive.service;

import com.sudobang.healtharchive.dto.FamilyDTO;
import com.sudobang.healtharchive.entity.Family;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;

import java.util.List;

public interface FamilyService {

    /**
     * 查询家庭组
     * @param id
     * @return
     */
    Family getById(Long id);

    /**
     * 创建新的家庭组
     * @param familyDTO
     */
    void save(FamilyDTO familyDTO);

    List<Integer> joinGroup(String shareCode, String password);

    PageBean<User> list(Integer pageNum, Integer pageSize);

    PageBean<Family> listAll(Integer pageNum, Integer pageSize);
}
