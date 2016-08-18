<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="questionList.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/questionList.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="${listType.text}"/> <fmt:message key="questions"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_${listType.text}')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="list-header">
                    <div>
                        <small class="list-type"><fmt:message key="${listType.text}"/> <fmt:message
                                key="questions"/></small>
                        <a href="createQuestion.html" type="button" class="btn btn-primary ask-button"><fmt:message
                                key="button.ask"/></a>
                    </div>
                </div>
                <hr>
                    <%--<c:forEach items="${questionsList}" var="question">--%>
                    <%--<div class="question-post">--%>
                    <%--<h2><a class="question-title" href="question.html">${question.title}</a></h2>--%>
                    <%--<h5><span class="glyphicon glyphicon-time"></span> <fmt:message key="askedBy"/>--%>
                    <%--<form id="profile-${question.authorId}" action="<c:url value="/Profile"/>" hidden>--%>
                    <%--<input value="${question.authorId}" name="login">--%>
                    <%--</form>--%>
                    <%--<a href="#" onclick="document.getElementById('profile-${question.authorId}').submit()">--%>
                    <%--${authorsList.get(question.authorId).firstName}--%>
                    <%--${authorsList.get(question.authorId).lastName}</a>--%>
                    <%--<fmt:formatDate type="both" timeStyle="short" value="${question.creationDate}"/></h5>--%>
                    <%--&lt;%&ndash;<c:set var="s${question.questionId}" value="${sectionsList.sections.get(question.sectionId)}"/>&ndash;%&gt;--%>
                    <%--${sectionsList.get(question.sectionId)}--%>
                    <%--<h5><span class="label"--%>
                    <%--style="background-color: '#${sectionsList.get(question.sectionId).labelColor}">--%>
                    <%--${sectionsList.get(question.sectionId).name}--%>
                    <%--</span>--%>
                    <%--</h5><br>--%>
                    <%--<p>--%>
                    <%--<c:choose>--%>
                    <%--<c:when test="${fn:length(question.text) > 200}">--%>
                    <%--${fn:substring(question.text, 0, 200)}... <a href="question.html"><fmt:message--%>
                    <%--key="link.readMore"/></a></p>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                    <%--${question.text}--%>
                    <%--</c:otherwise>--%>
                    <%--</c:choose>--%>

                    <%--<hr>--%>
                    <%--</div>--%>
                    <%--</c:forEach>--%>
                <c:forEach items="${questionsList}" var="question" varStatus="questIter">
                    <div class="question-post">
                        <h2><a class="question-title" href="question.html">${question.title}</a></h2>
                        <h5 class="section-label"><span class="label"
                                  style="background-color: #${sectionsList[questIter.index].labelColor}">
                                ${sectionsList[questIter.index].name}
                        </span>
                        </h5>
                        <h5><span class="glyphicon glyphicon-time"></span> <fmt:message key="askedBy"/>
                            <form id="profile-${question.authorId}" action="<c:url value="/Profile"/>" hidden>
                                <input value="${question.authorId}" name="login">
                            </form>
                            <a href="#" onclick="document.getElementById('profile-${question.authorId}').submit()">
                                    ${authorsList[questIter.index].firstName}
                                    ${authorsList[questIter.index].lastName}</a>
                            <fmt:formatDate type="both" timeStyle="short" value="${question.creationDate}"/></h5>
                        <br>
                        <p>
                            <c:choose>
                            <c:when test="${fn:length(question.text) > 200}">
                                ${fn:substring(question.text, 0, 200)}... <a href="question.html"><fmt:message
                                key="link.readMore"/></a></p>
                        </c:when>
                        <c:otherwise>
                            ${question.text}
                        </c:otherwise>
                        </c:choose>

                        <hr>
                    </div>
                </c:forEach>
                <nav class="text-center">
                    <ul class="pagination">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link" href="#">1 <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">4</a></li>
                        <li class="page-item"><a class="page-link" href="#">5</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>