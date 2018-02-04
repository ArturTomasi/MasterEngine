<%-- 
    Document   : setup
    Created on : 11/01/2016, 23:35:56
    Author     : Matheus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="../img/me_logo.png">
        <title>MasterEngine - Pendências</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/zk/DefaultApplication.zul">
            <jsp:param name="ui" value="com.me.eng.ui.apps.PendenciesApplicationUI"></jsp:param>
        </jsp:include>
    </body>
</html>
