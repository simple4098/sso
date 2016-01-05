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
 * 根据客栈id获取客栈个性化配置
 *
 * @author momo
 *
 */
@Controller
public class PersonalizedGetApiController extends AbstractController {

	@NotNull
	private UserDao userDao;

	@Override
	@ResponseBody
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 请求域名前缀，根据其获取所属客栈id
		String innId = request.getParameter("innId");
		response.setContentType("application/json;charset=UTF-8");
		if (StringUtils.isNotBlank(innId)) {
			Map<String, Object> personalized = userDao.findPersonalizedByInnId(Integer.parseInt(innId));
			if (personalized != null) {
				result.put(Constants.STATUS, Constants.HTTP_OK);
				result.put("personalized", personalized);
			} else {
				result.put(Constants.STATUS, Constants.HTTP_400);
				result.put(Constants.MESSAGE, "未找到相关配置！");
			}
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
