<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="createSection.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrap-select.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/jquery.minicolors.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/bootstrapValidator.min.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrapValidator.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-select.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.minicolors.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/createSection.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/createSection.js"></script>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <c:url value="/Home" var="url_home"/>
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="container medium-top-padding">
                    <div class="form-main-title"><fmt:message key="header"/></div>
                    <form id="create-section-form" method="post" class="form-horizontal form-wrap"
                          action="${url_home}">
                        <c:if test="${not empty actionError}">
                            <div class="alert alert-danger">
                                <a class="close" data-dismiss="alert" href="#">×</a>
                                <fmt:message key="${actionError.text}"/>
                            </div>
                        </c:if>
                        <input type="hidden" name="command" value="CREATE_SECTION"/>
                        <div class="form-group">
                            <label for="sectionName" class="col-sm-4 control-label"><fmt:message
                                    key="label.sectionName"/></label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="sectionName" name="sectionName"
                                       placeholder="<fmt:message key='placeholder.enterName'/>"
                                       value="${uncompleted.name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="topic" class="col-sm-4 control-label"><fmt:message
                                    key="label.majorSection"/></label>
                            <select class="selectpicker col-xs-12 col-sm-7" id="topic" data-live-search="true"
                                    name="majorSectionId" data-new-value="${uncompleted.majorSectionId}">
                                <option data-content="<fmt:message key='dropdown.asMajorSection'/>"></option>
                                <c:forEach var="majorSection" items="${majorSections}">
                                    <option data-content="${majorSection.name}">
                                            ${majorSection.id}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group" id="labelColorPicker">
                            <label for="hue-colorpicker" class="col-sm-4 control-label"><fmt:message
                                    key="label.color"/></label>
                            <div class="col-sm-7">
                                <input name="labelColor" type="text" id="hue-colorpicker" class="form-control"
                                       data-new-value="${uncompleted.labelColor}">
                            </div>
                        </div>
                        <div class="form-group button-bottom">
                            <button type="submit" class="btn btn-success"><fmt:message key="button.finish"/></button>
                            <a href="${url_home}" class="btn btn-default"><fmt:message key="button.cancel"/></a>
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