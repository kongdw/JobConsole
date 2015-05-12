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
                    <span class="breadcrumb-label"><i class="icon-list"></i> 数据源类型列表</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">

        <c:if test="${error != null }">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>异常:</strong> ${error}
            </div>
        </c:if>
        <c:if test="${msg != null }">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>提示:</strong> ${msg}
            </div>
        </c:if>
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-1">
                    <div class="title">数据源类型列表</div>
                </div>
                <div class="col-sm-1">
                    <div class="btn-group-sm" style="padding:2px;">
                        <a type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus"
                           href="${pageContext.request.contextPath}/databasetype/add"> 添加
                        </a>
                    </div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
                <div class="dataTables_wrapper form-inline">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>描述</th>
                                <th>URL</th>
                                <th>Driver</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${databasetypeList}" var="d">
                                <tr>
                                    <td><kbd>${d.type}</kbd></td>
                                    <td><kbd>${d.description}</kbd></td>
                                    <td>${d.url}</td>
                                    <td>${d.driver}</td>
                                    <td>
                                        <div class="btn-group">
                                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/databasetype/edit?type=${d.type}">修改</a>
                                            <button class="btn btn-danger"
                                                    onclick="openModal('/databasetype/del?type=${d.type}','删除数据源类型？','被数据源引用的类型无法删除，类型删除之后无法恢复，是否继续？')">
                                                删除
                                            </button>
                                        </div>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="position:fixed;bottom:0px; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<%@ include file="../include/bottom.jsp" %>
