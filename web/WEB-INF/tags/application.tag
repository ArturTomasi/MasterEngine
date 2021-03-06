<%@tag import="com.me.eng.core.application.ConfigurationManager"%>

<%@attribute name="title"   required="true"   type="String"%>
<%@attribute name="ui"      required="true"   type="String"%>
<%@attribute name="page"    required="false"  type="String"%>
<%@attribute name="module"  required="false"  type="String"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Master Engine - ${title}</title>
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/core/me_logo.png">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/inc/css/default.css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/inc/js/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/inc/js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/inc/js/application.js"></script>
        <script type="text/javascript">
            var contextPath = '<%=request.getContextPath()%>';
        </script>
    </head>
    
    <%if ( ConfigurationManager.getSystemProperty( "license_exception" ) != null ){%><jsp:forward page="/error/license.jsp"/><%}%>
    
    <jsp:include page="${page != null ? page : '/WEB-INF/zk/DefaultApplication.zul'}">
        <jsp:param name="ui" value="${ui}"></jsp:param>
        <jsp:param name="module" value="${module}"></jsp:param>
    </jsp:include>

</html>