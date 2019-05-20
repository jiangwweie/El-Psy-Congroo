package com.sg.user.dao;

import com.sg.SgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<SgUser, Object> {
}
