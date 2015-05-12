<%@ page import="cn.com.cis.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<nav class="navbar navbar-default navbar-inverse navbar-static-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/main">医保审核控制管理系统</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse navbar-collapse-top">
        <div class="navbar-right">

            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle dropdown-avatar" data-toggle="dropdown">
				<span>
					<img class="menu-avatar" src="${pageContext.request.contextPath}/res/images/blank_avatar.png"> <span> ${user.username}
                    <i class="icon-caret-down"></i></span>
				</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- the first element is the one with the big avatar, add a with-image class to it -->
                        <li class="with-image">
                            <div class="avatar">
                                <img src="${pageContext.request.contextPath}/res/images/blank_avatar.png">
                            </div>
                            <span>
                                ${user.username}
                            </span>
                        </li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/user/edit?id=${user.id}"><i class="icon-user"></i> <span>修改个人信息</span></a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/user/editpwd?id= ${user.id}"><i class="icon-user"></i> <span>修改密码</span></a>
                        </li>
                        <li><a data-toggle="modal" href="#modal-logout"><i class="icon-off"></i> <span>注销</span></a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#logoutButton').click(function () {
                window.location.replace("${pageContext.request.contextPath}/logout");
            });
        });
    </script>
    <!-- /.navbar-collapse -->
</nav>