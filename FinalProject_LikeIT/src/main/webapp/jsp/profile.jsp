<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="profile.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-confirmation/1.0.5/bootstrap-confirmation.js"></script>
        <link rel="stylesheet"
              href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/profile.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/profile.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/> ${profile.firstName} ${profile.lastName}</title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <c:choose>
                    <c:when test="${not empty profileError}">
                        <div><h2><fmt:message key="${profileError}"/></h2></div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-sm-12 col-md-8 col-lg-8 col-sm-offset-0 col-md-offset-2 col-lg-offset-2">
                            <div class="panel panel-warning user-panel">
                                <div class="panel-heading clearfix">
                                    <span class="panel-title">${profile.firstName} ${profile.lastName}</span>
                            <span class="pull-right">
                               <c:if test="${profile.id eq sessionScope.user.id}">
                                   <a href="#" data-original-title="Edit profile" data-toggle="tooltip" type="button"
                                      class="btn btn-md btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                               </c:if>
                                <c:if test="${profile.id eq userContainer.object.id or userContainer.object.isAdmin()}">
                                    <a data-toggle="modal" title="Delete profile" data-target="#delete" type="button"
                                       class="btn btn-md btn-danger"><i class="glyphicon glyphicon-trash"></i></a>
                                </c:if>
                        </span>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-3 col-lg-3 " align="center">
                                            <img alt="avatar"
                                                 src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                                 class="img-circle img-responsive">
                                        </div>
                                        <div class="col-md-9 col-lg-9 ">
                                            <table class="table table-user-information">
                                                <tbody>
                                                <tr>
                                                    <td><fmt:message key="label.login"/></td>
                                                    <td>${profile.id}</td>
                                                </tr>
                                                <ctm:adminOnly>
                                                    <tr>
                                                        <td><fmt:message key="label.role"/></td>
                                                        <td>${profile.role}</td>
                                                    </tr>
                                                    <tr>
                                                        <td><fmt:message key="label.profileState"/></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${profile.archive}">
                                                                    <fmt:message key="label.inArchive"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <fmt:message key="label.inUse"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </ctm:adminOnly>
                                                <tr>
                                                    <td><fmt:message key="label.since"/></td>
                                                    <td><fmt:formatDate value="${profile.signUpDate}"/></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.dateOfBirth"/></td>
                                                    <td><fmt:formatDate value="${profile.birthDate}"/></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.gender"/></td>
                                                    <td>${profile.gender}</td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.state"/></td>
                                                    <td class="state-${fn:toLowerCase(profile.state)}">${profile.state}</td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.rating"/></td>
                                                    <td><fmt:formatNumber value="${profile.rating}"/></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.email"/></td>
                                                    <td><a id="emailText"
                                                           href="mailto:${profile.email}">${profile.email}</a>
                                                        <c:if test="${not profile.emailConfirmed}">
                                                            <script>setEmailUnconfirmed()</script>
                                                <span class="glyphicon glyphicon-envelope" data-toggle="confirmation"
                                                      data-popout="true" data-placement="top"
                                                      title="Send confirmation message?"></span>
                                                        </c:if>
                                                    </td>
                                                </tbody>
                                            </table>
                                            <div class="text-center">
                                                <c:if test="${profile.id eq userContainer.object.id}">
                                                    <fmt:message key="label.buttonPrefix" var="buttonPrefix"/>
                                                </c:if>
                                                <button class="btn btn-warning profile">${buttonPrefix} <fmt:message
                                                        key="label.questions"/>
                                                    (${profile.questionNum})
                                                </button>
                                                <button class="btn btn-success profile">${buttonPrefix} <fmt:message
                                                        key="label.comments"/>
                                                    (${profile.commentNum})
                                                </button>
                                                <button class="btn btn-primary profile">${buttonPrefix} <fmt:message
                                                        key="label.answers"/>
                                                    (${profile.answerNum})
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose></div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>