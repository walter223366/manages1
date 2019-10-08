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
    var id = document.getElementById("add_school");
    downBox(schoolBox, id, "", lSelection);
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
            //basis.experience = Number(calAttr(basis.level, expNum, "add_experience"));
            var virtue = sessionStorage.getItem("virtue");
            var kongFu = sessionStorage.getItem("kongFu");
            var weapon = sessionStorage.getItem("weapon");
            var params = {
                basis: basis,
                virtue: JSON.parse(virtue),
                kongFu: JSON.parse(kongFu),
                weapon: JSON.parse(weapon)
            };
            aaUp(params, manages, "insert", "新增", pagingQuery)
        }, function (index, layero) {
            addReset();
        });
}
function virtueBut() {
    var virtue = {};
    var content = $("#attInfo");
    layerOpen(1, "属性分配", content, 900, 400, "确定", "重置",
        function (index, layero) {
            virtue.physique = document.getElementById("add_mphysique").value;
            virtue.force = document.getElementById("add_force").value;
            virtue.muscles = document.getElementById("add_muscles").value;
            virtue.chakra = document.getElementById("add_chakra").value;
            virtue.sensitivity = document.getElementById("add_sensitivity").value;
            virtue.willpower = document.getElementById("add_willpower").value;
            virtue.knowledge = document.getElementById("add_knowledge").value;
            virtue.lucky = document.getElementById("add_lucky").value;
            sessionStorage.setItem("virtue", JSON.stringify(virtue));
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            document.getElementById("add_mphysique").value = 0;
            document.getElementById("add_force").value = 0;
            document.getElementById("add_muscles").value = 0;
            document.getElementById("add_chakra").value = 0;
            document.getElementById("add_sensitivity").value = 0;
            document.getElementById("add_willpower").value = 0;
            document.getElementById("add_knowledge").value = 0;
            document.getElementById("add_lucky").value = 0;
        });
}
function kongFuBut() {
    downBox(kongFuBox, "add_kongFu", "", lSelection);
    var kongFu = {};
    var content = $("#artsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "确定", "重置",
        function (layero, index) {
            kongFu.kongFu1 = document.getElementById("add_kongFu1").value;
            kongFu.exp1 = document.getElementById("add_experience1").value;
            kongFu.kongFu2 = document.getElementById("add_kongFu2").value;
            kongFu.exp2 = document.getElementById("add_experience2").value;
            kongFu.kongFu3 = document.getElementById("add_kongFu3").value;
            kongFu.exp3 = document.getElementById("add_experience3").value;
            kongFu.kongFu4 = document.getElementById("add_kongFu4").value;
            kongFu.exp4 = document.getElementById("add_experience4").value;
            sessionStorage.setItem("kongFu", JSON.stringify(kongFu));
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            document.getElementById("add_kongFu1").value = '';
            document.getElementById("add_experience1").value = '';
            document.getElementById("add_kongFu2").value = '';
            document.getElementById("add_experience2").value = '';
            document.getElementById("add_kongFu3").value = '';
            document.getElementById("add_experience3").value = '';
            document.getElementById("add_kongFu4").value = '';
            document.getElementById("add_experience4").value = '';
            document.getElementById("add_kongFu").value = '';
            layui.form.render("select");
        });
}
function addKongFu() {
    var kongFu = $("#add_kongFu").find("option:selected").text();
    var kongFu1 = document.getElementById("add_kongFu1");
    var kongFu2 = document.getElementById("add_kongFu2");
    var kongFu3 = document.getElementById("add_kongFu3");
    var kongFu4 = document.getElementById("add_kongFu4");
    if (kongFu === "请选择") {
        layer.msg("请选择武学选项");
        return;
    }
    if (kongFu1.value === '') {
        kongFu1.value = kongFu;
    } else if (kongFu2.value === '') {
        kongFu2.value = kongFu;
    } else if (kongFu3.value === '') {
        kongFu3.value = kongFu;
    } else if (kongFu4.value === '') {
        kongFu4.value = kongFu;
    } else {
        layer.msg("添加武学选项已满");
    }
}
function resetKongFu(a,b) {
    document.getElementById(a).value = '';
    document.getElementById(b).value = '';
}
function weaponBut() {
    var weapon = {};
    var content = $("#weaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "确定", "重置",
        function () {
            weapon.melee = document.getElementById("add_melee_status").value;
            weapon.sword = document.getElementById("add_sword_status").value;
            weapon.axe = document.getElementById("add_axe_status").value;
            weapon.javelin = document.getElementById("add_javelin_status").value;
            weapon.hiddens = document.getElementById("hidden_weapons_status").value;
            weapon.sorcery = document.getElementById("add_sorcery_status").value;
            weapon.dodges = document.getElementById("add_dodge_skill_status").value;
            weapon.chakra = document.getElementById("chakra_status").value;
            sessionStorage.setItem("weapon", JSON.stringify(weapon));
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            document.getElementById("add_melee_status").value = '';
            document.getElementById("add_sword_status").value = '';
            document.getElementById("add_axe_status").value = '';
            document.getElementById("add_javelin_status").value = '';
            document.getElementById("add_hidden_weapons_status").value = '';
            document.getElementById("add_sorcery_status").value = '';
            document.getElementById("add_dodge_skill_status").value = '';
            document.getElementById("add_chakra_status").value = '';
        });
}



function butKongFu() {
    var a = $("#add_kongFu").val();
    var a1 = $("#add_kongFu1").val();
    if(isNull(al)){
        $('#productName').bind('input propertychange', function() {searchProductClassbyName();});
    }
}


$(function () {
    $("#add_level").on("input", function (e) {
        var obj = e.delegateTarget.value;
        calAttr(obj, expNum, "add_experience");
        calAttr(obj, attNum, "add_attributes");
    });
    $("#add_mphysique").on("input", function (e) {
        var value = e.delegateTarget.value;
        alert(vaule);
        sessionStorage.setItem("mphys",value);
    });
});
$(document).on("click","#mphysMin",function () {
    var min = $("#mphysMin");
    var a = sessionStorage.getItem("mphys");
    alert(a);
    var mph = calMin(Number(a),min);
    document.getElementById("add_mphysique").value = isNull(mph);
});
$(document).on("click","#mphysAdd",function () {
    var min = $("#mphysMin");
    var a = sessionStorage.getItem("mphys");
    alert(a);
    var mph = calAdd(Number(a),min);
    document.getElementById("add_mphysique").value = isNull(mph);
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

function verIf(basis) {
    if (basis.nickname === null || basis.nickname === "") {
        layer.msg("人物名称不能为空", {icon: 2});
        return false;
    }
    if (basis.school_id === null || basis.school_id === "") {
        layer.msg("所属门派不能为空", {icon: 2});
        return false;
    }
    if (basis.level <= 0) {
        layer.msg("等级不能为空或小于1", {icon: 2});
        return false;
    }
}