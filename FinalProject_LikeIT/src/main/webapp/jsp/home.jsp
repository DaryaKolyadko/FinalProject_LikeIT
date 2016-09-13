<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="home.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/font-awesome.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/whhg.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/home.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/home.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_home')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="panel-group sections top-padding">
                    <c:if test="${not empty actionInfo}">
                        <div class="alert alert-success">
                            <a class="close" data-dismiss="alert" href="#">×</a>
                            <fmt:message key="${actionInfo.text}"/>
                        </div>
                    </c:if>
                    <c:if test="${not empty actionError}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="#">×</a>
                            <fmt:message key="${actionError.text}"/>
                        </div>
                    </c:if>
                    <ctm:authenticatedOnly>
                        <div class="top-padding bottom-padding right-button-parent">
                            <ctm:adminOnly>
                                <a href="<c:url value="/CreateSection"/>" type="button"
                                   class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> <fmt:message
                                        key="button.addSection"/></a>
                            </ctm:adminOnly>
                            <form action="<c:url value="/CreateQuestion"/>" id="create-question-form" hidden></form>
                            <button id="createQuestionButton" onclick="$('#create-question-form').submit()"
                                    class="btn btn-primary ask-button right" data-tool="tooltip"><i
                                    class="fa fa-question"></i>
                                <fmt:message key="button.ask"/></button>
                            <c:if test="${not userContainer.object.isActive()}">
                                <script type="text/javascript">
                                    $('#createQuestionButton').attr("disabled", 'true');
                                    $('#createQuestionButton').attr('title', '<fmt:message key="tooltip.banned"/>');</script>
                            </c:if>
                        </div>
                    </ctm:authenticatedOnly>
                    <c:forEach items="${sectionsCatalogue}" var="majorSection">
                        <div class="panel panel-default">
                            <div class="panel-heading clearfix" data-target="#${majorSection.key.id}">
                                <div class="panel-title">
                                        ${majorSection.key.name}
                                    <c:if test="${majorSection.key.archive eq true}">
                                        <span class="archive-label">(<fmt:message key="label.archive"/>)</span>
                                    </c:if>
                                    <ctm:adminOnly>
                                        <c:choose>
                                            <c:when test="${majorSection.key.archive eq true}">
                                                <form id="form-restore-${majorSection.key.id}" method="post"
                                                      action="${url_home}" hidden>
                                                    <input type="text" name="command"
                                                           value="RESTORE_SECTION">
                                                    <input type="text" name="sectionId"
                                                           value="${majorSection.key.id}">
                                                </form>
                                                <a href="#" data-toggle="modal" data-target="#restore"
                                                   data-section="${majorSection.key.id}" class="restore-color">
                                                    <span class="glyphicon icon-unpackarchive small-left-margin"></span>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <form id="form-delete-${majorSection.key.id}" method="post"
                                                      action="${url_home}" hidden>
                                                    <input type="text" name="command"
                                                           value="DELETE_SECTION">
                                                    <input type="text" name="sectionId"
                                                           value="${majorSection.key.id}">
                                                </form>
                                                <a href="#" data-toggle="modal" data-target="#delete"
                                                   data-section="${majorSection.key.id}" class="delete-color">
                                                    <span class="glyphicon glyphicon-trash small-left-margin"></span>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                        <form id="form-edit-section-${majorSection.key.id}"
                                              action="<c:url value="/EditSection"/>" hidden>
                                            <input type="text" name="section" value="${majorSection.key.id}">
                                        </form>
                                        <a href="#"
                                           onclick="$('#form-edit-section-${majorSection.key.id}').submit()"
                                           class="edit-color">
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </a>
                                    </ctm:adminOnly>
                                </div>
                            </div>
                            <div id="${majorSection.key.id}" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <table class="table borderless">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="fix-column-width"><fmt:message key="table.header.section"/></th>
                                            <th><fmt:message key="table.header.questions"/></th>
                                            <th><fmt:message key="table.header.answered"/></th>
                                            <ctm:adminOnly>
                                                <th><fmt:message key="table.header.actions"/></th>
                                            </ctm:adminOnly>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${majorSection.value}" var="section"
                                                   varStatus="sectionIter">
                                            <tr>
                                                <th scope="row">${sectionIter.count}</th>
                                                <td>
                                                    <form id="section-${section.id}"
                                                          action="<c:url value="/Questions"/>" hidden>
                                                        <input value="${section.id}" name="section">
                                                    </form>
                                                    <a href="#"
                                                       onclick="document.getElementById('section-${section.id}').submit()">
                                                            ${section.name}
                                                        <c:if test="${section.archive eq true}">
                                                            <span class="archive-label">(<fmt:message
                                                                    key="label.archive"/>)</span>
                                                        </c:if>
                                                    </a>
                                                </td>
                                                <td>${section.questionNum}</td>
                                                <td>
                                                    <c:set var="answerPercent"
                                                           value="${100*section.answerNum/section.questionNum}"/>
                                                    <div class="statusBlock"><span
                                                            style="height: ${answerPercent}%"></span>
                                                        <div>${section.answerNum}</div>
                                                    </div>
                                                </td>
                                                <ctm:adminOnly>
                                                    <td>
                                                        <div class="btn-group">
                                                            <form id="form-edit-section-${section.id}" method="get"
                                                                  action="<c:url value="/EditSection"/>" hidden>
                                                                <input type="text" name="section" value="${section.id}">
                                                            </form>
                                                            <button class="btn btn-default"
                                                                    onclick="document.getElementById('form-edit-section-${section.id}').submit()">
                                                                <span class="icon-edit"></span>
                                                            </button>
                                                            <c:choose>
                                                                <c:when test="${section.archive eq true}">
                                                                    <form id="form-restore-${section.id}" method="post"
                                                                          action="${url_home}" hidden>
                                                                        <input type="text" name="command"
                                                                               value="RESTORE_SECTION">
                                                                        <input type="text" name="sectionId"
                                                                               value="${section.id}">
                                                                    </form>
                                                                    <button type="button" class="btn btn-warning"
                                                                            data-toggle="modal"
                                                                            data-target="#restore"
                                                                            data-section="${section.id}">
                                                                        <span class="icon-uploadalt"></span>
                                                                    </button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <form id="form-delete-${section.id}" method="post"
                                                                          action="${url_home}" hidden>
                                                                        <input type="text" name="command"
                                                                               value="DELETE_SECTION">
                                                                        <input type="text" name="sectionId"
                                                                               value="${section.id}">
                                                                    </form>
                                                                    <button type="button" class="btn btn-danger"
                                                                            data-toggle="modal" data-target="#delete"
                                                                            data-section="${section.id}">
                                                                        <span class="icon-remove"></span>
                                                                    </button>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td>
                                                </ctm:adminOnly>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
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
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="delete-section" class="btn btn-success"><span
                            class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
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
                    <button id="restore-section" class="btn btn-success"><span
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