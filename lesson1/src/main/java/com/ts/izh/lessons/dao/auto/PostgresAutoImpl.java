package com.ts.izh.lessons.dao.auto;

import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Profile("jdbc")
@Repository
public class PostgresAutoImpl implements AutoDAO {
    private static final String INSERT_QUERY = "INSERT INTO lsn_autos (id, model, user_id) VALUES (%d, '%s', %d)";
    private static final String SELECT_QUERY = "select * from lsn_autos where user_id = %d";
    private static final String SELECT_ALL_QUERY = "select * from lsn_autos";
    private static final String DELETE_QUERY = "DELETE FROM lsn_autos";
    private static final String UPDATE_QUERY = "UPDATE lsn_autos SET model = '%s', user_id = %d WHERE id=%d";

    private final DataSource dataSource;

    public PostgresAutoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Auto auto) throws AutoException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            connection.prepareStatement(
                    String.format(
                            INSERT_QUERY,
                            auto.getId(),
                            auto.getModel(),
                            auto.getUser()!=null?auto.getUser().getId():null
                    )
            ).execute();
        } catch (Exception e) {
            throw new AutoException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Auto auto) throws AutoException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            connection.prepareStatement(
                    String.format(
                            UPDATE_QUERY,
                            auto.getModel(),
                            auto.getUser()!=null?auto.getUser().getId():null,
                            auto.getId()
                    )
            ).execute();
        } catch (Exception e) {
            throw new AutoException(e.getMessage(), e);
        }
    }

    @Override
    public void removeAll() throws AutoException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            connection.prepareStatement(DELETE_QUERY).execute();
        } catch (Exception e) {
            throw new AutoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Auto> getAutos(User user) throws AutoException {
        List<Auto> autos = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            ResultSet resultSet = connection.prepareStatement(String.format(SELECT_QUERY, user.getId())).executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto(resultSet.getInt("id"),resultSet.getString("model"));
                autos.add(auto);
            }
        } catch (Exception e) {
            throw new AutoException(e.getMessage(), e);
        }
        return autos;
    }

    @Override
    public List<Auto> getAllAutos() throws AutoException {
        List<Auto> autos = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            ResultSet resultSet = connection.prepareStatement(SELECT_ALL_QUERY).executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto(resultSet.getInt("id"),resultSet.getString("model"));
                Integer userId = resultSet.getInt("user_id");
                if (userId!=null && userId>0) {
                    User user = new User(userId);
                    auto.setUser(user);
                }
                autos.add(auto);
            }
        } catch (Exception e) {
            throw new AutoException(e.getMessage(), e);
        }
        return autos;
    }
}
