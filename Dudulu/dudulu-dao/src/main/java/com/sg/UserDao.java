package com.sg.cristina.dao;

import com.sg.cristina.entity.SgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<SgUser, Object> {
}
