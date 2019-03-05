package com.ts.izh.lessons.dao.user;

import com.ts.izh.lessons.exception.UserException;
import com.ts.izh.lessons.domain.User;

import java.util.List;

public interface UserDao {
    void create(User user) throws UserException;
    void removeAll() throws UserException;
    List<User>findAll() throws UserException;
}
