<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<body>
<a href="${contextPath}/logout">logout</a>
<a href="${contextPath}/account">account</a>
<a href="${contextPath}/toChangePassword">toChangePassword</a>
<h2>Hello World!
    <%=request.getSession().getAttribute("name")%>
</h2>
</body>
</html>
