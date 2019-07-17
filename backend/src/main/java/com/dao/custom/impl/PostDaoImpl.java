package com.dao.custom.impl;

import com.dao.custom.PostDaoCustom;
import com.model.Post;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PostDaoImpl implements PostDaoCustom {
    private SessionFactory sessionFactory;

    public PostDaoImpl(EntityManagerFactory factory){
        if (factory.unwrap(SessionFactory.class)== null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory =factory.unwrap(SessionFactory.class);
    }

    @Override
    public List<Post> getPaginatedList(Integer offset, Integer limit) throws Exception {
        Session s = sessionFactory.openSession();
        Criteria criteria = s.createCriteria(Post.class);
        criteria.setFirstResult((offset-1)*limit);
        criteria.setMaxResults(limit);
        List<Post> page = criteria.list();
        s.close();
        return page;
    }
}