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
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 新建数据源类型</span>
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
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>新建数据源类型</div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
                <c:if test="${not empty message}">
                    <div class="alert alert-danger" role="alert">${message}</div>
                </c:if>
                <form class="form-horizontal" role="form" action="/databasetype/add" method="post">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">类型名称</label>

                        <div class="col-xs-2">
                            <input type="text" class="form-control" name="type" placeholder="类型名称"
                                   required>

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">描述</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="description" placeholder="描述" data-toggle="tooltip"
                               data-placement="left">
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">路径</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="url" placeholder="URL" data-toggle="tooltip"
                                  data-placement="left" required>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">DRIVER</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="driver" placeholder="DRIVER" data-toggle="tooltip"
                                     data-placement="left" required>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-success">提交</button>
                            <a type="button" class="btn btn-primary"
                               href="/databasetype/list">
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