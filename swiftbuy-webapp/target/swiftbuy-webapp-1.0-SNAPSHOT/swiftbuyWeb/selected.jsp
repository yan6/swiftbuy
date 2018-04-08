<%@ page import="java.util.Date" %>
<%@ page import="com.ywj.swiftbuy.utils.DateUtils" %><%--
  Created by IntelliJ IDEA.
  User: ywj
  Date: 2018/3/22
  Time: 下午12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

</head>
<body>


<h1><c:out value="${user.username}"></c:out></h1>
<p>
    今天的日期：<%= DateUtils.format(new Date(), "yyyy-MM-dd")%>
</p>

</body>
</html>
