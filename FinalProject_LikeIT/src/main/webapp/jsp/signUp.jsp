<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/signUp.css"/>
    <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
    <%--<script src="${pageContext.servletContext.contextPath}/resources/js/signUp.js"></script>--%>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
    <title>Sign up</title>
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
                <div class="form-main-title ">Sign up for LikeIT</div>
                <c:if test="${not empty signUpError}">
                    <div class="alert alert-danger">
                        <a class="close" data-dismiss="alert" href="#">×</a>
                            ${signUpError}
                    </div>
                </c:if>
                <form method="post" id="sign-up-form" data-toggle="validator" action="<c:url value="/Home"/>">
                    <input type="hidden" name="command" value="SIGN_UP"/>
                    <div class="row">
                        <div class="col-xs-12 col-sm-6 col-md-6">
                            <div class="form-group">
                                <label for="first-name" class="sr-only">First Name</label>
                                <input type="text" name="firstName" id="first-name" class="form-control"
                                       placeholder="First Name" value="${firstName}" required/>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-6 col-md-6">
                            <div class="form-group">
                                <label for="first-name" class="sr-only">Last Name</label>
                                <input type="text" name="lastName" id="last-name" class="form-control"
                                       placeholder="Last Name" value="${lastName}" required/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-login" class="sr-only">Login</label>
                        <input type="text" name="userLogin" id="user-login" class="form-control"
                               placeholder="Login" value="${userLogin}" required>
                    </div>
                    <div class="form-group">
                        <label for="user-email" class="sr-only">Email</label>
                        <input type="email" name="userEmail" id="user-email" class="form-control"
                               placeholder="Email" value="${userEmail}" required/>
                    </div>
                    <div class="form-group">
                        <label for="user-password" class="sr-only">Password</label>
                        <input type="password" name="userPassword" id="user-password" class="form-control"
                               placeholder="Password" value="${userPassword}" required/>
                    </div>
                    <div class="form-group">
                        <label for="user-confirm-password" class="sr-only">Confirm password</label>
                        <input type="password" name="userConfirmPassword" id="user-confirm-password"
                               class="form-control" placeholder="Confirm Password"
                               value="${userConfirmPassword}" required/>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="birthday-picker" data-provide="datepicker">
                            <label for="picker" class="sr-only">Birthday</label>
                            <input type="text" id="picker" class="form-control" placeholder="Birthday"
                                   name="birthday" value="${birthday}"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-horizontal">
                        <div id="gender-button-group" class="form-group">
                            <label class="sr-only" for="gender-choice">Gender</label>
                            <div class="col-xs-12 text-center">
                                <div id="gender-choice" class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-default">
                                        <input type="radio" name="gender" value="male"/> Male
                                    </label>
                                    <label class="btn btn-default">
                                        <input type="radio" name="gender" value="female"/> Female
                                    </label>
                                    <label class="btn btn-default">
                                        <input type="radio" name="gender" value="other"/> Other
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<jsp:useBean id="calendar" scope="page" class="java.util.GregorianCalendar"/>--%>
                    <%--<label hidden>--%>
                    <%--<input name="signUpDate" value="${calendar.timeInMillis}" hidden/>--%>
                    <%--</label>--%>
                    <%--Other--%>
                    <%--<div class="checkbox">--%>
                    <%--<span class="character-checkbox" onclick="showPassword()"></span>--%>
                    <%--<span class="label">Show password</span>--%>
                    <%--</div>--%>
                    <input type="submit" id="btn-sign-up" class="btn btn-custom btn-lg btn-block"
                           value="Sign up">
                </form>
            </div>
        </div>
        <%@include file="/jsp/include/rightSidePanel.jsp" %>
    </div>
    <%@include file="/jsp/include/footer.jsp" %>
</div>
</body>
</html>