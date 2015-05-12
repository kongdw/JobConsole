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
                    <span class="breadcrumb-label"><i class="icon-list"></i> 层级列表</span>
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
                    <div class="title">层级列表</div>
                </div>
                <div class="col-sm-1">
                    <div class="btn-group-sm" style="padding:2px;">
                        <a type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus"
                           href="${pageContext.request.contextPath}/layer/add"> 添加
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
                                <th>执行顺序</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${layerList}" var="layer">
                                <tr>
                                    <td><kbd>${layer.name}</kbd></td>
                                    <td><kbd>${layer.sequence}</kbd></td>
                                    <td>
                                        <div class="btn-group">
                                            <a type="button" class="btn btn-primary"
                                               href="${pageContext.request.contextPath}/layer/edit?id=${layer.id}">
                                                修改
                                            </a>
                                            <button type="button" class="btn btn-danger"
                                                    onclick="openModal('${pageContext.request.contextPath}/layer/del?id=${layer.id}','删除层级？','确定要删除当前选中层级吗？')">
                                                删除
                                            </button>
                                            <button type="button" class="btn btn-warning"
                                                    onclick="openModal('${pageContext.request.contextPath}/queue/start/${layer.id}','执行层级下所有作业？','确定要执行该层级下的所有作业吗？')">
                                                执行
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