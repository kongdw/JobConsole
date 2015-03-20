<%--
  Created by IntelliJ IDEA.
  User: irock
  Date: 15-3-4
  Time: 下午4:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>作业列表</title>
    <table>
        <c:forEach items="${list}" var="job" varStatus="idx">

        </c:forEach>
    </table>
</head>
<body>

</body>
</html>
