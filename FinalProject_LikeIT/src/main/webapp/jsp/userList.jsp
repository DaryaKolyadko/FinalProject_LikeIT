<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="userList.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrapValidator.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrapValidator.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-datepicker.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/datepicker3.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/userList.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_users')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="panel">
                    <div class="panel-body">
                        <table id="user_list" class="table borderless">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th><fmt:message key="username"/></th>
                                <th><fmt:message key="rating"/></th>
                                <th><fmt:message key="since"/></th>
                                <th><fmt:message key="state"/></th>
                                <ctm:adminOnly>
                                    <th><fmt:message key="profileState"/></th>
                                    <th><fmt:message key="role"/></th>
                                </ctm:adminOnly>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="u" items="${userList}" varStatus="usersIter">
                                <tr data-status="${fn:toLowerCase(u.state)}">
                                    <th scope="row">${usersIter.count}</th>
                                    <td>
                                        <form id="profile-${u.id}" action="<c:url value="/Profile"/>" hidden>
                                            <input value="${u.id}" name="login">
                                        </form>
                                        <a href="#"
                                           onclick="document.getElementById('profile-${u.id}').submit()">${u.firstName} ${u.lastName}</a>
                                    </td>
                                    <td>${u.rating}</td>
                                    <td>${u.signUpDate}</td>
                                    <td class="state-${fn:toLowerCase(u.state)}"><fmt:message
                                            key="${fn:toLowerCase(u.state)}"/></td>
                                    <ctm:adminOnly>
                                        <td class="state-${fn:toLowerCase(u.archive)}">
                                            <c:choose>
                                                <c:when test="${u.archive}">
                                                    <span class="archive-label"> <fmt:message key="inArchive"/> </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="inUse"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><span class="${fn:toLowerCase(u.role)}-label">${u.role}</span></td>
                                    </ctm:adminOnly>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
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