charset="utf-8";
manages="baseNpc";
expNum=100;//TODO 暂时为1等级为100经验值
attNum=5;//TODO 1等级5点属性值
addUrl=splictUrl+"/system/baseNpc_add";
$(function(){
    pagingQuery();
});


function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.nickname = $("#query_nickname").val();
    params.enable = $("#query_enable").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 100},
        {field: 'nickname', title: '人物名称', width: 100},
        {field: 'schoolName', title: '所属门派', width: 100},
        {field: 'sex', title: '性别'},
        {field: 'level', title: '等级', sort: true},
        {field: 'attitude', title: '态度'},
        {field: 'character', title: '性格'},
        {field: 'popularity', title: '声望', sort: true},
        {field: 'experience', title: '经验值'},
        {field: 'school_contribution', title: '门派贡献值', width: 100},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "人物管理", cole, add, edit, del, see, pagingQuery);
}


function cleanUp() {
    $("#query_nickname").val('');
    $("#query_enable").val('');
    layui.form.render("select");
    pagingQuery();
}


function add() {
    addReset();
    downBox(schoolBox, "add_school", "", lSelection);
    layerOpen(2, "新增", addUrl, 1200, 600, "立即提交", "重置",
        function () {

            //基础信息
            var basis = {
                nickname: $("#add_nickname").val(),
                school_id: $("#add_school").val(),
                sex: $("#add_sex").val(),
                level: $("#add_level").val(),
                attitude: $("#add_attitude").val(),
                character: $("#add_character").val(),
                popularity: $("#add_popularity").val(),
                coin: $("#add_coin").val(),
                gold: $("#add_gold").val(),
                school_contribution: $("#add_school_contribution").val()
            };
            if (basis.nickname === null || basis.nickname === "") {
                layer.msg("人物名称不能为空", {icon: 2});
                return;
            }
            if (basis.school_id === null || basis.school_id === "") {
                layer.msg("所属门派不能为空", {icon: 2});
                return;
            }
            if (basis.level <= 0) {
                layer.msg("等级不能为空或小于1", {icon: 2});
                return;
            }
            basis.experience = Number(calAttr(basis.level, expNum, "add_experience"));
            //属性分配
            var virtue = {
                physique: $("#add_mphysique").val(),
                force: $("#add_force").val(),
                muscles: $("#add_muscles").val(),
                chakra: $("#add_chakra").val(),
                sensitivity: $("#add_sensitivity").val(),
                willpower: $("#add_willpower").val(),
                knowledge: $("#add_knowledge").val(),
                lucky: $("#add_lucky").val()
            };
            //兵器造诣
            var weapon = {
                melee_status: $("#add_melee_status").val(),
                sword_status: $("#add_sword_status").val(),
                axe_status: $("#add_axe_status").val(),
                javelin_status: $("#add_javelin_status").val(),
                hidden_weapons_status: $("#add_hidden_weapons_status").val(),
                sorcery_status: $("#add_sorcery_status").val(),
                dodge_skill_status: $("#add_dodge_skill_status").val(),
                chakra_status: $("#add_chakra_status").val()
            };
            var params = {
                basis: basis,
                virtue: virtue,
                weapon: weapon
            };
            aTransfer(params, manages, pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}
function virtueBut() {
    var content = $("#attInfo");
    layerOpen(1, "属性分配", content, 900, 400, "确定", "重置",
        function () {

        }, function () {

        });
}
function kongFuBut() {
    downBox(kongFuBox, "add_kongFu", "", lSelection);
    var content = $("#artsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "确定", "重置",
        function () {

        }, function () {

        });
}
function weaponBut() {
    var content = $("#weaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "确定", "重置",
        function () {

        }, function () {

        });
}


function but() {
    $(".attInfo").html($("#attInfo").html());
    $(".weaponInfo").html($("#weaponInfo").html());
    $(".artsInfo").html($("#artsInfo").html());
}



function butKongFu() {
    var a = $("#add_kongFu").val();
    var a1 = $("#add_kongFu1").val();
    if(isNull(al)){
        $('#productName').bind('input propertychange', function() {searchProductClassbyName();});
    }
}


var mphysique1;
$(function () {
    but();
    $("#add_level").on("input", function (e) {
        var obj = e.delegateTarget.value;
        calAttr(obj, expNum, "add_experience");
        calAttr(obj, attNum, "add_attributes");
    });
    $("#add_mphysique").on("input", function (e) {
        mphysique1 = e.delegateTarget.value;
    });
});

$(document).on("click","#mphysMin",function () {
    var min = $("#mphysMin");
    $('#add_mphysique').bind('input propertychange', function() {
        searchProductClassbyName();
    });
    var mphys = calMin(mphysique1,min);
    document.getElementById("add_mphysique").value = isNull(mphys);
});
$(document).on("click","#mphysAdd",function () {
    var min = $("#mphysMin");
    alert(mphysique1);
    var mphys = calAdd(mphysique1,min);
    document.getElementById("add_mphysique").value = isNull(mphys);
});
//分配属性点
function attDistribution(){

}

function attributeAll(attributes){
    var attr = {};
    var mphysique=Number($("#add_mphysique").val());
    var force=Number($("#add_force").val());
    var muscles=Number($("#add_muscles").val());
    var chakra=Number($("#add_chakra").val());
    var sensitivity=Number($("#add_sensitivity").val());
    var willpower=Number($("#add_willpower").val());
    var knowledge=Number($("#add_knowledge").val());
    var lucky=Number($("#add_lucky").val());
}
$(function (){
    //获取武学下拉框
    var params = {};
    postRequest(params,"downBox",kongFuBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                layui.use('transfer', function(){
                    var transfer = layui.transfer;
                    transfer.render({
                        elem: '#addKongFu'
                        ,data:obj.data
                        ,parseData:function (res) {
                            return {
                                "value": res.kongfu_id,
                                "title": res.name,
                                "disabled": res.disabled,
                                "checked": res.checked
                            }
                        }
                        ,title: ['武学选项', '武学添加']
                        ,id: 'demo1' //定义索引
                        ,width:304
                        ,height:340
                        ,onchange:function () {

                        }
                    });
                });
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
});
//新增-重置
function addReset(){
    $("#add_nickname").val('');
    $("#add_level").val('');
    $("#add_attitude").val('');
    $("#add_character").val('');
    $("#add_popularity").val('');
    $("#add_coin").val('');
    $("#add_gold").val('');
    $("#add_experience").val('');
    $("#add_school_contribution").val('');
    $("#add_img").val('');
    $("#add_enable").val(1);
    $("#add_sex").val(1);
    layui.form.render("select");
}


function see(data) {
    var params = {nickname: data.nickname};
    postRequest(params, manages, sDetails, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("see_nickname").value = isNull(obj.nickname);
                document.getElementById("see_level").value = isNull(obj.level);
                document.getElementById("see_attitude").value = isNull(obj.attitude);
                document.getElementById("see_character").value = isNull(obj.character);
                document.getElementById("see_popularity").value = isNull(obj.popularity);
                document.getElementById("see_coin").value = isNull(obj.coin);
                document.getElementById("see_gold").value = isNull(obj.gold);
                document.getElementById("see_experience").value = isNull(obj.experience);
                document.getElementById("see_school_contribution").value = isNull(obj.school_contribution);
                document.getElementById("see_school").value = isNull(obj.schoolName);
                document.getElementById("see_sex").value = isNull(obj.sex);
                document.getElementById("see_enable").value = isNull(obj.enable);
                document.getElementById("see_user").value = isNull(obj.userName);
                var content = $("#seeInfo");
                layerSeeOpen("查看详情", content, 1000, 500);
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}


function edit(data) {
    downBox(schoolBox, "edit_school", "", lSelection);
    var params = {nickname: data.nickname};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.id);
                document.getElementById("edit_nickname").value = isNull(obj.nickname);
                document.getElementById("edit_level").value = isNull(obj.level);
                document.getElementById("edit_attitude").value = isNull(obj.attitude);
                document.getElementById("edit_character").value = isNull(obj.character);
                document.getElementById("edit_popularity").value = isNull(obj.popularity);
                document.getElementById("edit_coin").value = isNull(obj.coin);
                document.getElementById("edit_gold").value = isNull(obj.gold);
                document.getElementById("edit_experience").value = isNull(obj.experience);
                document.getElementById("edit_school_contribution").value = isNull(obj.school_contribution);
                $("#edit_school").val(obj.school_id);
                $("#edit_sex").val(String(obj.sex));
                $("#edit_enable").val(String(obj.enable));
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑", content, 1000, 500,
                    function () {
                        editRequest();
                    }, function (index, layero) {
                        $("#edit_nickname").val('');
                        $("#edit_level").val('');
                        $("#edit_attitude").val('');
                        $("#edit_character").val('');
                        $("#edit_popularity").val('');
                        $("#edit_coin").val('');
                        $("#edit_gold").val('');
                        $("#edit_experience").val('');
                        $("#edit_school_contribution").val('');
                        $("#edit_sex").val(1);
                        $("#edit_enable").val(1);
                        layui.form.render("select");
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}