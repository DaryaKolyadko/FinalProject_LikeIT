<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/error.css"/>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
    <title>Something is wrong =(</title></head>
<body>
<div id="mother">
    <div id="errorBox" style="background: url(${pageContext.servletContext.contextPath}/resources/img/error.png) no-repeat top left;">
        <div id="errorText">
            <h1>What a twist!</h1>

            <p>
                It seams like something has gone definitely wrong. But don't worry - it can happen to the best of us.
                And that has just happened to you.
            </p>

            <p>
                Try something from that:
            </p>

            <a class="btn btn-default" href="<c:url value="/Home"/>"> Home </a>
            <a class="btn btn-default" href="<c:url value="/About"/>"> About </a>
            <a class="btn btn-default" href="<c:url value="/Users"/>"> Users </a>

            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <a class="btn btn-default" href="<c:url value="/Login"/>"> Login </a>
                    <a class="btn btn-default" href="<c:url value="/SignUp"/>"> Sign up </a>
                </c:when>
                <c:otherwise>
                    <%--<a class="btn btn-default" href="<c:url value="/Home"/>"> Home </a>--%>
                    <%--TODO--%>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>