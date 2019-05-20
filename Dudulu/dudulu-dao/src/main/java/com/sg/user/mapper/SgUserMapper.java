package com.sg.user.mapper;


import com.sg.SgUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SgUserMapper {

    int deleteByPrimaryKey(Integer sgId);


    int insert(SgUser record);

    int insertSelective(SgUser record);


    SgUser selectByPrimaryKey(Integer sgId);


    int updateByPrimaryKeySelective(SgUser record);


    int updateByPrimaryKey(SgUser record);


    SgUser selectByUsername(String username);
}