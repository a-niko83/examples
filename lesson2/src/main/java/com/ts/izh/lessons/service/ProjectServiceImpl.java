package com.ts.izh.lessons.service;

import com.ts.izh.lessons.dao.auto.AutoDao;
import com.ts.izh.lessons.dao.auto.impl.AutoRepository;
import com.ts.izh.lessons.dao.user.impl.UserRepository;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
import com.ts.izh.lessons.dao.user.UserDao;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final UserDao userDAO;
    private final AutoDao autoDAO;

    @Autowired
    public ProjectServiceImpl(UserDao userDAO, AutoDao autoDAO) {
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
    public void addAtoToUser(User user, Auto auto) throws AutoException {
        auto.setUser(user);
        autoDAO.update(auto);
    }

    @Override
    public List<User> getAllUsers() throws UserException, AutoException {
        List<User> users = userDAO.findAll();
//        for (User user : users) {
//            List<Auto> autos = getAllAutos(user);
//           // user.setAutos(autos);
//        }
        return users;
    }

    @Override
    public List<Auto> getAllAutos(User user) throws AutoException {
        return autoDAO.findAutoByUser(user);
    }

    @Override
    public List<Auto> getAllAutos() throws AutoException {
        return autoDAO.findAll();
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
