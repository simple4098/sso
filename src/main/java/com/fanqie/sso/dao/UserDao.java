package com.fanqie.sso.dao;

import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/4/8
 * @version: v1.0.0
 */
public class UserDao extends BaseDao {
    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String,Object> findUserInfo(String ... params){
        Map<String,Object> map = getJdbcTemplate().queryForMap(this.sql,params);

        return map;
    }
}
