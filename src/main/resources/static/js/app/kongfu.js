charset="utf-8";
manages="kongFu";
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.type = $("#query_type").val();
    params.enable = $("#query_enable").val();
    params.LV = $("#query_LV").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 60},
        {field: 'name', title: '武学名称'},
        {field: 'typeValue', title: '武学类型'},
        {field: 'LV', title: '武学等级'},
        {field: 'experience_limit', title: '可学经验上限', sort: true},
        {field: 'kongfu_attainments', title: '造诣'},
        {fixed: 'right', title: '操作', width: 250, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "武学管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    $("#query_type").val('');
    $("#query_enable").val('');
    $("#query_LV").val('');
    layui.form.render("select");
    pagingQuery();
}

function add() {
    addReset();
    var downs = {};
    downBox(downs, moveNulBox, "add_kongfu_zhaoshi", "select_addMove", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 950, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_id = layui.formSelects.value('select_addMove', 'valStr');
            params.name = $("#add_name").val();
            params.type = $("#add_type").val();
            params.info = $("#add_info").val();
            var def = $("#add_experience_limit").val();
            params.experience_limit = setDefaults(def);
            params.kongfu_attainments = $("#add_kongfu_attainments").val();
            params.enable = $("#add_enable").val();
            params.kongfu_zhaoshi = move_id;
            params.LV = $("#add_LV").val();
            if (verIfy(params) === false) {
                return;
            }
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

//todo 多选下拉框清空未实现
function addReset() {
    $("#add_name").val('');
    $("#add_type").val('');
    $("#add_experience_limit").val(1000);
    $("#add_enable").val('1');
    $("#add_kongfu_attainments").val('');
    $("#add_LV").val('');
    $("#add_info").val('');
    //$("#add_kongfu_zhaoshi").val('');
    //$("#add_Special_buff").val('');
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
                        $("#see_type").val(isNull(obj.typeValue));
                        $("#see_experience_limit").val(isNull(obj.experience_limit));
                        $("#see_kongfu_attainments").val(isNull(obj.kongfu_attainments));
                        $("#see_enable").val(isNull(obj.enableValue));
                        $("#see_info").val(isNull(obj.info));
                        $("#see_kongfu_zhaoshi").val(isNull(obj.moveName.toString()));
                        $("#see_LV").val(isNull(obj.LV));
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
    downBox(downs, moveNulBox, "edit_kongfu_zhaoshi", "select_editMoves", mSelection);
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
                        $("#edit_type").val(isNull(obj.type));
                        $("#edit_experience_limit").val(obj.experience_limit);
                        $("#edit_enable").val(setEnable(obj.enable));
                        $("#edit_kongfu_attainments").val(isNull(obj.kongfu_attainments));
                        $("#edit_info").val(isNull(obj.info));
                        $("#edit_LV").val(obj.LV);
                        var zhaoId = multipleBox(obj.kongfu_zhaoshi);
                        if(zhaoId.length > 0) {
                            layui.formSelects.value('select_editMoves', zhaoId);
                        }
                        layui.form.render("select");
                    }, function (index, layero) {
                        var params = {};
                        var move_id = layui.formSelects.value('select_editMoves', 'valStr');
                        params.kongfu_id = obj.kongfu_id;
                        params.name = $("#edit_name").val();
                        params.type = $("#edit_type").val();
                        var def = $("#edit_experience_limit").val();
                        params.experience_limit = setDefaults(def);
                        params.enable = $("#edit_enable").val();
                        params.kongfu_attainments = $("#edit_kongfu_attainments").val();
                        params.kongfu_zhaoshi = move_id;
                        params.info = $("#edit_info").val();
                        params.LV = $("#edit_LV").val();
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_type").val('');
                        $("#edit_experience_limit").val(1000);
                        $("#edit_enable").val('1');
                        $("#edit_kongfu_attainments").val('');
                        $("#edit_kongfu_zhaoshi").val('');
                        $("#edit_Special_buff").val('');
                        $("#edit_info").val('');
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
        layer.msg("武学名称不能为空", {icon: 2});
        return false;
    }
    if (params.type === null || params.type === "") {
        layer.msg("武学类型不能为空", {icon: 2});
        return false;
    }
    if (params.LV === null || params.LV === "") {
        layer.msg("武学等级不能为空", {icon: 2});
        return false;
    }
    return true;
}

function setDefaults(def) {
    var defVal = 1000;
    if (def === null || def === "") {
        return defVal;
    } else {
        return def;
    }
}