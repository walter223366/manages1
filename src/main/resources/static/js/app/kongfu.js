charset="utf-8";
manages="kongFu";
var formSelects=layui.formSelects;
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
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 100},
        {field: 'name', title: '功夫名称', sort: true},
        {field: 'type', title: '功夫类型'},
        {field: 'experience_limit', title: '可学经验上限', sort: true},
        {field: 'kongfu_attainments', title: '造诣', sort: true},
        {field: 'enable', title: '状态'},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
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
    downBox(moveBox, "add_move", "select_addMove", mSelection);
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 1100, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            var move_id = formSelects.value('select_addMove', 'valStr');
            params.name = $("#add_name").val();
            params.type = Number($("#add_type").val());
            params.info = $("#add_info").val();
            params.experience_limit = Number($("#add_exp").val());
            params.kongfu_attainments = $("#add_attainments").val();
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
    $("#add_info").val('');
    $("#add_exp").val(100);
    $("#add_attainments").val('');
}

function see(data) {
    var params = {name: data.name};
    postRequest(params, manages, sDetails, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
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
                layerOpen(1, "查看详情", content, 1100, 500, "明白了", "关闭", "",
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
    downBox(moveBox, "edit_move", "select_editMove", mSelection);
    var params = {name: data.name};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.kongfu_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_exp").value = isNull(obj.experience_limit);
                document.getElementById("edit_attainments").value = isNull(obj.kongfu_attainments);
                document.getElementById("edit_info").value = isNull(obj.info);
                $("#edit_type").val(String(obj.type));
                $("#edit_enable").val(String(obj.enable));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 1100, 500, "立即提交", "重置", "",
                    function (index, layero) {
                        var params = {};
                        var move_id = formSelects.value('select_editMove', 'valStr');
                        params.kongfu_id = $("#edit_id").val();
                        params.name = $("#edit_name").val();
                        params.info = $("#edit_info").val();
                        params.type = Number($("#edit_type").val());
                        params.experience_limit = Number($("#edit_exp").val());
                        params.kongfu_attainments = $("#edit_attainments").val();
                        params.enable = Number($("#edit_enable").val());
                        params.kongfu_zhaoshi = move_id;
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_exp").val(100);
                        $("#edit_attainments").val('');
                        $("#edit_info").val('');
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