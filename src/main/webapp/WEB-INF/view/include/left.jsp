<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar-background">
    <div class="primary-sidebar-background"></div>
</div>
<div class="primary-sidebar">
    <!-- Main nav -->
    <ul id="mainNavigation" class="nav navbar-collapse collapse navbar-collapse-primary">

        <li class="active">
            <span class="glow"></span>
            <a href="${pageContext.request.contextPath}/main">
                <i class="icon-home icon-2x"></i>
                <span>主页</span>
            </a>
        </li>
        <c:if test="${user.roleType eq '1'}" >

            <li class="dark-nav">
                <span class="glow"></span>
                <a class="accordion-toggle" data-toggle="collapse" href="#jobList">
                    <i class="icon-list icon-2x"></i>
                    <span>配置管理<i class="icon-caret-down"></i></span>
                </a>
                <ul id="jobList" class="collapse in">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/job/list?page=1">
                            <i class="icon-chevron-right"></i> 作业管理</a>
                    </li>
                    <li class=""><a
                            href="${pageContext.request.contextPath}/layer/list"><i
                            class="icon-chevron-right"></i> 层级管理</a></li>
                    <li class=""><a
                            href="${pageContext.request.contextPath}/database/list"><i
                            class="icon-chevron-right"></i>数据源管理</a></li>
                    <li class=""><a
                            href="${pageContext.request.contextPath}/databasetype/list"><i
                            class="icon-chevron-right"></i>数据源类型管理</a></li>
                    <li class=""><a
                            href="${pageContext.request.contextPath}/parameter/glist"><i
                            class="icon-chevron-right"></i>全局参数管理</a></li>
                    <li class=""><a
                            href="${pageContext.request.contextPath}/user/list/1"><i
                            class="icon-chevron-right"></i>用户管理</a></li>

                </ul>
            </li>
            <li class="">
                <span class="glow"></span>
                <a href="${pageContext.request.contextPath}/queue/list">
                    <i class="icon-time icon-2x"></i>
                    <span>等待队列</span>
                </a>
            </li>
            <li class="">
                <span class="glow"></span>
                <a href="${pageContext.request.contextPath}/log/list/1">
                    <i class="icon-desktop icon-2x"></i>
                    <span>执行日志</span>
                </a>
            </li>
        </c:if>

        <%--<li class="dark-nav ">--%>
            <%--<span class="glow"></span>--%>
            <%--<a class="accordion-toggle collapsed " data-toggle="collapse" href="#dashboardList">--%>
                <%--<i class="icon-dashboard icon-2x"></i>--%>
                <%--<span>服务负载<i class="icon-caret-down"></i></span>--%>
            <%--</a>--%>
            <%--<ul id="dashboardList" class="collapse">--%>
                <%--<li class="">--%>
                    <%--<a href="#">--%>
                        <%--<i class="icon-chevron-right"></i> Customer Analysis</a>--%>
                <%--</li>--%>
                <%--<li class=""><a--%>
                        <%--href="#"><i--%>
                        <%--class="icon-chevron-right"></i> Product Comparison</a></li>--%>
                <%--<li class=""><a--%>
                        <%--href="#"><i--%>
                        <%--class="icon-chevron-right"></i> Top Ten Customers</a></li>--%>
                <%--<li class=""><a--%>
                        <%--href="#"><i--%>
                        <%--class="icon-chevron-right"></i> USA Sales</a></li>--%>
            <%--</ul>--%>
        <%--</li>--%>



        <%--<li class="dark-nav ">--%>
            <%--<span class="glow"></span>--%>
            <%--<a class="accordion-toggle collapsed " data-toggle="collapse" href="#analysisList">--%>
                <%--<i class="icon-bar-chart icon-2x"></i>--%>
                <%--<span>Analysis<i class="icon-caret-down"></i></span>--%>
            <%--</a>--%>
            <%--<ul id="analysisList" class="collapse ">--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-analysis"><i class="icon-chevron-right"></i> Coming--%>
                    <%--Soon!</a>--%>
                <%--</li>--%>
            <%--</ul>--%>
        <%--</li>--%>

        <%--<li class="dark-nav">
            <span class="glow"></span>
            <a class="accordion-toggle collapsed " data-toggle="collapse" href="#newList">
                <i class="icon-file icon-2x"></i>
                <span>新建<i class="icon-caret-down"></i></span>
            </a>
            <ul id="newList" class="collapse ">
                <li class=""><a href="/layer/add"><i class="icon-plus"></i> ETL 层级</a></li>
                <li class=""><a href="/database/add"><i class="icon-plus"></i> 数据源</a></li>
                <li class=""><a href="/job/add"><i class="icon-plus"></i> 新建作业</a></li>
                <li class=""><a href="/parameter/add"><i class="icon-chevron-right"></i>新建全局参数</a></li>
                <li class=""><a href="/user/add"><i class="icon-chevron-right"></i>新建用户</a></li>
            </ul>
        </li>--%>

        <%--<li class="dark-nav" id="ivydd-menu-option">--%>
            <%--<span class="glow"></span>--%>
            <%--<a class="accordion-toggle collapsed " data-toggle="collapse" href="#ivyDDList">--%>
                <%--<i class="icon-magic icon-2x"></i>--%>
                <%--<span>IvyDD<i class="icon-caret-down"></i></span>--%>
            <%--</a>--%>
            <%--<ul id="ivyDDList" class="collapse ">--%>
                <%--<li class=""><a target="_blank" href="http://demo.ivy-is.co.uk/pentaho/content/ivydd/html/index.html"><i--%>
                        <%--class="icon-plus"></i> New Dashboard</a></li>--%>
                <%--<li class=""><a--%>
                        <%--href="http://demo.ivy-is.co.uk/pentaho/content/ivydd/html/index.html#/view/?path=%2Fpublic%2Fdemo%2Fivydd%2FSample Dash 1.ivydd"--%>
                        <%--target="_blank"><i class="icon-chevron-right"></i> Sample Dash 1</a></li>--%>
                <%--<li class=""><a--%>
                        <%--href="http://demo.ivy-is.co.uk/pentaho/content/ivydd/html/index.html#/view/?path=%2Fpublic%2Fdemo%2Fivydd%2FSample Dash 2.ivydd"--%>
                        <%--target="_blank"><i class="icon-chevron-right"></i> Sample Dash 2</a></li>--%>
                <%--<li class=""><a--%>
                        <%--href="http://demo.ivy-is.co.uk/pentaho/content/ivydd/html/index.html#/view/?path=%2Fpublic%2Fdemo%2Fivydd%2FSample Dash 3.ivydd"--%>
                        <%--target="_blank"><i class="icon-chevron-right"></i> Sample Dash 3</a></li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <%--<li class="dark-nav ">--%>
            <%--<span class="glow"></span>--%>
            <%--<a class="accordion-toggle collapsed " data-toggle="collapse" href="#settings">--%>
                <%--<i class="icon-cogs icon-2x"></i>--%>
                <%--<span>Settings<i class="icon-caret-down"></i></span>--%>
            <%--</a>--%>
            <%--<ul id="settings" class="collapse ">--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-cog"></i> Datasources</a></li>--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-group"></i> Permissions</a></li>--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-time"></i> Schedules</a></li>--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-shopping-cart"></i>--%>
                    <%--Marketplace</a></li>--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-envelope"></i> Mail</a></li>--%>
                <%--<li class=""><a data-toggle="modal" href="#modal-admin"><i class="icon-wrench"></i> System</a></li>--%>

            <%--</ul>--%>
        <%--</li>--%>
        <li class="">
            <span class="glow"></span>
            <a data-toggle="modal" href="#modal-logout">
                <i class="icon-signout icon-2x"></i>
                <span>退出</span>
            </a>
        </li>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#logoutButton').click(function () {
                    window.location.replace("${pageContext.request.contextPath}/logout");
                });
            });
        </script>
    </ul>
</div>