<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="include/head.jsp" %>
<%@ include file="include/top.jsp" %>
<%@ include file="include/left.jsp" %>

<style type="text/css">
    <%--#serverLog {
        border: 1px solid #CCCCCC;
        border-right-color: #999999;
        border-bottom-color: #999999;
        overflow-y: scroll;
        padding: 5px;
        width: 100%;
        height: 100%
    }

    #serverLog p {
        padding: 0;
        margin: 0;
    }

    #jobLog {
        border: 1px solid #CCCCCC;
        border-right-color: #999999;
        border-bottom-color: #999999;
        overflow-y: scroll;
        padding: 5px;
        width: 100%;
        height: 100%
    }

    #jobLog p {
        padding: 0;
        margin: 0;
    }--%>
</style>
<script type="text/javascript">
    <%--
    $(document).ready(function () {
        connect();
    })--%>
</script>
<div class="main-content">
<div class="container">
<div class="row clearfix">
    <div class="area-top clearfix">
        <div class="col-md-8">
            <div class="pull-left header">
                <h3 id="mainTitle" class="title"><i class="icon-home"></i>主页</h3>
                <h5><span id="subTitle">服务器状态、服务器负载、服务器日志</span></h5>
            </div>
        </div>
        <div class="col-md-4">
            <div class="btn-group">
                <button type="button" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/queue/start'"  <c:if test="${serverStatus == true}">disabled</c:if>>启动服务</button>
                <button type="button" class="btn btn-danger" onclick="window.location.href='${pageContext.request.contextPath}/queue/stop'" <c:if test="${serverStatus == false}">disabled</c:if>>停止服务</button>
                <a type="button" href="${pageContext.request.contextPath}/queue/executeAll" class="btn btn-primary">执行作业</a>
            </div>
        </div>
    </div>
</div>
<div class="row clearfix">
    <div class="col-md-4 last">
        <div class="widget-box">
            <div class="widget-box-header">
                <div class="title">抽取速度</div>
            </div>
            <div class="widget-box-content" style="padding:15px;">
                <div id="container-speed" style="height:200px;width: 100%;"></div>
            </div>
        </div>
    </div>
    <div class="col-md-8">
        <div id="ParameterDiv" style=" width: 98%">
            <div class="widget-box">
                <div class="widget-box-header">
                    <div class="title">全局参数</div>
                </div>
                <div class="widget-box-content" style="padding:15px;">
                    <div class="dataTables_wrapper form-inline">
                        <div class="table-responsive" style="height: 200px; width: 100%;overflow:auto">
                            <table id="ParamTableTop" class="tableComponent table table-hover" width="100%">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>返回值</th>
                                    <th>缺省值</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${parameterRefList}" var="list" varStatus="idx">
                                    <tr>
                                        <td><kbd>${list.refName}</kbd></td>
                                        <c:if test="${list.returnType.description eq null}">
                                            <td><kbd>无</kbd></td>
                                        </c:if>
                                        <c:if test="${list.returnType.description != null}">
                                            <td><kbd>${list.returnType.description}</kbd></td>
                                        </c:if>
                                        <c:if test="${list.defaultVal eq null}">
                                            <td><input id="${list.refName}${list.id}" type="date" name="defaultVal"
                                                       style="height: 26px"
                                                       value="无"></td>
                                        </c:if>
                                        <c:if test="${list.defaultVal != null}">
                                            <td><input id="${list.refName}${list.id}" type="date" name="defaultVal"
                                                       style="height: 26px"
                                                       value="${list.defaultVal}"></td>
                                        </c:if>
                                        <td>
                                            <div class="btn-group">
                                                <a href="#" onclick="UpdateParamByID(${list.id},'${list.refName}')">
                                                    <button type="button"
                                                            class="btn btn-success btn-xs">保存
                                                    </button>
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
<div class="row clearfix">
    <div class="col-md-12">
        <ul id="myTab" class="nav nav-tabs">
            <li class="active">
                <a href="#job" data-toggle="tab" onclick="getJob('${pageContext.request.contextPath}/job/ajaxlistMain?page=1')">
                    作业列表
                </a>
            </li>
            <li>
                <a href="#queue" data-toggle="tab" onclick="getQueue()">
                    等待队列
                </a>
            </li>
            <li>
                <a href="#log" data-toggle="tab" onclick="getLog('${pageContext.request.contextPath}/log/ajaxlist')">
                    作业历史
                </a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="job">
                <div class="table-responsive" style="height: 390px; overflow:auto">
                    <table id="jobTable" class="tableComponent table table-hover" width="100%">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>层级</th>
                            <th>名称</th>
                            <th>执行模式</th>
                            <th>源数据库</th>
                            <th>目标数据库</th>
                            <th>目标表</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id = "jobbody">
                        <c:forEach items="${JobEntityList}" var="job" varStatus="idx">
                            <tr>
                                <td style="display:none">${job.id}</td>
                                <td id="sqlScript${job.id}" style="display:none">${job.sqlScript}</td>
                                <td id="truncateFlag${job.id}" style="display:none">${job.truncateFlag}</td>
                                <td id="splitValue${job.id}" style="display:none">${job.splitValue}</td>
                                <td id="description${job.id}" style="display:none">${job.description}</td>
                                <td id="sequence${job.id}">${job.sequence}</td>
                                <td id="layername${job.id}">${job.layer.name}</td>
                                <td id="name${job.id}">${job.name}</td>
                                <td id="modedescription${job.id}">${job.mode.description}</td>
                                <td id="sourceDatabasename${job.id}">${job.sourceDatabase.name}</td>
                                <td id="targetDatabasename${job.id}">${job.targetDatabase.name}</td>
                                <td id="targetTable${job.id}">${job.targetTable}</td>
                                <td>
                                    <button type="button" class="btn btn-primary btn-xs"
                                            onclick="showMessageJob('${job.id}')">查看详细
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="col-md-12" align="center">
                        <nav>
                            <ul class="pagination pagination-sm" id="paginationJob">

                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="queue">
                <div class="table-responsive" style="height: 390px; overflow:auto">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>执行顺序</th>
                        </tr>
                        </thead>
                        <tbody id="queuebody">

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tab-pane fade" id="log">
                <div class="table-responsive" style="height: 390px; overflow:auto">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>作业名称</th>
                            <th>执行用户</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>执行状态</th>
                            <th>插入条数</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="logbody">
                        </tbody>
                    </table>
                    <div class="col-md-12" align="center">
                        <nav>
                            <ul class="pagination pagination-sm" id="paginationLog">
                            </ul>
                        </nav>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>
</div>

    <div style="position:fixed;bottom:0; width:100%;" align="center">
        <%@ include file="include/footer.jsp" %>
    </div>
</div>


<!-- 模态框（Modal）Job -->
<div class="modal fade" id="myModalJob" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabelJob">作业明细</h4>
            </div>
            <div class="modal-body"  style=" height: 500px; overflow-y:scroll;">
                <div id="modalbodyvalueJob"></div>
                <br>
                <br>

                <div class="dataTables_wrapper form-inline">
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
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

            </div>
        </div>
    </div>
</div>
<!-- /.modal -->
<!-- 模态框（Modal）Log -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">查看详细</h4>
            </div>
            <div class="modal-body" id="modalbodyvalue" style=" height: 500px; overflow-y:scroll;">

            </div>

            <div class="modal-footer">
                <button  id="d_clip_button" type="button" class="btn btn-default" data-clipboard-target="modalbodyvalue">复制</button>

                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

            </div>
        </div>
    </div>
</div>


<!-- /.modal -->
<%@ include file="include/bottom.jsp" %>


<script type="text/javascript">
    // 定义一个新的复制对象
    var clip = new ZeroClipboard(document.getElementById("d_clip_button"), {
        moviePath: "${pageContext.request.contextPath}/res/js/ZeroClipboard.swf"
    } );

    // 复制内容到剪贴板成功后的操作
    clip.on('complete', function(client, args) {
        alert("复制成功!");
    } );


</script>


<script type="text/javascript">
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    var sysInfo = {

        tooltip: {
            formatter: function () {
                return '<span> 时间: ' + Highcharts.dateFormat('%y/%m/%d %H:%M:%S', this.x) + '</span><br/>' +
                        this.series.name + ': ' + Highcharts.numberFormat(this.y, 3, '.') + '%';
            }
        },
        xAxis: {
            type: 'datetime',
            minRange: 1000 * 10 // 30s
        },
        yAxis: {
            title: {
                text: '使用率(%)'
            },
            min: 0
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        }
    };
    <%-- $.ajax({
         type: "GET",
         url: '${pageContext.request.contextPath}/sys/sysinfo',//提供数据的Servlet
         success: function (data) {
             var cpu = [];
             var memory = [];
             var network = [];
             $.each(data, function (idx, item) {
                 var currentDate = new Date(item.date);
                 if (item.memory != 'NaN') {
                     memory.push({
                         x: currentDate,
                         y: item.memory
                     });
                 }
                 if (item.cpu != 'NaN') {
                     cpu.push({
                         x: currentDate,
                         y: item.cpu
                     });
                 }
                 if (item.upload != 'NaN' && item.download != 'NaN') {
                     network.push({
                         x: currentDate,
                         y: (item.upload + item.download) / (1024 * 1024)
                     });
                 }
             });
             $('#cpu').highcharts(Highcharts.merge(sysInfo, {
                 title: {
                     text: 'CPU 使用率(%)'
                 },
                 series: [
                     {
                         type: 'area',
                         name: '使用率',
                         data: cpu
                     }
                 ]
             }));
             $('#memory').highcharts(Highcharts.merge(sysInfo, {
                 title: {
                     text: '内存使用率(%)'
                 },
                 series: [
                     {
                         type: 'area',
                         name: '使用率',
                         data: memory
                     }
                 ]
             }));
             $('#network').highcharts(Highcharts.merge(sysInfo, {
                 title: {
                     text: '网络流量(M)'
                 },
                 yAxis: {
                     title: {
                         text: '流量(M)'
                     },
                     min: 0
                 },
                 tooltip: {
                     formatter: function () {
                         return '<span> 时间: ' + Highcharts.dateFormat('%y/%m/%d %H:%M:%S', this.x) + '</span><br/>' +
                                 this.series.name + ': ' + Highcharts.numberFormat(this.y, 3, '.') + 'M';
                     }
                 },
                 series: [
                     {
                         type: 'area',
                         name: '网络流量',
                         data: network
                     }
                 ]
             }));
         }
     });

     --%>
</script>
<script type="text/javascript">
    //    function setConnected(connected) {
    //        document.getElementById('connect').disabled = connected;
    //        document.getElementById('disconnect').disabled = !connected;
    //        document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    //        document.getElementById('serverLog').innerHTML = '';
    //    }
    <%--
    function connect() {
        if ('WebSocket' in window) {
            //console.log('Websocket supported');
            socket = new WebSocket('ws://localhost:8080/websocket?userName=ABC');

            //console.log('Connection attempted');

            socket.onopen = function () {
                //console.log('Connection open!');
                //setConnected(true);
            };

            socket.onclose = function () {
                console.log('Disconnecting connection');
            };

            socket.onmessage = function (evt) {
                var received_msg = evt.data;
                //console.log(received_msg);
                //console.log('message received!');
                showMessage(received_msg);
            }

        } else {
            //console.log('Websocket not supported');
        }
    }
    --%>
    <%--
    function disconnect() {
        //setConnected(false);
        //console.log("Disconnected");
    }

    function sendName() {
        var message = document.getElementById('message').value;
        socket.send(JSON.stringify({ 'message': message }));
    }

    function showMessage(message) {
        var p;
        if(message.indexOf("serverLog")>= 0){
            var serverLog = document.getElementById('serverLog');
            p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            serverLog.appendChild(p);
            while (serverLog.childNodes.length > 200) {
                serverLog.removeChild(serverLog.firstChild);
            }
            serverLog.scrollTop = serverLog.scrollHeight;
        }
        if(message.indexOf("jobLog")>= 0){
            var jobLog = document.getElementById('jobLog');
            p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            jobLog.appendChild(p);
            while (jobLog.childNodes.length > 200) {
                jobLog.removeChild(jobLog.firstChild);
            }
            jobLog.scrollTop = jobLog.scrollHeight;
        }
        var seriesLength = 0;
        if(message.indexOf("cpu") >= 0){
            var cpuSeries = $('#cpu').highcharts().series[0];
            seriesLength = cpuSeries.data.length;
            data = eval("(" + message + ")");
            $.each(data, function (idx, item) {
               var x = new Date(item.date).getTime(),y = item.cpu;
                if(seriesLength < 500){
                    cpuSeries.addPoint([x,y], true, false);
                }else{
                    cpuSeries.addPoint([x,y], true, true);
                }

            });
        }
        if(message.indexOf("memory") >= 0){
            var memorySeries = $('#memory').highcharts().series[0];
            //alert(cpuSeries.length);
            data = eval("(" + message + ")");
            $.each(data, function (idx, item) {
                var x = new Date(item.date).getTime(),y = item.memory;
                if(seriesLength < 500){
                    memorySeries.addPoint([x,y], true, false);
                }else{
                    memorySeries.addPoint([x,y], true, true);
                }
            });

        }
        if(message.indexOf("network") >= 0){
            var networkSeries = $('#network').highcharts().series[0];
            //alert(cpuSeries.length);
            data = eval("(" + message + ")");
            $.each(data, function (idx, item) {
                var x = new Date(item.date).getTime(),y = item.network/(1024*1024);
                if(seriesLength < 500){
                    networkSeries.addPoint([x,y], true, false);
                }else{
                    networkSeries.addPoint([x,y], true, true);
                }
            });
        }
    }
    --%>
</script>
<script type="text/javascript">
    var pages;
    //清空列表内容
    function OndeleteElement(tableid) {
        var elementList = document.getElementById(tableid);
        while (elementList.hasChildNodes()) {
            elementList.removeChild(elementList.lastChild);
        }
    }

    $(function () {

        $('input, textarea').placeholder();

        /* 定义所有class为copy标签，点击后可复制被点击对象的文本 */


        //加载作业列表
        getJob('${pageContext.request.contextPath}/job/ajaxlistMain?page=1');

        var gaugeOptions = {
            chart: {
                type: 'solidgauge'
            },
            title: null,
            pane: {
                center: ['50%', '85%'],
                size: '150%',
                startAngle: -90,
                endAngle: 90,
                background: {
                    backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                    innerRadius: '60%',
                    outerRadius: '100%',
                    shape: 'arc'
                }
            },
            tooltip: {
                enabled: false
            },
            // the value axis
            yAxis: {
                stops: [
                    [0.1, '#DF5353'], // green
                    [0.5, '#DDDF0D'], // yellow
                    [0.9, '#55BF3B'] // red
                ],
                lineWidth: 0,
                minorTickInterval: null,
                tickPixelInterval: 400,
                tickWidth: 0,
                title: {
                    y: -70
                },
                labels: {
                    y: 16
                }
            },

            plotOptions: {
                solidgauge: {
                    dataLabels: {
                        y: 5,
                        borderWidth: 0,
                        useHTML: true
                    }
                }
            }
        };

        // The speed gauge
        $('#container-speed').highcharts(Highcharts.merge(gaugeOptions, {
            title: {
                text: 'ETL 抽取速度(n/s)'
            },
            yAxis: {
                min: 0,
                max: 50000,
                title: {
                    text: null
                }
            },

            credits: {
                enabled: false
            },

            series: [
                {
                    name: 'Speed',
                    data: [0],
                    dataLabels: {
                        format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                                '<span style="font-size:12px;color:silver">n/s</span></div>'
                    },
                    tooltip: {
                        valueSuffix: 'n/s'
                    }
                }
            ]

        }));
        // Bring life to the dials

        //执行队列
        <%--
        setInterval(function () {
            //每次清空队列表
            OndeleteElement("ExecuteQueueBody");
            // Speed
            var speedChart1 = $('#container-speed').highcharts();
            if (speedChart1) {
                var point1 = speedChart1.series[0].points[0];
                $.ajax({
                    type: "GET",
                    url: '${pageContext.request.contextPath}/queue/ajaxList',//提供数据的Servlet
                    success: function (data) {
                        $.each(data, function (idx, item) {
                            $("#ExecuteQueueBody").append(
                                    "<tr>"
                                    + "<td>"
                                    + idx
                                    + "</td>"
                                    + "<td>"
                                    + item.jobName
                                    + "</td>"
                                    + "<td>"
                                    + item.status
                                    + "</td>"
                                    + "<td>"
                                    + item.progress
                                    + "</td>"
                                    + "<td>"
                                    + "<a href='#' class='c2'>查看日志</a>"
                                    + "</td>"
                                    + "</tr>"
                            );
                        });
                    },
                    error: function (e) {
                    }
                });
            }
        }, 2000);--%>

        <%--
        //初始化时,查询等待中
        $.ajax({
            type: "GET",
            url: '${pageContext.request.contextPath}/queue/list',
            success: function (data) {
                alert(data);
            },
            error: function (e) {
            }
        });

        //初始化时,查询执行中
        $.ajax({
            type: "GET",
            url: '${pageContext.request.contextPath}/queue/ajaxList',//提供数据的Servlet
            success: function (data) {
                $.each(data, function (idx, item) {

                });
            },
            error: function (e) {
            }
        });
        --%>

        setInterval(function () {
            // Speed
            var speedChart1 = $('#container-speed').highcharts();
            if (speedChart1) {
                var point1 = speedChart1.series[0].points[0];
                $.ajax({
                    type: "GET",
                    url: '${pageContext.request.contextPath}/log/power',//提供数据的Servlet
                    success: function (data) {
                        point1.update(data);
                    },
                    error: function (e) {
                    }
                });
            }
        }, 5000);


        $('#myModalJob').on('show.bs.modal', function () {
            //alert($("select").size());
            //$("select").attr("disabled", "disabled");
            //document.getElementsByTagName("select").disabled = true;

        });


        $('#myModalJob').on('shown.bs.modal', function () {
            //alert($("select").size());
            $("select").attr("disabled", "disabled");


            //document.getElementsByTagName("select").disabled = true;

        });


    });
</script>
<script type="text/javascript">
    var pagesLog;
    var pagesJob;
    //检查结果
    var checkResult = false;
//修改全局参数的值
function UpdateParamByID(id,refName) {

    //获取当前修默认值
    var defaultValNow = $("#"+refName+id).val();

    if(defaultValNow == null || defaultValNow == "")
    {
        alert(refName+"时间不能为空!");
        checkResult = true;
        return;
    }

    CheckDate(refName);
    //调用日期判断函数
    if(checkResult)
    {
        return;
    }
    else
    {
        $.ajax({
            async : false,
            type: "get",
            url: "${pageContext.request.contextPath}/parameter/UpdateParamByIDAjax?id=" + id + "&defaultVal=" + defaultValNow,
            beforeSend: function (XMLHttpRequest) {
                //ShowLoading();
            },
            success: function (data, textStatus) {
                if (data == "1") {
                    alert("修改成功!");
                }
                else {
                    alert("修改失败!")
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
}

//修改时，进行日期判断
function checkParamDate() {

}

//获取等待中
function getQueue() {
    //清空queuebody中的内容
    OndeleteElement("queuebody");
    $.ajax({
        type: "GET",
        url: '${pageContext.request.contextPath}/queue/MainAjaxlist',//提供数据的Servlet
        success: function (data) {
            $.each(data, function (idx, item) {
                $("#queuebody").append(
                                "<tr>"
                                + "<td><kbd>"
                                + item.jobName
                                + "</kbd></td>"
                                + "<td><kbd>"
                                + (Number(idx) + 1)
                                + "</kbd></td>"
                                + "</tr>"
                );
            });
        },
        error: function (e) {
        }
    });
}

//作业列表
function getJob(url) {

        OndeleteElement("jobbody");

        $.ajax({
            type: "GET",
            url: url,//提供数据的Servlet
            success: function (data) {
                $.each(data.list, function (idx, item) {
                    $("#jobbody").append(
                            "<tr>"
                            + "<td style='display:none'>"
                            + item.id
                            + "</td>"
                            + "<td id = 'sqlScript"+item.id+"' style='display:none' >"
                            + item.sqlScript
                            + "</td>"
                            + "<td id = 'truncateFlag"+item.id+"' style='display:none' >"
                            + item.truncateFlag
                            + "</td>"
                            + "<td id = 'splitValue"+item.id+"' style='display:none' >"
                            + item.splitValue
                            + "</td>"
                            + "<td id = 'description"+item.id+"' style='display:none' >"
                            + item.description
                            + "</td>"
                            + "<td id = 'sequence"+item.id+"' >"
                            + item.sequence
                            + "</td>"
                            + "<td id = 'layername"+item.id+"' >"
                            + item.layer.name
                            + "</td>"
                            + "<td id = 'name"+item.id+"' >"
                            + item.name
                            + "</td>"
                            + "<td id = 'modedescription"+item.id+"' >"
                            + item.mode.description
                            + "</td>"
                            + "<td id = 'sourceDatabasename"+item.id+"' >"
                            + (item.sourceDatabase == null ? "" : item.sourceDatabase.name)

                            + "</td>"
                            + "<td id = 'targetDatabasename"+item.id+"' >"

                            + (item.targetDatabase == null ? "" : item.targetDatabase.name)
                            + "</td>"
                            + "<td id = 'targetTable"+item.id+"' >"
                            + (item.targetTable == null ? "":item.targetTable)
                            + "<td>"
                            + "<button type='button' class='btn btn-primary btn-xs' onclick='showMessageJob(\"" + item.id + "\")'>查看详细</button>"
                            + "</td>"
                            + "</tr>"
                    );
                });
                pagesJob = data.pages;
                $('#paginationJob').twbsPagination({
                    totalPages: pagesJob,
                    visiblePages: 4,
                    onPageClick: function (event, page) {
                        getLog('/job/ajaxlistMain?page=' + page);
                    }
                });
            },
            error: function (e) {
            }
        });
    }

//获取执行中
function getLog(url) {
    //清空logbody中的内容
    OndeleteElement("logbody");
    $.ajax({
        type: "GET",
        url: url,//提供数据的Servlet
        success: function (data) {
            $.each(data.list, function (idx, item) {
                $("#logbody").append(
                                "<tr>"
                                + "<td><kbd>"
                                + item.jobName
                                + "</kbd></td>"
                                + "<td><kbd>"
                                + item.username
                                + "</kbd></td>"
                                + "<td>"
                                + item.beginTime
                                + "</td>"
                                + "<td>"
                                + item.endTime
                                + "</td>"
                                + "<td>"
                                + item.status
                                + "</td>"
                                + "<td>"
                                + item.nbLine
                                + "</td>"
                                + "<td>"
                                + "<button type='button' class='btn btn-primary btn-xs' onclick='showMessage(\"" + item.message + "\")'>查看详细</button>"
                                + "</td>"
                                + "</tr>"
                );
            });
            pagesLog = data.pages;
            $('#paginationLog').twbsPagination({
                totalPages: pagesLog,
                visiblePages: 4,
                onPageClick: function (event, page) {
                    getLog('${pageContext.request.contextPath}/log/ajaxlist?page=' + page);
                }
            });

        },
        error: function (e) {
        }
    });


}

$('.pagination').twbsPagination({
    totalPages: function(){
        return pages;
    },
    visiblePages: 6,
    onPageClick: function (event, page) {
        getLog('/log/ajaxlist?page=' + page);
    }
});
function showMessageJob(jobid) {


    //作业名称
    var name = $("#name" + jobid).html();
    //顺序号
    var sequence = $("#sequence" + jobid).html();
    //层级
    var layername = $("#layername" + jobid).html();
    //抽取模式
    var modedescription = $("#modedescription" + jobid).html();
    //抽取前先清除数据
    var truncateFlag = $("#truncateFlag" + jobid).html() == "true" ? "是" : "否";
    //拆分作业阈值
    var splitValue = $("#splitValue" + jobid).html();
    //目标表
    var targetTable = $("#targetTable" + jobid).html();
    //源数据库
    var sourceDatabasename = $("#sourceDatabasename" + jobid).html();
    //目标数据库
    var targetDatabasename = $("#targetDatabasename" + jobid).html();
    //SQL脚本
    var sqlScript = $("#sqlScript" + jobid).html();
    //备注
    var description = $("#description" + jobid).html();
    var JobInfoVal = " <form class='form-horizontal' role='form' >"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>作业名称:</label><div  class='col-sm-6 well well-sm'>" + name + " </div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>顺序号:</label><div  class='col-sm-6 well well-sm'>" + sequence + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>层级:</label><div class='col-sm-6 well well-sm'>" + layername + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>抽取模式:</label><div class='col-sm-6 well well-sm'>" + modedescription + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>抽取前先清除数据:</label><div class='col-sm-6 well well-sm'>" + truncateFlag + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>拆分作业阈值:</label><div class='col-sm-6 well well-sm'>" + splitValue + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>目标表:</label><div class='col-sm-6 well well-sm'>" + targetTable + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>源数据库:</label><div class='col-sm-6 well well-sm'>" + sourceDatabasename + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>目标数据库:</label><div class='col-sm-6 well well-sm'>" + targetDatabasename + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>SQL脚本:</label><div class='col-sm-6 well well-sm'>" + sqlScript + "</div></div>"
            + "<div class='form-group' style='margin-bottom: 0px;'> <label class='col-sm-3 control-label'>备注:</label><div class='col-sm-6 well well-sm'>" + description + "</div></div>";
    +"</form>"

    $("#modalbodyvalueJob").html(JobInfoVal);
    //参数
    getParameter(jobid);


    $('#myModalJob').modal('show');


    //$("select").attr("disabled", "disabled");
    //document.getElementsByTagName("select").disabled = true;
    //$(obj).popover({delay: { "show": 0, "hide": 0 }, html: true});
    //$(obj).popover('hide');
}

function showMessage(messageval) {

    $("#modalbodyvalue").html(messageval);
    $('#myModal').modal('show');
    //$(obj).popover({delay: { "show": 0, "hide": 0 }, html: true});
    //$(obj).popover('hide');
}

//根据毫秒数获取日期
function getDateByHM(valhm) {
    var newTime = new Date(valhm);
    return newTime;
}

    //全局参数日期检验
    function CheckDate(datetype) {
        //开始时间
        var begindate = new Date($("input[id^='BEGIN_DATE']").val());
        //结束时间
        var enddate = new Date($("input[id^='END_DATE']").val());
        //alert($("input[id^='END_DATE']").val());
        if ( "BEGIN_DATE"  == datetype) {
            if (begindate > enddate) {
                alert("开始时间不能大于结束时间!");
                checkResult = true;
                return;
            }
        }
        else {

            if (enddate < begindate) {
                alert("结束时间不能小于开始时间!");
                checkResult = true;
                return;
            }
            //执行异步调用 ，用结束时间与当前服务器时间做比较
            $.ajax({
                async : false,
                type: "get",
                url: "/parameter/UpdateParamCheckDateAjax?enddate=" + $("input[id^='END_DATE']").val(),
                beforeSend: function (XMLHttpRequest) {
                    //ShowLoading();
                },
                success: function (data) {
                    if (data == true) {
                        alert("结束时间不能大于服务器系统时间!");
                        checkResult = true;
                        return;
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

    }


//根据jobid获取当前参数信息
function GetParamInfoByJobID(jobid) {

}


</script>