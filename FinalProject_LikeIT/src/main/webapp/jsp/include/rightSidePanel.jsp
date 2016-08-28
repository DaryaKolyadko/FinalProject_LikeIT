<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="rightSidePanel.">
    <div class="col-sm-12 col-md-12 col-lg-2 sidenav">
        <div id="top-questions" class="well"><label><fmt:message key="label.top"/> </label>
            <p>
                <c:forEach items="${top5}" var="question">
            <form id="question-title-${question.id}" action="<c:url value="/Question"/>" hidden>
                <input value="${question.id}" name="question">
            </form>
            <a href="#" class=".no-decor"
               onclick="document.getElementById('question-title-${question.id}').submit()">
                <div><c:choose>
                    <c:when test="${fn:length(question.title) > 50}">
                        ${fn:substring(question.title, 0, 50)}...
                    </c:when>
                    <c:otherwise>
                        ${question.title}
                    </c:otherwise>
                </c:choose></div>
            </a>
            </c:forEach>
            </p>
        </div>
    </div>
</fmt:bundle>