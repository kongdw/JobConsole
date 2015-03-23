<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="include/head.jsp" %>
<body style="background: url('/res/images/bg.png')">
<nav class="navbar navbar-default navbar-inverse navbar-static-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a class="navbar-brand" href="#">医保审核控制管理系统</a>
    </div>
</nav>
<div class="container">
    <div class="col-md-4 col-md-offset-4">
        <div class="padded">
            <div class="login box" style="margin-top: 80px;">

                <div class="box-header">
                    <span class="title">Login</span>
                </div>

                <div class="box-content padded">
                    <form class="separate-sections" name="login" id="login" action="/login" method="POST">
                        <div class="input-group addon-left">
									<span class="input-group-addon" href="#">
										<i class="icon-user"></i>
									</span>
                            <input type="text" placeholder="用户名" id="username" name="username" value="">
                        </div>

                        <div class="input-group addon-left">
									<span class="input-group-addon" href="#">
										<i class="icon-key"></i>
									</span>
                            <input type="password" placeholder="密码" id="password" name="password">
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary btn-block">Login <i class="icon-signin"></i>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
            <c:if test="${error != null }">
                <div id="loginError" class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong>登录异常</strong> ${error}
                </div>
            </c:if>
        </div>
    </div>
</div>

<%--<script type="text/javascript">--%>



<%--function toggleEvalPanel() {--%>
<%--var evaluationPanel = $("#evaluationPanel");--%>
<%--evaluationPanel.toggleClass("afterSlide");--%>
<%--$("#eval-arrow").toggleClass("closed");--%>
<%--}--%>


<%--function bounceToReturnLocation() {--%>
<%--// pass--%>
<%--var locale = document.login.locale.value;--%>

<%--var returnLocation = 'http\x3A\x2F\x2Fdemo.ivy\x2Dis.co.uk\x2Fpentaho\x2Findex.jsp';--%>

<%--if (returnLocation != '' && returnLocation != null) {--%>
<%--window.location.href = returnLocation;--%>
<%--} else {--%>
<%--window.location.href = window.location.href.replace("Login", "Home") + "?locale=" + locale;--%>
<%--}--%>

<%--}--%>

<%--function doLogin() {--%>
<%--$("#loginError").hide();--%>
<%--$(".login.box").after("<div class='loading-img'><center><img src='/res/images/loading.gif'/></center></div>");--%>

<%--// if we have a valid session and we attempt to login on top of it, the server--%>
<%--// will actually log us out and will not log in with the supplied credentials, you must--%>
<%--// login again. So instead, if they're already logged in, we bounce out of here to--%>
<%--// prevent confusion.--%>
<%--if (false) {--%>
<%--bounceToReturnLocation();--%>
<%--return false;--%>
<%--}--%>

<%--jQuery.ajax({--%>
<%--type: "POST",--%>
<%--url: "j_spring_security_check",--%>
<%--dataType: "text",--%>
<%--data: $("#login").serialize(),--%>

<%--error:function (xhr, ajaxOptions, thrownError){--%>
<%--$(".loading-img").remove();--%>
<%--if (xhr.status == 404) {--%>
<%--// if we get a 404 it means login was successful but intended resource does not exist--%>
<%--// just let it go - let the user get the 404--%>
<%--bounceToReturnLocation();--%>
<%--return;--%>
<%--}--%>
<%--//Fix for BISERVER-7525--%>
<%--//parsereerror caused by attempting to serve a complex document like a prd report in any presentation format like a .ppt--%>
<%--//does not necesarly mean that there was a failure in the login process, status is 200 so just let it serve the archive to the web browser.--%>
<%--if (xhr.status == 200 && thrownError == 'parsererror') {--%>
<%--document.getElementById("j_password").value = "";--%>
<%--bounceToReturnLocation();--%>
<%--return;--%>
<%--}--%>
<%--// fail--%>
<%--if($("#loginError").size()>0){--%>
<%--$("#loginError").show();--%>
<%--}else{--%>
<%--$(".login.box").after('<div id="loginError" style="display: none;" class="alert alert-danger alert-dismissable">'+--%>
<%--'<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'+--%>
<%--'<strong>Login Error</strong> A login error occurred. Please try again.'+--%>
<%--'</div>');--%>
<%--$("#loginError").show();--%>
<%--}--%>
<%--},--%>

<%--success:function(data, textStatus, jqXHR){--%>
<%--$(".loading-img").remove();--%>
<%--if (data.indexOf("j_spring_security_check") != -1) {--%>
<%--// fail--%>
<%--if($("#loginError").size()>0){--%>
<%--$("#loginError").show();--%>
<%--}else{--%>
<%--$(".login.box").after('<div id="loginError" style="display: none;" class="alert alert-danger alert-dismissable">'+--%>
<%--'<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'+--%>
<%--'<strong>Login Error</strong> A login error occurred. Please try again.'+--%>
<%--'</div>');--%>
<%--$("#loginError").show();--%>
<%--}--%>
<%--return false;--%>
<%--} else {--%>
<%--document.getElementById("j_password").value = "";--%>
<%--bounceToReturnLocation();--%>
<%--}--%>
<%--}--%>
<%--});--%>
<%--return false;--%>
<%--}--%>

<%--$(document).ready(function(){--%>
<%--$("#login").submit(doLogin);--%>

<%--if (false) {--%>
<%--bounceToReturnLocation();--%>
<%--}--%>



<%--});--%>
<%--</script>--%>


<div class="ex-tooltip"></div>
<div id="galleryOverlay" style="display: none;">
    <div id="gallerySlider"></div>
    <a id="prevArrow"><i class="icon-angle-left icon-4x"></i></a><a id="nextArrow"><i
        class="icon-angle-right icon-4x"></i></a></div>

</body>
<%@ include file="include/bottom.jsp" %>

<div style="position:fixed;bottom:0; width:100%;" align="center">
    <%@ include file="include/footer.jsp" %>
</div>




