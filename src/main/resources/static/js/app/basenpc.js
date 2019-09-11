charset="utf-8";
manages="baseNpc";

/**查询部分*/
$(function(){
    //addKongFu();
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        nickname:$("#query_nickname").val(),
        //schoolId:$("#query_school").val(),
        enable:$("#query_enable").val()
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
            toolbar:'#toolbarBomb',
            id:'#dataInfo',
            title:'人物管理',
            even:true,
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
                    {type:'numbers',title:'序号',align:'center',fixed:'felt',width:100},
                    {field:'nickname',title:'人物名称',width:100},
                    {field:'schoolName',title:'所属门派',width:100},
                    {field:'sex',title:'性别'},
                    {field:'level',title:'等级',sort:true},
                    {field:'attitude',title:'态度'},
                    {field:'character',title:'性格'},
                    {field:'popularity',title:'声望',sort:true},
                    {field:'experience',title:'经验值'},
                    {field:'school_contribution',title:'门派贡献值',width:100},
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
//查询-门派下拉框（单选）
function addKongFu(){
    document.getElementById("query_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#query_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}