<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="about.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/about.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_about')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="widget-inside">
                    <div class="">
                        <div class="row">
                            <div class="text-center">
                                <h2 class="shadow"><fmt:message key="logo.headerText"/></h2>
                                <div class="shadow">
                                    <p><fmt:message key="logo.subText"/></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <p><fmt:message key="text"/></p>
                <form class="form-horizontal">
                    <h2><fmt:message key="contacts"/></h2>
                    <p><fmt:message key="contacts.text"/></p>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><fmt:message key="label.email"/></label>
                        <span id="email" class="col-sm-4 form-control-static">darya.kolyadko@gmail.com</span>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-2 control-label">
                            <fmt:message key="label.phone"/>
                        </label>
                        <span id="phone" class="col-sm-4 form-control-static">BY: +375(29)678-90-12</span>
                    </div>
                </form>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>