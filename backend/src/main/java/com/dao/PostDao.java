package com.dao;

import com.dao.custom.PostDaoCustom;
import com.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post,Integer>, PostDaoCustom {
}
