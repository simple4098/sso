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
	<meta charset="utf-8">
	<title>番茄来了-认证中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="免费客栈系统">
	<meta name="author" content="">
	<meta name="renderer" content="webkit">
	<link rel="shortcut icon" type="image/ico" href="http://assets.fanqiele.com/1.0.8/images/favicon.ico" />
	<link type="text/css" rel="stylesheet" href="http://assets.fanqiele.com/1.0.8/pkg/common.css">
	<link rel="stylesheet" href="/static/css/login.css">
</head>
<body>
<!-- head -->
<div id="head_wrap">
	<div id="head">
		<div class="wid1000">
			<a  href="http://www.fanqiele.com"><em class="icon"></em></a>
		</div>
	</div>
</div>
<!-- login -->
<div id="container">
	<div class="wid1000 wrap-login">
		<div class="box-login">
			<div class="login-head">
				<h3 class="login-title">登录</h3>
			</div>
			<div class="login-body">
				<formF:form method="post" id="fm1" cssClass="" commandName="${commandName}" htmlEscape="true">
					<form:errors path="*" id="error_v" cssClass="error_v" element="div" cssStyle="display: none" />
					<div class="input-wrapper" id="account">
						<c:if test="${not empty sessionScope.openIdLocalId}">
							<strong>${sessionScope.openIdLocalId}</strong>
							<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>
						<c:if test="${empty sessionScope.openIdLocalId}">
							<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
							<formF:input cssClass="login-input" cssErrorClass="error"  placeholder="登录账号/手机号" id="username"  size="15" tabindex="1"
										 accesskey="${userNameAccessKey}"  path="username" autocomplete="false" htmlEscape="true" />
							<div id="mobile_tip" class="tip">账户名必须填写！</div>
						</c:if>
					</div>
					<div class="input-wrapper"  id="pwd">
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<formF:password cssClass="login-input" cssErrorClass="error" id="password" size="25" tabindex="2"
									   path="password" placeholder="密码"  accesskey="${passwordAccessKey}" autocomplete="false" htmlEscape="true" />
						<div id="password_tip" class="tip">密码必须填写！</div>
					</div>

					<div class="captcha-wrapper">
						<div class="captcha-box" id="captcha">
							<%--<formF:input cssClass="login-input" cssErrorClass="error"  placeholder="输入验证码" id="code"   size="15" tabindex="1"
										 path="code"   autocomplete="false" htmlEscape="true" />--%>
							<input tabindex="3"  class="login-input captcha-input" name="code" type="text" placeholder="输入验证码">
						</div>
						<img id="captcha_img" src="/randomImg"  title="看不清楚?点击更换验证码" onclick="javascript:refresh(this);" class="m">
						<div id="captcha_tip" class="tip">验证码必须填写！</div>
					</div>
					<div class="form-group space" style="display: none">
						<input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
					</div>
					<input class="ui-botton login-botton" name="submit" accesskey="l" value="立即登录" tabindex="4" type="submit" />
					<a class="ui-botton registe-botton"  href="${fanQieFn:obtRegisterUrl()}">永久免费会员申请</a>
				</formF:form>
			</div>
		</div>
	</div>
</div>
<!-- foot -->
<div id="foot">
	<div class="wid1000" style="width: 670px;text-align: center;">
		<div class="contact-index">
			<em class="icon icon-tele"></em>咨询热线：4000-230-190<a href="tencent://message/?uin=1070375586&Site=1070375586&Menu=yes"><em class="icon icon-qq-index"><img src="/static/img/index_qq.png" alt="qq"></em></a><em class="icon icon-wx-index"><img src="/static/img/index_wx.png" alt="微信"></em><a target="_blank" href="http://weibo.com/fanqielaile"><em class="icon icon-weibo-index"></em></a>
		</div>
		<ul class="fl fl-first">
			<li><a href="http://www.fanqiele.com/about.html">关于我们</a></li>
			<li><a href="http://www.fanqiele.com/contact.html">联系我们</a></li>
		</ul>
		<ul class="fr">
			<li>成都番茄来了科技有限公司</li>
			<li>蜀ICP备13017483号</li>
			<li>推荐浏览器：<a style="color: #f4ae5b;" href="http://w.x.baidu.com/alading/anquan_soft_down_b/14744">谷歌浏览器Chrome</a></li>
		</ul>
		<div class="clear"></div>
	</div>
</div>
<script type="text/javascript" src="/static/js/jquery_1.9.js"></script>
<script type="text/javascript" src="/static/js/login.js"></script>
<script>

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