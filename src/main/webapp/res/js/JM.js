
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


    $('#errorMsg').attr('style','display: none');
    //getParamType();
    //getParamRef();
});









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