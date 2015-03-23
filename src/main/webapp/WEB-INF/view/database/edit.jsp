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
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 修改数据源</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>修改数据源</div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
                <form class="form-horizontal" role="form" action="/database/edit" method="post">
                    <input type="hidden" name="id" value="${database.id}"/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">数据源名称</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="name" placeholder="数据源名称"
                                   value="${database.name}" required readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">数据库类型</label>

                        <div class="col-sm-5">
                            <select class="form-control" name="type" required>
                                <option>请选择...</option>
                                <c:forEach items="${databaseTypeList}" var="d">
                                    <option <c:if
                                            test="${d.type  eq database.databaseType.type}"> selected </c:if>value="${d.type}">${d.type}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主机IP</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="hostName" placeholder="主机IP"
                                   value="${database.hostName}" required>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">端口号</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="port" value="${database.port}"
                                   placeholder="端口号" required="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">服务名</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="serverName" value="${database.serverName}"
                                   placeholder="服务名" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="username" value="${database.username}"
                                   placeholder="用户名" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>

                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="password" value="${database.password}"
                                   placeholder="密码" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-success">提交</button>
                            <a type="button" class="btn btn-primary"
                               href="/database/list">
                                返回列表
                            </a>
                        </div>

                    </div>
                </form>
            </div>
        </div>
    </div>
    <div style="position:fixed;bottom:0px; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<%@ include file="../include/bottom.jsp" %>