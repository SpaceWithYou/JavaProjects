<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.http.HttpRequest,java.net.URI,java.net.http.HttpClient,java.net.http.HttpResponse"%>
<%!
    public String callPost(String name, String email) {
        String res = "";
        try {
            String s = "http://localhost:8080/lab3/page?name=" + name + "&email=" + email;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(s)).POST(HttpRequest.BodyPublishers.ofString("Test")).build();
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
    String email = request.getParameter("email");
    if(name != null && email != null) {
        resp = callPost(name, email);
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
<%= resp%>
</body>
</html>