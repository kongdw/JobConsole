<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/top.jsp" %>
<%@ include file="../include/left.jsp" %>
<div class="main-content">

    <div class="container padded">
        <div class="row">
            <!-- Breadcrumb line -->
            <div id="breadcrumbs">
                <div class="breadcrumb-button blue">
                    <span class="breadcrumb-label"><i class="icon-home"></i> 主页</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
                <div class="breadcrumb-button">
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 修改用户</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>修改用户</div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
            <c:if test="${not empty message}">
                <div class="alert alert-danger" role="alert">${message}</div>
            </c:if>
            <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user/edit" method="post">
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名称:</label>

                    <div class="col-sm-5">
                        <c:if test="${user.roleType eq '1'}">
                        <input type="text" class="form-control" name="username" placeholder="用户名称" data-toggle="tooltip"
                               data-placement="left" value="${user.username}" required>
                        </c:if>
                        <c:if test="${user.roleType eq '2'}">
                            <input type="text" class="form-control" name="username" placeholder="用户名称" data-toggle="tooltip"
                                   data-placement="left" value="${user.username}" required readonly>
                        </c:if>
                    </div>
                </div>
                <c:if test="${user.roleType eq '1'}">
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码:</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="password" placeholder="密码" value="${user.password}">
                    </div>

                </div>
                </c:if>
                <div class="form-group">
                    <label class="col-sm-2 control-label">邮箱:</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="email" placeholder="邮箱"  value="${user.email}" required>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">联系电话:</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="phone" placeholder="联系电话"  value="${user.phone}" required>
                    </div>

                </div>
                <c:if test="${user.roleType eq '1'}">
                <div class="form-group">
                    <label class="col-sm-2 control-label">角色:</label>

                    <div class="col-sm-5">
                        <select class="form-control" name="roleType" required>
                            <option value="">请选择...</option>
                                <option value="1" <c:if test="${user.roleType == '1'}"> selected </c:if>>管理员</option>

                            <option value="2" <c:if test="${user.roleType == '2'}"> selected </c:if>>普通用户</option>

                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        启用状态:
                    </label>
                    <div class="col-sm-5 checkbox">
                        <input  name="enabled" type="checkbox"  <c:if test="${user.enabled}"> checked </c:if>  >
                    </div>
                </div>
                </c:if>
                <div class="form-group">
                    <label class="col-sm-2 control-label"></label>

                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-success">提交</button>
<c:if test="${user.roleType eq '1'}">
                        <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/user/list/1">
                            返回列表
                        </a>
    </c:if>
                        <c:if test="${user.roleType eq '2'}">
                            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/main">
                                返回主页
                            </a>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </div>
        <div style="position:fixed;bottom:0px; width:100%;" align="center">
            <%@ include file="../include/footer.jsp" %>
        </div>
</div>


<%@ include file="../include/bottom.jsp" %>