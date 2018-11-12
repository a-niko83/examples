package com.ts.izh.lessons.dao.auto;

import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;

import java.util.List;

public interface AutoDAO {
    void create(Auto auto) throws AutoException;
    void update(Auto auto) throws AutoException;
    List<Auto>getAllAutos() throws AutoException;
}
