<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.http.HttpRequest,java.net.URI,java.net.http.HttpClient,java.net.http.HttpResponse"%>
<%!
    public String callGet(String id) {
        String res = "";
        try {
            String s = "http://localhost:8080/lab3/page?id=" + id;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(s)).GET().build();
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
    if(id != null) {
        resp = callGet(id);
    }
%>
<html>
<head></head>
<body>
<h1>Client page</h1>
<form action="#">
    ID:<input type="text" name="id">
    <br>
    <button type="submit" name="button" value="button1">Search</button>
</form>
<%= resp%>
</body>
</html>