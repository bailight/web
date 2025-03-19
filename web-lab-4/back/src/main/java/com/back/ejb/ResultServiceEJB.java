package com.back.ejb;

import com.back.dao.ResultDao;
import com.back.entity.Result;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ResultServiceEJB {
    @Inject
    private ResultDao resultDao;

    public void saveResult(Result result) {
        resultDao.save(result);
    }

    public List<Result> getAllResultsByUsername(String username) {
        try {
            return resultDao.getAllByUsername(username);
        } catch (Exception e) {
            return List.of(); // 返回空列表
        }
    }

    public void deleteAllResultsByUsername(String username) {
        resultDao.deleteAllByUsername(username);
    }
}
