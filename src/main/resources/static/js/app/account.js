charset="utf-8";
manages="account";


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
        queryTime:$("#query_time").val(),
        cancellation:$("#query_cancellation").val()
    };
    var obj = {
        manages:manages,
        method:pQuery,
        params:$.base64.btoa(JSON.stringify(params),charset)
    };
    layui.use(['layer','table'],function (){
        var layer = layui.layer,table = layui.table;
        table.render({
            elem:'#dataInfo',
            url:url,
            method:'POST',
            contentType:"application/json",
            toolbar:'default',
            id:'#dataInfo',
            title:'账号管理',
            //even:true,
            expandRow:true,
            where:obj,
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
                    {type:'numbers',title:'序号',align:'center',fixed:'felt',width:70},
                    {field:'account',title:'账号',sort:true},
                    {field:'tellphone',title:'手机号码',sort:true},
                    {field:'source',title:'来源'},
                    {field:'lrrq',title:'创建时间',sort:true},
                    {field:'cancellation',title:'状态'},
                    {fixed:'right',title:'操作',width:300,align:'center',toolbar:'#operational'}
                ]
            ]
        });

        //新增、编辑、删除（批量）
        table.on('toolbar(dataInfo)',function (obj){
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch(obj.event){
                case 'add': add();
                    break;
                case 'update':
                    if(data.length === 0){
                        layer.msg('请勾选一条数据进行编辑');
                    } else if(data.length > 1){
                        layer.msg('只能同时编辑一条数据');
                    } else {
                        $.each(data,function (i,n) {
                            edit(n);
                        });
                    }
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('请勾选一条数据进行删除');
                    } else {
                        del(data);
                    }
                    break;
            }
        });

        //编辑、删除
        table.on('tool(dataInfo)',function (obj){
            var data = obj.data;
            if (obj.event === 'edit'){
                edit(data);
            }else if (obj.event === 'del'){
                var des = [data];
                del(des);
            }
        });
    });
}



/**清空条件*/
function cleanUp(){
    $("#query_account").val('');
    $("#query_tellPhone").val('');
    $("#query_time").val('');
    $("#query_source").val('');
    $("#query_cancellation").val('');
    layui.form.render("select");
    pagingQuery();
}



/**新增部分*/
function add(){
    addReset();
    var content =  $("#addInfo");
    layerOpen("新增",content,800,450,
        function () {
            addRequest();
        },function (index,layero) {
            addReset();
    });
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
        if(params.account.length>100){
            layer.msg("账号名称字符过长",{icon:2});
            return;
        }
    }
    layer.confirm('请确认要新增这条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            postRequest(params,manages,increase,function (data){
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("新增成功");
                    pagingQuery();
                }else{
                    layer.msg(data.msg,{icon:2});
                }
            });
        }, function(){
            layer.close();
        }
    );
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
    postRequest(params,manages,eQuery,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)){
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.id);
                document.getElementById("edit_account").value = isNull(obj.account);
                document.getElementById("edit_tellPhone").value = isNull(obj.tellphone);
                $("#edit_source").val(String(obj.source));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑",content,950,500,
                    function () {
                        editRequest();
                    },function (index,layero) {
                        editReset();
                });
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
        if(params.account.length>100){
            layer.msg("账号名称字符过长",{icon:2});
            return;
        }
    }
    layer.confirm('请确认要修改这条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            postRequest(params,manages,renew,function (data){
                if(data.code === "0" && data.result === "SUCCESS"){
                    layer.msg("修改成功");
                    pagingQuery();
                }else{
                    layer.msg(data.msg,{icon:2});
                }
            });
        }, function(){
            layer.close();
        }
    );
}
//编辑-重置
function editReset(){
    $("#edit_tellPhone").val('');
}



/**删除部分*/
function del(data){
    layer.confirm('请确认要删除这'+data.length+'条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            var params = {
                data:data
            };
            postRequest(params,manages,strike,function (data){
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



/**时间控件*/
layui.use('laydate', function(){
    var laydate = layui.laydate;
    laydate.render({
        elem:'#query_time'
        ,type:'datetime'
        ,range: true
    });
});


function baseInfo(){
    $("#nickname").open();
}