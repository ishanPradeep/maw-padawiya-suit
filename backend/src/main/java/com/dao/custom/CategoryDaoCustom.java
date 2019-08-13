package com.dao.custom;

import com.model.Category;

import java.util.List;

public interface CategoryDaoCustom {
    public List<Category> getPaginatedList(Integer offset, Integer limit) throws Exception;

}
