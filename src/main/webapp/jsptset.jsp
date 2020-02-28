<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/1/16
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.lb.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private int a;
    private int b;

    private void someMethod() {
    }
%> <!--属性和方法-->
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("/login.html");
        return;
    }
%> <!--doGet/doPost代码块-->
<html>
<head>
    <title>发布文章</title>
</head>
<body>
<h1><%= user.getNickname() %></h1>
<form method="post" action="/post">
    <input type="text" name="title">
    <textarea name="content"></textarea>
</form>
</body>
</html>
