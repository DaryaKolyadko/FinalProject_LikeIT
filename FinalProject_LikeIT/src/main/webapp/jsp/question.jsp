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
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/question.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jRate.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/question.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="question-post">
                    <h2>${questionData.question.title}</h2>
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
                    </h5>
                    <h5 class="section-label"><span class="label"
                                                    style="background-color: #${questionData.section.labelColor}">
                            ${questionData.section.name}
                    </span>
                    </h5>
                    <br>
                    <p>${questionData.question.text}</p>
                    <ul class="list-inline list-unstyled clearfix">
                        <ctm:authenticatedOnly>
                            <li>
                                <span id="rating-q-${questionData.question.id}" class="jRate"></span>
                                <small id="rating-q-${questionData.question.id}-value" class="rating-value"></small>
                                <script>generateQuestionJRate(${questionData.question.id}, ${questionData.mark})</script>
                            </li>
                            <li class="divider">|</li>
                        </ctm:authenticatedOnly>
                        <li>
                            <small class="rating-div-custom-float"><fmt:formatNumber
                                    maxFractionDigits="1">${questionData.question.rating}</fmt:formatNumber></small>
                        </li>
                    </ul>
                    <hr>
                </div>
                <p><span class="badge">${questionData.question.commentNum}</span> <fmt:message key="label.comments"/>
                </p><br>
                <div class="row">
                    <c:forEach items="${commentsData}" var="commentData">
                        <div class="col-lg-12">
                            <div class="col-xs-2 col-sm-1 text-center answer-block">
                                <c:choose>
                                    <c:when test="${questionData.user.id eq userContainer.object.id}">
                                        <c:if test="${commentData.comment.answer eq true}">
                                            <a href="#" class="glyphicon glyphicon-ok answer-mark-on center"></a>
                                        </c:if>
                                        <c:if test="${commentData.comment.answer eq false}">
                                            <a href="#" class="glyphicon glyphicon-ok answer-mark-off center"></a>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${commentData.comment.answer eq true}">
                                            <span class="glyphicon glyphicon-ok answer-mark-on center"></span>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-xs-3 col-sm-1 text-center">
                                <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                     class="img-circle" height="65" width="65" alt="Avatar">
                            </div>
                            <div class="col-sm-10">
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
                                <span class="right-panel">
                                    <c:if test="${commentData.user.id eq userContainer.object.id}">
                                        <a href="#"><i class="glyphicon glyphicon-edit"></i></a>
                                    </c:if>
                                    <c:if test="${commentData.user.id eq userContainer.object.id or userContainer.object.isAdmin()}">
                                        <a href="#"><i class="glyphicon glyphicon-trash"></i></a>
                                    </c:if>
                                </span>
                                </h4>
                                <div class="clearfix visible-xs-block"></div>
                                <p> ${commentData.comment.text}</p>
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
                <div class="row">
                    <div class="col-sm-10">
                        <h4><fmt:message key="label.leaveComment"/></h4>
                        <form role="form">
                            <div class="form-group">
                                <label for="comment" hidden></label>
                                <textarea id="comment" class="form-control" rows="3" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-success">Send</button>
                        </form>
                    </div>
                </div>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>