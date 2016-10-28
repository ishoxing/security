<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<body>
<a href="${staticPath}/logout">logout</a>
<h2>100$!
    <%=request.getSession().getAttribute("name")%>
</h2>
</body>
</html>
