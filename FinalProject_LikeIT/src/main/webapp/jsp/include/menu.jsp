<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <a href="${url_home}"> Home</a>
                </li>
                <li id="menu_about">
                    <a href="<c:url value="/About"/>"> About</a>
                </li>
                <li id="menu_users">
                    <a href="<c:url value="/Users"/>"> Users</a>
                </li>
                <li id="menu_recent">
                    <a href="<c:url value="/Recent"/>"> Recent</a>
                </li>
                <li id="menu_top">
                    <a href="<c:url value="/TOP"/>"> TOP</a>
                </li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group has-feedback">
                    <label for="search-input" class="sr-only">Search</label>
                    <input id="search-input" type="text" class="form-control" placeholder="Search"/>
                    <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <li id="menu_sign_up">
                            <a href="<c:url value="/SignUp"/>"><span class="glyphicon glyphicon-user"></span> Sign
                                Up</a>
                        </li>
                        <li id="menu_login">
                            <a href="<c:url value="/Login"/>"><span class="glyphicon glyphicon-log-in"></span> Login</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <form id="profileForm" action="<c:url value="/Profile"/>" hidden>
                                <input value="${user.id}" name="login">
                            </form>
                            <a href="#" onclick="document.getElementById('profileForm').submit()"> Hello, ${user.firstName}! </a>
                        </li>
                        <li>
                            <form id="form_menu_logout" method="post" action="${url_home}" hidden>
                                <input type="hidden" name="command" value="LOGOUT">
                            </form>
                            <a href="#" onclick="document.getElementById('form_menu_logout').submit()">
                                <span class="glyphicon glyphicon-log-out"></span> Logout
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li><a href="#"><span class="lang-sm" lang="ru"></span></a></li>
                <li><a href="#"><span class="lang-sm" lang="en"></span></a></li>
            </ul>
        </div>
    </div>
</nav>