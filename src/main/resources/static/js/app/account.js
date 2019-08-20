charset="utf-8";
var pagingQueryUrl="/manages/account/pagingQuery";//分页查询
var batchDelUrl="/manages/account/inBatchDelete";//批量删除
var addUrl="/manages/account/inAdd";//新增
var editQueryUrl ="/manages/account/editQuery";//编辑查询
var editUrl="/manages/account/inUpdate";//修改
var delUrl="/manages/account/inDelete";//删除


/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        account:$("#query_account").val(),
        tellPhone:$("#query_tellPhone").val(),
        source:$("#query_source").val(),
        startTime:$("#query_startTime").val(),
        endTime:$("#query_endTime").val()
    };
    layui.use(['layer','table'],function (){
        var layer = layui.layer,table = layui.table;
        table.render({
            elem:'#dataInfo',
            url:pagingQueryUrl,
            method:'POST',
            contentType:"application/json",
            toolbar:'#toolbarBomb',
            id:'#dataInfo',
            title:'账号管理',
            even:true,
            expandRow:true,
            where:params,
            page:true,
            limit:10,
            limits:[10, 20, 30, 40, 50],
            parseData:function (res){
                var rows = $.base64.atob(res.rows,charset);
                if(isJSON(rows)){
                    var obj = JSON.parse(rows);
                    return {
                        "code":res.code,
                        "msg":res.msg,
                        "count":obj.total,
                        "data":obj.data
                    }
                }
            },
            cols:[
                [
                    {type:'checkbox',fixed:'felt'},
                    {type:'numbers',title:'序号',align:'center',fixed:'felt',width:100},
                    {field:'account',title:'账号',sort:true},
                    {field:'tellphone',title:'手机号码',sort:true},
                    {field:'source',title:'来源'},
                    {field:'lrrq',title:'创建时间',sort:true},
                    {field:'cancellation',title:'状态'},
                    {fixed:'right',title:'操作',width:300,align:'center',toolbar:'#operational'}
                ]
            ]
        });
        //批量删除 新增
        table.on('checkbox(dataInfo)',function(){
            table.on('toolbar(dataInfo)',function (obj){
                var checkStatus = table.checkStatus(obj.config.id);
                if(obj.event === 'bombAdd'){
                    add();
                }else if(obj.event === 'bombDelete'){
                    batchDel(checkStatus);
                }
            });
        });
        //编辑、删除
        table.on('tool(dataInfo)',function (obj){
            var data = obj.data;
            if (obj.event === 'edit'){
                edit(data);
            }else if (obj.event === 'del'){
                del(data);
            }
        });
        //新增
        table.on('toolbar(dataInfo)',function (obj){
            if(obj.event === 'bombAdd'){
                add();
            }else if(obj.event === 'bombDelete'){
                layer.msg("请勾选一条数据进行删除");
            }
        });
    });
}


/**清空条件*/
function cleanUp(){
    $("#query_account").val('');
    $("#query_tellPhone").val('');
    $("#query_startTime").val('');
    $("#query_endTime").val('');
    $("#query_source").val("");
    layui.form.render("select");
    pagingQuery();
}


/**批量删除*/
function batchDel(checkStatus){
    var data = checkStatus.data;
    if(data.length <= 0){
        layer.msg("请勾选一条数据进行删除");
        return;
    }
    layer.confirm('请确定要删除这'+data.length+'条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            var params = {
                data:$.base64.btoa(JSON.stringify(data),charset)
            };
            postRequest(batchDelUrl,params,function (data){
                if (data.code === "0" && data.result === "SUCCESS"){
                    layer.msg("删除成功");
                    pagingQuery();
                }else{
                    layer.msg(data.msg,{icon:2});
                }
            });
        }, function(){
            layer.closeAll();
        }
    );
}


/**新增部分*/
function add(){
    addReset();
    var content =  $("#addInfo");
    layerOpen("新增",content,1000,500);
}
//新增-请求
function addRequest(){
    var params = {
        account:$("#add_account").val(),
        tellphone:Number($("#add_tellPhone").val()),
        password:$("#add_password").val(),
        source:Number($("#add_source").val())
    };
    if(params.account===null || params.account===""){
        layer.msg("账号名称不能为空",{icon:2});
        return;
    }else{
        if(params.account.length<6 || params.account.length>12){
            layer.msg("账号名称规定6-12个字符",{icon:2});
            return;
        }
    }
    postRequest(addUrl,params,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            layer.msg("新增成功");
            pagingQuery();
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//新增-重置
function addReset(){
    $("#add_account").val('');
    $("#add_tellPhone").val('');
    $("#add_password").val('');
    $("#add_cPassword").val('');
}


/**编辑部分*/
function edit(data){
    var params = {
        account:data.account
    };
    postRequest(editQueryUrl,params,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)){
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.id);
                document.getElementById("edit_account").value = isNull(obj.account);
                document.getElementById("edit_tellPhone").value = isNull(obj.tellphone);
                document.getElementById("edit_lrrq").value = isNull(obj.lrrq);
                $("#edit_source").val(String(obj.source));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑",content,1000,500);
            }
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-请求
function editRequest(){
    var params = {
        id:$("#edit_id").val(),
        account:$("#edit_account").val(),
        tellphone:Number($("#edit_tellPhone").val()),
        lrrq:$("#edit_lrrq").val(),
        source:Number($("#edit_source").val())
    };
    if(params.account===null || params.account===""){
        layer.msg("账号名称不能为空",{icon:2});
        return ;
    }else{
        if(params.account.length<6 || params.account.length>12){
            layer.msg("账号名称规定6-12个字符",{icon:2});
            return ;
        }
    }
    postRequest(editUrl,params,function (data){
        if(data.code === "0" && data.result === "SUCCESS"){
            layer.msg("修改成功");
            pagingQuery();
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-重置
function editReset(){
    $("#edit_account").val('');
    $("#edit_tellPhone").val('');
}


/**删除部分*/
function del(data){
    var params = {
        account:data.account
    };
    postRequest(delUrl,params,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            layer.msg("删除成功");
            pagingQuery();
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}


/**时间控件*/
layui.use('laydate', function(){
    var laydate = layui.laydate;
    laydate.render({
        elem: '#query_startTime'
    });
    laydate.render({
        elem: '#query_endTime'
    });
});