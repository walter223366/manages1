charset="utf-8";
manages="move";
//layer下拉框组件
var formSelects = layui.formSelects;


/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        name:$("#query_name").val(),
        sexperience:Number($("#query_sExp").val()),
        eexperience:Number($("#query_eExp").val())
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
            title:'招式管理',
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
                    {field:'name',title:'招式名称',sort:true},
                    {field:'kongFuName',title:'武学类型'},
                    {field:'MP_cost',title:'内力花费'},
                    {field:'zhaoshi_experience_cost',title:'武学经验',sort:true},
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
function cleanUp() {
    $("#query_name").val('');
    $("#query_sExp").val('');
    $("#query_eExp").val('');
    pagingQuery();
}



/**新增部分*/
function add(){
    addReset();
    addKongFu();
    addEffect();
    var content = $("#addInfo");
    layerOpen("新增",content,1000,500,
        function () {
            addRequest();
        },function (index,layero) {
            addReset();
        });
}
//新增-功夫下拉框（单选框）
function addKongFu(){
    document.getElementById("add_kongFu").options.length = 0;
    var params = {};
    postRequest(params,manages,mKongFuBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_kongFu").append("<option value='"+n.kongfu_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
                //layui.formSelects.config('select_addKongFu',{direction:'down'});
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//新增-效果下拉框
function addEffect(){
    document.getElementById("add_effect").options.length = 0;
    var params = {};
    postRequest(params,manages,mEffectBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_effect").append("<option value='"+n.effect_id+"'>"+n.name+"</option>");
                });
                layui.formSelects.config('select_addEffect',{direction:'down'});
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//新增-请求
function addRequest() {
    var formSelects = layui.formSelects;
    var zhaoshi_effect = formSelects.value('select_addEffect', 'valStr');
    var params = {
        name:$("#add_name").val(),
        kongfu_id:$("#add_kongFu").val(),
        zhaoshi_effect:zhaoshi_effect,
        yellowSpend:Number($("#add_yellowSpend").val()),
        goldSpend:Number($("#add_goldSpend").val()),
        greenSpend:Number($("#add_greenSpend").val()),
        blueSpend:Number($("#add_blueSpend").val()),
        purpleSpend:Number($("#add_purpleSpend").val()),
        zhaoshi_experience_cost:Number($("#add_exp").val()),
        info:$("#add_info").val()
    };
    var tver = new RegExp("[0-9]");
    if(!tver.test(params.yellowSpend) || !tver.test(params.goldSpend) || !tver.test(params.greenSpend)
        || !tver.test(params.blueSpend) || !tver.test(params.purpleSpend)){
        layer.msg("内力花费格式错误",{icon: 2});
        return false;
    }
    if(params.name===null || params.name===""){
        layer.msg("招式名称不能为空",{icon:2});
        return;
    }
    if(params.kongfu_id===null || params.kongfu_id===""){
        layer.msg("功夫选项不能为空",{icon:2});
        return;
    }
    if(params.zhaoshi_effect===null || params.zhaoshi_effect===""){
        layer.msg("效果选项不能为空",{icon:2});
        return;
    }
    layer.confirm('请确认要新增这条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            postRequest(params,manages,increase,function (data){
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("新增成功");
                    pagingQuery();
                } else {
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
    $("#add_name").val('');
    $("#add_kongFu").val('');
    $("#add_yellowSpend").val('');
    $("#add_goldSpend").val('');
    $("#add_greenSpend").val('');
    $("#add_blueSpend").val('');
    $("#add_purpleSpend").val('');
    $("#add_zhaoshi_effect").val('');
    $("#add_zhaoshi_experience_cost").val('');
    $("#add_info").val('');
    $("#add_exp").val('');
    formSelects.btns('select_addKongFu','remove');
    formSelects.btns('select_addEffect','remove');
}


/**查看部分*/
function see(data) {
    var params = {
        name:data.name
    };
    postRequest(params,manages,seeDetails,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("see_name").value = isNull(obj.name);
                document.getElementById("see_cost").value = isNull(obj.MP_cost);
                document.getElementById("see_exp").value = isNull(obj.zhaoshi_experience_cost);
                document.getElementById("see_kongFu").value = isNull(obj.kongFuName.toString());
                document.getElementById("see_effect").value = isNull(obj.effectName.toString());
                document.getElementById("see_info").value = isNull(obj.info);
                var content = $("#seeInfo");
                layerSeeOpen("查看详情",content,1000,500);
            }
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}


/**编辑部分*/
function edit(data) {
    editKongFu();
    editEffect();
    var params = {
        name:data.name
    };
    postRequest(params,manages,eQuery,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.zhaoshi_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_exp").value = isNull(obj.zhaoshi_experience_cost);
                document.getElementById("edit_info").value = isNull(obj.info);
                $("#edit_kongFu").val(String(obj.kongfu_id));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑",content,1000,500,
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
//编辑-功夫下拉框（单选框）
function editKongFu(){
    document.getElementById("edit_kongFu").options.length = 0;
    var params = {};
    postRequest(params,manages,mKongFuBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#edit_kongFu").append("<option value='"+n.kongfu_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-效果下拉框
function editEffect(){
    document.getElementById("edit_effect").options.length = 0;
    var params = {};
    postRequest(params,manages,mEffectBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#edit_effect").append("<option value='"+n.effect_id+"'>"+n.name+"</option>");
                });
                layui.formSelects.config('select_editEffect',{direction:'down'});
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-请求
function editRequest() {
    //var kongfu_id = formSelects.value('select_editKongFu', 'valStr');
    var zhaoshi_effect = formSelects.value('select_editEffect', 'valStr');
    var params = {
        zhaoshi_id:$("#edit_id").val(),
        kongfu_id:$("#edit_kongFu").val(),
        name:$("#edit_name").val(),
        MP_cost:$("#edit_spend").val(),
        info:$("#edit_info").val(),
        zhaoshi_effect:zhaoshi_effect,
        zhaoshi_experience_cost:Number($("#edit_exp").val())
    };
    layer.confirm('请确认要修改这条数据吗？', {
            btn: ['确定','取消']
        }, function(){
            postRequest(params,manages,renew,function (data){
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("修改成功");
                    pagingQuery();
                } else {
                    layer.msg(data.msg,{icon:2});
                }
            });
        }, function(){
            layer.close();
        }
    );
}
//编辑-重置
function editReset() {
    $("#edit_name").val('');
    $("#edit_spend").val('');
    $("#edit_info").val('');
    $("#edit_exp").val('');
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