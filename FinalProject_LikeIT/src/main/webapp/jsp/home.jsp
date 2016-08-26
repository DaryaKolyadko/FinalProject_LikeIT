<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="home.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet"
              href="${pageContext.servletContext.contextPath}/resources/css/lib/jquery.minicolors.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/home.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery.minicolors.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/home.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <script type="text/javascript">
        selectMenuItem('#menu_home')
    </script>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="panel-group sections top-padding" id="collapse-group">
                    <ctm:adminOnly>
                        <div class="top-padding bottom-padding right-button-parent">
                            <button class="btn btn-primary right" data-toggle="modal" data-target="#add">
                                <span class="glyphicon glyphicon-plus"></span> Add section
                            </button>
                        </div>
                    </ctm:adminOnly>
                    <c:forEach items="${sectionsCatalogue}" var="majorSection">
                        <div class="panel panel-default">
                            <div class="panel-heading clearfix" data-toggle="collapse"
                                 data-target="#${majorSection.key.id}">
                                <div class="panel-title">
                                        ${majorSection.key.name}
                                    <ctm:adminOnly>
                                        <a href="#" data-toggle="modal" data-target="#delete">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </a>
                                    </ctm:adminOnly>
                                </div>
                            </div>
                            <div id="${majorSection.key.id}" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <table class="table borderless">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th><fmt:message key="table.header.section"/></th>
                                            <th><fmt:message key="table.header.questions"/></th>
                                            <th><fmt:message key="table.header.answered"/></th>
                                            <ctm:adminOnly>
                                                <th><fmt:message key="table.header.actions"/></th>
                                            </ctm:adminOnly>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${majorSection.value}" var="section"
                                                   varStatus="sectionIter">
                                            <tr>
                                                <th scope="row">${sectionIter.count}</th>
                                                <td>
                                                    <form id="section-${section.id}"
                                                          action="<c:url value="/Questions"/>" hidden>
                                                        <input value="${section.name}" name="section">
                                                    </form>
                                                    <a href="#"
                                                       onclick="document.getElementById('section-${section.id}').submit()">
                                                            ${section.name}
                                                    </a>
                                                </td>
                                                <td>${section.questionNum}</td>
                                                <td>
                                                    <c:set var="answerPercent"
                                                           value="${100*section.answerNum/section.questionNum}"/>
                                                    <div class="statusBlock"><span
                                                            style="height: ${answerPercent}%"></span>
                                                        <div>${section.answerNum}</div>
                                                    </div>
                                                </td>
                                                <ctm:adminOnly>
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
                                                </ctm:adminOnly>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
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
                    <h4 class="modal-title custom_align">Add root section</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="hue-colorpicker">Label color</label>
                        <input type="text" id="hue-colorpicker" class="form-control" placeholder="Label color">
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
</fmt:bundle>
