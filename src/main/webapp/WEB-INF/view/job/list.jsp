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
                                        <a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/job/add?sqlType=i">新建[插入任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="${pageContext.request.contextPath}/job/add?sqlType=u">新建[更新任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="${pageContext.request.contextPath}/job/add?sqlType=d">新建[删除任务]</a>
                                    </li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"  href="${pageContext.request.contextPath}/job/add?sqlType=c">新建[存储过程任务]</a>
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
                                                       href="${pageContext.request.contextPath}/job/enable?jobId=${job.id}&page=${page}">禁用</a>
                                                </c:if>
                                                <c:if test="${job.enabled eq false}">
                                                    <a class="btn btn-warning"
                                                       href="${pageContext.request.contextPath}/job/enable?jobId=${job.id}&page=${page}">启用</a>
                                                </c:if>
                                                <a class="btn btn-primary"
                                                   href="${pageContext.request.contextPath}/job/edit?jobId=${job.id}&sqlType=${job.sqlType}&page=${page}">修改</a>
                                                <button type="button"
                                                        class="btn btn-danger"
                                                        onclick="openModal('${pageContext.request.contextPath}/job/del?jobId=${job.id}&page=${page}','删除作业?','删除的作业将无法恢复，是否继续?')">删除
                                                </button>
                                                <button type="button" class="btn btn-warning <c:if test="${job.enabled eq false}"> disabled  </c:if>"
                                                   onclick="openModal('${pageContext.request.contextPath}/queue/add?jobId=${job.id}&page=${page}','执行作业？','作业会被立即提交到等待队列，是否继？')">提交</button>
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
        var paramTableCount = 0;    //  表格的行数
        var selectHtml; //类型下拉列表
        var paramRefHtml;

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
        $(document).ready(function(){
            getParamRef();
            getParamType();
        });
    </script>

    <script type="text/javascript">


        /**
         *  修改下拉列表
         * @param obj
         */
        function selectChange(obj) {
            var typeStr = $(obj).find('option:selected').val();
            if (typeStr.indexOf('DATE') >= 0) {
                $(obj).parent().parent().find('input[name=parameterValue]').attr('type', 'date');
            } else {
                $(obj).parent().parent().find('input[name=parameterValue]').attr('type', 'text');
            }
        }
        function getParamRef() {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/parameter/paramRefAjax",
                beforeSend: function (XMLHttpRequest) {
                    //ShowLoading();
                },
                success: function (data, textStatus) {
                    var select = $("<select></select>");
                    var defaultOption = $("<option value=''></option>");
                    defaultOption.append("请选择...");
                    select.append(defaultOption);
                    $.each(data, function (idx, item) {
                        var option = $("<option></option>");
                        option.attr('value', item.refName);
                        option.append(item.refName);
                        select.append(option);
                    });
                    paramRefHtml =  select;
                },
                complete: function (XMLHttpRequest, textStatus) {
                    //HideLoading();
                },
                error: function () {
                    //请求出错处理
                }
            });
        }
        function getParamType() {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/parameter/typeList",
                beforeSend: function (XMLHttpRequest) {
                    //ShowLoading();
                },
                success: function (data, textStatus) {
                    var select = $("<select></select>");
                    var defaultOption = $("<option></option>");
                    defaultOption.append("请选择...");
                    defaultOption.attr('value', '');
                    select.append(defaultOption);
                    $.each(data, function (idx, item) {
                        var option = $("<option></option>");
                        option.attr('value', item.code);
                        option.append(item.description);
                        select.append(option);
                    });
                    selectHtml =  select;
                },
                complete: function (XMLHttpRequest, textStatus) {
                    //HideLoading();
                },
                error: function () {
                    //请求出错处理
                }
            });
        }

        function addRow(obj) {
            var jobId = $('#jobTable').find('.selected').find("td").first().text();

            if (jobId == null || jobId  == "") {
                alert("请选择一个作业。");
                return;
            }
            var scope = 'LOCAL';
            var type = 'DATE';
            if (obj != null) {
                scope = obj.scope;
                if (scope == 'LOCAL') {
                    type = obj.parameterType.code;
                } else {
                    type = obj.parameterRef.returnType.code;
                }
            }
            var paramTable = $('#paramTable');
            var row = rowTemplate(scope, type);
            paramTable.append(row);
            if (obj != null) {
                $("#param_" + paramTableCount).find("td").each(function () {
                    if (obj.scope == "GLOBAL") {

                        $(this).find('select[name=parameterRef]').val(obj.parameterRef.refName);
                        $(this).find('select[name=parameterType]').val(obj.parameterRef.returnType.code);
                        $(this).find('input[name=parameterValue]').val(obj.parameterRef.defaultVal);
                    } else if (obj.scope == 'LOCAL') {
                        $(this).find('input[name=parameterName]').val(obj.parameterName);
                        $(this).find('select[name=parameterType]').val(obj.parameterType.code);
                        $(this).find('input[name=parameterValue]').val(obj.parameterValue);
                    }
                    $(this).find('select[name=scope]').val(obj.scope);
                });
            }
            paramTableCount++;
        }

        function rowTemplate(scope, type) {

            var row = $("<tr id=param_" + paramTableCount + "></tr>");
            row.append($("<td ><select  name='scope' class='form-control col-xs-2' onchange='scopeChange(this)'><option value='LOCAL'>LOCAL</option> <option value='GLOBAL'>GLOBAL</option></select></td>"));
            if (scope == 'LOCAL') {
                row.append($("<td   ><input  name='parameterName' type='text' class='form-control col-xs-2' placeholder='名称'></td>"));
                row.append($("<td   ><select  name='parameterType' onchange='selectChange(this)'  class='form-control col-xs-2'>" + selectHtml.html()+ "</select></td>"));
                if (type.indexOf('DATE') >= 0) {
                    row.append($("<td ><input   name='parameterValue' type='date' class='form-control col-xs-2' placeholder='默认值'></td>"));
                } else {
                    row.append($("<td ><input  name='parameterValue' type='text' class='form-control col-xs-2' placeholder='默认值'></td>"));
                }
            } else if (scope == 'GLOBAL') {
                row.append($("<td  ><select  name='parameterRef' onchange='paramRefChange(this)' class='form-control col-xs-2'>" + paramRefHtml.html() + "</select></td>"));
                row.append($("<td  ><select  name='parameterType' onchange='selectChange(this)'  class='form-control col-xs-2' disabled>" + selectHtml.html() + "</select></td>"));
                if (type.indexOf('DATE') >= 0) {
                    row.append($("<td  ><input   name='parameterValue' type='date' class='form-control col-xs-2' placeholder='默认值' disabled></td>"));
                } else {
                    row.append($("<td   ><input  name='parameterValue' type='text' class='form-control col-xs-2' placeholder='默认值' disabled></td>"));
                }
            }


            return row;

        }

        function rowTemplateBySqlScript(paramname) {
            var row = $("<tr id=param_" + paramTableCount + "></tr>");
            row.append($("<td ><select  name='scope' class='form-control col-xs-2' onchange='scopeChange(this)'><option value='LOCAL'>LOCAL</option> <option value='GLOBAL'>GLOBAL</option></select></td>"));

            row.append($("<td><input  name='parameterName' type='text' class='form-control col-xs-2' placeholder='名称' value='"+paramname+"'></td>"));
            row.append($("<td><select  name='parameterType' onchange='selectChange(this)'  class='form-control col-xs-2'>" + selectHtml.html() + "</select></td>"));
            row.append($("<td ><input   name='parameterValue' type='date' class='form-control col-xs-2' placeholder='默认值'></td>"));


            return row;

        }

        function delRow() {
            $('#paramTable').find('.selected').first().remove();
            paramTableCount--;
        }

        function saveRow() {
            var paramData = getTableData("paramTable");
            var jobId = $('#jobTable').find('.selected').find("td").first().text();
            if (jobId == null || jobId == "") {
                alert("请选择一个作业。");
                return;
            }

            $.ajax({
                type: "POST",
                contentType: 'application/json',
                url: "${pageContext.request.contextPath}/parameter/save/" + jobId,
                data: JSON.stringify(paramData),
                dataType: 'json',
                complete: function (XMLHttpRequest, textStatus) {
                    $('#errorMsg').find('p').text(XMLHttpRequest.responseText);
                    $('#errorMsg').attr('style','');
                    // alert(XMLHttpRequest.responseText);
                }
            });

        }
        function getParameter(jobId) {
            paramTableCount = 0;
            var HaveParams = 0;
            $.ajax({

                type: "get",
                url: "${pageContext.request.contextPath}/parameter/list?jobId=" + jobId,
                beforeSend: function (XMLHttpRequest) {
                    //ShowLoading();
                },
                success: function (data, textStatus) {
                    $("#paramTable").find('tbody').html("");
                    $.each(data, function (idx, item) {
                        HaveParams = 1;
                        addRow(item);
                    });

                    //如果参数表中为空,则去sql语句中解析参数,得到参数名称
                    if(HaveParams == 0)
                    {
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/job/getjobparams?jobId=" + jobId,
                            beforeSend: function (XMLHttpRequest) {
                                //ShowLoading();
                            },
                            success: function (data, textStatus) {
                                $("#paramTable").find('tbody').html("");
                                var paramTable = $('#paramTable');
                                $.each(data, function (idx, item) {
                                    var row = rowTemplateBySqlScript(item);
                                    paramTable.append(row);
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
                },
                complete: function (XMLHttpRequest, textStatus) {
                    //HideLoading();
                },
                error: function () {
                    //请求出错处理
                }
            });



        }

        function paramRefChange(obj) {
            var name = $(obj).find('option:selected').val();
            if (name == null || name == "") {
                $(obj).parent().parent().find('select[name=parameterType]').val('');
                $(obj).parent().parent().find('select[name=parameterType]').removeAttr('disabled');
                $(obj).parent().parent().find('input[name=parameterValue]').val('');
                $(obj).parent().parent().find('input[name=parameterValue]').removeAttr('disabled');
                return;
            }
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/parameter/paramRefByNameAjax?name=" + name,
                beforeSend: function (XMLHttpRequest) {
                    //ShowLoading();
                },
                success: function (data, textStatus) {
                    var parameterType = $(obj).parent().parent().find('select[name=parameterType]');
                    parameterType.val(data.returnType.code);
                    parameterType.attr('disabled', 'disabled');
                    var parameterVal = $(obj).parent().parent().find('input[name=parameterValue]');
                    if (data.returnType.code == 'DATE') {
                        parameterVal.attr('type', 'date');
                    }
                    parameterVal.val(data.defaultVal);
                    parameterVal.attr('disabled', 'disabled')
                },
                complete: function (XMLHttpRequest, textStatus) {
                    //HideLoading();
                },
                error: function () {
                    //请求出错处理
                }
            });
        }
        function scopeChange(obj) {
            var typeStr = $(obj).find('option:selected').val();
            var i = 0;
            $(obj).parent().parent().find('input[name=parameterName]').remove();
            $(obj).parent().parent().find('select[name=parameterRef]').remove();
            if (typeStr.indexOf('GLOBAL') >= 0) {

                $(obj).parent().parent().find('td').each(function () {
                    i++;
                    if (i == 2) {
                        $(this).append($("<select name='parameterRef' onchange='paramRefChange(this)' class='form-control col-xs-2'>" + paramRefHtml.html() + "</select>"));
                    }
                });
            } else if (typeStr.indexOf('LOCAL') >= 0) {
                $(obj).parent().parent().find('td').each(function () {
                    i++;
                    if (i == 2) {
                        $(this).append($("<input name='parameterName' type='text' class='form-control col-xs-2' placeholder='名称'>"));
                    }
                });
                $(obj).parent().parent().find('select[name=parameterType]').val('');
                $(obj).parent().parent().find('select[name=parameterType]').removeAttr('disabled');
                $(obj).parent().parent().find('input[name=parameterValue]').val('');
                $(obj).parent().parent().find('input[name=parameterValue]').removeAttr('disabled');
            } else {
                return;
            }
        }
    </script>
    <div style="position:fixed;bottom:0; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<%@ include file="../include/bottom.jsp" %>
