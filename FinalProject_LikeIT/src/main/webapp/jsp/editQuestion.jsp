<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="editQuestion.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap-select.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrapValidator.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrapValidator.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-select.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/createQuestion.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/editQuestion.js"></script>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <c:url value="/Home" var="url_home"/>
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <c:choose>
                <c:when test="${not empty serverError}">
                    <div class="alert alert-danger top-margin">
                        <fmt:message key="${serverError}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="container medium-top-padding">
                        <div class="form-main-title"><fmt:message key="header"/></div>
                        <form id="edit-question-form" class="form-horizontal form-wrap" method="post"
                              action="${url_home}">
                            <c:if test="${not empty actionError}">
                                <div class="alert alert-danger">
                                    <a class="close" data-dismiss="alert" href="#">×</a>
                                    <fmt:message key="${actionError.text}"/>
                                </div>
                            </c:if>
                            <input type="hidden" name="command" value="EDIT_QUESTION"/>
                            <input type="hidden" name="question" value="${param.question}"/>
                            <div class="form-group">
                                <label for="topic" class="col-sm-2 control-label"><fmt:message
                                        key="label.section"/></label>
                                <select class="selectpicker col-sm-10" name="sectionId" id="topic"
                                        data-live-search="true"
                                        data-new-value="${uncompleted.sectionId}${questionToEdit.sectionId}">
                                    <c:forEach var="section" items="${sections}">
                                        <option value="${section.id}">${section.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="inputTitle" class="col-sm-2 control-label">
                                    <fmt:message key="label.question.title"/>
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="title" id="inputTitle"
                                           placeholder="<fmt:message key="placeholder.enterTitle"/>"
                                           value="${uncompleted.title}${questionToEdit.title}"
                                           required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputDescription" class="col-sm-2 control-label">
                                    <fmt:message key="label.question.text"/>
                                </label>
                                <div class="col-sm-10">
                            <textarea class="form-control no-resize" rows="10" id="inputDescription" name="text"
                                      placeholder="<fmt:message key="placeholder.enterText"/>"> ${uncompleted.text}${questionToEdit.text}</textarea>
                                </div>
                            </div>
                            <div class="form-group last">
                                <div class="button-bottom">
                                    <button type="submit" class="btn btn-success"><fmt:message
                                            key="button.finish"/></button>
                                    <a href="${url_home}" class="btn btn-default"><fmt:message key="button.cancel"/></a>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:otherwise>
                </c:choose>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>