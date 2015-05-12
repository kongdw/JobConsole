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
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 修改层级</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>修改层级</div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">

                <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/layer/edit" method="post">
                    <input type="hidden" name="id" value="${layer.id}"/>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">顺序号</label>

                        <div class="col-xs-2">
                            <input type="text" class="form-control" name="sequence" placeholder="执行顺序号"
                                   value="${layer.sequence}" readonly required>

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">层级名称</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="name" placeholder="层级名称" data-toggle="tooltip"
                                   data-placement="left" value="${layer.name}" required>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-success">提交</button>
                            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/layer/list">
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