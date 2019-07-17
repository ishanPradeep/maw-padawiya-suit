package com.dao;

import com.dao.custom.CategoryDaoCustom;
import com.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Integer>, CategoryDaoCustom {
    public Category findByName(String name);
}
