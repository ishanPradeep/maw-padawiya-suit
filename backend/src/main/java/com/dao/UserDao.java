package com.dao;

import com.dao.custom.UserDaoCustom;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer>, UserDaoCustom {
}
