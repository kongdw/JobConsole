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
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 新建参数</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>新建参数</div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
            <c:if test="${error != null }">
                <div id="loginError" class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong>异常:</strong> ${error}
                </div>
            </c:if>
            <form class="form-horizontal" role="form" action="/parameter/add" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">参数名</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="refName" placeholder="参数名称" data-toggle="tooltip"
                               data-placement="left" required="">
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">返回值类型</label>

                    <div class="col-sm-5">
                        <select class="form-control" name="returnType" required="">
                            <option value="">请选择...</option>
                            <c:forEach items="${parameterTypeList}" var="parameterType">
                                <option value="${parameterType.code}">${parameterType.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">缺省值</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="defaultVal" placeholder="缺省值"
                               data-toggle="tooltip" data-placement="left" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"></label>

                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-success">提交</button>
                        <a type="button" class="btn btn-primary"  href="/parameter/glist">
                            返回列表
                        </a>
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