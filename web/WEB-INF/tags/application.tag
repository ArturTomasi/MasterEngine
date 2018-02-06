<%@attribute name="title" required="true"  type="String"%>
<%@attribute name="ui"    required="true"   type="String"%>
<%@attribute name="page"  required="false"  type="String"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="../img/me_logo.png">
        
        <title>Master Engine - ${title}</title>

        <script type="text/javascript" src="inc/js/application.js"></script>
        
         <style>
            body, html
            {
                margin: 0 !important;
                padding: 0 !important;
            }
        </style>
    </head>
    
    <jsp:include page="${page != null ? page : '/WEB-INF/zk/DefaultApplication.zul'}">
        <jsp:param name="ui" value="${ui}"></jsp:param>
    </jsp:include>
    
    <jsp:doBody/>
     
</html>