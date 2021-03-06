charset="utf-8";
manages="move";
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.sexperience = Number($("#query_sExp").val());
    params.eexperience = Number($("#query_eExp").val());
    params.type = $("#query_type").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 60},
        {field: 'name', title: '招式名称'},
        {field: 'typeValue', title: '招式种类'},
        {field: 'kongFuName', title: '所属武学'},
        {field: 'MP_cost', title: '内力花费'},
        {field: 'zhaoshi_experience_cost', title: '招式经验', sort: true},
        {fixed: 'right', title: '操作', width: 240, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "招式管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    $("#query_sExp").val('');
    $("#query_eExp").val('');
    $("#query_type").val('');
    layui.form.render("select");
    pagingQuery();
}

function add() {
    addReset();
    var downs = {};
    downBox(downs, effectBox, "add_zhaoshi_effect", "select_addEffect", mSelection);
    //downBox(downs, '', "add_zhaoshi_buff", "select_addBuff", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 950, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_effect = layui.formSelects.value('select_addEffect', 'valStr');
            //var move_buff = layui.formSelects.value('select_addBuff', 'valStr');
            //params.zhaoshi_buff = move_buff;
            params.name = $("#add_name").val();
            params.zhaoshi_effect = move_effect;
            params.zhaoshi_experience_cost = Number($("#add_zhaoshi_experience_cost").val());
            params.MP_cost = $("#add_MP_cost").val();
            params.info = $("#add_info").val();
            params.Attacks = $("#add_Attacks").val();
            params.type = $("#add_type").val();
            if (verIfy(params) === false) {
                return;
            }
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

//todo 多选项暂无法清空
function addReset() {
    $("#add_name").val('');
    $("#add_zhaoshi_experience_cost").val(0);
    $("#add_MP_cost").val('');
    $("#add_info").val('');
    $("#add_Attacks").val('');
    $("#add_type").val('');
    layui.formSelects.closed("add_zhaoshi_effect");
    layui.form.render("select");
}

function see(data) {
    var params = {name: data.name};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var content = $("#seeInfo");
                layerOpen(1, "查看详情", content, 900, 500, "明白了", "关闭",
                    function (index, layero) {
                        var obj = JSON.parse(rows);
                        $("#see_name").val(isNull(obj.name));
                        $("#see_zhaoshi_experience_cost").val(isNull(obj.zhaoshi_experience_cost));
                        $("#see_kongFu").val(isNull(obj.kongFuName));
                        $("#see_MP_cost").val(isNull(obj.MP_cost));
                        $("#see_zhaoshi_effect").val(isNull(obj.effectName));
                        $("#see_zhaoshi_buff").val(isNull(obj.zhaoshi_buff));
                        $("#see_info").val(isNull(obj.info));
                        $("#see_Attacks").val(isNull(obj.Attacks));
                        $("#see_type").val(isNull(obj.typeValue));
                    }, function (index, layero) {
                        layer.closeAll();
                    }, function (index, layero) {
                        layer.closeAll();
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

function edit(data) {
    var downs = {};
    downBox(downs, kongFuBox, "edit_kongFu", "", lSelection);
    downBox(downs, effectBox, "edit_zhaoshi_effect", "select_editEffect", mSelection);
    var params = {name: data.name};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 950, 500, "立即提交", "重置",
                    function (index, layero) {
                        $("#edit_name").val(isNull(obj.name));
                        $("#edit_zhaoshi_experience_cost").val(obj.zhaoshi_experience_cost);
                        $("#edit_kongFu").val(isNull(obj.kongfu_id));
                        $("#edit_MP_cost").val(isNull(obj.MP_cost));
                        $("#edit_zhaoshi_buff").val(isNull(obj.zhaoshi_buff));
                        $("#edit_info").val(isNull(obj.info));
                        $("#edit_type").val(obj.type);
                        $("#edit_Attacks").val(obj.Attacks);
                        var effIds = multipleBox(obj.zhaoshi_effect);
                        if (effIds.length > 0) {
                            layui.formSelects.value("select_editEffect", effIds);
                        }
                        layui.form.render("select");
                    }, function (index, layero) {
                        var params = {};
                        var move_effect = layui.formSelects.value('select_editEffect', 'valStr');
                        params.zhaoshi_id = obj.zhaoshi_id;
                        params.name = $("#edit_name").val();
                        params.kongfu_id = $("#edit_kongFu").val();
                        params.zhaoshi_effect = move_effect;
                        params.zhaoshi_experience_cost = Number($("#edit_zhaoshi_experience_cost").val());
                        params.MP_cost = $("#edit_MP_cost").val();
                        params.info = $("#edit_info").val();
                        params.type = $("#edit_type").val();
                        params.Attacks = $("#edit_Attacks").val();
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_kongFu").val('');
                        $("#edit_zhaoshi_experience_cost").val(0);
                        $("#edit_MP_cost").val('');
                        $("#edit_info").val('');
                        $("#edit_type").val('');
                        $("#edit_Attacks").val('');
                        layui.form.render("select");
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

function verIfy(params) {
    if (params.name === null || params.name === "") {
        layer.msg("招式名称不能为空", {icon: 2});
        return false;
    }
    if (parmas.type === null || params.type === "") {
        layer.msg("招式种类不能为空", {icon: 2});
        return false;
    }
    if (params.MP_cost === null || params.MP_cost === "") {
        layer.msg("内力花费不能为空", {icon: 2});
        return false;
    }
    return true;
}