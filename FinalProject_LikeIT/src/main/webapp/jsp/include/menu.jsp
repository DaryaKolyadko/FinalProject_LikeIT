<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="likeit" prefix="menu.">
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <c:url value="/Home" var="url_home"/>
                <form id="form_menu_logo" method="get" action="${url_home}" hidden></form>
                <a href="#" onclick="document.getElementById('form_menu_logo').submit()" class="navbar-brand">
                    <img src="${pageContext.request.contextPath}/resources/img/logo.png"></a>
            </div>
            <div class="collapse navbar-collapse" id="mainMenu">
                <ul class="nav navbar-nav">
                    <li id="menu_home">
                        <a href="${url_home}"> <fmt:message key="home"/> </a>
                    </li>
                    <li id="menu_about">
                        <a href="<c:url value="/About"/>"> <fmt:message key="about"/></a>
                    </li>
                    <li id="menu_users">
                        <a href="<c:url value="/Users"/>"> <fmt:message key="users"/></a>
                    </li>
                    <li id="menu_recent">
                        <a href="<c:url value="/Recent"/>"> <fmt:message key="recent"/></a>
                    </li>
                    <li id="menu_top">
                        <a href="<c:url value="/TOP"/>"> <fmt:message key="top"/></a>
                    </li>
                </ul>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group has-feedback">
                        <label for="search-input" class="sr-only"><fmt:message key="search"/></label>
                        <input id="search-input" type="text" class="form-control"
                               placeholder="<fmt:message key="search"/>"/>
                        <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${empty userContainer}">
                            <li id="menu_sign_up">
                                <a href="<c:url value="/SignUp"/>"><span class="glyphicon glyphicon-user"></span>
                                    <fmt:message key="signUp"/></a>
                            </li>
                            <li id="menu_login">
                                <a href="<c:url value="/Login"/>"><span class="glyphicon glyphicon-log-in"></span>
                                    <fmt:message key="login"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <form id="profileForm" action="<c:url value="/Profile"/>" hidden>
                                    <input value="${userContainer.object.id}" name="login">
                                </form>
                                <a href="#" onclick="document.getElementById('profileForm').submit()">
                                    <fmt:message key="hello"/> ${userContainer.object.firstName}! </a>
                            </li>
                            <li>
                                <form id="form_menu_logout" method="post" action="${url_home}" hidden>
                                    <input type="hidden" name="command" value="LOGOUT">
                                </form>
                                <a href="#" onclick="document.getElementById('form_menu_logout').submit()">
                                    <span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logout"/>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <li>
                        <form id="ruForm" action="${originUrl}" hidden>
                            <input value="ru_RU" name="locale">
                        </form>
                        <a href="#" onclick="document.getElementById('ruForm').submit()"><span class="lang-sm"
                                                                                               lang="ru"></span></a>
                    </li>
                    <li>
                        <form id="enForm" action="${originUrl}" hidden>
                            <input value="en_US" name="locale">
                        </form>
                        <a href="#" onclick="document.getElementById('enForm').submit()"><span class="lang-sm"
                                                                                               lang="en"></span></a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</fmt:bundle>