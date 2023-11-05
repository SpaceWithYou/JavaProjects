<%@page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.lang.*,java.util.*,java.net.*,java.util.*,java.text.*"%>
<%@ page import="javax.activation.*,javax.mail.*,org.apache.commons.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="java.net.http"%>
<%!
    public void callPut(String name, String email) {
        String uri = "http://localhost:8080/lab3/page?name=" + name + "&email=" + email;
        HttpPut req = new HttpPut(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        httpClient.execute(req);
    }
%>
<%
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    if(name != null && email != null) {
        callPut(name, email);
    }
%>
<html>
<head></head>
<body>
<h1>Create page</h1>
    <label for="name">
        <input type="text" name="name">
            Name:
        <input>
    </label>
    <label for="email">
        <input type="text" name="email">
            Email:
        <input>
    </label>
</body>
</html>