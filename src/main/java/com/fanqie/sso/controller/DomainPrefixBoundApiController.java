package com.fanqie.sso.controller;

import java.util.HashMap;
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
import com.fanqie.sso.common.Constants;
import com.fanqie.sso.dao.UserDao;

/**
 * 客栈个性化
 * @author momo
 *
 */
@Controller
public class DomainPrefixBoundApiController extends AbstractController {

	@NotNull
	private UserDao userDao;

	@Override
	@ResponseBody
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String innId = request.getParameter("innId");
		String domainPrefix = request.getParameter("domainPrefix");
		response.setContentType("application/json;charset=UTF-8");
		if (StringUtils.isNotBlank(innId) && StringUtils.isNotBlank(domainPrefix)) {
			userDao.setPersonalized(Integer.parseInt(innId), domainPrefix);
			result.put(Constants.STATUS, Constants.HTTP_OK);
		} else {
			result.put(Constants.STATUS, Constants.HTTP_400);
			result.put(Constants.MESSAGE, "无效参数！");
		}
		response.getWriter().print(JSONUtils.toJSONString(result));
		return null;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
