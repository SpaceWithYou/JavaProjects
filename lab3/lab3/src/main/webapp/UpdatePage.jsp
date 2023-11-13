<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.http.HttpRequest,java.net.URI,java.net.http.HttpClient,java.net.http.HttpResponse"%>
<%!
    public String callPut(String id, String name, String email) {
        String res = "";
        try {
            String s = "http://localhost:8080/lab3/page?id=" + id + "&params=" + name + "&params=" + email;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(s)).PUT(HttpRequest.BodyPublishers.noBody()).build();
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
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    if(id != null && name != null && email != null) {
        resp = callPut(id, name, email);
    }
%>
<html>
<head></head>
<body>
<h1>Update page</h1>
<form action="#">
    ID:<input type="text" name="id">
    <br>
    New name:<input type="text" name="name">
    <br>
    New email:<input type="text" name="email">
    <br>
    <button type="submit" name="button" value="button1">Update</button>
</form>
<%= resp%>
</body>
</html>