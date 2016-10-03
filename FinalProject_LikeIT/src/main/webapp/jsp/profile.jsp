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
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/whhg.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-confirmation.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
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
                    <c:when test="${not empty serverError}">
                        <div><h2><fmt:message key="${serverError}"/></h2></div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-sm-12 col-md-7 col-lg-7 col-sm-offset-0 col-md-offset-3 col-lg-offset-3">
                            <c:if test="${not empty actionInfo}">
                                <div class="alert alert-success top-margin">
                                    <a class="close" data-dismiss="alert" href="#">×</a>
                                    <fmt:message key="${actionInfo.text}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty actionError}">
                                <div class="alert alert-danger top-margin">
                                    <a class="close" data-dismiss="alert" href="#">×</a>
                                    <fmt:message key="${actionError.text}"/>
                                </div>
                            </c:if>
                            <div class="panel panel-warning user-panel">
                                <div class="panel-heading clearfix">
                                    <span class="panel-title">${profile.firstName} ${profile.lastName}</span>
                                    <c:if test="${profile.archive eq true}">
                                        <span class="archive-label">(<fmt:message key="label.archive"/>)</span>
                                    </c:if>
                                    <div class="pull-right">
                                        <c:if test="${profile.id eq userContainer.object.id}">
                                            <form id="form-edit-profile" method="get"
                                                  action="<c:url value="/EditProfile"/>" hidden>
                                                <input type="text" name="login" value="${profile.id}">
                                            </form>
                                            <button class="btn btn-default"
                                                    onclick="$('#form-edit-profile').submit()">
                                                <span class="glyphicon icon-edit"></span>
                                            </button>
                                        </c:if>
                                        <ctm:adminOnly>
                                            <c:if test="${not profile.isAdmin() and not profile.getArchive()}">
                                                <c:choose>
                                                    <c:when test="${profile.isActive() eq true}">
                                                        <form id="form-ban-profile" method="post"
                                                              action="${url_home}" hidden>
                                                            <input type="text" name="command" value="BAN_PROFILE">
                                                            <input type="text" name="login" value="${profile.id}">
                                                        </form>
                                                        <button class="btn btn-death"
                                                                data-toggle="modal"
                                                                data-target="#ban"
                                                                data-tool="tooltip"
                                                                title="<fmt:message key="modal.ban.title"/>">
                                                            <span class="glyphicon glyphicon-ban-circle"></span>
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form id="form-activate-profile" method="post"
                                                              action="${url_home}" hidden>
                                                            <input type="text" name="command" value="ACTIVATE_PROFILE">
                                                            <input type="text" name="login" value="${profile.id}">
                                                        </form>
                                                        <button class="btn btn-success"
                                                                data-toggle="modal"
                                                                data-target="#activate"
                                                                data-tool="tooltip"
                                                                title="<fmt:message key="modal.activate.title"/>">
                                                            <span class="glyphicon glyphicon-ok-circle"></span>
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </ctm:adminOnly>
                                        <c:choose>
                                            <c:when test="${profile.archive eq true}">
                                                <ctm:adminOnly>
                                                    <form id="form-restore-profile" method="post"
                                                          action="${url_home}" hidden>
                                                        <input type="text" name="command" value="RESTORE_PROFILE">
                                                        <input type="text" name="login" value="${profile.id}">
                                                    </form>
                                                    <button data-toggle="modal" data-target="#restore"
                                                            class="btn btn-md btn-warning"><span
                                                            class="glyphicon icon-uploadalt"></span>
                                                    </button>
                                                </ctm:adminOnly>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${profile.id eq userContainer.object.id and not userContainer.object.isAdmin() and not profile.isAdmin()}">
                                                    <form id="form-delete-profile" method="post" action="${url_home}"
                                                          hidden>
                                                        <input type="text" name="command" value="DELETE_PROFILE">
                                                        <input type="text" name="login" value="${profile.id}">
                                                    </form>
                                                    <button data-toggle="modal" data-target="#delete"
                                                            class="btn btn-md btn-danger">
                                                        <i class="glyphicon glyphicon-trash"></i></button>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">
                                            <table class="table table-user-information">
                                                <tbody>
                                                <tr>
                                                    <td><fmt:message key="label.login"/></td>
                                                    <td>${profile.id}</td>
                                                </tr>
                                                <ctm:adminOnly>
                                                    <tr>
                                                        <td><fmt:message key="label.role"/></td>
                                                        <td><span
                                                                class="${fn:toLowerCase(profile.role)}-label">${profile.role}</span>
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
                                                    <td><fmt:message
                                                            key="gender.${fn:toLowerCase(profile.gender)}"/></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.state"/></td>
                                                    <td class="state-${fn:toLowerCase(profile.state)}"><fmt:message
                                                            key="${fn:toLowerCase(profile.state)}"/></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.rating"/></td>
                                                    <td><fmt:formatNumber
                                                            maxFractionDigits="1">${profile.rating}</fmt:formatNumber></td>
                                                </tr>
                                                <tr>
                                                    <td><fmt:message key="label.email"/></td>
                                                    <td><a id="emailText"
                                                           href="mailto:${profile.email}">${profile.email}</a>
                                                    </td>
                                                </tbody>
                                            </table>
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

    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="delete"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.delete.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.delete.text"/>
                        <br><b><fmt:message key="modal.delete.text.irreversible"/></b>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="delete-profile" class="btn btn-success"
                            onclick="$('#form-delete-profile').submit()">
                        <span class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span><fmt:message key="modal.button.no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="restore" tabindex="-1" role="dialog" aria-labelledby="restore"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.restore.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.restore.text"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="restore-profile" class="btn btn-success"
                            onclick="$('#form-restore-profile').submit()"><span
                            class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span><fmt:message key="modal.button.no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="ban" tabindex="-1" role="dialog" aria-labelledby="ban"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.ban.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.ban.text"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="ban-profile" class="btn btn-success"
                            onclick="$('#form-ban-profile').submit()"><span
                            class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span><fmt:message key="modal.button.no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="activate" tabindex="-1" role="dialog" aria-labelledby="activate"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.activate.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.activate.text"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="activate-profile" class="btn btn-success"
                            onclick="$('#form-activate-profile').submit()"><span
                            class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span><fmt:message key="modal.button.no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>