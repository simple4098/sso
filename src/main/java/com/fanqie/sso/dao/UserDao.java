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

    private String parentSql;
    private String innSql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParentSql() {
        return parentSql;
    }

    public void setParentSql(String parentSql) {
        this.parentSql = parentSql;
    }

    public String getInnSql() {
        return innSql;
    }

    public void setInnSql(String innSql) {
        this.innSql = innSql;
    }

    public Map<String,Object> findUserInfo(String ... params){
        return getJdbcTemplate().queryForMap(this.sql,params);
    }
    public Map<String,Object> findUserInfoById(Integer ... params){
        return  getJdbcTemplate().queryForMap(this.parentSql, params);
    }

    public Map<String,Object> findInnInfoById(Integer ... params){
        return  getJdbcTemplate().queryForMap(this.innSql, params);
    }
}
