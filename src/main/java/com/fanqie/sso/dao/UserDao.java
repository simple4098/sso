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
	private String personalizedGetByDomainPrefixSql;
	private String personalizedGetByInnIdSql;
	private String personalizedSetSql;

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

	public String getPersonalizedGetByDomainPrefixSql() {
		return personalizedGetByDomainPrefixSql;
	}

	public void setPersonalizedGetByDomainPrefixSql(String personalizedGetByDomainPrefixSql) {
		this.personalizedGetByDomainPrefixSql = personalizedGetByDomainPrefixSql;
	}

	public String getPersonalizedGetByInnIdSql() {
		return personalizedGetByInnIdSql;
	}

	public void setPersonalizedGetByInnIdSql(String personalizedGetByInnIdSql) {
		this.personalizedGetByInnIdSql = personalizedGetByInnIdSql;
	}

	public String getPersonalizedSetSql() {
		return personalizedSetSql;
	}

	public void setPersonalizedSetSql(String personalizedSetSql) {
		this.personalizedSetSql = personalizedSetSql;
	}

	public Map<String, Object> findUserInfo(String... params) {
		return getJdbcTemplate().queryForMap(this.sql, params);
	}

	public Map<String, Object> findUserInfoById(Integer... params) {
		return getJdbcTemplate().queryForMap(this.parentSql, params);
	}

	public Map<String, Object> findInnInfoById(Integer... params) {
		return getJdbcTemplate().queryForMap(this.innSql, params);
	}

	public Map<String, Object> findPersonalizedByDomainPrefix(String... params) {
		try {
			return getJdbcTemplate().queryForMap(this.personalizedGetByDomainPrefixSql, params);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Map<String, Object> findPersonalizedByInnId(Integer... params) {
		try {
			return getJdbcTemplate().queryForMap(this.personalizedGetByInnIdSql, params);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPersonalized(Object... params) {
		getJdbcTemplate().update(personalizedSetSql, params);
	}

}
