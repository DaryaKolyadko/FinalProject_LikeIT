<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="home.">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/index.css"/>
    <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
    <title><fmt:message key="title"/> </title>
</head>
<body>
<%@include file="/jsp/include/menu.jsp" %>
<script type="text/javascript">
    selectMenuItem('#menu_home')
</script>
<div class="container-fluid text-center main-wrapper">
    <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <h1><fmt:message key="welcome"/></h1>
                <p><fmt:message key="welcomeText"/></p>

                <c:forEach items="${sectionsCatalogue}" var="majorSection">
                    <div class="major-section">
                        <hr>
                            ${majorSection.key.name}
                        <c:if test="${user.isAdmin()}">
                            <a href="#" data-toggle="modal" data-target="#add">
                                <div class="glyphicon glyphicon-plus"></div>
                            </a>
                        </c:if>
                        <hr>
                    </div>
                    <div class="panel-group sections" id="collapse-group">
                        <c:forEach items="${majorSection.value}" var="section">
                            <div class="panel panel-default">
                                <div class="panel-heading collapsed clearfix" data-toggle="collapse"
                                     data-target="#${section.key.id}">
                                    <div class="panel-title">
                                            ${section.key.name}
                                        <c:if test="${user.isAdmin()}">
                                            <a href="#" data-toggle="modal" data-target="#delete">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                                <div id="${section.key.id}" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <table class="table borderless">
                                            <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Theme</th>
                                                <th>Topics</th>
                                                <th>Answered</th>
                                                <c:if test="${user.isAdmin()}">
                                                    <th>Actions</th>
                                                </c:if>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${section.value}" var="subsection"
                                                       varStatus="subsectionIter">
                                                <tr>
                                                    <th scope="row">${subsectionIter.count}</th>
                                                    <td><a href="#">${subsection.name}</a></td>
                                                    <td>${subsection.questionNum}</td>
                                                    <td>
                                                        <c:set var="answerPercent"
                                                               value="${100*subsection.answerNum/subsection.questionNum}"/>
                                                        <div class="statusBlock"><span
                                                                style="height: ${answerPercent}%"></span>
                                                            <div>${subsection.answerNum}</div>
                                                        </div>
                                                    </td>
                                                    <c:if test="${user.isAdmin()}">
                                                        <td>
                                                            <div class="btn-group">
                                                                <button type="button" class="btn btn-default"
                                                                        data-toggle="modal"
                                                                        data-target="#edit">
                                                                    <span class="glyphicon glyphicon-edit"></span>
                                                                </button>
                                                                <button type="button" class="btn btn-danger"
                                                                        data-toggle="modal"
                                                                        data-target="#delete">
                                                                    <span class="glyphicon glyphicon-remove"></span>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </fmt:bundle>
        <%@include file="/jsp/include/rightSidePanel.jsp" %>
    </div>
    <%@include file="/jsp/include/footer.jsp" %>
</div>

<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                        class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                <h4 class="modal-title custom_align">Edit section</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input class="form-control" type="text" placeholder="Name">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning btn-lg" style="width: 100%;"><span
                        class="glyphicon glyphicon-ok-sign"></span> Update
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="edit"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                        class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                <h4 class="modal-title custom_align">Delete section</h4>
            </div>
            <div class="modal-body">

                <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span>
                    Are you sure you
                    want to delete this section?
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success"><span
                        class="glyphicon glyphicon-ok-sign"></span> Yes
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span> No
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                        class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                <h4 class="modal-title custom_align">Add section</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input class="form-control" type="text" placeholder="Name">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success btn-lg" style="width: 100%;"><span
                        class="glyphicon glyphicon-ok-sign"></span> Add
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>