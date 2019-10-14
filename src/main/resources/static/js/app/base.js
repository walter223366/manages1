charset="utf-8";
manages="base";
expNum=100;//TODO 暂时为1等级为100经验值
attNum=5;//TODO 1等级5点属性值
$(function(){
    pagingQuery();
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.nickname = $("#query_nickname").val();
    params.cancellation = $("#query_cancellation").val();
    params.enable = $("#query_enable").val();
    params.is_npc = $("#query_isNpc").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 100},
        {field: 'nickname', title: '人物名称', width: 100},
        {field: 'userName', title: '所属账号', width: 100},
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
    var content = [splictUrl + '/system/baseAdd'];
    layerOpen(2, "新增", content, 1200, 600, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.nickname = getVal(layero, "add_nickname");
            params.school_id = getVal(layero, "add_school");
            params.enable = getVal(layero, "add_enable");
            params.sex = (getVal(layero, "add_sex"));
            params.level = (getVal(layero, "add_level"));
            params.attitude = (getVal(layero, "add_attitude"));
            params.characters = (getVal(layero, "add_character"));
            params.popularity = (getVal(layero, "add_popularity"));
            params.coin = (getVal(layero, "add_coin"));
            params.gold = (getVal(layero, "add_gold"));
            params.school_contribution = (getVal(layero, "add_school_contribution"));
            params.experience = (getVal(layero, "add_experience"));
            if (verIfy(params) === false) {
                return;
            }
            var akf = JSON.parse(sessionStorage.getItem("akf"));
            if(akf === null){
                akf = [];
            }
            params.kongFu = akf;
            params.virtue = parFormat(sessionStorage.getItem("avt"));
            params.weapon = parFormat(sessionStorage.getItem("awp"));
            params.potent = parFormat(sessionStorage.getItem("apt"));
            alert(JSON.stringify(params));
            sessionStorage.removeItem("avt");
            sessionStorage.removeItem("akf");
            sessionStorage.removeItem("awp");
            sessionStorage.removeItem("apt");
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset(layero);
        });
}

function aVirtueBut() {
    var level = $("#add_level").val();
    if (level !== "") {
        var content = $("#aAttInfo");
        layerOpen(1, "属性分配", content, 900, 400, "确定", "重置", "",
            function (index, layero) {
                var virtue = {};
                virtue.physique = $("#add_mphysique").val();
                virtue.force = $("#add_force").val();
                virtue.muscles = $("#add_muscles").val();
                virtue.chakra = $("#add_chakra").val();
                virtue.sensitivity = $("#add_sensitivity").val();
                virtue.willpower = $("#add_willpower").val();
                virtue.knowledge = $("#add_knowledge").val();
                virtue.lucky = $("#add_lucky").val();
                var sum = Number(virtue.physique) + Number(virtue.force);
                sum += Number(virtue.muscles);
                sum += Number(virtue.chakra);
                sum += Number(virtue.sensitivity);
                sum += Number(virtue.willpower);
                sum += Number(virtue.knowledge);
                sum += Number(virtue.lucky);
                var attNum = $("#add_attributes").val();
                if (sum > attNum) {
                    layer.msg("各项属性值的总和已超过了属性总值");
                    return;
                }
                sessionStorage.setItem("avt", JSON.stringify(virtue));
                layer.closeAll();
            }, function (index, layero) {
                clearVal(virtueID, 1);
            });
    } else {
        layer.msg("请先填写等级");
    }
}

function increase(id) {
    var obj = "#" + id;
    var num = parseInt($(obj).val()) || 0;
    $(obj).val(num + 1);
}

function cutback(id) {
    var obj = "#" + id;
    var num = parseInt($(obj).val()) || 0;
    num = num - 1;
    num = num < 1 ? 0 : num;
    $(obj).val(num);
}

function calcuAttNum(a,b,c,d,e,f,m,n) {
    var sum = Number($("#" + a)) + Number($("#" + b)) + Number($("#" + c)) + Number($("#" + d));
    sum = sum + Number($("#" + e)) + Number($("#" + f)) + Number($("#" + m)) + Number($("#" + n));
    var attNum = $("#add_attributes").val();
    if (sum >= attNum) {
        var beyond = Number(sum) - Number(attNum);
        return "属性值分配已超出：" + beyond;
    } else {
        var remain = Number(attNum) - Number(sum);
        return "剩余未分配属性值：" + remain;
    }
}

function aKongFuBut() {
    var content = $("#aArtsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "确定", "重置", "",
        function (layero, index) {
            var kongFu = [];
            var v1 = {}, v2 = {}, v3 = {}, v4 = {};
            var kongFu1 = document.getElementById("add_kongFu1").value;
            var exp1 = document.getElementById("add_experience1").value;
            if (kongFu1 !== "") {
                v1.name = kongFu1;
                v1.experience = Number(exp1);
                kongFu.push(v1);
            }
            var kongFu2 = document.getElementById("add_kongFu2").value;
            var exp2 = document.getElementById("add_experience2").value;
            if (kongFu2 !== "") {
                v2.name = kongFu2;
                v2.experience = Number(exp2);
                kongFu.push(v2);
            }
            var kongFu3 = document.getElementById("add_kongFu3").value;
            var exp3 = document.getElementById("add_experience3").value;
            if (kongFu3 !== "") {
                v3.name = kongFu3;
                v3.experience = Number(exp3);
                kongFu.push(v3);
            }
            var kongFu4 = document.getElementById("add_kongFu4").value;
            var exp4 = document.getElementById("add_experience4").value;
            if (kongFu4 !== "") {
                v4.name = kongFu4;
                v4.experience = Number(exp4);
                kongFu.push(v4);
            }
            sessionStorage.setItem("akf", JSON.stringify(kongFu));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(kongFuID, 1);
            document.getElementById("add_experience1").disabled = true;
            document.getElementById("add_experience2").disabled = true;
            document.getElementById("add_experience3").disabled = true;
            document.getElementById("add_experience4").disabled = true;
        });
}

function addTypeBut() {
    var type = $("#add_kfTypes").find("option:selected").val();
    var downs = {};
    downs.type = type;
    $("#add_kongFu").empty();
    downBox(downs, kongFuBox, "add_kongFu", "", lSelection);
}

/*$(function(){
    $("#add_kongFuInfo").html($("#formKongFu").html());
});*/

function addKongFu() {
    var kongFu = $("#add_kongFu").find("option:selected").text();
    if (kongFu === "请选择") {
        layer.msg("请选择武学选项");
        return;
    }
    var div = document.getElementById("aArtsInfo");
    var div1 = document.createElement("div");
    div1.className = "layui-form-item";
    var label = document.createElement("label");
    label.className = "layui-form-label";
    div1.appendChild(label);
    var div2 = document.createElement("div");
    div2.className = "layui-input-inline";
    var input = document.createElement("input");
    input.className = "layui-input";
    input.setAttribute('type', 'text');
    input.setAttribute('ReadOnly', 'True');  //设置文本为只读类型
    input.value = kongFu;
    div2.appendChild(input);
    div.appendChild(div1);
    div.appendChild(div2);
}

function addKongFu1() {
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
        $('#add_experience1').removeAttr("disabled");
    } else if (kongFu2.value === '') {
        kongFu2.value = kongFu;
        $('#add_experience2').removeAttr("disabled");
    } else if (kongFu3.value === '') {
        kongFu3.value = kongFu;
        $('#add_experience3').removeAttr("disabled");
    } else if (kongFu4.value === '') {
        kongFu4.value = kongFu;
        $('#add_experience4').removeAttr("disabled");
    } else {
        layer.msg("添加武学选项已满");
    }
}

function resetKongFu(a,b) {
    document.getElementById(a).value = '';
    document.getElementById(b).value = '';
    document.getElementById(b).disabled = true;
}

function aWeaponBut() {
    var content = $("#aWeaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "确定", "重置", "",
        function (index, layero) {
            var weapon = {};
            weapon.melee_status = document.getElementById("add_melee_status").value;
            weapon.sword_status = document.getElementById("add_sword_status").value;
            weapon.axe_status = document.getElementById("add_axe_status").value;
            weapon.javelin_status = document.getElementById("add_javelin_status").value;
            weapon.hidden_weapons_status = document.getElementById("add_hidden_weapons_status").value;
            weapon.sorcery_status = document.getElementById("add_sorcery_status").value;
            weapon.dodge_skill_status = document.getElementById("add_dodge_skill_status").value;
            weapon.chakra_status = document.getElementById("add_chakra_status").value;
            sessionStorage.setItem("awp", JSON.stringify(weapon));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 1);
        });
}

function aPotentBut() {
    var content = $("#aPotentInfo");
    layerOpen(1, "人物潜能", content, 900, 400, "确定", "重置", "",
        function (index, layero) {
            var potent = {};
            potent.wisdom1 = (document.getElementById("add_wisdom1").value);
            potent.wisdom2 = (document.getElementById("add_wisdom2").value);
            potent.wisdom3 = (document.getElementById("add_wisdom3").value);
            potent.healthy1 = (document.getElementById("add_healthy1").value);
            potent.healthy2 = (document.getElementById("add_healthy2").value);
            potent.healthy3 = (document.getElementById("add_healthy3").value);
            potent.mellow1 = (document.getElementById("add_mellow1").value);
            potent.mellow2 = (document.getElementById("add_mellow2").value);
            potent.mellow3 = (document.getElementById("add_mellow3").value);
            potent.mellow4 = (document.getElementById("add_mellow4").value);
            potent.mellow5 = (document.getElementById("add_mellow5").value);
            potent.fineness1 = (document.getElementById("add_fineness1").value);
            potent.fineness2 = (document.getElementById("add_fineness2").value);
            potent.burst1 = (document.getElementById("add_burst1").value);
            potent.burst2 = (document.getElementById("add_burst2").value);
            potent.sharp1 = (document.getElementById("add_sharp1").value);
            potent.sharp2 = (document.getElementById("add_sharp2").value);
            potent.tenacity1 = (document.getElementById("add_tenacity1").value);
            potent.tenacity2 = (document.getElementById("add_tenacity2").value);
            potent.tenacity3 = (document.getElementById("add_tenacity3").value);
            sessionStorage.setItem("apt", JSON.stringify(potent));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(potentID, 1);
        });
}

$(function () {
    $("#add_level").on("input", function (e) {
        var obj = e.delegateTarget.value;
        calAttr(obj, expNum, "add_experience");
        calAttr(obj, attNum, "add_attributes");
    });
});

$(function () {
    $("#edit_level").on("input", function (e) {
        var obj = e.delegateTarget.value;
        calAttr(obj, expNum, "edit_experience");
        calAttr(obj, attNum, "edit_attributes");
    });
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
                        document.getElementById("see_character").value = isNull(obj.characters);
                        document.getElementById("see_popularity").value = isNull(obj.popularity);
                        document.getElementById("see_coin").value = isNull(obj.coin);
                        document.getElementById("see_gold").value = isNull(obj.gold);
                        document.getElementById("see_experience").value = isNull(obj.experience);
                        document.getElementById("see_school_contribution").value = isNull(obj.school_contribution);
                        document.getElementById("see_school").value = isNull(obj.schoolName);
                        document.getElementById("see_sex").value = isNull(obj.sex);
                        document.getElementById("see_enable").value = isNull(obj.enable=1?"已启用":"未启用");
                        document.getElementById("see_user").value = isNull(obj.userName);
                        if(obj.virtue.length > 0){
                            var virtue = obj.virtue[0];
                            document.getElementById("see_mphysique").value = isNull(virtue.physique);
                            document.getElementById("see_force").value = isNull(virtue.force);
                            document.getElementById("see_muscles").value = isNull(virtue.muscles);
                            document.getElementById("see_chakra").value = isNull(virtue.chakra);
                            document.getElementById("see_sensitivity").value = isNull(virtue.sensitivity);
                            document.getElementById("see_willpower").value = isNull(virtue.willpower);
                            document.getElementById("see_knowledge").value = isNull(virtue.knowledge);
                            document.getElementById("see_lucky").value = isNull(virtue.lucky);
                        }
                        if(obj.kongFu.length > 0){
                            var kongFu1 = obj.kongFu[0];
                            document.getElementById("see_kongFu1").value = isNull(kongFu1.kongFuName);
                            document.getElementById("see_experience1").value = isNull(kongFu1.experience);
                            var kongFu2 = obj.kongFu[1];
                            if(kongFu2 !== "undefined"){
                                document.getElementById("see_kongFu2").value = isNull(kongFu2.kongFuName);
                                document.getElementById("see_experience3").value = isNull(kongFu2.experience);
                            }
                            var kongFu3 = obj.kongFu[1];
                            if(kongFu3 !== "undefined"){
                                document.getElementById("see_kongFu3").value = isNull(kongFu3.kongFuName);
                                document.getElementById("see_experience3").value = isNull(kongFu3.experience);
                            }
                            var kongFu4 = obj.kongFu[1];
                            if(kongFu4 !== "undefined"){
                                document.getElementById("see_kongFu4").value = isNull(kongFu4.kongFuName);
                                document.getElementById("see_experience4").value = isNull(kongFu4.experience);
                            }
                        }
                        if(obj.weapon.length > 0){
                            var weapon = obj.weapon[0];
                            document.getElementById("see_melee_status").value = isNull(weapon.melee_status);
                            document.getElementById("see_sword_status").value = isNull(weapon.sword_status);
                            document.getElementById("see_axe_status").value = isNull(weapon.axe_status);
                            document.getElementById("see_javelin_status").value = isNull(weapon.javelin_status);
                            document.getElementById("see_hidden_weapons_status").value = isNull(weapon.hidden_weapons_status);
                            document.getElementById("see_sorcery_status").value = isNull(weapon.sorcery_status);
                            document.getElementById("see_dodge_skill_status").value = isNull(weapon.dodge_skill_status);
                            document.getElementById("see_chakra_status").value = isNull(weapon.chakra_status);
                        }
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
    downBox(downs, schoolBox, "edit_school", "", lSelection);
    var params = {nickname: data.nickname};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = [splictUrl + '/system/baseUpdate'];
                layerOpen(2, "编辑", content, 1200, 600, "立即提交", "重置",
                    function (index, layero) {
                        editVal(layero, "edit_nickname", obj.nickname);
                        editVal(layero, "edit_level", obj.level);
                        editVal(layero, "edit_attitude", obj.attitude);
                        editVal(layero, "edit_character", obj.characters);
                        editVal(layero, "edit_popularity", obj.popularity);
                        editVal(layero, "edit_coin", obj.coin);
                        editVal(layero, "edit_gold", obj.gold);
                        editVal(layero, "edit_experience", obj.experience);
                        editVal(layero, "edit_school_contribution", obj.school_contribution);
                        editVal(layero, "edit_enable", obj.enable);
                        editVal(layero, "edit_sex", obj.sex);
                        editVal(layero, "edit_school", obj.school_id);
                        layui.form.render("select");
                        sessionStorage.setItem("qvt", JSON.stringify(obj.virtue));
                        sessionStorage.setItem("qkf", JSON.stringify(obj.kongFu));
                        sessionStorage.setItem("qmp", JSON.stringify(obj.weapon));
                    }, function (index, layero) {
                        var params = {};
                        params.id = obj.id;
                        params.nickname = getVal(layero, "edit_nickname");
                        params.school_id = getVal(layero, "edit_school");
                        params.enable = getVal(layero, "edit_enable");
                        params.sex = Number(getVal(layero, "edit_sex"));
                        params.level = Number(getVal(layero, "edit_level"));
                        params.attitude = Number(getVal(layero, "edit_attitude"));
                        params.characters = Number(getVal(layero, "edit_character"));
                        params.popularity = Number(getVal(layero, "edit_popularity"));
                        params.coin = Number(getVal(layero, "edit_coin"));
                        params.gold = Number(getVal(layero, "edit_gold"));
                        params.school_contribution = Number(getVal(layero, "edit_school_contribution"));
                        params.experience = Number(getVal(layero, "edit_experience"));
                        if (verIfy(params) === false) {
                            return;
                        }
                        var ekf = JSON.parse(sessionStorage.getItem("ekf"));
                        if (ekf === null) {
                            ekf = [];
                        }
                        params.kongFu = ekf;
                        params.virtue = parFormat(sessionStorage.getItem("evt"));
                        params.weapon = parFormat(sessionStorage.getItem("ewp"));
                        alert(JSON.stringify(params));
                        sessionStorage.removeItem("evt");
                        sessionStorage.removeItem("ekf");
                        sessionStorage.removeItem("ewp");
                        aaUp(params, manages, update, "修改", pagingQuery);
                    }, function (index, layero) {
                        editReset(layero);
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

function eVirtueBut() {
    var content = $("#eAttInfo");
    layerOpen(1, "属性分配", content, 900, 400, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("evt"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("qvt"));
            }
            if (obj.length > 0) {
                var virtue = obj[0];
                document.getElementById("edit_mphysique").value = isNull(virtue.physique);
                document.getElementById("edit_force").value = isNull(virtue.force);
                document.getElementById("edit_muscles").value = isNull(virtue.muscles);
                document.getElementById("edit_chakra").value = isNull(virtue.chakra);
                document.getElementById("edit_sensitivity").value = isNull(virtue.sensitivity);
                document.getElementById("edit_willpower").value = isNull(virtue.willpower);
                document.getElementById("edit_knowledge").value = isNull(virtue.knowledge);
                document.getElementById("edit_lucky").value = isNull(virtue.lucky);
            }
        }, function (index, layero) {
            var virtue = {};
            virtue.physique = document.getElementById("edit_mphysique").value;
            virtue.force = document.getElementById("edit_force").value;
            virtue.muscles = document.getElementById("edit_muscles").value;
            virtue.chakra = document.getElementById("edit_chakra").value;
            virtue.sensitivity = document.getElementById("edit_sensitivity").value;
            virtue.willpower = document.getElementById("edit_willpower").value;
            virtue.knowledge = document.getElementById("edit_knowledge").value;
            virtue.lucky = document.getElementById("edit_lucky").value;
            sessionStorage.setItem("evt", JSON.stringify(virtue));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(virtueID, 2);
        });
}

function eKongFuBut() {
    var downs = {};
    downBox(downs, kongFuBox, "edit_kongFu", "", lSelection);
    var content = $("#eArtsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "确定", "重置",
        function (layero, index) {
            var obj = JSON.parse(sessionStorage.getItem("ekf"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("qkf"));
            }
            if (obj.length > 0) {

            }
        }, function (layero, index) {
            var kongFu = [];
            var v1 = {}, v2 = {}, v3 = {}, v4 = {};
            var kongFu1 = document.getElementById("edit_kongFu1").value;
            var exp1 = document.getElementById("edit_experience1").value;
            if (kongFu1 !== "") {
                v1.kongFu = kongFu1;
                v1.exp = Number(exp1);
                kongFu.push(v1);
            }
            var kongFu2 = document.getElementById("edit_kongFu2").value;
            var exp2 = document.getElementById("edit_experience2").value;
            if (kongFu2 !== "") {
                v2.kongFu = kongFu2;
                v2.exp = Number(exp2);
                kongFu.push(v2);
            }
            var kongFu3 = document.getElementById("edit_kongFu3").value;
            var exp3 = document.getElementById("edit_experience3").value;
            if (kongFu3 !== "") {
                v3.kongFu = kongFu3;
                v3.exp = Number(exp3);
                kongFu.push(v3);
            }
            var kongFu4 = document.getElementById("edit_kongFu4").value;
            var exp4 = document.getElementById("edit_experience4").value;
            if (kongFu4 !== "") {
                v4.kongFu = kongFu4;
                v4.exp = Number(exp4);
                kongFu.push(v4);
            }
            sessionStorage.setItem("ekf", JSON.stringify(kongFu));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(kongFuID, 2);
            $('#edit_experience1').removeAttr("disabled");
            $('#edit_experience2').removeAttr("disabled");
            $('#edit_experience3').removeAttr("disabled");
            $('#edit_experience4').removeAttr("disabled");
        });
}

function editKongFu() {
    var kongFu = $("#edit_kongFu").find("option:selected").text();
    var kongFu1 = document.getElementById("edit_kongFu1");
    var kongFu2 = document.getElementById("edit_kongFu2");
    var kongFu3 = document.getElementById("edit_kongFu3");
    var kongFu4 = document.getElementById("edit_kongFu4");
    if (kongFu === "请选择") {
        layer.msg("请选择武学选项");
        return;
    }
    if (kongFu1.value === '') {
        kongFu1.value = kongFu;
        $('#edit_experience1').removeAttr("disabled");
    } else if (kongFu2.value === '') {
        kongFu2.value = kongFu;
        $('#edit_experience2').removeAttr("disabled");
    } else if (kongFu3.value === '') {
        kongFu3.value = kongFu;
        $('#edit_experience3').removeAttr("disabled");
    } else if (kongFu4.value === '') {
        kongFu4.value = kongFu;
        $('#edit_experience4').removeAttr("disabled");
    } else {
        layer.msg("添加武学选项已满");
    }
}

function eWeaponBut() {
    var content = $("#eWeaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("ewp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("qwp"));
            }
            if(obj !== null){
                if (obj.length > 0) {
                    var weapon = obj[0];
                    document.getElementById("edit_melee_status").value = isNull(weapon.melee_status);
                    document.getElementById("edit_sword_status").value = isNull(weapon.sword_status);
                    document.getElementById("edit_axe_status").value = isNull(weapon.axe_status);
                    document.getElementById("edit_javelin_status").value = isNull(weapon.javelin_status);
                    document.getElementById("edit_hidden_weapons_status").value = isNull(weapon.hidden_weapons_status);
                    document.getElementById("edit_sorcery_status").value = isNull(weapon.sorcery_status);
                    document.getElementById("edit_dodge_skill_status").value = isNull(weapon.dodge_skill_status);
                    document.getElementById("edit_chakra_status").value = isNull(weapon.chakra_status);
                }
            }
        }, function (index, layero) {
            var weapon = {};
            weapon.melee_status = document.getElementById("edit_melee_status").value;
            weapon.sword_status = document.getElementById("edit_sword_status").value;
            weapon.axe_status = document.getElementById("edit_axe_status").value;
            weapon.javelin_status = document.getElementById("edit_javelin_status").value;
            weapon.hidden_weapons_status = document.getElementById("edit_hidden_weapons_status").value;
            weapon.sorcery_status = document.getElementById("edit_sorcery_status").value;
            weapon.dodge_skill_status = document.getElementById("edit_dodge_skill_status").value;
            weapon.chakra_status = document.getElementById("edit_chakra_status").value;
            sessionStorage.setItem("ewp", JSON.stringify(weapon));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 2);
        });
}

function ePotentBut() {
    var content = $("#ePotentInfo");
    layerOpen(1, "人物潜能", content, 900, 400, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("ewp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("qwp"));
            }
            if(obj !== null){
                if (obj.length > 0) {
                    var weapon = obj[0];
                    document.getElementById("edit_melee_status").value = isNull(weapon.melee_status);
                    document.getElementById("edit_sword_status").value = isNull(weapon.sword_status);
                    document.getElementById("edit_axe_status").value = isNull(weapon.axe_status);
                    document.getElementById("edit_javelin_status").value = isNull(weapon.javelin_status);
                    document.getElementById("edit_hidden_weapons_status").value = isNull(weapon.hidden_weapons_status);
                    document.getElementById("edit_sorcery_status").value = isNull(weapon.sorcery_status);
                    document.getElementById("edit_dodge_skill_status").value = isNull(weapon.dodge_skill_status);
                    document.getElementById("edit_chakra_status").value = isNull(weapon.chakra_status);
                }
            }
        }, function (index, layero) {
            var weapon = {};
            weapon.melee_status = document.getElementById("edit_melee_status").value;
            weapon.sword_status = document.getElementById("edit_sword_status").value;
            weapon.axe_status = document.getElementById("edit_axe_status").value;
            weapon.javelin_status = document.getElementById("edit_javelin_status").value;
            weapon.hidden_weapons_status = document.getElementById("edit_hidden_weapons_status").value;
            weapon.sorcery_status = document.getElementById("edit_sorcery_status").value;
            weapon.dodge_skill_status = document.getElementById("edit_dodge_skill_status").value;
            weapon.chakra_status = document.getElementById("edit_chakra_status").value;
            sessionStorage.setItem("ewp", JSON.stringify(weapon));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 2);
        });
}

function editReset(layero) {
    cleanVal(layero, "edit_nickname");
    cleanVal(layero, "edit_level");
    cleanVal(layero, "edit_attitude");
    cleanVal(layero, "edit_character");
    cleanVal(layero, "edit_popularity");
    cleanVal(layero, "edit_coin");
    cleanVal(layero, "edit_gold");
    cleanVal(layero, "edit_experience");
    cleanVal(layero, "edit_school_contribution");
    cleanVal(layero, "edit_experience");
    cleanVal(layero, "edit_experience");
    cleanVal(layero, "edit_experience");
}

function verIfy(params) {
    if (params.nickname === null || params.nickname === "") {
        layer.msg("人物名称不能为空", {icon: 2});
        return false;
    }
    if (params.school_id === null || params.school_id === "") {
        layer.msg("所属门派不能为空", {icon: 2});
        return false;
    }
    if (params.level <= 0) {
        layer.msg("等级不能为空或小于1", {icon: 2});
        return false;
    }
    if(params.attitude !== "") {
        var reg = /^(\-|\+)?\d+(\.\d+)?$/;
        if (!reg.test(params.attitude)) {
            layer.msg("态度格式错误", {icon: 2});
        }
    }
    return true;
}

virtueID = "mphysique,force,muscles,chakra,sensitivity,willpower,knowledge,lucky";
kongFuID = "kongFu1,experience1,kongFu2,experience2,kongFu3,experience3,kongFu4,experience4,kongFu";
weaponID = "melee_status,sword_status,axe_status,javelin_status,hidden_weapons_status,sorcery_status";
potentID = "wisdom1,wisdom2,wisdom3,healthy1,healthy2,healthy3,mellow1,mellow2,mellow3,mellow4,mellow5," +
    "fineness1,fineness2,burst1,burst2,sharp1,sharp2,tenacity1,tenacity2,tenacity3";