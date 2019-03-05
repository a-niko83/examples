package com.ts.izh.lessons.service;

import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;

import java.util.List;

public interface ProjectService {
    void createUser(User user) throws UserException;
    void createAuto(Auto auto) throws AutoException;
    void addAtoToUser(User user, Auto auto) throws UserException, AutoException;
    List<User> getAllUsers() throws UserException, AutoException;
    List<Auto> getAllAutos(User user) throws AutoException;
    List<Auto> getAllAutos() throws AutoException;
    void removeAllUsers() throws UserException;
    void removeAllAutos() throws AutoException;
}
