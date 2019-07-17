package com.dao.custom.impl;

import com.dao.UserDao;
import com.dao.custom.UserDaoCustom;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserDaoImpl implements UserDaoCustom {

    private SessionFactory sessionFactory;

    public UserDaoImpl(EntityManagerFactory factory){
        if (factory.unwrap(SessionFactory.class)== null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory =factory.unwrap(SessionFactory.class);
    }

    @Override
    public List<User> getPaginatedList(Integer offset, Integer limit) throws Exception {
        Session s = sessionFactory.openSession();
        Criteria criteria = s.createCriteria(User.class);
        criteria.setFirstResult((offset-1)*limit);
        criteria.setMaxResults(limit);
        List<User> page = criteria.list();
        s.close();
        return page;
    }
}
