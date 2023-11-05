<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.lang.*,java.util.*,java.net.*,java.util.*,java.text.*"%>
<%--<%@ page import="javax.activation.*,javax.mail.*,org.apache.commons.*"%>--%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.net.http.HttpRequest,java.net.URI,java.net.http.HttpClient,java.net.http.HttpResponse"%>
<%!
    //java.net.http.*,
    public void callPost(String name, String email) {
        try {
            String s = "http://localhost:8080/lab3/page?name=" + name + "&email=" + email;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(s)).POST(HttpRequest.BodyPublishers.ofString("Test")).build();
            HttpClient client = HttpClient.newBuilder().build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception e) {
        }
    }
%>
<%
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    if(name != null && email != null) {
        callPost(name, email);
    }
%>
<html>
<head></head>
<body>
<h1>Create page</h1>
<form action="#" method="post">
    Name:<input type="text" name="name">
    Email:<input type="text" name="email">
    <br>
    <button type="submit" name="button" value="button1">Create</button>
</form>
</body>
</html>