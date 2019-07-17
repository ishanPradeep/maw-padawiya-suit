package com.dao.custom.impl;

import com.dao.custom.CategoryDaoCustom;
import com.model.Category;
import com.model.Post;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CategoryDaoImpl implements CategoryDaoCustom {

    private SessionFactory sessionFactory;

    public CategoryDaoImpl(EntityManagerFactory factory){
        if (factory.unwrap(SessionFactory.class)== null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory =factory.unwrap(SessionFactory.class);
    }


    @Override
    public List<Category> getPaginatedList(Integer offset, Integer limit) throws Exception {
        Session s = sessionFactory.openSession();
        Criteria criteria = s.createCriteria(Category.class);
        criteria.setFirstResult((offset-1)*limit);
        criteria.setMaxResults(limit);
        List<Category> page = criteria.list();
        s.close();
        return page;
    }
}
