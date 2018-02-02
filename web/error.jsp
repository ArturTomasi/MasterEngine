<%@page import="org.apache.catalina.util.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.codec.binary.StringUtils"%>
<%
    session.setAttribute( "auth-status", true );
    
    response.sendRedirect( request.getHeader( "referer" ) );
%>