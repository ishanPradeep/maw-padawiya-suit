package com.dao.custom;

import com.model.Post;

import java.util.List;

public interface PostDaoCustom {
    public List<Post> getPaginatedList(Integer offset, Integer limit) throws Exception;

}
