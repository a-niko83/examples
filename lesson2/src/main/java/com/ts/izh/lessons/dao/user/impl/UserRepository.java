package com.ts.izh.lessons.dao.user.impl;

import com.ts.izh.lessons.dao.user.UserDao;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.UserException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Profile("data")
@Transactional
public interface UserRepository extends UserDao, CrudRepository<User, Integer> {
    @Override
    default void create(User user) {
        save(user);
    }
    @Override
    @Query("DELETE FROM User")
    @Modifying
    void removeAll() throws UserException;
}
