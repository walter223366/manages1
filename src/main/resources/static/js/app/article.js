charset="utf-8";
var pagingQueryUrl="/article/pagingQuery";//分页查询
var batchDelUrl="/article/inBatchDelete";//批量删除
var addUrl="/article/inAdd";//新增
var editQueryUrl="/article/editQuery";//编辑查询
var editUrl="/article/inUpdate";//修改
var delUrl="/article/inDelete";//删除

/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery() {
    $("#dataInfo").empty();
    var params = {
        name:$("#query_name").val()
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
            title:'物品管理',
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
                    {field:'name',title:'物品名称',sort:true},
                    {field:'img',title:'物品图标'},
                    {fixed:'right',title:'操作',width:300,align:'center',toolbar:'#operational'}
                ]
            ]
        });
        //批量删除、新增
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
function cleanUp() {
    $("#query_name").val('');
    pagingQuery();
}


/**批量删除*/
function  batchDel(checkStatus){
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


/**新增部分*/
function add(){
    addReset();
    var content = $("#addInfo");
    layerOpen("新增",content,1000,500);
}
//新增-请求
function addRequest(){
    var params = {
        name:$("#add_name").val(),
        img:$("#add_img").val(),
        info:$("#add_info").val()
    };
    if(params.name===null || params.name===""){
        layer.msg("物品名称不能为空",{icon:2});
        return;
    }
    if(params.img===null || params.img===""){
        layer.msg("物品图标不能为空",{icon:2});
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
    $("#add_img").val('');
}


/**查看部分*/
function see(data) {
    document.getElementById("see_name").value = isNull(data.name);
    document.getElementById("see_img").value = isNull(data.img);
    document.getElementById("see_info").value = isNull(data.info);
    var content = $("#seeInfo");
    layerSeeOpen("查看详情",content,1000,500);
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
                document.getElementById("edit_id").value = isNull(obj.article_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_img").value = isNull(obj.img);
                document.getElementById("edit_info").value = isNull(obj.info);
                var content = $("#editInfo");
                layerOpen("编辑",content,1000,500);
            }
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}
//编辑-请求
function editRequest() {
    var params = {
        article_id:$("#edit_id").val(),
        name:$("#edit_name").val(),
        info:$("#edit_info").val(),
        img:$("#edit_img").val()
    };
    if(params.name===null || params.name===""){
        layer.msg("物品名称不能为空",{icon:2});
        return;
    }
    if(params.img===null || params.img===""){
        layer.msg("物品图标不能为空",{icon:2});
    }
    postRequest(editUrl,params,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
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
    $("#edit_img").val('');
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

/**上传文件组件*/
layui.use('upload', function(){
    //TODO
    var $ = layui.jquery,upload = layui.upload;
    upload.render({
        elem: '#add_img',
        url: '/upload/',
        done: function (res) {
            console.log(res)
        }
    });
});