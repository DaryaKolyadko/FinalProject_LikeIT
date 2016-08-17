<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctm" uri="customtags" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="question.">
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/question.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/lib/jRate.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/question.js"></script>
        <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
        <title>Question //TODO</title>
    </head>
    <body>
    <%@include file="/jsp/include/menu.jsp" %>
    <div class="container-fluid text-center main-wrapper">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
                <div class="question-post">
                    <h2>Serious question</h2>
                    <h5><span class="glyphicon glyphicon-time"></span> Asked by <a href="user.html">Clary Fray</a>, Jul
                        12,
                        2016, 10:12 AM</h5>
                    <h5><span class="label label-danger">Java</span></h5><br>
                    <p>Food is my passion. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor
                        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                        ullamco laboris nisi ut aliquip ex ea commodo consequat. Excepteur sint occaecat cupidatat non
                        proident, sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing
                        elit.Food is my passion. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor
                        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                        ullamco laboris nisi ut aliquip ex ea commodo consequat. Excepteur sint occaecat cupidatat non
                        proident, sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing
                        elit.</p>
                    <hr>
                </div>
                <p><span class="badge">3</span> Comments:</p><br>
                <div class="row">
                    <div class="col-lg-11">
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65" alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4><a href="user.html" class="user-block">Isabelle Lightwood</a>
                                <small class="user-block">Jul 12, 2016, 10:32 AM</small>
                                <span id="rating-1" class="jRate"></span>
                                <small id="rating-1-value" class="rating-value"></small>
                                <script>generateJRate(1, 2)</script>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>Keep up the GREAT work! I am cheering for you!! Lorem ipsum dolor sit amet, consectetur
                                adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                        </div>
                    </div>
                    <div class="col-lg-11">
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65" alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4><a href="user.html" class="user-block">Clary Fray</a>
                                <small class="user-block">Jul 12, 2016, 10:12 PM</small>
                                <span id="rating-2" class="jRate"></span>
                                <small id="rating-2-value" class="rating-value"></small>
                                <script>generateJRate(2, 7)</script>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>I am so happy for you man! Finally. I am looking forward to read about your trendy life.
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                incididunt utlabore et dolore magna aliqua.</p>
                        </div>
                    </div>
                    <div class="col-lg-11">
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65"
                                 alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4><a href="user.html" class="user-block">Isabelle Lightwood</a>
                                <small class="user-block">Sep 25, 2015, 8:28 PM</small>
                                <span id="rating-3" class="jRate"></span>
                                <small id="rating-3-value" class="rating-value"></small>
                                <script>generateJRate(3, 5)</script>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>Me too! WOW!</p>
                        </div>
                    </div>
                </div>
                <br><br>
                <div class="row">
                    <div class="col-sm-10">
                        <h4>Leave a Comment:</h4>
                        <form role="form">
                            <div class="form-group">
                                <label for="comment" hidden></label>
                                <textarea id="comment" class="form-control" rows="3" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-success">Send</button>
                        </form>
                    </div>
                </div>
            </div>
            <%@include file="/jsp/include/rightSidePanel.jsp" %>
        </div>
        <%@include file="/jsp/include/footer.jsp" %>
    </div>
    </body>
    </html>
</fmt:bundle>