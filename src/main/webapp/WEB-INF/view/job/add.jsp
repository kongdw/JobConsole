<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/top.jsp" %>
<%@ include file="../include/left.jsp" %>
<style type="text/css" media="screen">
    .ace_editor {
        position: relative !important;
        border: 1px solid lightgray;
        margin: auto;
        height: 200px;
        width: 100%;
    }

</style>
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
                    <span class="breadcrumb-label"><i class="icon-edit"></i> 新建作业</span>
                    <span class="breadcrumb-arrow"><span></span></span>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="col-sm-2">
                    <div class="title"><i class="icon-edit">&nbsp;</i>
                        新建
                            <c:if test="${sqlType eq 'i'}">插入</c:if>
                            <c:if test="${sqlType eq 'u'}">更新</c:if>
                            <c:if test="${sqlType eq 'd'}">删除</c:if>
                            <c:if test="${sqlType eq 'c'}">存储过程</c:if>
                        作业
                    </div>
                </div>
            </div>
            <div class="widget-box-content" style="padding:10px;">
                <c:if test="${error != null }">
                    <div id="loginError" class="alert alert-danger alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <strong>异常</strong> ${error}
                    </div>
                </c:if>
                <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/job/add" method="post">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">作业名称</label>

                        <div class="col-sm-5">
                            <input id="txtJobName" type="text" class="form-control" name="jobName" placeholder="作业名称"
                                   data-toggle="tooltip" data-placement="left" title="为作业起一个方便辨识的名称。"  value="${bean.jobName}">

                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spanJobName"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">顺序号</label>

                        <div class="col-xs-2">
                            <input id="txtSequence" type="text" class="form-control" name="sequence" placeholder="执行顺序号"
                                   value="${bean.sequence}">
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spanSequence"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">层级</label>

                        <div class="col-sm-5">
                            <select id="selLayer" class="form-control" name="layerId">
                                <option value="-1">请选择...</option>
                                <c:forEach items="${layerList}" var="layer">
                                    <option
                                            <c:if test="${layer.id == bean.layerId}">selected</c:if>
                                            value="${layer.id}">${layer.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spanLayer"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">执行模式</label>

                        <div class="col-sm-5">
                            <select id="selModeCode" class="form-control" name="modeCode">
                                <option value="">请选择...</option>
                                <c:forEach items="${modeList}" var="c">
                                    <option
                                            <c:if test="${c.code == bean.modeCode}">selected</c:if>
                                            value="${c.code}">${c.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spanModeCode"></span>
                        </div>
                    </div>
                    <c:if test="${sqlType eq 'i'}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">
                                抽取前先清除数据
                            </label>

                            <div class="col-sm-5 checkbox">
                                <input name="truncateFlag" type="checkbox"
                                <c:if test="${bean.truncateFlag}"> checked </c:if> ">
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group" id="divShowSplitValue" style="display: none">
                        <label class="col-xs-2 control-label">拆分作业阈值</label>

                        <div class="col-xs-2">
                            <input id="splitValue" type="text" class="form-control" name="nosplitValue"
                                   placeholder="拆分作业阈值" value="${bean.splitValue}">
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spansplitValue"></span>
                        </div>
                    </div>

                    <c:if test="${sqlType eq 'i'}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">目标表</label>

                            <div class="col-sm-5">
                                <input id="targetTable" type="text" class="form-control" name="targetTable"
                                       placeholder="目标表" value="${bean.targetTable}">
                            </div>
                            <div style="margin-top: 5px;">
                                <span style="color:red" id="spantargetTable"></span>
                            </div>
                        </div>

                    </c:if>
                    <c:if test="${sqlType eq 'i'}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">源数据库</label>

                            <div class="col-sm-5">
                                <select id="selsourceDatabaseId" class="form-control" name="sourceDatabaseId">
                                    <option value="-1">请选择...</option>
                                    <c:forEach items="${databaseList}" var="c">
                                        <option
                                                <c:if test="${c.id == bean.sourceDatabaseId}">selected</c:if>
                                                value="${c.id}">${c.name}(${c.username})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div style="margin-top: 5px;">
                                <span style="color:red" id="spansourceDatabaseId"></span>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">目标数据库</label>

                        <div class="col-sm-5">
                            <select id="targetDatabaseId" class="form-control" name="targetDatabaseId">
                                <option value="">请选择...</option>
                                <c:forEach items="${databaseList}" var="c">
                                    <option
                                            <c:if test="${c.id == bean.targetDatabaseId}">selected</c:if>
                                            value="${c.id}">${c.name}(${c.username})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spantargetDatabaseId"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">SQL脚本</label>

                        <div class="col-sm-5">
                            <pre id="editor"></pre>
                        </div>
                        <div style="margin-top: 5px;">
                            <span style="color:red" id="spaneditor"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>

                        <div class="col-sm-5">
                            <textarea class="form-control" rows="5" name="description"
                                      placeholder="备注">${bean.description}</textarea>
                        </div>
                    </div>
                    <input id="sqlType" type="hidden" class="form-control" name="sqlType"
                           value="${sqlType}">
                    <input id="sqlScript" type="hidden" class="form-control" name="sqlScript"
                           value="">

                    <%--<div class="form-group">--%>
                    <%--<label class="col-sm-2 control-label">sqlType</label>--%>

                    <%--<div class="col-sm-5">--%>
                    <%----%>
                    <%--</div>--%>

                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-success" onclick="return CheckValue()">提交</button>
                            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/job/list?page=1">
                                返回列表
                            </a>
                        </div>
                    </div>
                </form>
            </div>

        </div>

    </div>
    <div style="position:fixed;bottom:0; width:100%;" align="center">
        <%@ include file="../include/footer.jsp" %>
    </div>
</div>
<script type="text/javascript">

    function modeChange(obj) {
        //ShowSplitValue();
        $('#splitValue').attr("disabled", false);
        $('#targetTable').attr("disabled", false);
        $('#targetDatabaseId').attr("disabled", false);
        var modeType = $(obj).find('option:selected').val();
        if (modeType == 'TASK_LIST' || modeType == 'TIME_PERIOD') {
            $('#splitValue').attr("disabled", false);
        } else if (modeType == "SQL_SCRIPT") {
            $('#splitValue').attr("disabled", true);
            $('#targetTable').attr("disabled", true);
            $('#targetDatabaseId').attr("disabled", true);
        } else {
            $('#splitValue').attr("disabled", true);
        }

    }

    var editor = ace.edit("editor");
    editor.session.setMode("ace/mode/sql");


    $(function () {
        //执行模式为并发时间段任务时,显示"拆分作业阈值"
        ShowSplitValue();
        //注册事件
        $("#selModeCode").change(function () {
            ShowSplitValue();
        });
    });

    //执行模式为并发时间段任务时,显示"拆分作业阈值"
    function ShowSplitValue()
    {
        if($("#selModeCode").val()== "TIME_PERIOD")
        {
            //var SplitValueHTML="<input id='splitValue' type='text'　name='splitValue'  value=''>"
            $("#splitValue").attr("name","splitValue");
            $("#divShowSplitValue").show();
        }
        else
        {
            $("#splitValue").attr("name","nosplitValue");
            $("#divShowSplitValue").hide();
        }
    }

    //js验证方法
    function CheckValue()
    {

        var returnval = true;

        $("#spanJobName").html("");
        $("#spanSequence").html("");
        $("#spanLayer").html("");
        $("#spanModeCode").html("");
        $("#spansplitValue").html("");
        var sqlType = $("#sqlType").val();
        //插入任务时验证
        if(sqlType == "i") {
            $("#spantargetTable").html("");
            $("#spansourceDatabaseId").html("");

        }
        $("#spantargetDatabaseId").html("");
        $("#spaneditor").html("");

        /*******************以下为公共验证*****************************/

            //获取当前作业名称
            var NowJobName = $("#txtJobName").val();
            if(NowJobName != null && NowJobName != "")
            {

                var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
                if(pattern.test(NowJobName)){
                    $("#spanJobName").html("作业名称包含非法字符!");
                    $("#txtJobName").focus();
                    returnval = false;
                }
            }
            else
            {
                $("#spanJobName").html("作业名称不能为空!");
                $("#txtJobName").focus();
                returnval = false;
            }

            //获取当前顺序号
            var NowSequence = $("#txtSequence").val();
            //必须为正整数
            if(NowSequence != null && NowSequence != "") {

                var pattern = /^[1-9]+[0-9]*]*$/;
                if (!pattern.test(NowSequence)) {
                    $("#spanSequence").html("顺序号必须为大于0的正整数");
                    $("#txtSequence").focus();
                    returnval = false;
                }
            }
            else
            {
                $("#spanSequence").html("顺序号不能为空!");
                $("#txtSequence").focus();
                returnval = false;
            }


            //判断层级
            if($("#selLayer").val() == "-1")
            {
                $("#spanLayer").html("请选择层级");
                $("#selLayer").focus();
                returnval = false;
            }

            //判断执行模式
            if($("#selModeCode").val() == "")
            {
                $("#spanModeCode").html("请选择执行模式");
                $("#selModeCode").focus();
                returnval = false;
            }

        //判断拆分值
        if($("#selModeCode").val()== "TIME_PERIOD") {
            var splitValueval = $("#splitValue").val();
            //必须为正整数
            if (splitValueval != null && splitValueval != "") {

                var pattern = /^[1-9]+[0-9]*]*$/;
                if (!pattern.test(splitValueval)) {
                    $("#spansplitValue").html("拆分作业阈值必须为大于1的正整数");
                    $("#splitValue").focus();
                    returnval = false;
                }
                else
                {
                    if(splitValueval == 1)
                    {
                        $("#spansplitValue").html("拆分作业阈值必须为大于1的正整数");
                        $("#splitValue").focus();
                        returnval = false;
                    }
                }
            }
            else {
                $("#spansplitValue").html("拆分作业阈值不能为空!");
                $("#splitValue").focus();
                returnval = false;
            }
        }




            //插入任务时验证
            if(sqlType == "i")
            {

                //目标表不能为空
                if($("#targetTable").val() == "")
                {
                    $("#spantargetTable").html("目标表不能为空!");
                    $("#targetTable").focus();
                    returnval = false;
                }
                else
                {
                    $("#spantargetTable").html("");
                }

                //源数据库不能为空
                if($("#selsourceDatabaseId").val() == "-1")
                {
                    $("#spansourceDatabaseId").html("请选择源数据库!");
                    $("#selsourceDatabaseId").focus();
                    returnval = false;
                }
                else
                {
                    $("#spansourceDatabaseId").html("");
                }
            }

        //目标数据库不能为空
        if($("#targetDatabaseId").val() == "")
        {
            $("#spantargetDatabaseId").html("请选择目标数据库!");
            $("#targetDatabaseId").focus();
            returnval = false;
        }

        //sql脚本
        var sqlscriptval = editor.getSession().getValue();
        if(sqlscriptval == "")
        {
            $("#spaneditor").html("sql脚本不能为空!");
            returnval = false;
        }
        else
        {
            //不为空时,设置sql
            SetSqlScript();
        }
        return returnval;
    }

    //设置sql到隐藏字段sqlScript
    function SetSqlScript()
    {
        var sqlscriptval = editor.getSession().getValue();
        $("#sqlScript").val(sqlscriptval);
    }

</script>
<%@ include file="../include/bottom.jsp" %>
