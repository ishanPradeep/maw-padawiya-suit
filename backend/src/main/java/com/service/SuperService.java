package com.service;

import java.util.List;

public interface SuperService <T>{
    public T add(T t) throws Exception;

    public boolean update(T t ) throws Exception;

    public boolean delete(T t) throws Exception;

    public T findById(Integer id) throws Exception;

    public List<T> getAll() throws Exception;
}
