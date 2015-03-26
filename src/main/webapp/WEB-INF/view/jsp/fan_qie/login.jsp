<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<title>番茄来了-认证中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- CSS -->
<link rel="stylesheet" href="/css/supersized.css">
<link rel="stylesheet" href="/css/login.css">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="js/html5.js"></script>
<![endif]-->
<script src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>
<script type="text/javascript" src="/js/tooltips.js"></script>
<script type="text/javascript" src="/js/login.js"></script>
</head>
<body>
<div class="page-container">
	<div class="main_box">
		<div class="login_box">
			<div class="login_logo">
				<img src="/images/login_logo.png" >
			</div>
			<div class="login_form">
				<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
                  <form:errors path="*" id="msg" cssClass="errors" element="div" />
					<div class="form-group">
						<label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid" /></label>
						<c:if test="${not empty sessionScope.openIdLocalId}">
							<strong>${sessionScope.openIdLocalId}</strong>
							<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>
						<c:if test="${empty sessionScope.openIdLocalId}">
							<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
							<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
					</div>
					<div class="form-group">
						<label for="password" class="fl-label"><spring:message code="screen.welcome.label.password" /></label>
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
					</div>
					<div class="form-group">
						<label  class="fl-label">验证码：</label>
						 <input type="text" id="yid" name="code"  class="form-control x164 in">
						<img id="captcha_img" src="/randomImg"  title="看不清楚?点击更换验证码" onclick="javascript:refresh(this);" class="m">
					</div>
					<div class="form-group">
						<label class="fl-label"></label>
						<label for="j_remember" class="m">
						<input id="j_remember" type="checkbox" value="true">&nbsp;记住登陆账号!</label>
					</div>
                  <div class="form-group space">
                    <input type="hidden" name="lt" value="${loginTicket}" />
                    <input type="hidden" name="execution" value="${flowExecutionKey}" />
                    <input type="hidden" name="_eventId" value="submit" />

                    <input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
                    <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" />
                  </div>
				</form:form>
			</div>
		</div>
		<div class="bottom">Copyright &copy;2012-2015成都番茄来了科技有限公司</div>
	</div>
</div>
<script>
	function refresh(obj){
		obj.src = "<c:url value='/randomImg?'/>"+Math.random();
	}
</script>
<!-- Javascript -->
<script src="/js/supersized.3.2.7.min.js"></script>
<script src="/js/supersized-init.js"></script>
<script src="/js/scripts.js"></script>

</body>
</html>