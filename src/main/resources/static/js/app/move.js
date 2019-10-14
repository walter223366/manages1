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
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 150},
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
    downBox(downs, effectBox, "add_effect", "select_addEffect", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 1000, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_effect = formSelects.value('select_addEffect', 'valStr');
            params.name = $("#add_name").val();
            params.kongfu_id = $("#add_kongFu").val();
            params.zhaoshi_effect = move_effect;
            params.zhaoshi_experience_cost = Number($("#add_exp").val());
            params.info = $("#add_info").val();
            if (verIfy(params) === false) {
                return;
            }
            var yellow = ($("#add_yellowSpend").val());
            var gold = ($("#add_goldSpend").val());
            var green = ($("#add_greenSpend").val());
            var blue = ($("#add_blueSpend").val());
            var purple = ($("#add_purpleSpend").val());
            if (verIfs(yellow, gold, green, blue, purple) === false) {
                return;
            }
            params.MP_cost = cost(yellow, gold, green, blue, purple);
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

function addReset() {
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
    layui.form.render("select");
    formSelects.btns('select_addEffect', 'remove');
}

function see(data) {
    var params = {name: data.name};
    postRequest(params, manages, sDetails, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("see_name").value = isNull(obj.name);
                document.getElementById("see_cost").value = isNull(obj.MP_cost);
                document.getElementById("see_exp").value = isNull(obj.zhaoshi_experience_cost);
                document.getElementById("see_kongFu").value = isNull(obj.kongFuName.toString());
                document.getElementById("see_effect").value = isNull(obj.effectName.toString());
                document.getElementById("see_info").value = isNull(obj.info);
                var content = $("#seeInfo");
                layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭", "",
                    function (index, layero) {
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
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.zhaoshi_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_exp").value = isNull(obj.zhaoshi_experience_cost);
                document.getElementById("edit_info").value = isNull(obj.info);
                var mpCost = obj.MP_cost.split(",");
                document.getElementById("edit_yellowSpend").value = mpCost[0];
                document.getElementById("edit_goldSpend").value = mpCost[1];
                document.getElementById("edit_greenSpend").value = mpCost[2];
                document.getElementById("edit_blueSpend").value = mpCost[3];
                document.getElementById("edit_purpleSpend").value = mpCost[4];
                $("#edit_kongFu").val(String(obj.kongfu_id));
                layui.form.render("select");
                $("#edit_effect").val();
                formSelects.value("select_editEffect", obj.effect_id);
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 1000, 500, "立即提交", "重置", "",
                    function (index, layero) {
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
    if (params.zhaoshi_effect === null || params.zhaoshi_effect === "") {
        layer.msg("效果选项不能为空", {icon: 2});
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