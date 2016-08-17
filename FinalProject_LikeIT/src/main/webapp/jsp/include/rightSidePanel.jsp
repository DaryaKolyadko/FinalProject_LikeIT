<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale.text}"/>
<fmt:bundle basename="likeit" prefix="rightSidePanel.">
    <div class="col-sm-12 col-md-12 col-lg-2 sidenav">
        <div id="top-questions" class="well"><label><fmt:message key="label.top"/> </label>
            <p><a id="q1" href="#">
                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit?</div>
            </a> <a id="q2" href="#">
                <div>Aenean libero leo, aliquam id facilisis at, tempus a ante. Nulla quis dictum tellus?</div>
            </a> <a id="q3" href="#">
                <div>Suspendisse potenti hofke. Vestibulum nec augue ut nisi fermentum vulputate?</div>
            </a> <a id="q4" href="#">
                <div>Curabitur sodales nunc sit amet pharetra lobortis?</div>
            </a> <a id="q5" href="#">
                <div>Maecenas et laoreet enim, in porta metus?</div>
            </a></p>
        </div>
    </div>
</fmt:bundle>