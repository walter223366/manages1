charset="utf-8";
manages="move";
var formSelects = layui.formSelects;
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.sexperience = Number($("#query_sExp").val());
    params.eexperience = Number($("#query_eExp").val());
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt'},
        {field: 'name', title: '招式名称', sort: true},
        {field: 'kongFuName', title: '武学类型'},
        {field: 'MP_cost', title: '内力花费'},
        {field: 'zhaoshi_experience_cost', title: '招式经验', sort: true},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "招式管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    $("#query_sExp").val('');
    $("#query_eExp").val('');
    pagingQuery();
}

function add() {
    addReset();
    var downs = {};
    downBox(downs, kongFuBox, "add_kongFu", "", lSelection);
    downBox(downs, effectBox, "add_zhaoshi_effect", "select_addEffect", mSelection);
    downBox(downs, '', "add_zhaoshi_buff", "select_addEffect", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 1000, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_effect = formSelects.value('select_addEffect', 'valStr');
            var move_buff = formSelects.value('select_addBuff', 'valStr');
            params.name = $("#add_name").val();
            params.kongfu_id = $("#add_kongFu").val();
            params.zhaoshi_effect = move_effect;
            params.zhaoshi_buff = move_buff;
            params.zhaoshi_experience_cost = Number($("#add_exp").val());
            params.info = $("#add_info").val();
            params.MP_cost = $("#add_MP_cost").val();
            if (verIfy(params) === false) {
                return;
            }
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

function addReset() {
    $("#add_name").val('');
    $("#add_kongFu").val('');
    $("#add_zhaoshi_effect").val('');
    $("#add_zhaoshi_buff").val('');
    $("#add_zhaoshi_experience_cost").val('');
    $("#add_info").val('');
    $("#add_exp").val('');
    layui.form.render("select");
    formSelects.btns('select_addEffect', 'remove');
    formSelects.btns('select_addBuff', 'remove');
}

function see(data) {
    var params = {name: data.name};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var content = $("#seeInfo");
                layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭",
                    function (index, layero) {
                        var obj = JSON.parse(rows);
                        $("#see_name").val(isNull(obj.name));
                        $("#see_cost").val(isNull(obj.MP_cost));
                        $("#see_exp").val(isNull(obj.zhaoshi_experience_cost));
                        $("#see_kongFu").val(isNull(obj.kongFuName()));
                        $("#see_effect").val(isNull(obj.effectName()));
                        $("#see_info").val(isNull(obj.info));
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
    downBox(downs, effectBox, "edit_effect", "select_editEffect", mSelection);
    var params = {name: data.name};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 1000, 500, "立即提交", "重置",
                    function (index, layero) {
                        var obj = JSON.parse(rows);
                        $("#edit_id").val(isNull(obj.zhaoshi_id));
                        $("#edit_name").val(isNull(obj.name));
                        $("#edit_exp").val(isNull(obj.zhaoshi_experience_cost));
                        $("#edit_info").val(isNull(obj.info));
                        var mpCost = obj.MP_cost.split(",");
                        $("#edit_yellowSpend").val(mpCost[0]);
                        $("#edit_goldSpend").val(mpCost[1]);
                        $("#edit_greenSpend").val(mpCost[2]);
                        $("#edit_blueSpend").val(mpCost[3]);
                        $("#edit_purpleSpend").valu(mpCost[4]);
                        $("#edit_kongFu").val(String(obj.kongfu_id));
                        layui.form.render("select");
                        $("#edit_effect").val();
                        formSelects.value("select_editEffect", obj.effect_id);
                    }, function (index, layero) {
                        var params = {};
                        var move_effect = formSelects.value('select_editEffect', 'valStr');
                        params.zhaoshi_id = $("#edit_id").val();
                        params.kongfu_id = $("#edit_kongFu").val();
                        params.name = $("#edit_name").val();
                        params.info = $("#edit_info").val();
                        params.zhaoshi_effect = move_effect;
                        params.zhaoshi_experience_cost = Number($("#edit_exp").val());
                        if (verIfy(params) === false) {
                            return;
                        }
                        var yellow = ($("#edit_yellowSpend").val());
                        var gold = ($("#edit_goldSpend").val());
                        var green = ($("#edit_greenSpend").val());
                        var blue = ($("#edit_blueSpend").val());
                        var purple = ($("#edit_purpleSpend").val());
                        if (verIfs(yellow, gold, green, blue, purple) === false) {
                            return;
                        }
                        params.MP_cost = cost(yellow, gold, green, blue, purple);
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_spend").val('');
                        $("#edit_info").val('');
                        $("#edit_exp").val('');
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
    if (params.kongfu_id === null || params.kongfu_id === "") {
        layer.msg("功夫选项不能为空", {icon: 2});
        return false;
    }
    if(params.MP_cost === null || params.MP_cost === ""){
        layer.msg("内力花费不能为空", {icon: 2});
        return false;
    }
    return true;
}

function verIfs(yellow,gold,green,blue,purple) {
    var ter = new RegExp("[0-9]");
    if (!ter.test(yellow) || !ter.test(gold) || !ter.test(green)
        || !ter.test(blue) || !ter.test(purple)) {
        layer.msg("内力花费格式错误", {icon: 2});
        return false;
    }
    return true;
}