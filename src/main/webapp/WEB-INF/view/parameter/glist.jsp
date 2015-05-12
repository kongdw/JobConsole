<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/top.jsp" %>
<%@ include file="../include/left.jsp" %>
<div class="main-content">

    <%@ include file="../include/navigate.jsp" %>
    <div class="container">
        <div class="row clearfix ">
            <div class="col-md-12 last ">
                <div class="widget-box">
                    <div class="widget-box-header">
                        <div class="col-sm-1">
                            <div class="title">参数列表</div>
                        </div>
                        <div class="col-sm-1">
                            <div class="btn-group-sm" style="padding:2px;">
                                <a type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus"
                                   href="${pageContext.request.contextPath}/parameter/add"> 添加
                                </a>
                            </div>
                        </div>
                    </div>
                    <c:if test="${error != null }">
                        <div id="loginError" class="alert alert-danger alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                            <strong>异常:</strong> ${error}
                        </div>
                    </c:if>
                    <div class="widget-box-content" style="padding:15px;">
                        <div class="dataTables_wrapper form-inline">
                            <div class="table-responsive">
                                <table id="jobTable" class="tableComponent table table-hover" width="100%">
                                    <thead>
                                    <tr>
                                        <th>名称</th>
                                        <%--<th>数据源</th>--%>
                                        <%--<th>SQL脚本</th>--%>
                                        <th>返回值</th>
                                        <th>缺省值</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${parameterRefList}" var="list" varStatus="idx">
                                        <tr>
                                            <td><kbd>${list.refName}</kbd></td>
                                                <%--<c:if test="${list.database.name eq null}">--%>
                                                <%--<td>无</td>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${list.database.name != null}">--%>
                                                <%--<td>${list.database.name}</td>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${list.sqlScript eq null || list.sqlScript eq ''}">--%>
                                                <%--<td>无</td>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${list.sqlScript != null && list.sqlScript != '' }">--%>
                                                <%--<td>${list.sqlScript}</td>--%>
                                                <%--</c:if>--%>
                                            <c:if test="${list.returnType.description eq null}">
                                                <td><kbd>无</kbd></td>
                                            </c:if>
                                            <c:if test="${list.returnType.description != null}">
                                                <td><kbd>${list.returnType.description}</kbd></td>
                                            </c:if>
                                            <c:if test="${list.defaultVal eq null}">
                                                <td>无</td>
                                            </c:if>
                                            <c:if test="${list.defaultVal != null}">
                                                <td>${list.defaultVal}</td>
                                            </c:if>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/parameter/edit?id=${list.id}">
                                                        <button type="submit" class="btn btn-primary">修改</button>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/parameter/del?id=${list.id}">
                                                        <button type="submit" class="btn btn-danger">删除</button>
                                                    </a>
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
        </div>
    </div>
    <div id="modal-logout" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">退出系统?</h4>
                </div>
                <div class="modal-body">
                    <p>确认对出系统吗?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                    <button id="logoutButton" type="button" class="btn btn-blue">是</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <div style="position:fixed;bottom:0px; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<%@ include file="../include/bottom.jsp" %>