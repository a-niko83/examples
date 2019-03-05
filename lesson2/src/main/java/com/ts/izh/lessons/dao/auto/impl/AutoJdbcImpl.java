package com.ts.izh.lessons.dao.auto.impl;

import com.ts.izh.lessons.dao.auto.AutoDao;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.exception.AutoException;
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

@Profile("jdbc")
@Repository
public class AutoJdbcImpl implements AutoDao {
    private static final String INSERT_QUERY = "INSERT INTO autos (model, id_user) VALUES (?, ?)";
    private static final String SELECT_QUERY = "select * from autos left join users on autos.id_user = users.id_user where users.id_user = ?";
    private static final String SELECT_ALL_QUERY = "select * from autos left join users on autos.id_user = users.id_user";
    private static final String DELETE_QUERY = "DELETE FROM autos";
    private static final String UPDATE_QUERY = "UPDATE autos SET model = ?, id_user = ? WHERE id_auto=?";

    private final JdbcTemplate template;

    private RowMapper<Auto> autoRowMapper = new AutoRowMapper();

    public AutoJdbcImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void create(Auto auto) throws AutoException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update( new PreparedStatementCreator() {

                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[] {"id_auto"});
                            ps.setString(1, auto.getModel());
                            if (auto.getUser()!=null){
                                ps.setInt(2, auto.getUser().getId());
                            } else {
                                ps.setNull(2,java.sql.Types.NULL);
                            }
                            return ps;
                        }
                    }, keyHolder);
            auto.setId(keyHolder.getKey().intValue());
        } catch(Exception e) {
            throw new AutoException("Error create auto "+auto, e);
        }
    }

    @Override
    public void update(Auto auto) throws AutoException {
        try {
            template.update(UPDATE_QUERY,
                    auto.getModel(),
                    auto.getUser()!=null?auto.getUser().getId():null,
                    auto.getId());
        } catch (Exception e) {
            throw new AutoException("Error update auto", e);
        }
    }

   // @Override
    public void removeAll() throws AutoException {
        try {
            template.update(DELETE_QUERY);
        } catch (Exception e) {
            throw new AutoException("Error remove all auto", e);
        }
    }

    @Override
    public List<Auto> findAutoByUser(User user) throws AutoException {
        try {
            return template.query(SELECT_QUERY, new Object[]{user.getId()}, autoRowMapper );
        } catch (Exception e) {
            throw new AutoException("Error get auto by auto", e);
        }
    }

    @Override
    public List<Auto> findAll() throws AutoException {
        try {
            return template.query(SELECT_ALL_QUERY, autoRowMapper);
        } catch (Exception e) {
            throw new AutoException("Error get all autos", e);
        }
    }

    static class AutoRowMapper implements RowMapper<Auto> {
        @Override
        public Auto mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user =  User.builder().id(rs.getInt("id_user")).name(rs.getString("name")).build();
            Auto auto = Auto.builder().id(rs.getInt("id_auto")).model(rs.getString("model")).user(user).build();
            return auto;
        }
    }

}
