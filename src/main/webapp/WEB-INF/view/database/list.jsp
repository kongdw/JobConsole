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
                    <span class="breadcrumb-label"><i class="icon-list"></i> 数据源列表</span>
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
                    <div class="title">数据源列表</div>
                </div>
                <div class="col-sm-1">
                    <div class="btn-group-sm" style="padding:2px;">
                        <a type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus"
                           href="${pageContext.request.contextPath}/database/add"> 添加
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
                                <th>类型</th>
                                <th>主机IP</th>
                                <th>端口号</th>
                                <th>服务名</th>
                                <th>用户名</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${databaseList}" var="d">
                                <tr>
                                    <td><kbd>${d.name}</kbd></td>
                                    <td><kbd>${d.databaseType.type}</kbd></td>
                                    <td>${d.hostName}</td>
                                    <td>${d.port}</td>
                                    <td>${d.serverName}</td>
                                    <td>${d.username}</td>
                                    <td>
                                        <div class="btn-group">
                                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/database/edit?id=${d.id}">修改</a>
                                            <button class="btn btn-danger"
                                                    onclick="openModal('${pageContext.request.contextPath}/database/del?id=${d.id}','删除数据源？','被作业引用的数据源无法删除，数据源删除之后无法恢复，是否继续？')">
                                                删除
                                            </button>
                                            <button id="testDatabaseBtn" class="btn btn-warning"
                                                    onclick="testDatabase(${d.id})">测试
                                            </button>
                                            <p id="message${d.id}"></p>
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
<script type="text/javascript">
    function testDatabase(id) {
        $.ajax({
            type: "get",
            url: "${pageContext.request.contextPath}/database/test?id=" + id,
            beforeSend: function (XMLHttpRequest) {
                //ShowLoading();
            },
            success: function (data, textStatus) {
                $.each(data, function (idx, item) {
                    $("#message" + id)[0].innerHTML = "success"
                });

            },
            complete: function (XMLHttpRequest, textStatus) {
                //HideLoading();
            },
            error: function () {
                //请求出错处理
            }
        });
    }
</script>
<%@ include file="../include/bottom.jsp" %>
