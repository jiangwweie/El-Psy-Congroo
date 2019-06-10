package com.sg.cristina.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sg.cristina.config.aspect.CacheEvict;
import com.sg.cristina.config.aspect.Cacheable;
import com.sg.cristina.dao.SgUserMapper;
import com.sg.cristina.entity.SgUser;
import com.sg.cristina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jiangwei
 * @Date: 2019/4/20
 * @Desc:
 */
@Component
//@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {


    @Autowired
    SgUserMapper userMapper ;
    /**
     * @param sgId
     * @mbggenerated
     */
    @CacheEvict(key = "SgUser",fieldKey = "#sgId")
    @Override
    public int deleteByPrimaryKey(Integer sgId) {
        return userMapper.deleteByPrimaryKey(sgId);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @Override
    public int insert(SgUser record) {
        return userMapper.insert(record);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @CacheEvict(key = "SgUser",fieldKey = "#record.sgId")
    @Override
    public int insertSelective(SgUser record) {
        return userMapper.insertSelective(record);
    }

    /**
     * @param sgId
     * @mbggenerated
     */
    @Cacheable(key = "SgUser",fieldKey = "#sgId")
    @Override
    public SgUser selectByPrimaryKey(Integer sgId) {
        return userMapper.selectByPrimaryKey(sgId);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @CacheEvict(key = "SgUser",fieldKey = "#record.sgId")
    @Override
    public int updateByPrimaryKeySelective(SgUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @CacheEvict(key = "SgUser",fieldKey = "#record.sgId")
    @Override
    public int updateByPrimaryKey(SgUser record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public SgUser selectByGitId(String gitId) {
        return userMapper.selectByGitId(gitId);
    }
}
