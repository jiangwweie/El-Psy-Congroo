package com.sg;

public interface UserService {

    /**
     * @mbggenerated
     */
    void deleteByPrimaryKey(Integer sgId);

    /**
     * @mbggenerated
     */
    void insert(SgUser record);

    /**
     * @mbggenerated
     */
    void insertSelective(SgUser record);

    /**
     * @mbggenerated
     */
    SgUser selectByPrimaryKey(Integer sgId);

    /**
     * @mbggenerated
     */
    void updateByPrimaryKeySelective(SgUser record);

    /**
     * @mbggenerated
     */
    void updateByPrimaryKey(SgUser record);


    /**
     * select user by gitId
     * @param gitId
     * @return
     */
    SgUser selectByGitId(String gitId);

    SgUser selectByUsername(String username);
}
