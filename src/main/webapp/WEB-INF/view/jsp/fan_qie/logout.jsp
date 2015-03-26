<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fanQieFn" uri="http://www.fanqie.com/sso/functions" %>
<html>
<head>
	<title>番茄来了认证中心</title>
</head>
<body>
 <div id="msg" class="success">
	 <h2><spring:message code="screen.logout.header" /></h2>
     <p><spring:message code="screen.logout.success" /></p>
	 <p><spring:message code="screen.logout.security" /></p>
	 <p>正在跳转<span id="endTime"></span></p>
</div>
</body>
<script src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	var i = 5;
	function remainTime(){
		if(i==0){
			location.href='${fanQieFn:obtDefaultWebUrl()}';
		}
		$('#endTime').html(i--);
		setTimeout("remainTime()",1000);
	}
	remainTime();
</script>
</html>


