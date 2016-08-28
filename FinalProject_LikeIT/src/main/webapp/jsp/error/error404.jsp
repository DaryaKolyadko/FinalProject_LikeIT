<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="error.404.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/error404.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-5 col-lg-offset-1 text-left">
                <div class="errorText">
                    <h1><fmt:message key="header"/></h1>
                    <p><fmt:message key="parag1"/></p>
                    <p><fmt:message key="parag2"/> ${originUrl}</p>
                    <p><fmt:message key="parag3"/></p>
                    <a class="btn btn-default" href="<c:url value="/Home"/>"> <fmt:message key="button.home"/> </a>
                    <a class="btn btn-default" href="<c:url value="/About"/>"> <fmt:message key="button.about"/> </a>
                    <a class="btn btn-default" href="<c:url value="/Users"/>"> <fmt:message key="button.users"/> </a>
                </div>
            </div>
            <div class="col-sm-12 col-md-12 col-lg-6">
                <img src="${pageContext.servletContext.contextPath}/resources/img/404.jpg" alt="404"
                     style="width:100%;"/>
            </div>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>