<%-- 
    Document   : teste
    Created on : 16/12/2011, 14:45:10
    Author     : Administrador
--%>

<%@page import="Until.functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            java.io.File  arquivo = new java.io.File("/home/inoveticao/webapps/ROOT/upload/");
            String acc=(arquivo).getAbsolutePath();
            out.write(acc);
        %>
    </body>
</html>
