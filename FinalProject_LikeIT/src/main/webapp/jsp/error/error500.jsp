<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="error.500.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/error500.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <div class="container-fluid text-center">
        <div class="row">
            <div class="col-md-12 col-lg-11 col-lg-offset-1 text-left">
                <img src="${pageContext.servletContext.contextPath}/resources/img/500.png" alt="500"
                     class="img-center"/>
                <h2><fmt:message key="exceptionMessage"/>
                    <c:choose>
                        <c:when test="${not empty pageContext.exception.stackTrace}">
                            ${pageContext.exception.message}
                        </c:when>
                        <c:otherwise>
                            ${exceptionContainer.object.message}
                        </c:otherwise>
                    </c:choose>
                </h2>
                <h2><fmt:message key="stackTrace"/></h2>
                <p class="stack-trace">
                    <c:choose>
                        <c:when test="${not empty pageContext.exception.stackTrace}">
                            <c:forEach items="${pageContext.exception.stackTrace}" var="element">
                                <c:out value="${element}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${exceptionContainer.object.stackTrace}" var="element">
                                <c:out value="${element}"/>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>