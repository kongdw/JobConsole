<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/top.jsp" %>
<%@ include file="../include/left.jsp" %>
<div class="main-content">

    <div class="container padded">
        <div class="row clearfix ">
            <!-- Breadcrumb line -->
            <div id="breadcrumbs">
                <div class="breadcrumb-button blue">
                    <span class="breadcrumb-label"><i class="icon-home"></i> 主页</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
                <div class="breadcrumb-button">
                    <span class="breadcrumb-label"><i class="icon-list"></i> 日志列表</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
            <div class="widget-box">
                <div class="widget-box-header">
                    <div class="col-sm-1">
                        <div class="title">日志列表</div>
                    </div>
                </div>
                <div class="widget-box-content" style="padding:15px;">
                    <div class="dataTables_wrapper form-inline">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>作业名称</th>
                                    <th>执行用户</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>执行状态</th>
                                    <th>插入条数</th>
                                    <th>查看日志</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="bean">
                                    <tr>
                                        <td><kbd>${bean.jobName}</kbd></td>
                                        <td><kbd>${bean.username}</kbd></td>

                                        <td><fmt:formatDate value="${bean.beginTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>${bean.status}</td>
                                        <td>${bean.nbLine}</td>
                                        <td>
                                            <button type="button" class="btn btn-default" onclick="showMessage(this)"
                                                    data-container="body" data-placement="left"
                                                    data-content="${bean.message}">查看详细
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </div>
        <div class="row clearfix ">
            <div class="col-md-12" align="center">
                <nav>
                    <ul class="pagination pagination-sm">
                    </ul>
                </nav>
            </div>
        </div>
    </div>
    <div style="position:fixed;bottom:0px; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<script type="text/javascript">
    //    $('#help').popover({
    //        html: true,
    //        placement: 'right',
    //        trigger: 'hover',
    //        title: '输出格式',
    //        content: 'html格式下标签会解析; &nbsp; text格式则原样显示，text格式输出\t和\n可进行Tab缩进和换行。'
    //    })
    function showMessage(obj) {
        $(obj).popover({delay: { "show": 0, "hide": 0 }, html: true});
        //$(obj).popover('hide');
    }
    $('.pagination').twbsPagination({
        totalPages: ${pageInfo.pages},
        visiblePages: 5,
        href: '/log/list/{{number}}'
    });
</script>
<%@ include file="../include/bottom.jsp" %>