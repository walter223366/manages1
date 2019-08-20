charset="utf-8";
var pagingQueryUrl="/kongFu/pagingQuery";//分页查询
var batchDelUrl="/kongFu/inBatchDelete";//批量删除
var moveUrl="/kongFu/moveDownBox";//招式下拉框查询
var addUrl="/kongFu/inAdd";//新增
var seeUrl="/kongFu/inSee";//查看
var editQueryUrl="/kongFu/editQuery";//编辑查询
var editUrl="/kongFu/inUpdate";//修改
var delUrl="/kongFu/inDelete";//删除

//layer多选框组件
var formSelects=layui.formSelects;


/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        name:$("#query_name").val(),
        type:$("#query_type").val(),
        enable:$("#query_enable").val()
    };
    layui.use(['layer','table'],function(){
        var layer = layui.layer,table = layui.table;
        table.render({
            elem:'#dataInfo',
            url:pagingQueryUrl,
            method:'POST',
            contentType:"application/json",
            toolbar:'#toolbarBomb',
            id:'#dataInfo',
            title:'武学管理',
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
                    {field:'name',title:'功夫名称',sort:true},
                    {field:'type',title:'功夫类型'},
                    {field:'experience_limit',title:'可学经验上限',sort:true},
                    {field:'kongfu_attainments',title:'造诣',sort:true},
                    {field:'enable',title:'状态'},
                    {fixed:'right',title:'操作',width:300,align:'center',toolbar:'#operational'}
                ]
            ]
        });
        //批量删除、新增
        table.on('checkbox(dataInfo)',function(){
            table.on('toolbar(dataInfo)',function (obj){
                var checkStatus = table.checkStatus(obj.config.id);
                if(obj.event === 'bombDelete'){
                    batchDel(checkStatus);
                }else if(obj.event === 'bombAdd'){
                    add();
                }
            });
        });
        //查看、编辑、删除
        table.on('tool(dataInfo)',function (obj){
            var data = obj.data;
            if(obj.event === 'see'){
                see(data);
            }else if (obj.event === 'edit'){
                edit(data);
            }else if (obj.event === 'del'){
                del(data);
            }
        });
        //新增、勾选批量删除
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
    $("#query_name").val('');
    $("#query_type").val("");
    $("#query_enable").val("");
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
    addMove();
    var content = $("#addInfo");
    layerOpen("新增",content,1000,500);
}
//新增-招式下拉框
function addMove(){
    document.getElementById("add_move").options.length = 0;
    getRequest(moveUrl,function (data){
        if (data.code === "0" && data.result === "SUCCESS"){
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_move").append("<option value='"+n.zhaoshi_id+"'>"+n.name+"</option>");
                });
                layui.formSelects.config('select_addMove',{direction:'down'});
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//新增-请求
function addRequest(){
    var move_id = formSelects.value('select_addMove','valStr');
    var params = {
        name:$("#add_name").val(),
        type:Number($("#add_type").val()),
        info:$("#add_info").val(),
        experience_limit:Number($("#add_exp").val()),
        kongfu_attainments:$("#add_attainments").val(),
        enable:$("#add_enable").val(),
        kongfu_zhaoshi:move_id
    };
    if(params.name===null || params.name===""){
        layer.msg("功夫名称不能为空",{icon:2});
        return;
    }
    if(params.type===null || params.type===""){
        layer.msg("功夫类型不能为空",{icon:2});
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
function addReset() {
    $("#add_name").val('');
    $("#add_info").val('');
    $("#add_exp").val(100);
    $("#add_attainments").val('');
}


/**查看*/
function see(data) {
    var params = {
        name: data.name
    };
    postRequest(seeUrl,params,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = $("#seeInfo");
                document.getElementById("see_name").value = isNull(obj.name);
                document.getElementById("see_type").value = isNull(obj.type);
                document.getElementById("see_exp").value = isNull(obj.experience_limit);
                document.getElementById("see_attainments").value = isNull(obj.kongfu_attainments);
                document.getElementById("see_enable").value = isNull(obj.enable);
                document.getElementById("see_info").value = isNull(obj.info);
                document.getElementById("see_move").value = isNull(obj.moveName.toString());
                layerSeeOpen("查看详情",content,1000,500);
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}


/**编辑部分*/
function edit(data) {
    var params = {
        name:data.name
    };
    postRequest(editQueryUrl,params,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)) {
                var obj = JSON.parse(rows);
                editMove();
                document.getElementById("edit_id").value = isNull(obj.kongfu_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_exp").value = isNull(obj.experience_limit);
                document.getElementById("edit_attainments").value = isNull(obj.kongfu_attainments);
                document.getElementById("edit_info").value = isNull(obj.info);
                $("#edit_type").val(String(obj.type));
                $("#edit_enable").val(String(obj.enable));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑",content,1000,500);
            }
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-招式下拉框
function editMove(){
    document.getElementById("edit_move").options.length = 0;
    getRequest(moveUrl,function (data){
        if (data.code === "0" && data.result === "SUCCESS"){
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#edit_move").append("<option value='"+n.zhaoshi_id+"'>"+n.name+"</option>");
                });
                layui.formSelects.config('select_editMove',{direction:'down'});
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-请求
function editRequest() {
    var move_id = formSelects.value('select_editMove','valStr');
    var params = {
        kongfu_id:$("#edit_id").val(),
        name:$("#edit_name").val(),
        info:$("#edit_info").val(),
        type:Number($("#edit_type").val()),
        experience_limit:Number($("#edit_exp").val()),
        kongfu_attainments:$("#edit_attainments").val(),
        enable:Number($("#edit_enable").val()),
        kongfu_zhaoshi:move_id
    };
    if(params.name===null || params.name===""){
        layer.msg("功夫名称不能为空",{icon:2});
        return;
    }
    if(params.type===null || params.type===""){
        layer.msg("功夫类型不能为空",{icon:2});
    }
    postRequest(editUrl,params,function (data){
        if (data.code === "0" && data.result === "SUCCESS"){
            layer.msg("修改成功");
            pagingQuery();
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-重置
function editReset() {
    $("#edit_name").val('');
    $("#edit_exp").val(100);
    $("#edit_attainments").val('');
    $("#edit_info").val('');
}


/**删除部分*/
function del(data) {
    var params = {
        name:data.name
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
