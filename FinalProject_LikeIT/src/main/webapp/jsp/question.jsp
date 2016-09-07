<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="question.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/font-awesome.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/whhg.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/question.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jRate.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/question.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/> ${questionData.question.title}</title>
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
                        <div class="question-post">
                            <h2>${questionData.question.title}
                                <c:if test="${questionData.question.archive eq true}">
                                    <span class="archive-label">(<fmt:message key="label.archive"/>)</span>
                                </c:if></h2>
                            <form id="profile-${questionData.question.authorId}"
                                  action="<c:url value="/Profile"/>"
                                  hidden>
                                <input value="${questionData.question.authorId}" name="login">
                            </form>
                            <h5>
                                <span class="glyphicon glyphicon-time"></span> <fmt:message key="askedBy"/>
                                <a href="#"
                                   onclick="document.getElementById('profile-${questionData.question.authorId}').submit()">
                                        ${questionData.user.firstName}
                                        ${questionData.user.lastName}</a>
                                <fmt:formatDate type="both" timeStyle="short"
                                                value="${questionData.question.creationDate}"/>
                        <span class="right-panel font-icon">
                        <c:if test="${questionData.user.id eq userContainer.object.id}">
                            <form id="form-edit-question" action="<c:url value="/EditQuestion"/>" hidden>
                                <%--<input type="text" name="authorId" value="${questionData.user.id}">--%>
                                <input type="text" name="question" value="${questionData.question.id}">
                            </form>
                            <a href="#" onclick="$('#form-edit-question').submit()"><i
                                    class="glyphicon glyphicon-edit edit-color"></i></a>
                        </c:if>
                        <c:if test="${(questionData.user.id eq userContainer.object.id or userContainer.object.isAdmin())
                        and questionData.question.archive eq false}">
                            <form id="form-delete-question" action="<c:url value="/Home"/>" method="post" hidden>
                                <input type="text" name="command" value="DELETE_QUESTION">
                                <input type="text" name="authorId" value="${questionData.user.id}">
                                <input type="text" name="question" value="${questionData.question.id}">
                            </form>
                            <a href="#" data-toggle="modal" data-target="#delete-question"><i
                                    class="glyphicon glyphicon-trash delete-color small-left-margin"></i></a>
                        </c:if>
                            <ctm:adminOnly>
                                <c:if test="${questionData.question.archive eq true}">
                                    <form id="form-restore-question" method="post"
                                          action="${url_home}" hidden>
                                        <input type="text" name="command"
                                               value="RESTORE_QUESTION">
                                        <input type="text" name="question"
                                               value="${questionData.question.id}">
                                    </form>
                                    <a href="#" data-toggle="modal" data-target="#restore-question"
                                       class="restore-color">
                                        <span class="glyphicon icon-unpackarchive small-left-margin"></span>
                                    </a>
                                </c:if>
                            </ctm:adminOnly>
                    </span>
                            </h5>
                            <h5 class="section-label"><span class="label"
                                                            style="background-color: #${questionData.section.labelColor}">
                                    ${questionData.section.name}
                            </span>
                            </h5>
                            <br>
                            <p><ctm:newLineFormat text="${questionData.question.text}"/></p>
                            <ul class="list-inline list-unstyled clearfix">
                                <ctm:authenticatedOnly>
                                    <li>
                                        <span id="rating-q-${questionData.question.id}" class="jRate"></span>
                                        <small id="rating-q-${questionData.question.id}-value"
                                               class="rating-value"></small>
                                        <script>generateQuestionJRate(${questionData.question.id}, ${questionData.mark})</script>
                                    </li>
                                    <li class="divider">|</li>
                                </ctm:authenticatedOnly>
                                <li>
                                    <small class="rating-div-custom-float"><fmt:message key="label.rating"/>
                                        <fmt:formatNumber maxFractionDigits="1">${questionData.question.rating}
                                        </fmt:formatNumber></small>
                                </li>
                            </ul>
                            <hr>
                        </div>
                        <p><span class="badge">${questionData.question.commentNum}</span> <fmt:message
                                key="label.comments"/>
                        </p><br>
                        <div class="row">
                            <c:forEach items="${commentsData}" var="commentData">
                                <div class="col-lg-12">
                                    <div class="col-xs-2 col-sm-1 text-center answer-block">
                                        <c:choose>
                                            <c:when test="${questionData.user.id eq userContainer.object.id}">
                                                <c:if test="${commentData.comment.answer eq true}">
                                                    <a href="#"
                                                       class="glyphicon glyphicon-ok answer-mark-on center"></a>
                                                </c:if>
                                                <c:if test="${commentData.comment.answer eq false}">
                                                    <a href="#"
                                                       class="glyphicon glyphicon-ok answer-mark-off center"></a>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${commentData.comment.answer eq true}">
                                                    <span class="glyphicon glyphicon-ok answer-mark-on center"></span>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-sm-11">
                                        <form id="profile-${commentData.user.id}"
                                              action="<c:url value="/Profile"/>"
                                              hidden>
                                            <input value="${commentData.user.id}" name="login">
                                        </form>
                                        <h4 class="h4-custom-float">
                                            <a href="#" class="user-block"
                                               onclick="document.getElementById('profile-${commentData.user.id}').submit()">
                                                    ${commentData.user.firstName}
                                                    ${commentData.user.lastName}</a>
                                <span class="right-panel font-icon">
                                    <c:if test="${commentData.user.id eq userContainer.object.id}">
                                        <a href="#"><i class="glyphicon glyphicon-edit edit-color"></i></a>
                                    </c:if>
                                    <c:if test="${commentData.user.id eq userContainer.object.id or userContainer.object.isAdmin()}">
                                        <a href="#"><i
                                                class="glyphicon glyphicon-trash delete-color small-left-margin"></i></a>
                                    </c:if>
                                </span>
                                        </h4>
                                        <div class="clearfix visible-xs-block"></div>
                                        <p style="display: inline"><ctm:newLineFormat
                                                text="${commentData.comment.text}"/></p>
                                        <ul class="list-inline list-unstyled clearfix">
                                            <li>
                                                <small class="date-block">
                                                    <i class="glyphicon glyphicon-calendar"></i>
                                                    <fmt:formatDate type="both" timeStyle="short"
                                                                    value="${commentData.comment.creationDate}"/>
                                                </small>
                                            </li>
                                            <ctm:authenticatedOnly>
                                                <li class="divider">|</li>
                                                <li>
                                                    <span id="rating-${commentData.comment.id}" class="jRate"></span>
                                                    <small id="rating-${commentData.comment.id}-value"
                                                           class="rating-value"></small>
                                                    <script>generateJRate(${commentData.comment.id}, ${commentData.mark})</script>
                                                </li>
                                            </ctm:authenticatedOnly>
                                            <li class="divider">|</li>
                                            <li>
                                                <small class="rating-div-custom-float"><fmt:formatNumber
                                                        maxFractionDigits="1">${commentData.comment.rating}</fmt:formatNumber></small>
                                            </li>
                                        </ul>
                                    </div>
                                    <hr>
                                </div>
                            </c:forEach>
                        </div>
                        <br><br>
                        <ctm:authenticatedOnly>
                            <div class="row">
                                <div class="col-sm-10">
                                    <h4><fmt:message key="label.leaveComment"/></h4>
                                    <form method="post" action="${originUrl}?question=${questionData.question.id}">
                                        <div class="form-group">
                                            <label for="comment" hidden></label>
                                    <textarea id="comment" name="text" class="form-control no-resize" rows="6"
                                              required></textarea>
                                            <input name="command" value="CREATE_COMMENT" hidden>
                                            <input name="question" value="${questionData.question.id}" hidden>
                                        </div>
                                        <button id="addCommentButton" type="submit" data-tool="tooltip"
                                                class="btn btn-success">
                                            <fmt:message
                                                    key="button.addComment"/></button>
                                        <c:if test="${not userContainer.object.isActive()}">
                                            <script type="text/javascript">
                                                $('#addCommentButton').addClass('disabled');
                                                $('#addCommentButton').attr('onclick', '');
                                                $('#createQuestionButton').attr("type", 'button');
                                                $('#addCommentButton').attr('title', '<fmt:message key="tooltip.banned"/>');
                                            </script>
                                        </c:if>
                                    </form>
                                </div>
                            </div>
                        </ctm:authenticatedOnly>
                    </c:otherwise>
                </c:choose>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>

    <div class="modal fade" id="delete-question" tabindex="-1" role="dialog" aria-labelledby="delete-question"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.deleteQuestion.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.deleteQuestion.text"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="delete-question-button" class="btn btn-success"
                            onclick="$('#form-delete-question').submit()">
                        <span class="glyphicon glyphicon-ok-sign"></span><fmt:message key="modal.button.yes"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove"></span><fmt:message key="modal.button.no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="restore-question" tabindex="-1" role="dialog" aria-labelledby="restore-question"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align"><fmt:message key="modal.restoreQuestion.title"/></h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-warning"><span class="glyphicon glyphicon-warning-sign"></span>
                        <fmt:message key="modal.restoreQuestion.text"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="restore-section" class="btn btn-success" onclick="$('#form-restore-question').submit()"><span
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