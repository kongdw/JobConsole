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
                    <span class="breadcrumb-label"><i class="icon-list"></i> 用户列表</span>
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
        <div class="row clearfix ">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div class="widget-box-header">
                        <div class="col-sm-1">
                            <div class="title">用户列表</div>
                        </div>
                        <div class="col-sm-1">
                            <div class="btn-group-sm" style="padding:2px;">
                                <a type="button" class="btn btn-default btn-sm glyphicon glyphicon-plus" href="/user/add"> 添加
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="widget-box-content" style="padding:15px;">
                        <div class="dataTables_wrapper form-inline">
                            <div class="table-responsive">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>用户名称</th>
                                            <th>密码</th>
                                            <th>邮箱</th>
                                            <th>电话</th>
                                            <th>启用状态</th>
                                            <th>角色</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${userList}" var="user">
                                            <tr>
                                                <td>${user.username}</td>
                                                <td>${user.password}</td>
                                                <td>${user.email}</td>
                                                <td>${user.phone}</td>
                                                <td>
                                                    <c:if test="${user.enabled}" var="enabled">
                                                        启用
                                                    </c:if>
                                                    <c:if test="${!user.enabled}" var="enabled">
                                                        未启用
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${user.roleType == '1'}">
                                                            管理员
                                                        </c:when>
                                                        <c:when test="${user.roleType == '2'}">
                                                            普通用户
                                                        </c:when>
                                                        <c:otherwise>
                                                            未设定角色
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <div class="btn-group">
                                                        <a type="button" class="btn btn-primary"
                                                           href="/user/edit?id=${user.id}">
                                                            修改
                                                        </a>
                                                        <button type="button" class="btn btn-danger"
                                                                onclick="openModal('/user/del?id=${user.id}','删除用户？','确定要删除当前选中用户吗？')">
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
            </div>
        </div>
        <div class="row clearfix ">
            <div class="col-md-4">
            </div>
            <div class="col-md-4">
                <nav>
                    <ul class="pagination pagination-sm">
                    </ul>
                </nav>
            </div>
        </div>
        <div style="position:fixed;bottom:0px; width:100%;" align="center">
            <%@ include file="../include/footer.jsp" %>
        </div>
    </div>

<style type="text/css">
    .line{margin-bottom:20px;}
    /* 复制提示 */
    .copy-tips{position:fixed;z-index:999;bottom:50%;left:50%;margin:0 0 -20px -80px;background-color:rgba(0, 0, 0, 0.2);filter:progid:DXImageTransform.Microsoft.Gradient(startColorstr=#30000000, endColorstr=#30000000);padding:6px;}
    .copy-tips-wrap{padding:10px 20px;text-align:center;border:1px solid #F4D9A6;background-color:#FFFDEE;font-size:14px;}
</style>

<script type="text/javascript">

    function UpdateUserPost()
    {

    }

    $('.pagination').twbsPagination({
        totalPages: ${pageInfo.pages},
        visiblePages: 5,
        href: '?page={{number}}'
    });
</script>
<script type="text/javascript">
    function sdfs()
    {
        var test = $("#hehe").val();
        alert(test);
    }
    $(function () {
        /* 定义所有class为copy标签，点击后可复制被点击对象的文本 */
        $(".copy").zclip({
            path: "/res/js/ZeroClipboard.swf",
            copy: function(){
                return $(this).text();
            },
            beforeCopy:function(){/* 按住鼠标时的操作 */
                $(this).css("color","orange");
            },
            afterCopy:function(){/* 复制成功后的操作 */
                var $copysuc = $("<div class='copy-tips'><div class='copy-tips-wrap'>☺ 复制成功</div></div>");
                $("body").find(".copy-tips").remove().end().append($copysuc);
                $(".copy-tips").fadeOut(3000);
            }
        });
        /* 定义所有class为copy-input标签，点击后可复制class为input的文本 */
        $(".copy-input").zclip({
            path: "/res/js/ZeroClipboard.swf",
            copy: function(){
                return $(this).parent().find(".input").val();
            },
            afterCopy:function(){/* 复制成功后的操作 */
                var $copysuc = $("<div class='copy-tips'><div class='copy-tips-wrap'>☺ 复制成功</div></div>");
                $("body").find(".copy-tips").remove().end().append($copysuc);
                $(".copy-tips").fadeOut(3000);
            }
        });
    });
</script>
<%@ include file="../include/bottom.jsp" %>