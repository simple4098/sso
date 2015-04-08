package com.fanqie.sso.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/4/8
 * @version: v1.0.0
 */
public class BaseDao {
    @NotNull
    private JdbcTemplate jdbcTemplate;

    @NotNull
    private DataSource dataSource;

    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    protected final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected final DataSource getDataSource() {
        return this.dataSource;
    }

}
