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
                            <h4 class="list-type"><fmt:message key="${listType}"/>
                                <c:choose>
                                    <c:when test="${not empty currentSection}">
                                        <span class="none-transform">
                                            "${currentSection.name}"
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="questions"/>
                                    </c:otherwise>
                                </c:choose>
                            </h4>
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
                                              action="<c:url value="/Question"/>" hidden>
                                            <input value="${data.question.id}" name="question">
                                        </form>
                                        <h2>
                                            <a href="#"
                                               onclick="$('#question-title-${data.question.id}').submit()">${data.question.title}</a>
                                            <c:if test="${data.question.archive eq true}">
                                                <span class="archive-label">(<fmt:message key="label.archive"/>)</span>
                                            </c:if>
                                        </h2>
                                        <h5><span class="label" style="background-color: #${data.section.labelColor}">
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
                                               onclick="$('#profile-${data.question.authorId}').submit()">
                                                    ${data.user.firstName}
                                                    ${data.user.lastName}</a>
                                            <fmt:formatDate type="both" timeStyle="short"
                                                            value="${data.question.creationDate}"/>
                                        </h5>
                                        <br>
                                        <p>
                                            <c:choose>
                                                <c:when test="${fn:length(data.question.text) > 200}">
                                                    <ctm:htmlTextFormat
                                                            text="${fn:substring(data.question.text, 0, 200)}"/>...
                                                </c:when>
                                                <c:otherwise>
                                                    <ctm:htmlTextFormat text="${data.question.text}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="#"
                                               onclick="$('#question-title-${data.question.id}').submit()">
                                                <fmt:message key="link.readMore"/></a>
                                        </p>
                                        <hr>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                <nav class="text-center">
                    <ul class="pagination">
                        <c:if test="${currentPage != 1}">
                            <li class="page-item" id="previous">
                                <form id="page-previous" action="${originUrl}" hidden>
                                    <input name="page" value="${currentPage-1}">
                                    <c:if test="${not empty param.section}">
                                        <input value="${param.section}" name="section">
                                    </c:if>
                                </form>
                                <a class="page-link" onclick="$('#page-previous').submit()"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <c:set value="1" var="shift"/>
                        <c:set value="${numOfPages}" var="intermediate"/>
                        <c:if test="${currentPage+shift lt numOfPages}">
                            <c:set value="${currentPage+shift}" var="intermediate"/>
                        </c:if>
                        <c:forEach begin="${currentPage}" end="${intermediate}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link">${i}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <form id="page-${i}" action="${originUrl}" hidden>
                                        <input name="page" value="${i}">
                                        <c:if test="${not empty param.section}">
                                            <input value="${param.section}" name="section">
                                        </c:if>
                                    </form>
                                    <li class="page-item"><a class="page-link"
                                                             onclick="$('#page-${i}').submit()">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${intermediate ne numOfPages}">
                            <li class="page-item"><a class="page-link">...</a></li>
                        </c:if>

                        <c:if test="${currentPage lt numOfPages}">
                            <li class="page-item">
                                <form id="page-next" action="${originUrl}" hidden>
                                    <input name="page" value="${currentPage+1}">
                                    <c:if test="${not empty param.section}">
                                        <input value="${param.section}" name="section">
                                    </c:if>
                                </form>
                                <a class="page-link" onclick="$('#page-next').submit()" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </c:if>
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