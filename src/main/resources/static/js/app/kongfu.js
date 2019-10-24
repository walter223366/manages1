charset="utf-8";
manages="kongFu";
var formSelects = layui.formSelects;
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.type = $("#query_type").val();
    params.enable = $("#query_enable").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 60},
        {field: 'name', title: '功夫名称', sort: true},
        {field: 'typeValue', title: '功夫类型'},
        {field: 'experience_limit', title: '可学经验上限', sort: true},
        {field: 'kongfu_attainments', title: '造诣', sort: true},
        {fixed: 'right', title: '操作', width: 250, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "武学管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    $("#query_type").val("");
    $("#query_enable").val("");
    layui.form.render("select");
    pagingQuery();
}

function add() {
    addReset();
    var downs = {};
    downBox(downs, "moveNulBox", "add_kongfu_zhaoshi", "select_addMove", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 950, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_id = formSelects.value('select_addMove', 'valStr');
            params.name = $("#add_name").val();
            params.type = $("#add_type").val();
            params.info = $("#add_info").val();
            params.experience_limit = Number($("#add_experience_limit").val());
            params.kongfu_attainments = $("#add_kongfu_attainments").val();
            params.enable = $("#add_enable").val();
            params.kongfu_zhaoshi = move_id;
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
    $("#add_type").val('');
    $("#add_experience_limit").val(100);
    $("#add_enable").val('1');
    $("#add_kongfu_attainments").val('');
    $("#add_kongfu_zhaoshi").val('');
    $("#add_Special_buff").val('');
    $("#add_info").val('');
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
                        layui.form.render("select");
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
    var params = {name: data.name};
    downBox(params, "moveNulBox", "edit_kongfu_zhaoshi", "select_editMove", mSelection);
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
                        $("#edit_experience_limit").val(isNull(obj.experience_limit));
                        $("#edit_enable").val(isNull(setEnable(obj.enable)));
                        $("#edit_kongfu_attainments").val(obj.kongfu_attainments);
                        $("#edit_Special_buff").val(obj.Special_buff);
                        $("#edit_info").val(obj.info);
                        var movIds = multipleBox(obj.kongfu_zhaoshi);
                        if(movIds.length > 0) {
                            //formSelects.value("select_editMove", movIds);
                        }
                        layui.form.render("select");
                    }, function (index, layero) {
                        var params = {};
                        var move_id = formSelects.value('select_editMove', 'valStr');
                        params.kongfu_id = obj.kongfu_id;
                        params.name = $("#edit_name").val();
                        params.type = $("#edit_type").val();
                        params.experience_limit = Number($("#edit_experience_limit").val());
                        params.enable = $("#edit_enable").val();
                        params.kongfu_attainments = $("#edit_kongfu_attainments").val();
                        params.kongfu_zhaoshi = move_id;
                        params.info = $("#edit_info").val();
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_type").val('');
                        $("#edit_experience_limit").val(100);
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
        layer.msg("功夫名称不能为空", {icon: 2});
        return false;
    }
    if (params.type === null || params.type === "") {
        layer.msg("功夫类型不能为空", {icon: 2});
        return false;
    }
    return true;
}