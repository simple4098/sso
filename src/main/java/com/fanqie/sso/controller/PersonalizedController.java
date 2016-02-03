package com.fanqie.sso.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.alibaba.druid.support.json.JSONUtils;
import com.fanqie.sso.common.Configuration;
import com.fanqie.sso.dao.UserDao;

/**
 * 客栈个性化
 *
 * @author momo
 *
 */
@Controller
public class PersonalizedController extends AbstractController {

	@NotNull
	private UserDao userDao;

	@Override
	@ResponseBody
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String domainPrefix = request.getParameter("domainPrefix");
		// 请求header中的个性化配置名称，根据其获取所属客栈id
		String personalizedName = request.getHeader("personalized");
		logger.error("----------请求个性化配置header值：" + personalizedName);
		try {
			if (StringUtils.isNotBlank(personalizedName)) {
				Map<String, Object> personalized = userDao.findPersonalizedByDomainPrefix(personalizedName);
				if (personalized != null) {
					personalized.put("pms_domain", Configuration.getWebHost());
					response.setContentType("application/json;charset=UTF-8");
					response.getWriter().print(JSONUtils.toJSONString(personalized));
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
