package com.sg.cristina.service;

import com.sg.cristina.entity.SgUser;
import org.apache.ibatis.annotations.Select;

public interface UserService {

    /**
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer sgId);

    /**
     * @mbggenerated
     */
    int insert(SgUser record);

    /**
     * @mbggenerated
     */
    int insertSelective(SgUser record);

    /**
     * @mbggenerated
     */
    SgUser selectByPrimaryKey(Integer sgId);

    /**
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SgUser record);

    /**
     * @mbggenerated
     */
    int updateByPrimaryKey(SgUser record);


    /**
     * select user by gitId
     * @param gitId
     * @return
     */
    SgUser selectByGitId(String gitId);
}
