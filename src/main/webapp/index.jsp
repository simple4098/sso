<%@ page import="com.fanqie.sso.function.FanQieFunction" %>
<%@ page language="java"  session="false" %>
<%
final String queryString = request.getQueryString();
final String personalizedName = request.getHeader("personalized");
System.out.println("------------personalizedName:" + personalizedName);
String url_ =  FanQieFunction.obtDefaultWebIndex();
final String url = request.getContextPath() + "/login" + (queryString != null ? "?" + queryString : "?service=" + url_ + "&personalized=" + personalizedName);
response.sendRedirect(response.encodeURL(url));
%>
