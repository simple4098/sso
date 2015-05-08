package com.fanqie.sso.controller;

import com.fanqie.sso.dao.UserDao;
import net.sf.json.JSONObject;
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
        if ("fanQieST".equals(tonKent)) {
            Map<String, Object> userInfo = userDao.findUserInfo(userName, userName);
            String parentMobile = "";
            if (userInfo!=null){
                Integer pId = (Integer)userInfo.get("parent_id");
                Map<String, Object> parentMap = null;
                if (pId!=null){
                    parentMap = userDao.findUserInfo(pId.toString());
                    parentMobile = (String)parentMap.get("mobile");
                }else {
                    parentMobile = (String)userInfo.get("mobile");
                }
                userInfo.put("parentMobile",parentMobile);
            }
            JSONObject jsonObject = JSONObject.fromObject(userInfo);
            response.setContentType("json/html;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());
            //response.getWriter().flush();
        }
        return null;
    }



    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
