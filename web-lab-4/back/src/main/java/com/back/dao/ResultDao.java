package com.back.dao;

import com.back.entity.Result;
import com.back.entity.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class ResultDao {
    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;
    @Inject
    private UserDao userDao;

    public void save(Result result) {
        entityManager.persist(result);
    }

    public List<Result> getAllByUsername(String username) {
        try {
            TypedQuery<Result> query = entityManager.createQuery("SELECT r FROM Result r JOIN r.user u WHERE u.username = :username", Result.class);
            query.setParameter("username", username);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error retrieving results by username: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteAllByUsername(String username) {
        User user = userDao.getByUsername(username);
        if (user != null) {
            List<Result> results = getAllByUsername(username);
            for (Result result : results) {
                entityManager.remove(result);
            }
        }
    }
}
