<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/top.jsp" %>
<%@ include file="../include/left.jsp" %>
<div class="main-content">

    <%@ include file="../include/navigate.jsp" %>
    <div class="container">
        <div class="row clearfix ">

            <div class="col-md-12 last ">
                <div id="errorMsg" class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <p></p>
                </div>
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
                            <div class="title">作业列表</div>
                        </div>
                        <div class="col-sm-1">
                            <div class="btn-group-sm" style="padding:2px;">
                                <button type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus"
                                    id="dropdownMenu1" data-toggle="dropdown" > 新建
                                </button>

                                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1" href="/job/add?sqlType=i">新建[插入任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="/job/add?sqlType=u">新建[更新任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="/job/add?sqlType=d">新建[删除任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="/job/add?sqlType=c">新建[存储过程任务]</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="widget-box-content">
                        <div class="dataTables_wrapper form-inline" style="height: 300px; overflow:auto">
                            <div class="table-responsive">
                                <table id="jobTable" class="tableComponent table table-hover" width="100%">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>层级</th>
                                        <th>名称</th>
                                        <th>执行模式</th>
                                        <th>任务类型</th>
                                        <th>源数据库</th>
                                        <th>目标数据库</th>
                                        <th>目标表</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody >
                                    <c:forEach items="${list}" var="job" varStatus="idx">
                                        <c:if test="${job.enabled eq true}">
                                            <tr onclick="getParameter(${job.id})">
                                        </c:if>
                                        <c:if test="${job.enabled eq false}">
                                            <tr onclick="getParameter(${job.id})">
                                        </c:if>
                                        <td style="display: none">${job.id}</td>
                                        <td><kbd>${job.sequence}</kbd></td>
                                        <td><kbd>${job.layer.name}</kbd></td>
                                        <td>${job.name}</td>
                                        <td>${job.mode.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${job.sqlType == 'i'}">
                                                    抽入任务
                                                </c:when>
                                                <c:when test="${job.sqlType == 'u'}">
                                                    更新任务
                                                </c:when>
                                                <c:when test="${job.sqlType == 'd'}">
                                                    删除任务
                                                </c:when>
                                                <c:when test="${job.sqlType == 'c'}">
                                                    存储过程任务
                                                </c:when>
                                                <c:otherwise>
                                                    未设定
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td >${job.sourceDatabase.name}</td>
                                        <td >${job.targetDatabase.name}</td>
                                        <td >${job.targetTable}</td>
                                        <td >
                                            <div class="btn-group">
                                                <c:if test="${job.enabled eq true}">
                                                    <a class="btn btn-success"
                                                       href="/job/enable?jobId=${job.id}&page=${page}">禁用</a>
                                                </c:if>
                                                <c:if test="${job.enabled eq false}">
                                                    <a class="btn btn-warning"
                                                       href="/job/enable?jobId=${job.id}&page=${page}">启用</a>
                                                </c:if>
                                                <a class="btn btn-primary"
                                                   href="/job/edit?jobId=${job.id}&sqlType=${job.sqlType}&page=${page}">修改</a>
                                                <button type="button"
                                                        class="btn btn-danger"
                                                        onclick="openModal('/job/del?jobId=${job.id}&page=${page}','删除作业?','删除的作业将无法恢复，是否继续?')">删除
                                                </button>
                                                <button type="button" class="btn btn-warning <c:if test="${job.enabled eq false}"> disabled  </c:if>"
                                                   onclick="openModal('/queue/add?jobId=${job.id}&page=${page}','执行作业？','作业会被立即提交到等待队列，是否继？')">提交</button>
                                            </div>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row clearfix " align="center">
                            <div class="col-md-12">
                                <nav>
                                    <ul class="pagination pagination-sm">

                                    </ul>
                                </nav>
                            </div>
                        </div>

                    </div>


                </div>

                <div class="widget-box">
                    <div class="widget-box-header">
                        <div class="col-sm-2">
                            <div class="title">参数列表</div>
                        </div>

                         <div class="col-sm-10" align="right" style="margin-top: 2px;" >
                                <div class="btn-group">
                                    <button type="button" onclick="addRow()"
                                            class="btn btn-sm btn-primary">添加
                                    </button>
                                    <button type="button" onclick="delRow()"
                                            class="btn btn-sm btn-danger"><span>删除</span>
                                    </button>
                                    <button type="button" onclick="saveRow()"
                                            class="btn btn-sm btn-success">保存
                                    </button>
                                </div>

                         </div>


                    </div>
                    <div class="widget-box-content" style="padding:15px;">


                        <div class="dataTables_wrapper form-inline" style="height: 200px; overflow:auto">
                            <div class="table-responsive">
                                <table id="paramTable" class="tableComponent table table-hover">
                                    <thead>
                                    <tr>
                                        <th>作用域</th>
                                        <th>名称</th>
                                        <th>类型</th>
                                        <th>默认值</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
    <script type="text/javascript">

        //        $(document).ready(function () {
        //            $('#delJob').click(function () {
        //                var jobid = $('#jobTable').find('.selected').find("td").first().text();
        //                window.location.replace("/job/del?jobId=" + jobid);
        //            });
        //        });
        $('.pagination').twbsPagination({
            totalPages: ${pageInfo.pages},
            visiblePages: 5,
            href: '?page={{number}}'
        });
    </script>


    <div style="position:fixed;bottom:0; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<%@ include file="../include/bottom.jsp" %>
