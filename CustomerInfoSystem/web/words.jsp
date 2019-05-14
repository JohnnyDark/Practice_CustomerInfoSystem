<%--
  Created by IntelliJ IDEA.
  User: works-0326
  Date: 2019/5/9
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/filterTestServlet" method="post">
        <input type="text" name="name" id="n"><br>
        <input type="text" name="address" id="addr"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
