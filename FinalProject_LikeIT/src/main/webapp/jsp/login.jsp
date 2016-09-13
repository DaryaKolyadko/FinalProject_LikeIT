<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="login.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrapValidator.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrapValidator.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/login.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/login.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_login')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="form-wrap medium-top-padding">
                    <div class="form-main-title"><fmt:message key="formHeader"/></div>
                    <c:if test="${not empty loginError}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="#">×</a>
                            <fmt:message key="${loginError.text}"/>
                        </div>
                    </c:if>
                    <form role="form" method="post" id="login-form" data-toggle="validator"
                          action="<c:url value="/Home"/>">
                        <input type="hidden" name="command" value="LOGIN"/>
                        <div class="form-group">
                            <label for="user-login" class="sr-only"><fmt:message key="label.login"/></label>
                            <input type="text" name="userLogin" id="user-login" class="form-control"
                                   placeholder="<fmt:message key="label.login"/>" required>
                        </div>
                        <div class="form-group">
                            <label for="user-password" class="sr-only"><fmt:message key="label.password"/></label>
                            <input type="password" name="userPassword" id="user-password"
                                   class="form-control" placeholder="<fmt:message key="label.password"/>" required>
                        </div>
                        <input type="submit" id="btn-login" class="btn btn-custom btn-lg btn-block"
                               value="<fmt:message key="button"/>">
                    </form>
                </div>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>