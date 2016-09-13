<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="editProfile.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap-select.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrapValidator.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrapValidator.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-datepicker.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-datepicker.ru.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-select.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/datepicker3.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/editProfile.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/editProfile.js"></script>
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
                <c:choose>
                    <c:when test="${not empty serverError}">
                        <div class="alert alert-danger top-margin">
                            <fmt:message key="${serverError}"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="container medium-top-padding">
                            <div class="form-main-title "><fmt:message key="formHeader"/></div>
                            <form method="post" id="edit-profile-form" data-toggle="validator"
                                  action="<c:url value="/Home"/>" class="form-horizontal form-wrap">
                                <c:if test="${not empty actionError}">
                                    <div class="alert alert-danger">
                                        <a class="close" data-dismiss="alert" href="#">×</a>
                                        <fmt:message key="${actionError.text}"/>
                                    </div>
                                </c:if>
                                <input type="hidden" name="command" value="EDIT_PROFILE"/>
                                <input type="hidden" name="login" value="${param.login}"/>
                                <div class="form-group">
                                    <label for="first-name" class="col-sm-4 control-label"><fmt:message
                                            key="label.firstName"/></label>
                                    <div class="col-sm-7">
                                        <input type="text" name="firstName" id="first-name" class="form-control"
                                               placeholder="<fmt:message key="label.firstName"/>"
                                               value="${uncompleted.firstName}${profileToEdit.firstName}" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="last-name" class="col-sm-4 control-label"><fmt:message
                                            key="label.lastName"/></label>
                                    <div class="col-sm-7">
                                        <input type="text" name="lastName" id="last-name" class="form-control"
                                               placeholder="<fmt:message key="label.lastName"/>"
                                               value="${uncompleted.lastName}${profileToEdit.lastName}" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="user-email" class="col-sm-4 control-label"><fmt:message
                                            key="label.email"/></label>
                                    <div class="col-sm-7">
                                        <input type="email" name="userEmail" id="user-email" class="form-control"
                                               placeholder="<fmt:message key="label.email"/>"
                                               value="${uncompleted.email}${profileToEdit.email}"
                                               required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="date" id="birthday-picker" data-provide="datepicker"
                                         data-locale="${fn:substring(locale.text, 0, 2)}">
                                        <label for="picker" class="col-sm-4 control-label"><fmt:message
                                                key="label.birthDate"/></label>
                                        <div class="col-sm-7">
                                            <div class="input-group">
                                                <input type="text" id="picker" class="form-control"
                                                       placeholder="<fmt:message key="label.birthDate"/>"
                                                       name="birthDate"
                                                       value="${uncompleted.birthDate}<fmt:formatDate value="${profileToEdit.birthDate}" type="date" pattern="MM/dd/yyyy"/>"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="gender-choice" class="col-sm-4 control-label"><fmt:message
                                            key="label.gender"/></label>
                                    <div class="col-xs-12 col-sm-7">
                                        <select class="selectpicker form-control" id="gender-choice"
                                                name="gender"
                                                data-new-value="${uncompleted.gender}${profileToEdit.gender}">
                                            <option data-content="<fmt:message key="gender.male"/>"
                                                    value="MALE"></option>
                                            <option data-content="<fmt:message key="gender.female"/>"
                                                    value="FEMALE"></option>
                                            <option data-content="<fmt:message key="gender.other"/>"
                                                    value="OTHER"></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group button-bottom">
                                    <button id="edit-profile-submit" type="submit" class="btn btn-success"><fmt:message
                                            key="button.finish"/></button>
                                    <a href="${url_home}" class="btn btn-default"><fmt:message key="button.cancel"/></a>
                                </div>
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>