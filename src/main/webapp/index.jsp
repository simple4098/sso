<%@ page import="com.fanqie.sso.function.FanQieFunction" %>
<%@ page language="java"  session="false" %>
<%
final String queryString = request.getQueryString();
String url_ =  FanQieFunction.obtDefaultWebIndex();
final String url = request.getContextPath() + "/login" + (queryString != null ? "?" + queryString : "?service=" + url_ + "&from=index");
response.sendRedirect(response.encodeURL(url));
%>
