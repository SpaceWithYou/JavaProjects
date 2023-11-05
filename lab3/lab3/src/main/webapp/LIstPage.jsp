<%@page contentType = "text/html;charset=UTF-8" language="java" %>
<%@page import="clients.Client" import="services.ClientService" %>
<html>
<head></head>
<body>
<h1>List page</h1>
<%
    ClientService service = new ClientService();
    for(Client client: service.getCache().getClientCache().asMap().values()) {
        out.println("<p>" +  client.toString() + "</p><br>");
    }
%>
</body>
</html>