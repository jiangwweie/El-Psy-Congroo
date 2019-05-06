package com.sg.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sg.SgUser;
import com.sg.user.UserDao;
import com.sg.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: jiangwei
 * @Date: 2019/4/23
 * @Desc:
 */
@Component
@Service(interfaceClass =UserService.class )
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao ;


    /**
     * @param sgId
     * @mbggenerated
     */
    @Override
    public void deleteByPrimaryKey(Integer sgId) {
        userDao.deleteById(sgId);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @Override
    public void insert(SgUser record) {
        userDao.save(record);
    }

    /**
     * @param record
     * @mbggenerated
     */
    @Override
    public void insertSelective(SgUser record) {
        userDao.save(record);
    }

    /**
     * @param sgId
     * @mbggenerated
     */
    @Override
    public SgUser selectByPrimaryKey(Integer sgId) {
        Optional<SgUser> optionalSgUser = userDao.findById(sgId);
        if (optionalSgUser.isPresent()){
            SgUser sgUser = optionalSgUser.get();
            return sgUser ;
        }
        return  null ;
    }

    /**
     * @param record
     * @mbggenerated
     */
    @Override
    public void updateByPrimaryKeySelective(SgUser record) {
        userDao.save(record);

    }

    /**
     * @param record
     * @mbggenerated
     */
    @Override
    public void updateByPrimaryKey(SgUser record) {
        userDao.save(record);
    }

    /**
     * select user by gitId
     *
     * @param gitId
     * @return
     */
    @Override
    public SgUser selectByGitId(String gitId) {
        return null;
    }
}
