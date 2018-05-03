<%@attribute name="title"   required="true"    type="String"%>
<%@attribute name="message" required="true"    type="String"%>
<%@attribute name="home"    required="false"   type="Boolean"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/core/me_logo.png">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/inc/css/error.css">
        <title>Master Engine</title>
       </style>
    </head>
    <body class="body-error">
        <div class="box-error">
            <div class="text-error title-error">
                ${title}
            </div>
            <div class="text-error subtitle-error">
                ${message}
            </div>
            <%if ( home ) {%><a class="home-error" href="<%=request.getContextPath()%>/admin/index.jsp">Voltar para a página inical</a><%}%>
        </div>
    </body>
</html>