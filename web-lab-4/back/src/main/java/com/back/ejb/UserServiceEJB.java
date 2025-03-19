package com.back.ejb;

import com.back.dao.UserDao;
import com.back.entity.User;
import com.back.util.HashPassword;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class UserServiceEJB {
    @Inject
    private UserDao userDao;

    public User register(String username, String password) {
        if (userDao.getByUsername(username) == null){
            User user = new User(username, HashPassword.hashPassword(password));
            userDao.save(user);
            return user;
        }
        return null;
    }

    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (user != null && HashPassword.checkPassword(password, user.getPassword())){
            return user;
        }
        return null;
    }

    public User check(String username) {
        User user = userDao.getByUsername(username);
        if (user != null){
            return user;
        }
        return null;
    }

}
