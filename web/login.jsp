<%-- 
    Document   : login
    Created on : 22/11/2015, 13:23:05
    Author     : Matheus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if ( request.getUserPrincipal() != null )
    {
        response.sendRedirect( request.getContextPath() + "/admin" );
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Master Engine - Login</title>
        <link rel="shortcut icon" href="/img/me_logo.png">
        <style>
            body, html
            {
                margin: 0 !important;
                padding: 0 !important;
            }
        </style>
    </head>
    <body>
        <jsp:include  page="WEB-INF/zk/LoginApplication.zul"/>
    </body>
</html>