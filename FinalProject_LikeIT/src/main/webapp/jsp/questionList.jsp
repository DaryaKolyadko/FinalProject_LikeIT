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
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/font-awesome.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/questionList.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="${listType}"/> <fmt:message key="questions"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_${listType}')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <c:choose>
                    <c:when test="${not empty serverError}">
                        <div><h2><fmt:message key="${serverError}"/></h2></div>
                    </c:when>
                    <c:otherwise>
                        <div class="list-header">
                            <small class="list-type"><fmt:message key="${listType}"/>
                                <c:choose>
                                    <c:when test="${not empty currentSection}">
                                        <small class="none-transform">
                                            "${currentSection.name}"
                                        </small>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="questions"/>
                                    </c:otherwise>
                                </c:choose>
                            </small>
                            <ctm:authenticatedOnly>
                                <a href="<c:url value="/CreateQuestion"/>" type="button"
                                   class="btn btn-primary ask-button"><i class="fa fa-question"></i> <fmt:message
                                        key="button.ask"/></a>
                            </ctm:authenticatedOnly>
                        </div>
                        <hr>
                        <c:choose>
                            <c:when test="${empty questionDataList}">
                                <div><h2><fmt:message key="emptyList"/></h2></div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${questionDataList}" var="data">
                                    <div class="question-post">
                                        <form id="question-title-${data.question.id}"
                                              action="<c:url value="/Question"/>"
                                              hidden>
                                            <input value="${data.question.id}" name="question">
                                        </form>
                                        <h2>
                                            <a href="#" class="question-title"
                                               onclick="document.getElementById('question-title-${data.question.id}').submit()">${data.question.title}</a>
                                        </h2>
                                        <h5 class="section-label"><span class="label"
                                                                        style="background-color: #${data.section.labelColor}">
                                                ${data.section.name}
                                        </span>
                                        </h5>
                                        <h5><span class="glyphicon glyphicon-time"></span> <fmt:message key="askedBy"/>
                                            <form id="profile-${data.question.authorId}"
                                                  action="<c:url value="/Profile"/>"
                                                  hidden>
                                                <input value="${data.question.authorId}" name="login">
                                            </form>
                                            <a href="#"
                                               onclick="document.getElementById('profile-${data.question.authorId}').submit()">
                                                    ${data.user.firstName}
                                                    ${data.user.lastName}</a>
                                            <fmt:formatDate type="both" timeStyle="short"
                                                            value="${data.question.creationDate}"/>
                                        </h5>
                                        <br>
                                        <div>
                                            <c:choose>
                                                <c:when test="${fn:length(data.question.text) > 200}">
                                                    <form id="question-${data.question.id}"
                                                          action="<c:url value="/Question"/>"
                                                          hidden>
                                                        <input value="${data.question.id}" name="question">
                                                    </form>
                                                    ${fn:substring(data.question.text, 0, 200)}...
                                                    <a href="#"
                                                       onclick="document.getElementById('question-${data.question.id}').submit()">
                                                        <fmt:message key="link.readMore"/></a>
                                                </c:when>
                                                <c:otherwise>
                                                    ${data.question.text}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <hr>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
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