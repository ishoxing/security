<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<html>
<body>
<a href="${contextPath}/logout">logout</a>
<sec:authorize url="/account" access="hasRole('ADMIN') and fullyAuthenticated" >
    <li><a href="${contextPath}/account">account</a></li>
</sec:authorize>
<c:if test="${showAccountLink}" >
    <li><a href="${contextPath}/toChangePassword">toChangePassword</a></li>
</c:if>



<h2>Hello World!
    <%=request.getSession().getAttribute("name")%>
</h2>
</body>
</html>
