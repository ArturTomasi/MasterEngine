<%
    session.setAttribute( "auth-status", true );
    
    response.sendRedirect( request.getHeader( "referer" ) );
%>