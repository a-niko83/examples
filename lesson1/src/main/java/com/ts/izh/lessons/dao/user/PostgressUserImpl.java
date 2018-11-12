package com.ts.izh.lessons.dao.user;

import com.ts.izh.lessons.dao.user.UserDAO;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.UserException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class PostgressUserImpl implements UserDAO {
    private static final String INSERT_STATMENT = "INSERT INTO lsn_users (id, name) VALUES (%d, '%s')";
    private static final String SELECT_QUERY = "select * from lsn_users";

    private final DataSource dataSource;

    public PostgressUserImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(User user) throws UserException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            connection.prepareStatement(
                    String.format(
                            INSERT_STATMENT,
                            user.getId(),
                            user.getName()
                    )
            ).execute();
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws UserException {

    }

    @Override
    public List<User> getAllUsers() throws UserException {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            ResultSet resultSet = connection.prepareStatement(SELECT_QUERY).executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"),resultSet.getString("name"));
                users.add(user);
            }
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return users;
    }
}
