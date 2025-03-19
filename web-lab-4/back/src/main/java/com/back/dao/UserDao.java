package com.back.dao;

import com.back.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class UserDao {
    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User getByUsername(String username) {
        TypedQuery<User> userQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        userQuery.setParameter("username", username);
        try {
            return userQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

