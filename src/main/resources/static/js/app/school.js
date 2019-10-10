charset="utf-8";
manages="school";
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.sinfluence = $("#query_sinfluence").val();
    params.einfluence = $("#query_einfluence").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 150},
        {field: 'name', title: '门派名称', sort: true},
        {field: 'influence', title: '门派影响力值', sort: true},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "门派管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp(){
    $("#query_name").val('');
    $("#query_sinfluence").val('');
    $("#query_einfluence").val('');
    pagingQuery();
}

function add() {
    addReset();
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 900, 450, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.name = $("#add_name").val();
            params.influence = Number($("#add_influence").val());
            params.info = $("#add_info").val();
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
    $("#add_influence").val('');
    $("#add_info").val('');
}

function see(data) {
    document.getElementById("see_name").value = isNull(data.name);
    document.getElementById("see_influence").value = isNull(data.influence);
    document.getElementById("see_info").value = isNull(data.info);
    var content = $("#seeInfo");
    layerOpen(1, "查看详情", content, 900, 450, "明白了", "关闭", "",
        function () {
            layer.closeAll();
        }, function () {
            layer.closeAll();
        });
}

function edit(data) {
    var params = {name: data.name};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_school_id").value = isNull(obj.school_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_influence").value = isNull(obj.influence);
                document.getElementById("edit_info").value = isNull(obj.info);
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 900, 450, "立即提交", "重置", "",
                    function () {
                        var params = {};
                        params.school_id = $("#edit_school_id").val();
                        params.name = $("#edit_name").val();
                        params.influence = Number($("#edit_influence").val());
                        params.info = $("#edit_info").val();
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_influence").val('');
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
        layer.msg("门派名称不能为空", {icon: 2});
        return false;
    }
    return true;
}