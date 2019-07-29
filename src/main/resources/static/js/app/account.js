
$("#query_accountEmpty").click(function (){
    document.getElementById("query_account").value = '';
    document.getElementById("query_tellphone").value = '';
    document.getElementById("query_starttime").value = '';
    document.getElementById("query_endtime").value = '';
});


/**初始化*/
pagingQuery();

$(function () {
    $("#pagingQuery").click(function () {
        pagingQuery();
    });
});



/**查询部分*/
function pagingQuery(){
    $("#showData").empty();
    alert("123");
    var account = $("#query_account").val();
    var tellphone = $("#query_tellphone").val();
    var starttime = $("#query_starttime").val();
    var endtime = $("#query_endtime").val();
    $.ajax({
        type:"POST",
        url:"/account/pagingQuery",
        data: JSON.stringify({
            "account":account,
            "tellphone":tellphone,
            "starttime":starttime,
            "endtime":endtime
        }),
        dataType:"JSON",
        contentType:"application/json",
        success:function(data){
            if(data.code == "0" && data.result == "SECCESS"){
                var rows = $.base64.atob(data.rows,"utf-8");
                if(isJSON(rows)){
                    obj = JSON.parse(rows);
                    pagingPlugin(obj,account,tellphone,starttime,endtime);
                }
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
}
function pagingPlugin(obj,account,tellphone,starttime,endtime){
    layui.use('laypage', function(){
        var laypage = layui.laypage;
        laypage.render({
            elem: 'page_Info'
            ,count: obj.total
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            ,theme: '#15c142'
            ,jump:function (obj) {
                $("#showData").empty();
                $.ajax({
                    type:"POST",
                    url:"/account/pagingQuery",
                    data: JSON.stringify({
                        "account":account,
                        "tellphone":tellphone,
                        "starttime":starttime,
                        "endtime":endtime,
                        "pageNum":obj.curr,
                        "pageSize":obj.limit
                    }),
                    dataType:"JSON",
                    contentType:"application/json",
                    success:function(data){
                        if(data.code == "0" && data.result == "SECCESS"){
                            var rows = $.base64.atob(data.rows,"utf-8");
                            if(isJSON(rows)){
                                obj = JSON.parse(rows);
                                showData(obj);
                            }
                        }else{
                            layer.msg(data.msg,{icon: 2});
                        }
                    },
                    error:function () {
                        layer.msg("数据加载超时，请求重试！",{icon: 2});
                    }
                })
            }
        });
    });
}
function showData(obj){
    var total = obj.total;
    var data = "";
    if(total == 0){
        data = "<tr><td colspan='8' style='text-align:center;'>未查询到数据</td></tr>";
    }else {
        $.each(obj.datas,function (i,n) {
            data = data+"<tr>";
            data = data+"<td><div class='layui-unselect layui-form-checkbox' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' lay-skin='primary' data-id='2'><i class='layui-icon layui-icon-ok'></i></div></td>";
            data = data+"<td>"+(i+1)+"</td>";
            data = data+"<td>"+(isNull(n.account))+"</td>";
            data = data+"<td>"+(isNull(n.tellphone))+"</td>";
            data = data+"<td>"+(isNull(n.source))+"</td>";
            data = data+"<td>"+(isNull(n.lrrq))+"</td>";
            data = data+"<td>"+(isNull(n.cancellation))+"</td>";
            data = data+"<td class='td-manage'>";
            data = data+"<a title='编辑' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' href='javascript:void(0);' class='layui-btn layui-btn-sm layui-btn-warm edit'><i class='layui-icon layui-icon-edit'></i>编辑</a>";
            data = data+"<a title='删除' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' href='javascript:void(0);' class='layui-btn layui-btn-sm layui-btn-danger del'><i class='layui-icon layui-icon-delete'></i>删除</a>";
            data = data+"</td></tr>";
        });
    }
    $("#showData").html(data);
}
//清空条件
$("#cleanUp").click(function () {
    cleanUp();
});
function cleanUp() {
    document.getElementById("query_account").value = '';
    document.getElementById("query_tellphone").value = '';
    document.getElementById("query_starttime").value = '';
    document.getElementById("query_endtime").value = '';
}



/**新增部分*/
$(function () {
    //新增弹出层
    $("#add_bomb").click(function () {
        add_bomb();
    });
    $("#add_request").click(function (){
        add_request();
    });
    $("#add_reset").click(function () {
        add_reset();
    });
});
function add_bomb() {
    add_reset();
    layer.open({
        type: 1,
        offset: "auto",
        id: 'layerDemo' + 'auto',
        area: [800 + 'px', 400 + 'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: "新增",
        content: $("#add_Info"),
        yes: function () {
            $("#add_Info").hide();
        }
    });
}
function add_request() {
    var account = $("#add_account").val();
    var tellphone = $("#add_tellphone").val();
    var password = $("#add_spassword").val();
    var epassword = $("#add_epassword").val();
    var source = $("#add_source").val();
    var flag = checkParams(account,tellphone,password,epassword);
    if(flag == false){
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "/account/add",
        data: {
            account: account,
            tellphone: Number(tellphone),
            password: password,
            source: Number(source)
        },
        success: function (data) {
            if (data.code == "0" && data.result == "SECCESS") {
                layer.msg(data.msg,{icon: 1});
                pagingQuery();
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
}
function add_reset(){
    document.getElementById("add_account").value = '';
    document.getElementById("add_tellphone").value = '';
    document.getElementById("add_spassword").value = '';
    document.getElementById("add_epassword").value = '';
    document.getElementById("add_source").value = '';
}
function checkParams(account,tellphone,password,epassword){
    if(account==null||account==""){
        layer.msg("账号名称不能为空",{icon: 2});
        return false;
    }else{
        if(account.length<6 || account.length>12){
            layer.msg("账号名称规定6-12个字符",{icon: 2});
            return false;
        }
    }
    if(tellphone==null||tellphone==""){
        layer.msg("手机号码不能为空",{icon: 2});
        return false;
    }else{
        var tver = new RegExp("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
        if(!tver.test(tellphone)){
            layer.msg("手机号码格式不合法",{icon: 2});
            return false;
        }
    }
    if(password==null||password==""){
        layer.msg("密码不能为空",{icon: 2});
        return false;
    }else{
        if(password.length<6 || password.length>12){
            layer.msg("密码规定6-12个字符",{icon: 2});
            return false;
        }
    }
    if(epassword==null||epassword==""){
        layer.msg("确认密码不能为空",{icon: 2});
        return false;
    }else{
        if(password != epassword){
            layer.msg("两次密码不一致",{icon: 2});
            return false;
        }
    }
    return true;
}


/**编辑部分*/
$("#show_accountData").on('click','.edit',function () {
    var str = $.base64.atob($(this).attr("obj"), "utf-8");
    obj = JSON.parse(str);
    document.getElementById("update_account").value = toNull(obj.account);
    document.getElementById("update_tellphone").value = toNull(obj.tellphone);
    document.getElementById("update_lrrq").value = toNull(obj.lrrq);
    document.getElementById("update_source").value = toNull(obj.source);
    document.getElementById("update_cancellation").value = toNull(obj.cancellation);
    layer.open({
        type: 1,
        offset: "auto",
        id: 'layerDemo'+'auto',
        area: [800+'px', 400 +'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: "编辑",
        content: $("#update_accountInfo"),
        yes:function() {
            $("#update_accountInfo").hide();
        }
    });
});
$("#updateAccount_but").click(function () {
    var account = $("#update_account").val();
    var tellphone = $("#update_tellphone").val();
    var lrrq = $("#update_lrrq").val();
    var source = $("#update_source").val();
    var cancellation = $("#update_cancellation").val();
    if(tellphone==null||tellphone==""){
        layer.msg("手机号码不能为空",{icon: 2});
        return;
    }else{
        var tver = new RegExp("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
        if(!tver.test(tellphone)){
            layer.msg("手机号码格式不合法",{icon: 2});
            return;
        }
    }
    $.ajax({
        type:"POST",
        dataType:"JSON",
        url:"/account/accountUpdate",
        data: {
            account:account,
            tellphone:Number(tellphone)
        },
        success:function(data){
            if(data.code == "0" && data.result == "SECCESS"){
                layer.msg(data.msg,{icon: 1});
                pagingQueryAccount();
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
});
$("#updateAccount_reset").click(function () {
    document.getElementById("update_tellphone").value = "";
});


/**删除部分*/
$('#show_accountData').on('click','.del',function () {
    var str = $.base64.atob($(this).attr("obj"), "utf-8");
    var obj = JSON.parse(str);
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "/account/accountDelete",
        data: {account:obj.account},
        success: function (data) {
            if(data.code == "0" && data.result == "SECCESS") {
                layer.msg(data.msg,{icon: 1});
                pagingQueryAccount();
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
});


/**判断json格式、字符串*/
function isJSON(str){
    try{
        JSON.parse(str);
        return true;
    }catch (e) {
        return false;
    }
}
function isNull(str){
    if(str == null || str == ""){
        return "-";
    }else{
        return str;
    }
}
function toNull(str){
    if(str == null || str == ""){
        return "";
    }
    return str;
}


/**时间控件*/
layui.use('laydate', function(){
    var laydate = layui.laydate;
    laydate.render({
        elem: '#query_starttime'
    });
    laydate.render({
        elem: '#query_endtime'
    });
});