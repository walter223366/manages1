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
        {field: 'zhaoshi_experience_cost', title: '武学经验', sort: true},
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
    downBox(kongFuBox, "add_kongFu", "", lSelection);
    downBox(effectBox, "add_effect", "select_addEffect", mSelection);
    var content = $("#addInfo");
    layerOpen("新增", content, 1000, 500,
        function () {
            var formSelects = layui.formSelects;
            var move_effect = formSelects.value('select_addEffect', 'valStr');
            var params = {
                name: $("#add_name").val(),
                kongfu_id: $("#add_kongFu").val(),
                zhaoshi_effect: move_effect,
                yellowSpend: Number($("#add_yellowSpend").val()),
                goldSpend: Number($("#add_goldSpend").val()),
                greenSpend: Number($("#add_greenSpend").val()),
                blueSpend: Number($("#add_blueSpend").val()),
                purpleSpend: Number($("#add_purpleSpend").val()),
                zhaoshi_experience_cost: Number($("#add_exp").val()),
                info: $("#add_info").val()
            };
            var tver = new RegExp("[0-9]");
            if (!tver.test(params.yellowSpend) || !tver.test(params.goldSpend) || !tver.test(params.greenSpend)
                || !tver.test(params.blueSpend) || !tver.test(params.purpleSpend)) {
                layer.msg("内力花费格式错误", {icon: 2});
                return false;
            }
            if (params.name === null || params.name === "") {
                layer.msg("招式名称不能为空", {icon: 2});
                return;
            }
            if (params.kongfu_id === null || params.kongfu_id === "") {
                layer.msg("功夫选项不能为空", {icon: 2});
                return;
            }
            if (params.zhaoshi_effect === null || params.zhaoshi_effect === "") {
                layer.msg("效果选项不能为空", {icon: 2});
                return;
            }
            aTransfer(params, manages, pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}
function addReset(){
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
    formSelects.btns('select_addKongFu','remove');
    formSelects.btns('select_addEffect','remove');
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
                layerSeeOpen("查看详情", content, 1000, 500);
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}


function edit(data) {
    downBox(kongFuBox, "edit_kongFu", "", lSelection);
    downBox(effectBox, "edit_effect", "select_editEffect", mSelection);
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
                $("#edit_kongFu").val(String(obj.kongfu_id));
                layui.form.render("select");
                $("#edit_effect").val();
                formSelects.value("select_editEffect",obj.effect_id);
                var content = $("#editInfo");
                layerOpen("编辑", content, 1000, 500,
                    function () {
                        var move_effect = formSelects.value('select_editEffect', 'valStr');
                        var params = {
                            zhaoshi_id: $("#edit_id").val(),
                            kongfu_id: $("#edit_kongFu").val(),
                            name: $("#edit_name").val(),
                            MP_cost: $("#edit_spend").val(),
                            info: $("#edit_info").val(),
                            zhaoshi_effect: move_effect,
                            zhaoshi_experience_cost: Number($("#edit_exp").val())
                        };
                        eTransfer(params, manages, pagingQuery);
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