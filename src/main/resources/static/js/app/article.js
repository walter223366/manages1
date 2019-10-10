charset="utf-8";
manages="article";
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 100},
        {field: 'name', title: '物品名称', sort: true},
        {field: 'img', title: '物品图标'},
        {fixed: 'right', title: '操作', width: 400, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "物品管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    pagingQuery();
}

function add() {
    addReset();
    var content = $("#addInfo");
    layerOpen(1, "新增", content, 1000, 500, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.name = $("#add_name").val();
            params.img = $("#add_img").val();
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
    $("#add_info").val('');
    $("#add_img").val('');
}

function see(data) {
    document.getElementById("see_name").value = isNull(data.name);
    document.getElementById("see_img").value = isNull(data.img);
    document.getElementById("see_info").value = isNull(data.info);
    var content = $("#seeInfo");
    layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭", "",
        function (index, layero) {
            layer.closeAll();
        }, function (index, layero) {
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
                document.getElementById("edit_id").value = isNull(obj.article_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_img").value = isNull(obj.img);
                document.getElementById("edit_info").value = isNull(obj.info);
                var content = $("#editInfo");
                layerOpen(1, "编辑", content, 1000, 500, "立即提交", "重置", "",
                    function (index, layero) {
                        var params = {};
                        params.article_id = $("#edit_id").val();
                        params.name = $("#edit_name").val();
                        params.info = $("#edit_info").val();
                        params.img = $("#edit_img").val();
                        if (verIfy(params) === false) {
                            return;
                        }
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        $("#edit_name").val('');
                        $("#edit_img").val('');
                        $("#edit_info").val('');
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

function verIfy(params){
    if (params.name === null || params.name === "") {
        layer.msg("物品名称不能为空", {icon: 2});
        return false;
    }
    if (params.img === null || params.img === "") {
        layer.msg("物品图标不能为空", {icon: 2});
        return false;
    }
    return true;
}

layui.use('upload', function(){
    //TODO
    var $ = layui.jquery,upload = layui.upload;
    upload.render({
        elem: '#add_img',
        url: '/upload/',
        done: function (res) {
            console.log(res)
        }
    });
});