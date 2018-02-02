<%-- 
    Document   : administrator
    Created on : 10/01/2016, 12:26:15
    Author     : Matheus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="../img/me_logo.png">
        <title>MasterEngine - Administração de Amostras</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/zk/DefaultApplication.zul">
            <jsp:param name="ui" value="com.mn.engcivil.interfaces.apps.AdministratorApplicationUI"></jsp:param>
        </jsp:include>
    </body>
</html>
