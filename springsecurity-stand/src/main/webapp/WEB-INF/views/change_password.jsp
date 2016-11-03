<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<a href="${contextPath}/logout">logout</a>
<a href="${contextPath}/account">account</a>
<h2>
    修改密码
</h2>
<form action="${contextPath}/changePassword">
    <div>
        旧密码：<input name="oldPassword" placeholder="请输入原始密码">
    </div>
    <div>
        新密码：<input name="newPassword" placeholder="新密码">
    </div>
    <button name="提交" type="submit" >提交</button>
</form>
</body>
</html>
