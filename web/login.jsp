<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if ( request.getUserPrincipal() != null )
    {
        response.sendRedirect( request.getContextPath() + "/admin" );
        return;
    }
%>

<%@taglib prefix="me" tagdir="/WEB-INF/tags/"%>

<me:application title="Login" page="WEB-INF/zk/LoginApplication.zul" ui="com.me.eng.core.ui.apps.AdministratorApplicationUI"></me:application>
