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
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/lib/languages.min.css"/>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbarOrange.css"/>
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
                    <ul class="list-inline list-unstyled clearfix">
                        <li>
                            <span id="rating-q-1" class="jRate"></span>
                            <small id="rating-q-1-value" class="rating-value"></small>
                            <script>generateQuestionJRate(1, 0)</script>
                        </li>
                        <li class="divider">|</li>
                        <li>
                            <small class="rating-div-custom-float"><fmt:formatNumber
                                    maxFractionDigits="1">5.495</fmt:formatNumber></small>
                        </li>
                    </ul>
                    <hr>
                </div>
                <p><span class="badge">3</span> Comments:</p><br>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="col-xs-2 col-sm-1 text-center answer-block">
                            <a href="#" class="glyphicon glyphicon-ok answer-mark-off center"></a></div>
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65" alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4 class="h4-custom-float"><a href="user.html" class="user-block">Isabelle Lightwood</a>
                                <span class="right-panel">
                                    <a href="#"><i class="glyphicon glyphicon-edit"></i></a>
                                    <a href="#"><i class="glyphicon glyphicon-trash"></i></a>
                                </span>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>Keep up the GREAT work! I am cheering for you!! Lorem ipsum dolor sit amet, consectetur
                                adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                            <ul class="list-inline list-unstyled clearfix">
                                <li>
                                    <small class="date-block">
                                        <i class="glyphicon glyphicon-calendar"></i> Jul 12, 2016,
                                        10:32 AM
                                    </small>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <span id="rating-1" class="jRate"></span>
                                    <small id="rating-1-value" class="rating-value"></small>
                                    <script>generateJRate(1, 2)</script>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <small class="rating-div-custom-float"><fmt:formatNumber
                                            maxFractionDigits="1">6.645</fmt:formatNumber></small>
                                </li>
                            </ul>
                        </div>
                        <hr>
                    </div>
                    <div class="col-lg-12">
                        <div class="col-xs-2 col-sm-1 text-center answer-block">
                            <a href="#" class="glyphicon glyphicon-ok answer-mark-on center" style=""></a></div>
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65" alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4 class="h4-custom-float"><a href="user.html" class="user-block">Clary Fray</a>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>I am so happy for you man! Finally. I am looking forward to read about your trendy life.
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                incididunt utlabore et dolore magna aliqua.</p>
                            <ul class="list-inline list-unstyled clearfix">
                                <li>
                                    <small class="date-block"><i class="glyphicon glyphicon-calendar"></i> Jul 12, 2016,
                                        10:12 PM
                                    </small>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <span id="rating-2" class="jRate"></span>
                                    <small id="rating-2-value" class="rating-value"></small>
                                    <script>generateJRate(2, 7)</script>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <small class="rating-div-custom-float"><fmt:formatNumber
                                            maxFractionDigits="1">7.395</fmt:formatNumber></small>
                                </li>
                            </ul>
                        </div>
                        <hr>
                    </div>
                    <div class="col-lg-12">
                        <div class="col-xs-2 col-sm-1 text-center answer-block">
                            <a href="#" class="glyphicon glyphicon-ok answer-mark-off center" style=""></a></div>
                        <div class="col-xs-3 col-sm-1 text-center">
                            <img src="${pageContext.servletContext.contextPath}/resources/img/avatar-300x300.png"
                                 class="img-circle" height="65" width="65"
                                 alt="Avatar">
                        </div>
                        <div class="col-sm-10">
                            <h4 class="h4-custom-float"><a href="user.html" class="user-block">Isabelle Lightwood</a>
                            </h4>
                            <div class="clearfix visible-xs-block"></div>
                            <p>Me too! WOW!</p>
                            <ul class="list-inline list-unstyled clearfix">
                                <li>
                                    <small class="date-block"><i class="glyphicon glyphicon-calendar"></i> Sep 25, 2015,
                                        8:28 PM
                                    </small>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <span id="rating-3" class="jRate"></span>
                                    <small id="rating-3-value" class="rating-value"></small>
                                    <script>generateJRate(3, 0)</script>
                                </li>
                                <li class="divider">|</li>
                                <li>
                                    <small class="rating-div-custom-float"><fmt:formatNumber
                                            maxFractionDigits="1">5.495</fmt:formatNumber></small>
                                </li>
                            </ul>
                        </div>
                        <hr>
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


<%--<div class="col-lg-12">
    <div class="col-xs-2 col-sm-1 text-center" style="height: 65px; font-size: 40px; color: rgb(67, 195, 59);">
        <span class="glyphicon glyphicon-ok center" style=""></span></div>
                        <div style="" class="col-xs-3 col-sm-1 text-center">

                            <img style="" src="/LikeIT/resources/img/avatar-300x300.png" class="img-circle" alt="Avatar" height="65" width="65">
                        </div>
                        <div class="col-sm-10">
                            <h4 class="h4-custom-float"><a href="user.html" class="user-block">Clary Fray</a>
                                <small class="user-block">Jul 12, 2016, 10:12 PM</small>
                                <span style="white-space: nowrap; cursor: pointer;" id="rating-2" class="jRate"><svg style="margin-right: 0px;" width="20" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 12.705 512 486.59"><defs><linearGradient id="rating-2_grad1" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="100%" stop-color="yellow"></stop><stop offset="0%" stop-color="white"></stop></linearGradient></defs><polygon style="fill: url(#rating-2_grad1);stroke:black;stroke-width:2px;" points="256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566 "></polygon></svg><svg style="margin-right: 0px;" width="20" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 12.705 512 486.59"><defs><linearGradient id="rating-2_grad2" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="100%" stop-color="rgba(204,230,0,1)"></stop><stop offset="0%" stop-color="white"></stop></linearGradient></defs><polygon style="fill: url(#rating-2_grad2);stroke:black;stroke-width:2px;" points="256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566 "></polygon></svg><svg style="margin-right: 0px;" width="20" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 12.705 512 486.59"><defs><linearGradient id="rating-2_grad3" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="100%" stop-color="rgba(153,205,0,1)"></stop><stop offset="0%" stop-color="white"></stop></linearGradient></defs><polygon style="fill: url(#rating-2_grad3);stroke:black;stroke-width:2px;" points="256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566 "></polygon></svg><svg style="margin-right: 0px;" width="20" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 12.705 512 486.59"><defs><linearGradient id="rating-2_grad4" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="50%" stop-color="rgba(102,180,0,1)"></stop><stop offset="0%" stop-color="white"></stop></linearGradient></defs><polygon style="fill: url(#rating-2_grad4);stroke:black;stroke-width:2px;" points="256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566 "></polygon></svg><svg style="margin-right: 0px;" width="20" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 12.705 512 486.59"><defs><linearGradient id="rating-2_grad5" x1="0%" y1="0%" x2="100%" y2="0%"><stop offset="0%" stop-color="white"></stop><stop offset="0%" stop-color="white"></stop></linearGradient></defs><polygon style="fill: url(#rating-2_grad5);stroke:black;stroke-width:2px;" points="256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566 "></polygon></svg></span>
                                <small id="rating-2-value" class="rating-value">7</small>
                                <script>generateJRate(2, 7)</script>
                            </h4>
                            <div class="rating-div-custom-float">7.4</div>
                            <div class="clearfix visible-xs-block"></div>
                            <p>I am so happy for you man! Finally. I am looking forward to read about your trendy life.
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                incididunt utlabore et dolore magna aliqua.</p>
                        </div>
                    </div>--%>