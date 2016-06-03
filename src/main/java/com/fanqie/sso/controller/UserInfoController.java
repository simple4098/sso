package com.fanqie.sso.controller;

import com.fanqie.sso.dao.UserDao;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/4/24
 * @version: v1.0.0
 */
@Controller
public class UserInfoController extends AbstractController {
    private  static  final Logger log =   Logger.getLogger(UserInfoController.class);
    @NotNull
    private UserDao userDao;

  /*  @RequestMapping("/userInfo")
    @ResponseBody
    public Object obtUserInfo(String userName){
        Map<String, Object> userInfo = userDao.findUserInfo(userName, userName);
        return userInfo;

    }*/

    @Override
    @ResponseBody
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String tonKent = request.getParameter("tonKent");
        String innIdStr = request.getParameter("innId");
        Integer omsInnId = null;
        if ("fanQieST".equals(tonKent)) {
            Map<String, Object> userInfo = null;
            Map<String, Object> userInn = null;
            if (StringUtils.isNotEmpty( innIdStr)){
                omsInnId = Integer.valueOf(innIdStr);
                userInfo = userDao.findOmsUserInfo(omsInnId);
            }
            if (StringUtils.isNotEmpty(userName)){
                userInn = userDao.findUserInfo(userName,userName);
            }
            if (userInfo!=null && userInn!=null){
                userInfo.put("user_code",userInn.get("user_code"));
            }
            String parentMobile = "";
            String innName = "";
            if (userInfo!=null){
                Integer innId =  (Integer)userInfo.get("inn_id");
                if (innId!=null){
                    Map<String, Object> innInfo = userDao.findInnInfoById(innId);
                    innName = (String)innInfo.get("name");
                }
                Integer pId = (Integer)userInfo.get("parent_id");
                Map<String, Object> parentMap = null;
                if (pId!=null){
                    parentMap = userDao.findUserInfoById(pId);
                    parentMobile = (String)parentMap.get("mobile");
                }else {
                    parentMobile = (String)userInfo.get("mobile");
                }
                userInfo.put("parentMobile",parentMobile);
                userInfo.put("innName",innName);
            }
            JSONObject jsonObject = JSONObject.fromObject(userInfo);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());
            response.getWriter().flush();
        }
        return null;
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
