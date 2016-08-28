<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="createQuestion.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap-select.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-select.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/createQuestion.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <c:url value="/Home" var="url_home"/>
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div id="askQuestion" class="container">
                    <div class="form-main-title"><fmt:message key="header"/></div>
                    <form id="askQuestionForm" class="form-horizontal" action="${url_home}">
                        <div class="form-group">
                            <label for="topic" class="col-sm-2 control-label"><fmt:message key="label.section"/></label>
                            <select class="selectpicker col-sm-5" id="topic" data-live-search="true">
                                <c:forEach var="section" items="${sections}">
                                    <option>${section.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="inputTitle" class="col-sm-2 control-label">
                                <fmt:message key="label.question.title"/>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="inputTitle" placeholder="<fmt:message key="placeholder.enterTitle"/>"
                                       required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputDescription" class="col-sm-2 control-label">
                                <fmt:message key="label.question.text"/>
                            </label>
                            <div class="col-sm-8">
                            <textarea class="form-control" rows="5" id="inputDescription"
                                      placeholder="<fmt:message key="placeholder.enterText"/>"></textarea>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-2 col-sm-5">
                                <a type="submit" class="btn btn-warning"><fmt:message key="button.finish"/></a>
                                <a href="${url_home}" class="btn btn-primary"><fmt:message key="button.cancel"/></a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>