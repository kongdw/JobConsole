var paramTableCount = 0;    //  表格的行数
var selectHtml; //类型下拉列表
var paramRefHtml;

$(document).ready(function () {
    $('table').find('tbody').each(function () {
        $(this).on('click', 'tr', function () {
            $(this).parent().find('tr').each(function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                }
            });
            $(this).addClass('selected');
        })
    });

    $('#logoutButton').click(function () {
        window.location.replace("/logout");
    });
    $('#errorMsg').attr('style','display: none');
    getParamType();
    getParamRef();
});

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
        url: "/parameter/paramRefByNameAjax?name=" + name,
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

function getParameter(jobId) {
    paramTableCount = 0;
    var HaveParams = 0;
    $.ajax({

        type: "get",
        url: "/parameter/list?jobId=" + jobId,
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
                    url: "/job/getjobparams?jobId=" + jobId,
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
function getParamRef() {
    $.ajax({
        type: "get",
        url: "/parameter/paramRefAjax",
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
            paramRefHtml = select;
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
        url: "/parameter/typeList",
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
            selectHtml = select;
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
        row.append($("<td   ><select  name='parameterType' onchange='selectChange(this)'  class='form-control col-xs-2'>" + selectHtml.html() + "</select></td>"));
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
        url: "/parameter/save/" + jobId,
        data: JSON.stringify(paramData),
        dataType: 'json',
        complete: function (XMLHttpRequest, textStatus) {
            $('#errorMsg').find('p').text(XMLHttpRequest.responseText);
            $('#errorMsg').attr('style','');
            // alert(XMLHttpRequest.responseText);
        }
    });

}

/**
 * 获取table表格的数据项.
 */
function getTableData(tableId) {
    var data = "[";                                                                 //定义数据变量
    $("#" + tableId).find("tbody > tr").each(function () {
        data += "{"
        $(this).find("input, select").each(function () {
            data += "\"" + $(this).attr("name") + "\":\"" + $(this).val() + "\",";
        });
        data += "},"
    });
//    $("#" + tableId).find("input, select").each(function(){                         //遍历表格中的input、select等标签
//        if($(this).attr("name")){                                                     //如果此标签设置了id，则取出其中数据
//            data += "\"" + $(this).attr("name") + "\":\"" + $(this).val() + "\",";    //拼接id和数据
//        }
//    });
    if (data.length != 1) {                                                           //如果取出了数据，删除多余的符号
        data = data.substring(0, data.length - 1);                                    //删除多余的符号','
    }
    data += "]";                                                                    //添加结束符
    data = eval("(" + data + ")");                                                  //将数据转换成json对象
    return data;                                                                    //返回数据
}

//(function($){
//    $.fn.serializeJson=function(){
//        var serializeObj={};
//        var array=this.serializeArray();
//        var str=this.serialize();
//        $(array).each(function(){
//            if(serializeObj[this.name]){
//                if($.isArray(serializeObj[this.name])){
//                    serializeObj[this.name].push(this.value);
//                }else{
//                    serializeObj[this.name]=[serializeObj[this.name],this.value];
//                }
//            }else{
//                serializeObj[this.name]=this.value;
//            }
//        });
//        return serializeObj;
//    };
//})(jQuery);

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
//$(document).ready(
//    function () {
//        jQuery.ajax({
//            type: 'GET',
//            contentType: 'application/json',
//            url: 'user/list',
//            dataType: 'json',
//            success: function (data) {
//                if (data && data.success == "true") {
//                    $('#info').html("共" + data.total + "条数据。<br/>");
//                    $.each(data.data, function (i, item) {
//                        $('#info').append(
//                                "编号：" + item.id + "，姓名：" + item.username
//                                + "，年龄：" + item.age);
//                    });
//                }
//            },
//            error: function () {
//                alert("error")
//            }
//        });
//        $("#submit").click(function () {
//            var jsonuserinfo = $.toJSON($('#form').serializeObject());
//            alert(jsonuserinfo);
//            jQuery.ajax({
//                type: 'POST',
//                contentType: 'application/json',
//                url: 'user/add',
//                data: jsonuserinfo,
//                dataType: 'json',
//                success: function (data) {
//                    alert("新增成功！");
//                },
//                error: function (data) {
//                    alert("error")
//                }
//            });
//        });
//    });

function openModal(url, title, message) {
    $('#modalLabel').text(title);
    $('#modalBody').text(message);
    $('#modalMessage').modal('show');
    $('#modalButton').click(function () {
        window.location.replace(url);
    });
}

(function ($) {
    $.fn.selectbox = function () {

        //用变量idm存储select的id或name
        var idm = $(this).attr("id") || $(this).attr("name");
        if ($("#" + idm + "div").length <= 0) {//判断动态创建的div是否已经存在，如果不存在则创建
            var divHtml = "<div style='display:none' id='" + idm + "div'><input type='text' id='" + idm + "inputText'/></div>";
            $(this).attr("tabindex", -1).after(divHtml);
            $("#" + idm + "div").css({position: "absolute", top: $(this).position().top - 1, left: $(this).position().left - 7}).show();
            $("#" + idm + "inputText").val($(this).val()).css({width: $(this).width() - 13, height: $(this).height() + 1});
            //select注册onchange事件
            $(this).change(function () {
                $("#" + idm + "inputText").val($(this).val());
            })
        }
        ("#" + idm + "inputText").attr("disabled", $(this).attr("disabled"));
        //解决ie6下select浮在div上面的bug
        //$("#" + idm + "div").bgIframe();
        return $("#" + idm + "inputText").val();
    }
})(jQuery);