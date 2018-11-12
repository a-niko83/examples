package com.ts.izh.lessons.service;

import com.ts.izh.lessons.dao.auto.AutoDAO;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
import com.ts.izh.lessons.dao.user.UserDAO;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserDAO userDAO;
    private final AutoDAO autoDAO;

    public ProjectServiceImpl(UserDAO userDAO, AutoDAO autoDAO) {
        this.userDAO = userDAO;
        this.autoDAO = autoDAO;
    }

    @Override
    public void createUser(User user) throws UserException {
        userDAO.create(user);
    }

    @Override
    public void createAuto(Auto auto) throws AutoException {
        autoDAO.create(auto);
    }

    @Override
    public void addAtoToUser(User user, Auto auto) throws UserException, AutoException {
        user.getAutos().add(auto);
        auto.setUser(user);
        userDAO.update(user);
        autoDAO.update(auto);
    }

    @Override
    public List<User> getAllUsers() throws UserException, AutoException {
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            List<Auto> autos = getAllAutos(user);
            user.setAutos(autos);
        }
        return users;
    }

    @Override
    public List<Auto> getAllAutos(User user) throws AutoException {
        if (user==null){
            return autoDAO.getAllAutos();
        } else {
            return autoDAO.getAutos(user);
        }
    }

    @Override
    public void removeAllUsers() throws UserException {
        userDAO.removeAll();
    }

    @Override
    public void removeAllAutos() throws AutoException {
        autoDAO.removeAll();
    }
}
