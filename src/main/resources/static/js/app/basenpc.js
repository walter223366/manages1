charset="utf-8";
manages="baseNpc";


/**查询部分*/
$(function(){
    pagingQuery();
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {};
    params.nickname=$("#query_nickname").val();
    params.enable=$("#query_enable").val();
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
            title:'人物管理',
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
    $("#query_nickname").val('');
    $("#query_enable").val('');
    layui.form.render("select");
    pagingQuery();
}


/**新增部分*/
function add(){
    addReset();
    addUser();
    addSchool();
    var content =  $("#addInfo");
    layerOpen("新增",content,1000,500,
        function () {
            addRequest();
        },function (index,layero) {
            addReset();
        });
}
//新增-请求
function addRequest(){
    var params = {};
    params.nickname=$("#add_nickname").val();
    params.school_id=$("#add_school").val();
    params.sex=Number($("#add_sex").val());
    params.level=Number($("#add_level").val());
    params.attitude=Number($("#add_attitude").val());
    params.character=Number($("#add_character").val());
    params.popularity=Number($("#add_popularity").val());
    params.coin=Number($("#add_coin").val());
    params.gold=Number($("#add_gold").val());
    params.school_contribution=Number($("#add_school_contribution").val());
    params.enable=$("#add_enable").val();
    if(params.nickname===null || params.nickname===""){
        layer.msg("人物名称不能为空",{icon:2});
        return;
    }
    if(params.school_id===null || params.school_id===""){
        layer.msg("所属门派不能为空",{icon:2});
        return;
    }
    if(params.level <= 0 ){
        layer.msg("等级不能为空或小于1",{icon:2});
        return;
    }
    params.experience = Number(calExperience(params.level));
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
$(function () {
    $("#add_level").on("input",function (e) {
        var obj = e.delegateTarget.value;
        calExperience(obj);
    });
});
//根据等级计算经验值
function calExperience(level){
    var experience = level*100;
    document.getElementById("add_experience").value = isNull(experience);
    return experience;
}
//新增-账号下拉框（单选）
function addUser(){
    document.getElementById("add_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
//新增-门派下拉框（单选）
function addSchool(){
    document.getElementById("add_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
$(function () {
    layui.use('transfer', function(){
        var transfer = layui.transfer;
        transfer.render({
            elem: '#addKongFu'  //绑定元素
            ,data: [
                {"value": "1", "title": "李白", "disabled": "", "checked": ""},
                {"value": "2", "title": "杜甫", "disabled": "", "checked": ""},
                {"value": "3", "title": "贤心", "disabled": "", "checked": ""}
            ]
            ,id: 'demo1' //定义索引
            ,width:150
            ,height:100
        });
    });
});
//新增-重置
function addReset(){
    $("#add_nickname").val('');
    $("#add_level").val('');
    $("#add_attitude").val('');
    $("#add_character").val('');
    $("#add_popularity").val('');
    $("#add_coin").val('');
    $("#add_gold").val('');
    $("#add_experience").val('');
    $("#add_school_contribution").val('');
    $("#add_img").val('');
    $("#add_enable").val(1);
    $("#add_sex").val(1);
    layui.form.render("select");
}



/**查看部分*/
function see(data) {
    var params = {
        nickname:data.nickname
    };
    postRequest(params,manages,seeDetails,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("see_nickname").value = isNull(obj.nickname);
                document.getElementById("see_level").value = isNull(obj.level);
                document.getElementById("see_attitude").value = isNull(obj.attitude);
                document.getElementById("see_character").value = isNull(obj.character);
                document.getElementById("see_popularity").value = isNull(obj.popularity);
                document.getElementById("see_coin").value = isNull(obj.coin);
                document.getElementById("see_gold").value = isNull(obj.gold);
                document.getElementById("see_experience").value = isNull(obj.experience);
                document.getElementById("see_school_contribution").value = isNull(obj.school_contribution);
                document.getElementById("see_school").value = isNull(obj.schoolName);
                document.getElementById("see_sex").value = isNull(obj.sex);
                document.getElementById("see_enable").value = isNull(obj.enable);
                document.getElementById("see_user").value = isNull(obj.userName);
                var content = $("#seeInfo");
                layerSeeOpen("查看详情",content,1000,500);
            }
        }else{
            layer.msg(data.msg,{icon:2});
        }
    });
}



/**编辑部分*/
function edit(data){
    editSchool();
    var params = {
        nickname:data.nickname
    };
    postRequest(params,manages,eQuery,function (data){
        if(data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if(isJSON(rows)){
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.id);
                document.getElementById("edit_nickname").value = isNull(obj.nickname);
                document.getElementById("edit_level").value = isNull(obj.level);
                document.getElementById("edit_attitude").value = isNull(obj.attitude);
                document.getElementById("edit_character").value = isNull(obj.character);
                document.getElementById("edit_popularity").value = isNull(obj.popularity);
                document.getElementById("edit_coin").value = isNull(obj.coin);
                document.getElementById("edit_gold").value = isNull(obj.gold);
                document.getElementById("edit_experience").value = isNull(obj.experience);
                document.getElementById("edit_school_contribution").value = isNull(obj.school_contribution);
                $("#edit_school").val(obj.school_id);
                $("#edit_sex").val(String(obj.sex));
                $("#edit_enable").val(String(obj.enable));
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
//编辑-门派下拉框（单选）
function editSchool(){
    document.getElementById("edit_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#edit_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
function editReset() {
    $("#edit_nickname").val('');
    $("#edit_level").val('');
    $("#edit_attitude").val('');
    $("#edit_character").val('');
    $("#edit_popularity").val('');
    $("#edit_coin").val('');
    $("#edit_gold").val('');
    $("#edit_experience").val('');
    $("#edit_school_contribution").val('');
    $("#edit_sex").val(1);
    $("#edit_enable").val(1);
    layui.form.render("select");
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