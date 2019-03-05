package com.ts.izh.lessons.dao.user.impl;

import com.ts.izh.lessons.dao.user.UserDao;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("jdbc")
public class UserJdbcDaoImpl implements UserDao {
    private static final String INSERT_QUERY = "insert into users (name) values(?);";
    //private static final String SELECT_BY_ID_QUERY = "select * from users where id_user = ?;";
    private static final String SELECT_ALL_QUERY = "select * from users";
    private static final String DELETE_ALL_QUERY = "DELETE FROM users";

    private final JdbcTemplate template;

    private RowMapper<User> userRowMapper = new UserRowMapper();

    @Autowired
    public UserJdbcDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void create(User user) throws UserException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update( new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[] {"id_user"});
                    ps.setString(1, user.getName());
                    return ps;
                }
            }, keyHolder);
            user.setId(keyHolder.getKey().intValue());
        } catch(Exception e) {
            throw new UserException("Error create user "+user, e);
        }
    }

//
//    public User findById(int id) throws UserException {
//        try {
//            return template.queryForObject(SELECT_BY_ID_QUERY, new Object[]{id}, new UserRowMapper());
//        } catch (Exception e) {
//            throw new UserException("Error getById("+id+") error", e);
//        }
//    }



    @Override
    public void removeAll() throws UserException {
        try {
            template.update(DELETE_ALL_QUERY);
        } catch (Exception e) {
            throw new UserException("Error remove all users", e);
        }
    }

    @Override
    public List<User> findAll() throws UserException {
        try {
            return template.query(SELECT_ALL_QUERY, userRowMapper);
        } catch (Exception e) {
            throw new UserException("Error get all users", e);
        }
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder().id(rs.getInt("id_user")).name(rs.getString("name")).build();
        }
    }
}
