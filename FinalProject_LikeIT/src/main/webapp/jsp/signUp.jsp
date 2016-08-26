<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="signUp.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
        <link rel="stylesheet"
              href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/signUp.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/signUp.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_sign_up')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="form-wrap" id="signUp">
                    <div class="form-main-title "><fmt:message key="formHeader"/></div>
                    <c:if test="${not empty signUpError}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="#">×</a>
                            <fmt:message key="${signUpError.text}"/>
                        </div>
                    </c:if>
                    <form method="post" id="sign-up-form" data-toggle="validator" action="<c:url value="/Home"/>">
                        <input type="hidden" name="command" value="SIGN_UP"/>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label for="first-name" class="sr-only"><fmt:message key="label.firstName"/></label>
                                    <input type="text" name="firstName" id="first-name" class="form-control"
                                           placeholder="<fmt:message key="label.firstName"/>"
                                           value="${uncompleted.firstName}" required/>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label for="first-name" class="sr-only"><fmt:message key="label.lastName"/></label>
                                    <input type="text" name="lastName" id="last-name" class="form-control"
                                           placeholder="<fmt:message key="label.lastName"/>"
                                           value="${uncompleted.lastName}" required/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="user-login" class="sr-only"><fmt:message key="label.login"/></label>
                            <input type="text" name="userLogin" id="user-login" class="form-control"
                                   placeholder="<fmt:message key="label.login"/>" value="${uncompleted.login}" required>
                        </div>
                        <div class="form-group">
                            <label for="user-email" class="sr-only"><fmt:message key="label.email"/></label>
                            <input type="email" name="userEmail" id="user-email" class="form-control"
                                   placeholder="<fmt:message key="label.email"/>" value="${uncompleted.email}"
                                   required/>
                        </div>
                        <div class="form-group">
                            <label for="user-password" class="sr-only"><fmt:message key="label.password"/></label>
                            <input type="password" name="userPassword" id="user-password" class="form-control"
                                   placeholder="<fmt:message key="label.password"/>" value="${uncompleted.password}"
                                   required/>
                        </div>
                        <div class="form-group">
                            <label for="user-confirm-password" class="sr-only"><fmt:message
                                    key="label.confirmPassword"/></label>
                            <input type="password" name="userConfirmPassword" id="user-confirm-password"
                                   class="form-control" placeholder="<fmt:message key="label.confirmPassword"/>"
                                   value="${uncompleted.passwordConfirmation}" required/>
                        </div>
                        <div class="form-group">
                            <div class="input-group date" id="birthday-picker" data-provide="datepicker">
                                <label for="picker" class="sr-only"><fmt:message key="label.birthDate"/></label>
                                <input type="text" id="picker" class="form-control"
                                       placeholder="<fmt:message key="label.birthDate"/>"
                                       name="birthDate" value="${uncompleted.birthDate}"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                            </div>
                        </div>
                        <div class="form-horizontal">
                            <div id="gender-button-group" class="form-group">
                                <label class="sr-only" for="gender-choice"><fmt:message key="label.gender"/></label>
                                <div class="col-xs-12 text-center">
                                    <div id="gender-choice" class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-default">
                                            <input type="radio" name="gender" value="male"/> <fmt:message
                                                key="gender.male"/>
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="gender" value="female"/> <fmt:message
                                                key="gender.female"/>
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="gender" value="other"/> <fmt:message
                                                key="gender.other"/>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="submit" id="btn-sign-up" class="btn btn-custom btn-lg btn-block"
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