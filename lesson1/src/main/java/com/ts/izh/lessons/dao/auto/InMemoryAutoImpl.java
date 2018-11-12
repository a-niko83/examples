package com.ts.izh.lessons.dao.auto;

import com.ts.izh.lessons.dao.user.UserDAO;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("!jdbc")
public class InMemoryAutoImpl implements AutoDAO {

    private Map<Integer, Auto> autos = new HashMap<>();

    @Override
    public void create(Auto auto) throws AutoException {
        autos.put(auto.getId(), auto);
    }

    @Override
    public void update(Auto auto) throws AutoException {
        autos.replace(auto.getId(), auto);
    }

    @Override
    public void removeAll() throws AutoException {
        autos = new HashMap<>();
    }

    @Override
    public List<Auto> getAllAutos() throws AutoException {
        return new ArrayList<>(autos.values());
    }

    @Override
    public List<Auto> getAutos(User user) throws AutoException {
        List<Auto> result = new ArrayList<>();
        for (Auto auto: autos.values()) {
            if (auto.getUser()!=null && auto.getUser().getId() == user.getId()) {
                result.add(auto);
            }
        }
        return result;
    }
}
