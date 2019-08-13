package com.dao.custom;

import com.model.User;

import java.util.List;

public interface UserDaoCustom {
    public List<User> getPaginatedList(Integer offset, Integer limit) throws Exception;

}
