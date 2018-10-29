package com.ts.izh.lessons.dao.auto;

import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
import com.ts.izh.lessons.exception.UserException;
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
    private static final String INSERT_STATMENT = "INSERT INTO lsn_autos (id, model, user_id) VALUES (%d, '%s', %d)";
    private static final String SELECT_QUERY = "select * from lsn_autos";

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
                            INSERT_STATMENT,
                            auto.getId(),
                            auto.getModel(),
                            auto.getUser()!=null?auto.getUser().getId():null
                    )
            ).execute();
        } catch (Exception e) {
            throw new AutoException(e.getMessage());
        }
    }

    @Override
    public void update(Auto auto) throws AutoException {

    }

    @Override
    public List<Auto> getAllAutos() throws AutoException {
        List<Auto> autos = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            ResultSet resultSet = connection.prepareStatement(SELECT_QUERY).executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto(resultSet.getInt("id"),resultSet.getString("model"));
                autos.add(auto);
            }
        } catch (Exception e) {
            throw new AutoException(e.getMessage());
        }
        return autos;
    }
}
