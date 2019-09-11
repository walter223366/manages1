charset="utf-8";
manages="effect";


/**查询部分*/
$(function(){
    pagingQuery()
});
function pagingQuery(){
    $("#dataInfo").empty();
    var params = {
        name:$("#query_name").val(),
        target:$("#query_target").val()
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
            title:'效果管理',
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
                    {type:'numbers',title:'序号',align:'center',fixed:'felt'},
                    {field:'name',title:'效果名称',sort:true},
                    {field:'target',title:'执行目标'},
                    {field:'Aggressivity',title:'攻击力'},
                    {field:'Defense',title:'防御力'},
                    {field:'burst',title:'暴击力'},
                    {field:'hp',title:'HP影响',sort:true},
                    {field:'injury',title:'外伤'},
                    {field:'internal_injury',title:'内伤'},
                    {field:'poisoning',title:'毒伤'},
                    {field:'suck_HP',title:'吸血',sort:true},
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
    $("#query_target").val("");
    layui.form.render("select");
    pagingQuery();
}


/**新增部分*/
function add() {
    addReset();
    var content = $("#addInfo");
    layerOpen("新增",content,1050,500,
        function () {
            addRequest();
        },function (index,layero) {
            addReset();
        });
}
function hp(){
    alert("开始");

}
//新增-请求
function addRequest() {
    var params = {
        name:$("#add_name").val(),
        target:Number($("#add_target").val()),
        hp:Number($("#add_hp").val()),
        mp_yellow:$("#add_mp_yellow").val(),
        mp_gold:$("#add_mp_gold").val(),
        mp_green:$("#add_mp_green").val(),
        mp_blue:$("#add_mp_blue").val(),
        mp_purple:$("#add_mp_purple").val(),
        Aggressivity:Number($("#add_aggressivity").val()),
        Defense:Number($("#add_defense").val()),
        burst:Number($("#add_burst").val()),
        injury:Number($("#add_injury").val()),
        internal_injury:Number($("#add_internal_injury").val()),
        poisoning:Number($("#add_poisoning").val()),
        stun:Number($("#add_stun").val()),
        dodge:Number($("#add_dodge").val()),
        hit_rate:Number($("#add_hit_rate").val()),
        crit_rate:Number($("#add_crit_rate").val()),
        suck_HP:Number($("#add_suck_HP").val()),
        suck_mp_yellow:$("#add_suck_mp_yellow").val(),
        suck_mp_gold:$("#add_suck_mp_gold").val(),
        suck_mp_green:$("#add_suck_mp_green").val(),
        suck_mp_blue:$("#add_suck_mp_blue").val(),
        suck_mp_purple:$("#add_suck_mp_purple").val(),
        info:$("#add_info").val()
    };
    if(params.name===null || params.name===""){
        layer.msg("效果名称不能为空",{icon:2});
        return ;
    }
    if(params.target===null || params.target===""){
        layer.msg("效果执行目标不能为空",{icon:2});
        return ;
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
    $("#add_hp").val('');
    $("#add_mp_yellow").val('');
    $("#add_mp_gold").val('');
    $("#add_mp_green").val('');
    $("#add_mp_blue").val('');
    $("#add_mp_purple").val('');
    $("#add_aggressivity").val('');
    $("#add_defense").val('');
    $("#add_burst").val('');
    $("#add_injury").val('');
    $("#add_internal_injury").val('');
    $("#add_poisoning").val('');
    $("#add_stun").val('');
    $("#add_dodge").val('');
    $("#add_hit_rate").val('');
    $("#add_crit_rate").val('');
    $("#add_suck_HP").val('');
    $("#add_suck_mp_yellow").val('');
    $("#add_suck_mp_gold").val('');
    $("#add_suck_mp_green").val('');
    $("#add_suck_mp_blue").val('');
    $("#add_suck_mp_purple").val('');
    $("#add_info").val('');
}


/**查看部分*/
function see(data){
    document.getElementById("see_name").value = isNull(data.name);
    document.getElementById("see_target").value = isNull(data.target);
    document.getElementById("see_hp").value = isNull(data.hp);
    document.getElementById("see_mp_yellow").value = isNull(data.mp_yellow);
    document.getElementById("see_mp_gold").value = isNull(data.mp_gold);
    document.getElementById("see_mp_green").value = isNull(data.mp_green);
    document.getElementById("see_mp_blue").value = isNull(data.mp_blue);
    document.getElementById("see_mp_purple").value = isNull(data.mp_purple);
    document.getElementById("see_aggressivity").value = isNull(data.Aggressivity);
    document.getElementById("see_defense").value = isNull(data.Defense);
    document.getElementById("see_burst").value = isNull(data.burst);
    document.getElementById("see_injury").value = isNull(data.injury);
    document.getElementById("see_internal_injury").value = isNull(data.internal_injury);
    document.getElementById("see_poisoning").value = isNull(data.poisoning);
    document.getElementById("see_stun").value = isNull(data.stun);
    document.getElementById("see_dodge").value = isNull(data.dodge);
    document.getElementById("see_hit_rate").value = isNull(data.hit_rate);
    document.getElementById("see_crit_rate").value = isNull(data.crit_rate);
    document.getElementById("see_suck_HP").value = isNull(data.suck_HP);
    document.getElementById("see_suck_mp_yellow").value = isNull(data.suck_mp_yellow);
    document.getElementById("see_suck_mp_gold").value = isNull(data.suck_mp_gold);
    document.getElementById("see_suck_mp_green").value = isNull(data.suck_mp_green);
    document.getElementById("see_suck_mp_blue").value = isNull(data.suck_mp_blue);
    document.getElementById("see_suck_mp_purple").value = isNull(data.suck_mp_purple);
    document.getElementById("see_info").value = isNull(data.info);
    var content = $("#seeInfo");
    layerSeeOpen("查看详情",content,1050,500);
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
                document.getElementById("edit_effect_id").value = isNull(obj.effect_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_hp").value = isNull(obj.hp);
                document.getElementById("edit_mp_yellow").value = isNull(obj.mp_yellow);
                document.getElementById("edit_mp_gold").value = isNull(obj.mp_gold);
                document.getElementById("edit_mp_green").value = isNull(obj.mp_green);
                document.getElementById("edit_mp_blue").value = isNull(obj.mp_blue);
                document.getElementById("edit_mp_purple").value = isNull(obj.mp_purple);
                document.getElementById("edit_aggressivity").value = isNull(obj.Aggressivity);
                document.getElementById("edit_defense").value = isNull(obj.Defense);
                document.getElementById("edit_burst").value = isNull(obj.burst);
                document.getElementById("edit_injury").value = isNull(obj.injury);
                document.getElementById("edit_internal_injury").value = isNull(obj.internal_injury);
                document.getElementById("edit_poisoning").value = isNull(obj.poisoning);
                document.getElementById("edit_stun").value = isNull(obj.stun);
                document.getElementById("edit_dodge").value = isNull(obj.dodge);
                document.getElementById("edit_hit_rate").value = isNull(obj.hit_rate);
                document.getElementById("edit_crit_rate").value = isNull(obj.crit_rate);
                document.getElementById("edit_suck_HP").value = isNull(obj.suck_HP);
                document.getElementById("edit_suck_mp_yellow").value = isNull(obj.suck_mp_yellow);
                document.getElementById("edit_suck_mp_gold").value = isNull(obj.suck_mp_gold);
                document.getElementById("edit_suck_mp_green").value = isNull(obj.suck_mp_green);
                document.getElementById("edit_suck_mp_blue").value = isNull(obj.suck_mp_blue);
                document.getElementById("edit_suck_mp_purple").value = isNull(obj.suck_mp_purple);
                document.getElementById("edit_info").value = isNull(obj.info);
                $("#edit_target").val(String(obj.target));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑",content,1050,500,
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
        effect_id:$("#edit_effect_id").val(),
        name:$("#edit_name").val(),
        target:Number($("#edit_target").val()),
        hp:Number($("#edit_hp").val()),
        mp_yellow:$("#edit_mp_yellow").val(),
        mp_gold:$("#edit_mp_gold").val(),
        mp_green:$("#edit_mp_green").val(),
        mp_blue:$("#edit_mp_blue").val(),
        mp_purple:$("#edit_mp_purple").val(),
        Aggressivity:Number($("#edit_aggressivity").val()),
        Defense:Number($("#edit_defense").val()),
        burst:Number($("#edit_burst").val()),
        injury:Number($("#edit_injury").val()),
        internal_injury:Number($("#edit_internal_injury").val()),
        poisoning:Number($("#edit_poisoning").val()),
        stun:Number($("#edit_stun").val()),
        dodge:Number($("#edit_dodge").val()),
        hit_rate:Number($("#edit_hit_rate").val()),
        crit_rate:Number($("#edit_crit_rate").val()),
        suck_HP:Number($("#edit_suck_HP").val()),
        suck_mp_yellow:$("#edit_suck_mp_yellow").val(),
        suck_mp_gold:$("#edit_suck_mp_gold").val(),
        suck_mp_green:$("#edit_suck_mp_green").val(),
        suck_mp_blue:$("#edit_suck_mp_blue").val(),
        suck_mp_purple:$("#edit_suck_mp_purple").val(),
        info:$("#edit_info").val()
    };
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
    $("#edit_hp").val('');
    $("#edit_mp_yellow").val('');
    $("#edit_mp_gold").val('');
    $("#edit_mp_green").val('');
    $("#edit_mp_blue").val('');
    $("#edit_mp_purple").val('');
    $("#edit_aggressivity").val('');
    $("#edit_defense").val('');
    $("#edit_burst").val('');
    $("#edit_injury").val('');
    $("#edit_internal_injury").val('');
    $("#edit_poisoning").val('');
    $("#edit_stun").val('');
    $("#edit_dodge").val('');
    $("#edit_hit_rate").val('');
    $("#edit_crit_rate").val('');
    $("#edit_suck_HP").val('');
    $("#edit_suck_mp_yellow").val('');
    $("#edit_suck_mp_gold").val('');
    $("#edit_suck_mp_green").val('');
    $("#edit_suck_mp_blue").val('');
    $("#edit_suck_mp_purple").val('');
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