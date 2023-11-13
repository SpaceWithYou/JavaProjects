<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.http.HttpRequest,java.net.URI,java.net.http.HttpClient,java.net.http.HttpResponse"%>
<%!
    public String callPost(String name, String id) {
        String res = "";
        try {
            String s = "http://localhost:8080/lab3/AuthPage.jsp";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(s)).POST().header("TomcatSession", name:" + name + "/id:"+ id).build();;
            request.getSession().setAttribute("TomcatSession", "name:" + name + "/id:"+ id);
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            res = response.body();
        } catch(Exception e) {
            res = "Error" + e.getMessage();
        }
        return res;
    }
%>
<%
    String resp = "";
    String name = request.getParameter("name");
    String id = request.getParameter("id");
    if(id != null && name != null) {
        resp = callPost(name, id);
    }
%>
<html>
<head></head>
<body>
<h1>Auth page</h1>
<form action="#">
    Name:<input type="text" name="name">
    <br>
    Password(id):<input type="text" name="id">
    <br>
    <button type="submit" name="button" value="button1">Auth</button>
</form>
<%= resp%>
</body>
</html>