charset="utf-8";
manages="account";
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.account = $("#query_account").val();
    params.tellPhone = $("#query_tellPhone").val();
    params.source = $("#query_source").val();
    params.queryTime = $("#query_time").val();
    params.cancellation = $("#query_cancellation").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 70},
        {field: 'account', title: '账号', sort: true},
        {field: 'tellphone', title: '手机号码', sort: true},
        {field: 'sourceValue', title: '来源'},
        {field: 'lrrq', title: '创建时间', sort: true},
        {field: 'cancelValue', title: '状态'},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "账号管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_account").val('');
    $("#query_tellPhone").val('');
    $("#query_time").val('');
    $("#query_source").val('');
    $("#query_cancellation").val('');
    layui.form.render("select");
    pagingQuery();
}

function add() {
    addReset();
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 800, 450, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.account = $("#add_account").val();
            params.tellphone = Number($("#add_tellPhone").val());
            params.password = $("#add_password").val();
            params.source = Number($("#add_source").val());
            if (verIfy(params) === false) {
                return;
            }
            layer.msg("暂时无法新增用户账号，请至微信公众号进行注册账号");
            //aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

function addReset() {
    $("#add_account").val('');
    $("#add_tellPhone").val('');
    $("#add_password").val('');
    $("#add_cPassword").val('');
}

function see(data) {
    layer.msg("暂未处理");
}

function edit(data) {
    var params = {account: data.account};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 800, 450, "立即提交", "重置",
                    function (index, layero) {
                        var obj = JSON.parse(rows);
                        $("#edit_id").val(isNull(obj.id));
                        $("#edit_account").val(isNull(obj.account));
                        $("#edit_tellPhone").val(isNull(obj.tellphone));
                        $("#edit_source").val(String(obj.source));
                        layui.form.render("select");
                    }, function (index, layero) {
                        var params = {};
                        params.id = $("#edit_id").val();
                        params.account = $("#edit_account").val();
                        params.tellphone = Number($("#edit_tellPhone").val());
                        params.lrrq = $("#edit_lrrq").val();
                        params.source = Number($("#edit_source").val());
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_tellPhone").val('');
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

layui.use('laydate', function() {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#query_time'
        , type: 'datetime'
        , range: true
    });
});

function verIfy(params) {
    if (params.account === null || params.account === "") {
        layer.msg("账号名称不能为空", {icon: 2});
        return false;
    } else {
        if (params.account.length > 100) {
            layer.msg("账号名称字符过长", {icon: 2});
            return false;
        }
    }
    return true;
}