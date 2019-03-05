package com.ts.izh.lessons.dao.auto.impl;

import com.ts.izh.lessons.dao.auto.AutoDao;
import com.ts.izh.lessons.dao.user.UserDao;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Profile("data")
@Transactional
public interface AutoRepository extends  AutoDao, CrudRepository<Auto, Integer> {
    @Override
    default void create(Auto auto) {
        save(auto);
    }
    @Override
    default void update(Auto auto) { save(auto); }
    @Override
    @Query("DELETE FROM Auto")
    @Modifying
    void removeAll() throws AutoException;
}
