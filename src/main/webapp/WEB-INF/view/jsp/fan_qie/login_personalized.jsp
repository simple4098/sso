<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formF" uri="http://www.fanqiele.com/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fanQieFn" uri="http://www.fanqie.com/sso/functions" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="免费客栈系统">
	<meta name="author" content="">
	<meta name="renderer" content="webkit">
	<title>番茄来了-认证中心</title>
	<link rel="stylesheet" media="screen" href="/static/css/lib.min.css" />
	<link rel="stylesheet" media="screen" href="/static/css/login_personalized.css" />
</head>
<body>
	<div class="header">
		<img id="login_header_img">
		<span id="login_header_title"></span>
		<input type="hidden" id="personalizedName" value="<%=request.getParameter("personalized")%>" />
	</div>
	<div id="container" class="content">
		<div class="login">
			<div class="login-title">
				登录
				<a href="${fanQieFn:obtRegisterUrl()}">立即注册</a>
			</div>
			<div class="login-form">
			<formF:form method="post" id="fm1" cssClass="" commandName="${commandName}" htmlEscape="true">
				<form:errors path="*" id="error_v" cssClass="error_v" element="div" cssStyle="display: none" />
				<div class="input-wrapper" id="account">
					<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
					</c:if>
					<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<formF:input cssClass="nuber-mail" cssErrorClass="error" placeholder="登录账号/手机号" id="username" tabindex="1"
							accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						<div id="mobile_tip" class="tip">登录账号/手机号必须填写！</div>
					</c:if>
				</div>
				<div class="input-wrapper"  id="pwd">
					<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
					<formF:password cssClass="password" cssErrorClass="error" placeholder="密码" id="password" tabindex="2"
						accesskey="${passwordAccessKey}" path="password" autocomplete="false" htmlEscape="true" />
					<div id="password_tip" class="tip">密码必须填写！</div>
				</div>
				<div class="input-wrapper" id="captcha">
					<input tabindex="3" type="text" name="code" placeholder="输入验证码" class="input-code fl">
					<img id="captcha_img" src="/randomImg" title="看不清楚?点击更换验证码" onclick="javascript:refresh(this);" class="fr codeimg">
					<div id="captcha_tip" class="tip clear">验证码必须填写！</div>
				</div>
				<div class="form-group space" style="display: none">
					<input type="hidden" name="lt" value="${loginTicket}" />
					<input type="hidden" name="execution" value="${flowExecutionKey}" />
					<input type="hidden" name="_eventId" value="submit" />
				</div>
				<div class="forgetpassword"><a target="_blank" href="${fanQieFn:forgetPassword()}">忘记登录密码？</a></div>
				<button accesskey="l" tabindex="4" class="loginbar">立即登录</button>
			</formF:form>
			</div>
		</div>
	</div>
	<div class="footer">
		<p><span id="login_footer_content"></span>  蜀ICP备13017483号   技术支持：番茄来了</p>
	</div>
	
	<div id="warn_tip_popups" class="tomasky-ui-popups dialog-browser-detection" style="display: none">
	    <div class="ui-popups-dialog">
	        <em class="close" data-dismiss="popups" ms-click="modifyRoomNumClose">x</em>
	        <div class="ui-popups-head">
	            <h3 class="ui-popups-title">浏览器版本提示</h3>
	        </div>
	        <div class="ui-popups-body" style="width: 560px">
	        	<h2>检测到您的浏览器已经过时了</h2>
	        	<p>低版本浏览器将导致系统无法正常使用！为了您更好的使用体验</p>
	        	<p>建议您点击下载以下浏览器。</p>
	        	<div class="down-brower">
	        		<ul>
	        			<li>
	        				<a href="http://se.360.cn/">
		        				<dl>
		        					<dd><img src="/static/img/360.png"></dd>
		        					<dd class="grey">360浏览器</dd>
		        					<dd>可连接身份读卡器</dd>
		        				</dl>
	        				</a>
	        			</li>
	        			<li>
	        				<a href="http://down.tech.sina.com.cn/page/40975.html?qq-pf-to=pcqq.c2c">
		        				<dl>
		        					<dd><img src="/static/img/google.png"></dd>
		        					<dd class="grey">chrome浏览器</dd>
		        					<dd>不支持身份读卡器</dd>
		        				</dl>
	        				</a>
	        			</li>
	        		</ul>
	        	</div>
	        	<p><a class="accept">我知道了，仍然进入系统>></a></p>
	        </div>
	    </div>
	</div>
	<script type="text/javascript" src="/static/js/jquery_1.9.js"></script>
	<script type="text/javascript" src="/static/js/login.js"></script>
	<script>
		//客栈个性化
		$.get("/getPersonalizedByDomainPrefix?personalized=" + $("#personalizedName").val()).done(function(obj) {
			if(obj){
				var pms_domain = obj.pms_domain
				$("#login_header_title").text(obj.login_header_title)
				$("#login_header_img").prop("src", pms_domain + obj.login_header_img)
				$("#container").css("background-image","url(" + pms_domain + obj.login_main_img + ")")
				$("#login_footer_content").text(obj.login_footer_content)
			} else {
				$("#login_header_title").text("番茄来了")
				$("#login_header_img").prop("src", "/static/img/LOGO.png")
				$("#container").css("background-image","url(/static/img/loginBg.png)")
				$("#login_footer_content").text("Copyright © 2016 番茄来了 All Rights Reserved.")
			}
		});

		// 百度统计
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "//hm.baidu.com/hm.js?0973f65b53ae7e57f9418dfcd41357a8";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
</body>
</html>