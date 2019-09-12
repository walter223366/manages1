charset="utf-8";
manages="school";

/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        name: $("#query_name").val(),
        sinfluence: $("#query_sinfluence").val(),
        einfluence: $("#query_einfluence").val()
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
            title:'门派管理',
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
                    {type:'numbers',title:'序号',align:'center',fixed:'felt',width:150},
                    {field:'name',title:'门派名称',sort:true},
                    {field:'influence',title:'门派影响力值',sort:true},
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

        //查看、编辑、删除
        table.on('tool(dataInfo)',function (obj){
            var data = obj.data;
            if(obj.event === 'see'){
                see(data);
            }else if (obj.event === 'edit'){
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
    $("#query_name").val('');
    $("#query_sinfluence").val('');
    $("#query_einfluence").val('');
    pagingQuery();
}



/**新增部分*/
function add(){
    addReset();
    var content = $("#addInfo");
    layerOpen("新增",content,900,450,
        function () {
            addRequest();
        },function (index,layero) {
            addReset();
        });
}
//新增-请求
function addRequest() {
    var params = {
        name:$("#add_name").val(),
        influence: Number($("#add_influence").val()),
        info: $("#add_info").val()
    };
    if(params.name===null || params.name===""){
        layer.msg("门派名称不能为空",{icon:2});
        return;
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
function addReset() {
    $("#add_name").val('');
    $("#add_influence").val('');
    $("#add_info").val('');
}



/**查看部分*/
function see(data){
    document.getElementById("see_name").value = isNull(data.name);
    document.getElementById("see_influence").value = isNull(data.influence);
    document.getElementById("see_info").value = isNull(data.info);
    var content = $("#seeInfo");
    layerSeeOpen("查看详情",content,900,450);
}



/**编辑部分*/
function edit(data){
    var params = {
        name:data.name
    };
    postRequest(params,manages,eQuery,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_school_id").value = isNull(obj.school_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_influence").value = isNull(obj.influence);
                document.getElementById("edit_info").value = isNull(obj.info);
                var content = $("#editInfo");
                layerOpen("编辑",content,900,450,
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
        school_id:$("#edit_school_id").val(),
        name:$("#edit_name").val(),
        influence:Number($("#edit_influence").val()),
        info:$("#edit_info").val()
    };
    if(params.name===null || params.name===""){
        layer.msg("门派名称不能为空",{icon:2});
        return;
    }
    layer.confirm('请确认要修改这条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            postRequest(params,manages,renew,function (data){
                if (data.code === "0" && data.result === "SUCCESS") {
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
    $("#edit_name").val('');
    $("#edit_influence").val('');
    $("#edit_info").val('');
}



/**删除部分*/
function del(data){
    if(data.length <= 0){
        layer.msg("请勾选一条数据进行删除");
        return;
    }
    layer.confirm('请确定要删除这'+data.length+'条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            var params = {
                data:data
            };
            postRequest(params,manages,strike,function (data){
                if (data.code === "0" && data.result === "SUCCESS") {
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