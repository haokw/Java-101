<html>
    <head>
        <title>Hello Hao - JSP</title>
    </head>
    <body>
        <%-- JSP Comment --%>
        <h1>Hello Hao!</h1>
        <p>
            <%
                out.println("Your Ip address is ");
            %>
            <span style="color:red">
                <%= request.getRemoteAddr() %>
            </span>
        </p>
    </body>
</html>
