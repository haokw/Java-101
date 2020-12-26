<%@ page import="com.hao.learnjava.bean.*" %>
<%
    User user = (User) request.getAttribute("user");
%>
<html>
    <head>
        <title>Hello Hao - JSP</title>
    </head>
    <body>
        <h1>Hello <%= user.name %>!</h1>
        <p>School Name:
            <span style="color:red">
                <%= user.school.name %>
            </span>
        </p>
        <p>School Address:
            <span style="color:red">
                <%= user.school.address %>
            </span>
        </p>
    </body>
</html>
