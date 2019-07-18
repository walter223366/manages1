/**初始化*/
$(function(){
    pagingQueryAccount();
});

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


/**-----------------------------------------查询部分-----------------------------------------*/
$("#query_accountPage").click(function () {
    pagingQueryAccount();
});
/**分页查询*/
function pagingQueryAccount(){
    $("#show_accountData").empty();
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
                    pagingPluginAccount(obj,account,tellphone,starttime,endtime);
                }
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
}
/**分页插件*/
function pagingPluginAccount(obj,account,tellphone,starttime,endtime){
    layui.use('laypage', function(){
        var laypage = layui.laypage;
        laypage.render({
            elem: 'page_accountInfo'
            ,count: obj.total
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            ,theme: '#15c142'
            ,jump:function (obj) {
                $("#show_accountData").empty();
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
                                showAccountData(obj);
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
/**查询数据展示*/
function showAccountData(obj){
    var total = obj.total;
    var data = "";
    if(total == 0){
        data = "<tr><td colspan='8' style='text-align:center;'>未查询到数据</td></tr>";
    }else {
        $.each(obj.datas,function (i,n) {
            data = data+"<tr>";
            data = data+"<td><div class='layui-unselect layui-form-checkbox' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' lay-skin='primary' data-id='2'><i class='layui-icon'>&#xe605;</i></div></td>";
            data = data+"<td>"+(i+1)+"</td>";
            data = data+"<td>"+(isNull(n.account))+"</td>";
            data = data+"<td>"+(isNull(n.tellphone))+"</td>";
            data = data+"<td>"+(isNull(n.source))+"</td>";
            data = data+"<td>"+(isNull(n.lrrq))+"</td>";
            data = data+"<td>"+(isNull(n.cancellation))+"</td>";
            data = data+"<td class='td-manage'>";
            data = data+"<a title='编辑' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' href='javascript:void(0);' class='layui-btn layui-btn-sm layui-btn-warm edit'><i class='layui-icon'>&#xe642;</i>编辑</a>";
            data = data+"<a title='删除' obj='"+$.base64.btoa(JSON.stringify(n),'utf-8')+"' href='javascript:void(0);' class='layui-btn layui-btn-sm layui-btn-danger del'><i class='layui-icon'>&#xe640;</i>删除</a>";
            data = data+"</td></tr>";
        });
    }
    $("#show_accountData").html(data);
}
/**清空条件*/
$("#query_accountEmpty").click(function (){
    document.getElementById("query_account").value = '';
    document.getElementById("query_tellphone").value = '';
    document.getElementById("query_starttime").value = '';
    document.getElementById("query_endtime").value = '';
});


/**-----------------------------------------新增部分-----------------------------------------*/
$("#add_button").click(function () {
    addAccount_reset();
    layer.open({
        type: 1,
        offset: "auto",
        id: 'layerDemo'+'auto',
        area: [800+'px', 400 +'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: "新增用户",
        content: $("#add_accountInfo"),
        yes:function() {
            $("#add_accountInfo").hide();
        }
    });
});
/**新增*/
$("#addAccount_but").click(function () {
    var account = $("#add_account").val();
    var tellphone = $("#add_tellphone").val();
    var password = $("#add_spassword").val();
    var repassword = $("#add_epassword").val();
    var source = $("#add_source").val();
    var flag = checkParams(account,tellphone,password,repassword);
    if(flag == false){
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "/account/accountAdd",
        data: {
            account: account,
            tellphone: Number(tellphone),
            password: password,
            source: Number(source)
        },
        success: function (data) {
            if (data.code == "0" && data.result == "SECCESS") {
                layer.msg(data.msg,{icon: 1});
                pagingQueryAccount();
            }else{
                layer.msg(data.msg,{icon: 2});
            }
        }
    });
});
/**参数校验*/
function checkParams(account,tellphone,password,repassword){
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
    if(repassword==null||repassword==""){
        layer.msg("确认密码不能为空",{icon: 2});
        return false;
    }else{
        if(password != repassword){
            layer.msg("两次密码不一致",{icon: 2});
            return false;
        }
    }
    return true;
}
/**重置*/
$("#addAccount_reset").click(function () {
    addAccount_reset();
});
function addAccount_reset(){
    document.getElementById("add_account").value = '';
    document.getElementById("add_tellphone").value = '';
    document.getElementById("add_spassword").value = '';
    document.getElementById("add_epassword").value = '';
    document.getElementById("add_source").value = '';
}


/**-----------------------------------------编辑部分-----------------------------------------*/
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
/**编辑*/
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
/**重置*/
$("#updateAccount_reset").click(function () {
    document.getElementById("update_tellphone").value = "";
});


/**-----------------------------------------删除部分-----------------------------------------*/
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
