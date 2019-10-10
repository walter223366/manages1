charset="utf-8";
manages="baseNpc";
expNum=100;//TODO 暂时为1等级为100经验值
attNum=5;//TODO 1等级5点属性值
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
    var content = [splictUrl + '/system/baseNpcAdd'];
    layerOpen(2, "新增", content, 1200, 600, "立即提交", "重置",
        function(index, layero){
            var body = layer.getChildFrame('body', layero);
            var addSchool = body.contents().find("#add_school");
            alert(JSON.stringify(addSchool));
            downBox(schoolBox, addSchool, "", lSelection);
        },function (index, layero) {
            var params = {};
            var basis = {};//基础信息
            basis.nickname = getVal(layero, "add_nickname");
            basis.school_id = getVal(layero, "add_school");
            basis.sex = getVal(layero, "add_sex");
            basis.level = getVal(layero, "add_level");
            basis.attitude = getVal(layero, "add_attitude");
            basis.character = getVal(layero, "add_character");
            basis.popularity = getVal(layero, "add_popularity");
            basis.coin = getVal(layero, "add_coin");
            basis.gold = getVal(layero, "add_gold");
            basis.school_contribution = getVal(layero, "add_school_contribution");
            basis.experience = getVal(layero, "add_experience");
            if (verIfy(basis) === false) {
                return;
            }
            params.basis = basis;
            params.virtue = JSON.parse(sessionStorage.getItem("virtue"));
            params.kongFu = JSON.parse(sessionStorage.getItem("kongFu"));
            params.weapon = JSON.parse(sessionStorage.getItem("weapon"));
            alert(JSON.stringify(params));
            sessionStorage.removeItem("virtue");
            sessionStorage.removeItem("kongFu");
            sessionStorage.removeItem("weapon");
            aaUp(params, manages, "insert", "新增", pagingQuery)
        }, function (index, layero) {
            addReset(layero);
        });
}

function virtueBut() {
    var content = $("#attInfo");
    layerOpen(1, "属性分配", content, 900, 400, "确定", "重置",
        function (index, layero) {
            var virtue = {};
            virtue.physique = document.getElementById("add_mphysique").value;
            virtue.force = document.getElementById("add_force").value;
            virtue.muscles = document.getElementById("add_muscles").value;
            virtue.chakra = document.getElementById("add_chakra").value;
            virtue.sensitivity = document.getElementById("add_sensitivity").value;
            virtue.willpower = document.getElementById("add_willpower").value;
            virtue.knowledge = document.getElementById("add_knowledge").value;
            virtue.lucky = document.getElementById("add_lucky").value;
            sessionStorage.setItem("virtue", JSON.stringify(virtue));
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
    var content = $("#artsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "确定", "重置",
        function (layero, index) {
            var kongFu = {};
            kongFu.kongFu1 = document.getElementById("add_kongFu1").value;
            kongFu.exp1 = document.getElementById("add_experience1").value;
            kongFu.kongFu2 = document.getElementById("add_kongFu2").value;
            kongFu.exp2 = document.getElementById("add_experience2").value;
            kongFu.kongFu3 = document.getElementById("add_kongFu3").value;
            kongFu.exp3 = document.getElementById("add_experience3").value;
            kongFu.kongFu4 = document.getElementById("add_kongFu4").value;
            kongFu.exp4 = document.getElementById("add_experience4").value;
            sessionStorage.setItem("kongFu", JSON.stringify(kongFu));
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
    var content = $("#weaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "确定", "重置",
        function () {
            var weapon = {};
            weapon.melee = document.getElementById("add_melee_status").value;
            weapon.sword = document.getElementById("add_sword_status").value;
            weapon.axe = document.getElementById("add_axe_status").value;
            weapon.javelin = document.getElementById("add_javelin_status").value;
            weapon.hiddens = document.getElementById("hidden_weapons_status").value;
            weapon.sorcery = document.getElementById("add_sorcery_status").value;
            weapon.dodges = document.getElementById("add_dodge_skill_status").value;
            weapon.chakra = document.getElementById("chakra_status").value;
            sessionStorage.setItem("weapon", JSON.stringify(weapon));
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

function addReset(layero) {
    cleanVal(layero, "add_nickname");
    cleanVal(layero, "add_level");
    cleanVal(layero, "add_attitude");
    cleanVal(layero, "add_character");
    cleanVal(layero, "add_popularity");
    cleanVal(layero, "add_coin");
    cleanVal(layero, "add_gold");
    cleanVal(layero, "add_experience");
    cleanVal(layero, "add_school_contribution");
    cleanVal(layero, "add_img");
    $(layero).find("iframe")[0].contentWindow.document.getElementById("add_enable").value = 1;
    $(layero).find("iframe")[0].contentWindow.document.getElementById("add_sex").value = 1;
    layui.form.render("select");
}

function see(data) {
    var params = {nickname: data.nickname};
    postRequest(params, manages, sDetails, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = $("#seeInfo");
                layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭",
                    function (index, layero) {
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
    downBox(schoolBox, "edit_school", "", lSelection);
    var params = {nickname: data.nickname};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                /*document.getElementById("edit_id").value = isNull(obj.id);
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
                layui.form.render("select");*/
                var content = [splictUrl + '/system/baseNpcUpdate'];
                layerOpen(2, "编辑", content, 1200, 600, "立即提交", "重置",
                    function (index, layero) {

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

function verIfy(basis) {
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
    return true;
}