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
        {field: 'sexValue', title: '性别'},
        {field: 'level', title: '等级', sort: true},
        {field: 'attitude', title: '态度'},
        {field: 'characters', title: '性格'},
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
    $("#query_cancellation").val('');
    $("#query_isNpc").val('');
    layui.form.render("select");
    pagingQuery();
}

function add() {
    var content = [splictUrl + '/system/baseAdd'];
    layerOpen(2, "新增", content, 1100, 600, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.nickname = getVal(layero, "add_nickname");
            params.school_id = getVal(layero, "add_school");
            params.enable = Number(getVal(layero, "add_enable"));
            params.sex = Number(getVal(layero, "add_sex"));
            params.level = Number(getVal(layero, "add_level"));
            params.attitude = Number(getVal(layero, "add_attitude"));
            params.characters = Number(getVal(layero, "add_characters"));
            params.popularity = Number(getVal(layero, "add_popularity"));
            params.coin = Number(getVal(layero, "add_coin"));
            params.gold = Number(getVal(layero, "add_gold"));
            params.school_contribution = Number(getVal(layero, "add_school_contribution"));
            params.experience = Number(getVal(layero, "add_experience"));
            params.is_npc = Number(getVal(layero, "add_isNpc"));
            params.user_id = getVal(layero, "add_account");
            if (verIfy(params) === false) {
                return;
            }
            var akf = JSON.parse(sessionStorage.getItem("akf"));
            if (akf === null) {
                akf = [];
            }
            params.kongfu_have_id = akf;
            params.virtue = parFormat(sessionStorage.getItem("avt"));
            params.weapon = parFormat(sessionStorage.getItem("awp"));
            params.potent = parFormat(sessionStorage.getItem("apt"));
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
        if (level === "0") {
            layer.msg("请填写等级大于0的数");
            return;
        }
        var content = $("#aAttInfo");
        layerOpen(1, "属性分配", content, 900, 400, "保存", "重置", "",
            function (index, layero) {
                var virtue = {};
                virtue.physique = Number($("#add_physique").val());
                virtue.forces = Number($("#add_forces").val());
                virtue.muscles = Number($("#add_muscles").val());
                virtue.mp = Number($("#add_mp").val());
                virtue.sensitivity = Number($("#add_sensitivity").val());
                virtue.willpower = Number($("#add_willpower").val());
                virtue.knowledge = Number($("#add_knowledge").val());
                virtue.lucky = Number($("#add_lucky").val());
                var sum = calAdd(virtue);
                var attNum = ($("#add_attributes").val());
                if (sum > attNum) {
                    layer.msg("各项属性值总和超出总属值的：" + calSub(sum, attNum));
                    return;
                } else {
                    layer.confirm('余下 ' + calSub(attNum, sum) + ' 点属性值待分配', {
                            btn: ['确认', '取消']
                        }, function () {
                            sessionStorage.setItem("avt", JSON.stringify(virtue));
                            layer.closeAll();
                        }, function () {
                            layer.close();
                        }
                    );
                }
            }, function (index, layero) {
                clearVal(virtueID, 1);
            });
    } else {
        layer.msg("等级不能为空");
    }
}

function aKongFuBut() {
    var content = $("#aArtsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "保存", "关闭", "",
        function (layero, index) {
            var kongFu = [];
            var forms = document.getElementById("formKongFu");
            var div = forms.getElementsByClassName("inp");
            for (var i = 1; i <= div.length; i++) {
                var diValue = {};
                diValue.exp = Number($("#exp" + i).val());
                var a = $("#use" + i).val();
                if(a === "未使用"){
                    diValue.use = "0";
                }else{
                    diValue.use = "1";
                }
                diValue.id = $("#id" + i).val();
                kongFu.push(diValue);
            }
            sessionStorage.setItem("akf", JSON.stringify(kongFu));
            layer.closeAll();
        }, function (index, layero) {
            layer.closeAll();
        });
}

function aWeaponBut() {
    var content = $("#aWeaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "保存", "重置", "",
        function (index, layero) {
            var weapon = {};
            weapon.melee_status = Number($("#add_melee_status").val());
            weapon.sword_status = Number($("#add_sword_status").val());
            weapon.axe_status = Number($("#add_axe_status").val());
            weapon.javelin_status = Number($("#add_javelin_status").val());
            weapon.hidden_weapons_status = Number($("#add_hidden_weapons_status").val());
            weapon.sorcery_status = Number($("#add_sorcery_status").val());
            weapon.mp_status = Number($("#add_mp_status").val());
            weapon.dodge_skill_status = Number($("#add_dodge_skill_status").val());
            sessionStorage.setItem("awp", JSON.stringify(weapon));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 1);
        });
}

function aPotentBut() {
    var content = $("#aPotentInfo");
    layerOpen(1, "人物潜能", content, 900, 400, "保存", "重置", "",
        function (index, layero) {
            var potent = {};
            potent.wisdom1 = Number($("#add_wisdom1").val());
            potent.wisdom2 = Number($("#add_wisdom2").val());
            potent.wisdom3 = Number($("#add_wisdom3").val());
            potent.healthy1 = Number($("#add_healthy1").val());
            potent.healthy2 = Number($("#add_healthy2").val());
            potent.healthy3 = Number($("#add_healthy3").val());
            potent.mellow1 = Number($("#add_mellow1").val());
            potent.mellow2 = Number($("#add_mellow2").val());
            potent.mellow3 = Number($("#add_mellow3").val());
            potent.mellow4 = Number($("#add_mellow4").val());
            potent.mellow5 = Number($("#add_mellow5").val());
            potent.fineness1 = Number($("#add_fineness1").val());
            potent.fineness2 = Number($("#add_fineness2").val());
            potent.burst1 = Number($("#add_burst1").val());
            potent.burst2 = Number($("#add_burst2").val());
            potent.sharp1 = Number($("#add_sharp1").val());
            potent.sharp2 = Number($("#add_sharp2").val());
            potent.tenacity1 = Number($("#add_tenacity1").val());
            potent.tenacity2 = Number($("#add_tenacity2").val());
            potent.tenacity3 = Number($("#add_tenacity3").val());
            sessionStorage.setItem("apt", JSON.stringify(potent));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(potentID, 1);
        });
}

function addReset(layero) {
    cleanVal(layero, "add_nickname", '');
    cleanVal(layero, "add_level", '');
    cleanVal(layero, "add_attitude", '');
    cleanVal(layero, "add_characters", '');
    cleanVal(layero, "add_popularity", '');
    cleanVal(layero, "add_coin", '');
    cleanVal(layero, "add_gold", '');
    cleanVal(layero, "add_experience", '');
    cleanVal(layero, "add_school_contribution", '');
    cleanVal(layero, "add_school", '');
    cleanVal(layero, "add_account", '');
    cleanVal(layero, "add_enable", '1');
    cleanVal(layero, "add_sex", '1');
    cleanVal(layero, "add_isNpc", '1');
}

function see(data) {
    var params = {nickname: data.nickname};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var content = $("#seeInfo");
                layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭",
                    function (index, layero) {
                        var obj = JSON.parse(rows);
                        $("#see_nickname").val(isNull(obj.nickname));
                        $("#see_level").val(isNull(obj.level));
                        $("#see_attitude").val(isNull(obj.attitude));
                        $("#see_characters").val(isNull(obj.characters));
                        $("#see_popularity").val(isNull(obj.popularity));
                        $("#see_coin").val(isNull(obj.coin));
                        $("#see_gold").val(isNull(obj.gold));
                        $("#see_experience").val(isNull(obj.experience));
                        $("#see_school_contribution").val(isNull(obj.school_contribution));
                        $("#see_school").val(isNull(obj.schoolName));
                        $("#see_sex").val(isNull(obj.sex));
                        $("#see_enable").val(isNull(obj.enable = 1 ? "已启用" : "未启用"));
                        $("#see_user").val(isNull(obj.userName));
                        $("#see_isNpc").val(isNull(obj.is_npc = 1 ? "NPC" : "玩家"));
                        var virtue = obj.virtue;
                        if (virtue !== null) {
                            $("#see_physique").val(isNull(virtue.physique));
                            $("#see_forces").val(isNull(virtue.forces));
                            $("#see_muscles").val(isNull(virtue.muscles));
                            $("#see_chakra").val(isNull(virtue.mp));
                            $("#see_sensitivity").val(isNull(virtue.sensitivity));
                            $("#see_willpower").val(isNull(virtue.willpower));
                            $("#see_knowledge").val(isNull(virtue.knowledge));
                            $("#see_lucky").val(isNull(virtue.lucky));
                        }
                        var weapon = obj.weapon;
                        if (weapon !== null) {
                            $("#see_melee_status").val(isNull(weapon.melee_status));
                            $("#see_sword_status").val(isNull(weapon.sword_status));
                            $("#see_axe_status").val(isNull(weapon.axe_status));
                            $("#see_javelin_status").val(isNull(weapon.javelin_status));
                            $("#see_hidden_weapons_status").val(isNull(weapon.hidden_weapons_status));
                            $("#see_sorcery_status").val(isNull(weapon.sorcery_status));
                            $("#see_mp_status").val(isNull(weapon.mp_status));
                            $("#see_dodge_skill_status").val(isNull(weapon.dodge_skill_status));
                        }
                        var potent = obj.potent;
                        if (potent !== null) {
                            $("#see_wisdom1").val(isNull(potent.wisdom1));
                            $("#see_wisdom2").val(isNull(potent.wisdom2));
                            $("#see_wisdom3").val(isNull(potent.wisdom3));
                            $("#see_healthy1").val(isNull(potent.healthy1));
                            $("#see_healthy2").val(isNull(potent.healthy2));
                            $("#see_healthy3").val(isNull(potent.healthy3));
                            $("#see_mellow1").val(isNull(potent.mellow1));
                            $("#see_mellow2").val(isNull(potent.mellow2));
                            $("#see_mellow3").val(isNull(potent.mellow3));
                            $("#see_mellow4").val(isNull(potent.mellow4));
                            $("#see_mellow5").val(isNull(potent.mellow5));
                            $("#see_fineness1").val(isNull(potent.fineness1));
                            $("#see_fineness2").val(isNull(potent.fineness2));
                            $("#see_burst1").val(isNull(potent.burst1));
                            $("#see_burst2").val(isNull(potent.burst2));
                            $("#see_sharp1").val(isNull(potent.sharp1));
                            $("#see_sharp2").val(isNull(potent.sharp2));
                            $("#see_tenacity1").val(isNull(potent.tenacity1));
                            $("#see_tenacity2").val(isNull(potent.tenacity2));
                            $("#see_tenacity3").val(isNull(potent.tenacity3));
                        }
                        var fkf = document.getElementById("formSeeKongFu");
                        $("#formSeeKongFu").empty();
                        if (obj.kongfu_have_id.length > 0) {
                            var kongFu = obj.kongfu_have_id;
                            for (var i = 0; i < kongFu.length; i++) {
                                var div = document.createElement("div");
                                div.id = "div" + i;
                                div.className = "layui-form-item";
                                div.style.marginLeft = "55px";
                                var div1 = document.createElement("div");
                                div1.className = "layui-input-inline";
                                div1.style.width = "700px";
                                var input1 = document.createElement("input");
                                input1.setAttribute("type", "text");
                                input1.className = "layui-input see-input";
                                input1.style.width = "700px";
                                input1.value = (i + 1) + "、 武学名称：" + kongFu[i].kfName + "        " +
                                    "武学类型：" + typeValue(kongFu[i].kfType) + "         " +
                                    "武学经验值：" + kongFu[i].exp + "        " +
                                    "使用情况：" + useValue(kongFu[i].use);
                                div1.appendChild(input1);
                                div.appendChild(div1);
                                fkf.appendChild(div);
                            }
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
    var params = {nickname: data.nickname};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = [splictUrl + '/system/baseUpdate'];
                layerOpen(2, "编辑", content, 1100, 600, "立即提交", "重置",
                    function (index, layero) {
                        editVal(layero, "edit_nickname", obj.nickname);
                        editVal(layero, "edit_level", obj.level);
                        editVal(layero, "edit_attitude", obj.attitude);
                        editVal(layero, "edit_characters", obj.characters);
                        editVal(layero, "edit_popularity", obj.popularity);
                        editVal(layero, "edit_coin", obj.coin);
                        editVal(layero, "edit_gold", obj.gold);
                        editVal(layero, "edit_experience", obj.experience);
                        editVal(layero, "edit_school_contribution", obj.school_contribution);
                        editVal(layero, "edit_enable", setEnable(obj.enable));
                        editVal(layero, "edit_isNpc", obj.is_npc);
                        editVal(layero, "edit_sex", obj.sex);
                        sessionStorage.setItem("schoolID", obj.school_id);
                        sessionStorage.setItem("userID", obj.user_id);
                        sessionStorage.setItem("qvt", JSON.stringify(obj.virtue));
                        sessionStorage.setItem("qkf", JSON.stringify(obj.kongfu_have_id));
                        sessionStorage.setItem("qwp", JSON.stringify(obj.weapon));
                        sessionStorage.setItem("qpt", JSON.stringify(obj.potent));
                    }, function (index, layero) {
                        var params = {};
                        params.id = obj.id;
                        params.nickname = getVal(layero, "edit_nickname");
                        params.school_id = getVal(layero, "edit_school");
                        params.enable = Number(getVal(layero, "edit_enable"));
                        params.sex = Number(getVal(layero, "edit_sex"));
                        params.level = Number(getVal(layero, "edit_level"));
                        params.attitude = Number(getVal(layero, "edit_attitude"));
                        params.characters = Number(getVal(layero, "edit_characters"));
                        params.popularity = Number(getVal(layero, "edit_popularity"));
                        params.coin = Number(getVal(layero, "edit_coin"));
                        params.gold = Number(getVal(layero, "edit_gold"));
                        params.school_contribution = Number(getVal(layero, "edit_school_contribution"));
                        params.experience = Number(getVal(layero, "edit_experience"));
                        params.school_id = getVal(layero, "edit_school");
                        params.user_id = getVal(layero, "edit_account");
                        params.is_npc = Number(getVal(layero,"edit_isNpc"));
                        if (verIfy(params) === false) {
                            return;
                        }
                        params.kongfu_have_id = getSessionValue("ekf", "qkf");
                        params.virtue = getSessionValue("evt", "qvt");
                        params.weapon = getSessionValue("ewp", "qwp");
                        params.potent = getSessionValue("ept", "qpt");
                        aaUp(params, manages, update, "修改", pagingQuery);
                        removeSession();
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
    var level = $("#edit_level").val();
    if (level !== "") {
        if (level === "0") {
            layer.msg("请填写等级大于0的数");
            return;
        }
        var content = $("#eAttInfo");
        layerOpen(1, "属性分配", content, 900, 400, "保存", "重置",
            function (index, layero) {
                var obj = JSON.parse(sessionStorage.getItem("evt"));
                if(obj === null){
                    var q = sessionStorage.getItem("qvt");
                    if(q !== "undefined"){
                        obj = JSON.parse(q);
                        var aNum = Number(level) * Number(attNum);
                        $("#edit_attributes").val(aNum);
                    }
                }
                if (obj !== null) {
                    $("#edit_physique").val(isNull(obj.physique));
                    $("#edit_forces").val(isNull(obj.forces));
                    $("#edit_muscles").val(isNull(obj.muscles));
                    $("#edit_mp").val(isNull(obj.mp));
                    $("#edit_sensitivity").val(isNull(obj.sensitivity));
                    $("#edit_willpower").val(isNull(obj.willpower));
                    $("#edit_knowledge").val(isNull(obj.knowledge));
                    $("#edit_lucky").val(isNull(obj.lucky));
                }
            }, function (index, layero) {
                var virtue = {};
                virtue.physique = Number($("#edit_physique").val());
                virtue.forces = Number($("#edit_forces").val());
                virtue.muscles = Number($("#edit_muscles").val());
                virtue.mp = Number($("#edit_mp").val());
                virtue.sensitivity = Number($("#edit_sensitivity").val());
                virtue.willpower = Number($("#edit_willpower").val());
                virtue.knowledge = Number($("#edit_knowledge").val());
                virtue.lucky = Number($("#edit_lucky").val());
                var sum = calAdd(virtue);
                var  attNum = ($("#edit_attributes").val());
                if (sum > attNum) {
                    layer.msg("各项属性值总和超出总属值的：" + calSub(sum, attNum));
                    return;
                } else {
                    layer.confirm('余下 ' + calSub(attNum, sum) + ' 点属性值待分配', {
                            btn: ['确认', '取消']
                        }, function () {
                            sessionStorage.setItem("evt", JSON.stringify(virtue));
                            layer.closeAll();
                        }, function () {
                            layer.close();
                        }
                    );
                }
            }, function (index, layero) {
                clearVal(virtueID, 2);
            });
    } else {
        layer.msg("等级不能为空");
    }
}

function eKongFuBut() {
    var downs = {};
    downBox(downs, kongFuBox, "edit_kongFu", "", lSelection);
    var content = $("#eArtsInfo");
    layerOpen(1, "武学信息", content, 900, 400, "保存", "关闭",
        function (layero, index) {
            var obj = JSON.parse(sessionStorage.getItem("ekf"));
            if(obj === null){
                var qkf = sessionStorage.getItem("qkf");
                if(qkf.length >0){
                    obj = JSON.parse(qkf);
                }
            }
            if(obj.length > 0){
                editE(obj);
            }
        }, function (layero, index) {
            var kongFu = [];
            sessionStorage.setItem("ekf", JSON.stringify(kongFu));
            layer.closeAll();
        }, function (index, layero) {
            layer.closeAll();
        });
}

function editE(obj) {
    var fkf = document.getElementById("formKfInfo");
    for(var i=0; i<obj.length; i++){
        var div = document.createElement("div");
        div.id = "eDiv"+i;
        div.className = "layui-form-item enp";
        var div1 = document.createElement("div");
        div1.className = "layui-input-inline";
        div1.style.width = "50px";
        div1.style.marginLeft = "50px";
        var input1 = document.createElement("input");
        input1.className = "layui-input";
        input1.id = "type" + i;
        input1.style.width = "50px";
        input1.style.border = "0";

        input1.setAttribute('type', 'text');
        input1.setAttribute('ReadOnly', 'True');
        input1.value = "111000";
        div1.appendChild(input1);
        div.appendChild(div1);
    }
}

function eWeaponBut() {
    var content = $("#eWeaponInfo");
    layerOpen(1, "兵器造诣", content, 900, 400, "保存", "重置",
        function (index, layero) {
            var obj = getSession("ewp","qwp");
            if (obj !== null) {
                $("#edit_melee_status").val(isNull(obj.melee_status));
                $("#edit_sword_status").val(isNull(obj.sword_status));
                $("#edit_axe_status").val(isNull(obj.axe_status));
                $("#edit_javelin_status").val(isNull(obj.javelin_status));
                $("#edit_hidden_weapons_status").val(isNull(obj.hidden_weapons_status));
                $("#edit_sorcery_status").val(isNull(obj.sorcery_status));
                $("#edit_mp_status").val(isNull(obj.mp_status));
                $("#edit_dodge_skill_status").val(isNull(obj.dodge_skill_status));
            }
        }, function (index, layero) {
            var weapon = {};
            weapon.melee_status = Number($("#edit_melee_status").val());
            weapon.sword_status = Number($("#edit_sword_status").val());
            weapon.axe_status = Number($("#edit_axe_status").val());
            weapon.javelin_status = Number($("#edit_javelin_status").val());
            weapon.hidden_weapons_status = Number($("#edit_hidden_weapons_status").val());
            weapon.sorcery_status = Number($("#edit_sorcery_status").val());
            weapon.mp_status = Number($("#edit_mp_status").val());
            weapon.dodge_skill_status = Number($("#edit_dodge_skill_status").val());
            sessionStorage.setItem("ewp", JSON.stringify(weapon));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 2);
        });
}

function ePotentBut() {
    var content = $("#ePotentInfo");
    layerOpen(1, "人物潜能", content, 900, 400, "保存", "重置",
        function (index, layero) {
            var obj = getSession("ept","qpt");
            if (obj !== null) {
                $("#edit_wisdom1").val(isNull(obj.wisdom1));
                $("#edit_wisdom2").val(isNull(obj.wisdom2));
                $("#edit_wisdom3").val(isNull(obj.wisdom3));
                $("#edit_healthy1").val(isNull(obj.healthy1));
                $("#edit_healthy2").val(isNull(obj.healthy2));
                $("#edit_healthy3").val(isNull(obj.healthy3));
                $("#edit_mellow1").val(isNull(obj.mellow1));
                $("#edit_mellow2").val(isNull(obj.mellow2));
                $("#edit_mellow3").val(isNull(obj.mellow3));
                $("#edit_mellow4").val(isNull(obj.mellow4));
                $("#edit_mellow5").val(isNull(obj.mellow5));
                $("#edit_fineness1").val(isNull(obj.fineness1));
                $("#edit_fineness2").val(isNull(obj.fineness2));
                $("#edit_burst1").val(isNull(obj.burst1));
                $("#edit_burst2").val(isNull(obj.burst2));
                $("#edit_sharp1").val(isNull(obj.sharp1));
                $("#edit_sharp2").val(isNull(obj.sharp2));
                $("#edit_tenacity1").val(isNull(obj.tenacity1));
                $("#edit_tenacity2").val(isNull(obj.tenacity2));
                $("#edit_tenacity3").val(isNull(obj.tenacity3));
            }
        }, function (index, layero) {
            var potent = {};
            potent.wisdom1 = Number($("#edit_wisdom1").val());
            potent.wisdom2 = Number($("#edit_wisdom2").val());
            potent.wisdom3 = Number($("#edit_wisdom3").val());
            potent.healthy1 = Number($("#edit_healthy1").val());
            potent.healthy2 = Number($("#edit_healthy2").val());
            potent.healthy3 = Number($("#edit_healthy3").val());
            potent.mellow1 = Number($("#edit_mellow1").val());
            potent.mellow2 = Number($("#edit_mellow2").val());
            potent.mellow3 = Number($("#edit_mellow3").val());
            potent.mellow4 = Number($("#edit_mellow4").val());
            potent.mellow5 = Number($("#edit_mellow5").val());
            potent.fineness1 = Number($("#edit_fineness1").val());
            potent.fineness2 = Number($("#edit_fineness2").val());
            potent.burst1 = Number($("#edit_burst1").val());
            potent.burst2 = Number($("#edit_burst2").val());
            potent.sharp1 = Number($("#edit_sharp1").val());
            potent.sharp2 = Number($("#edit_sharp2").val());
            potent.tenacity1 = Number($("#edit_tenacity1").val());
            potent.tenacity2 = Number($("#edit_tenacity2").val());
            potent.tenacity3 = Number($("#edit_tenacity3").val());
            sessionStorage.setItem("ept", JSON.stringify(potent));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(weaponID, 2);
        });
}

function editReset(layero) {
    cleanVal(layero, "edit_nickname",'');
    cleanVal(layero, "edit_level",'');
    cleanVal(layero, "edit_attitude",'');
    cleanVal(layero, "edit_characters",'');
    cleanVal(layero, "edit_popularity",'');
    cleanVal(layero, "edit_coin",'');
    cleanVal(layero, "edit_gold",'');
    cleanVal(layero, "edit_experience",'');
    cleanVal(layero, "edit_school_contribution",'');
    cleanVal(layero, "edit_experience",'');
    cleanVal(layero, "edit_experience",'');
    cleanVal(layero, "edit_experience",'');
}

function verIfy(params) {
    if (params.nickname === null || params.nickname === "") {
        layer.msg("人物名称不能为空", {icon: 2});
        return false;
    }
    if (params.is_npc === null || params.is_npc === "") {
        layer.msg("人物类型不能为空", {icon: 2});
        return false;
    }
    if (params.user_id === null || params.user_id === "") {
        layer.msg("所属账号不能为空", {icon: 2});
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
    return true;
}

function getSession(a,b) {
    var obj = JSON.parse(sessionStorage.getItem(a));
    if(obj === null){
        var q = sessionStorage.getItem(b);
        if(q !== "undefined"){
            obj = JSON.parse(q);
        }
    }
    return obj;
}

virtueID = "physique,forces,muscles,mp,sensitivity,willpower,knowledge,lucky";
kongFuID = "kongFu1,experience1,kongFu2,experience2,kongFu3,experience3,kongFu4,experience4,kongFu";
weaponID = "melee_status,sword_status,axe_status,javelin_status,hidden_weapons_status,sorcery_status,mp_status,dodge_skill_status";
potentID = "wisdom1,wisdom2,wisdom3,healthy1,healthy2,healthy3,mellow1,mellow2,mellow3,mellow4,mellow5,fineness1,fineness2,burst1,burst2,sharp1,sharp2,tenacity1,tenacity2,tenacity3";

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

function calAdd(virtue) {
    var sum = Number(virtue.physique) + Number(virtue.forces);
    sum += Number(virtue.muscles);
    sum += Number(virtue.mp);
    sum += Number(virtue.sensitivity);
    sum += Number(virtue.willpower);
    sum += Number(virtue.knowledge);
    sum += Number(virtue.lucky);
    return sum;
}

function calSub(a,b) {
    return Number(a) - Number(b);
}

function addTypeBut() {
    var type = $("#add_kfTypes").find("option:selected").val();
    var downs = {};
    downs.type = type;
    $("#add_kongFu").empty();
    downBox(downs, kongFuBox, "add_kongFu", "", lSelection);
}

function addKongFu() {
    var kongFu = $("#add_kongFu").find("option:selected");
    var kText = kongFu.text();
    if (kText === "请选择" || kText === "") {
        layer.msg("请选择武学选项");
        return;
    }
    var typeV = $("#add_kfTypes").find("option:selected").text();
    createE(typeV,kText,kongFu.val());
}

var i = 1;
function createE(typeV,kText,kVal) {
    var fkf = document.getElementById("formKongFu");
    var div = document.createElement("div");
    div.id = "pDiv" + i;
    div.className = "layui-form-item inp";
    var div1 = document.createElement("div");
    div1.className = "layui-input-inline";
    div1.style.width = "50px";
    div1.style.marginLeft = "50px";
    var input1 = document.createElement("input");
    input1.className = "layui-input";
    input1.id = "type" + i;
    input1.style.width = "50px";
    input1.style.border = "0";
    input1.setAttribute('type', 'text');
    input1.setAttribute('ReadOnly', 'True');
    input1.value = typeV;
    div1.appendChild(input1);
    div.appendChild(div1);
    var div2 = document.createElement("div");
    div2.className = "layui-input-inline";
    var input2 = document.createElement("input");
    input2.className = "layui-input";
    input2.id = "name" + i;
    input2.setAttribute('type', 'text');
    input2.setAttribute('ReadOnly', 'True');
    input2.value = kText;
    div2.appendChild(input2);
    div.appendChild(div2);
    var div3 = document.createElement("div");
    div3.className = "layui-input-inline";
    var input3 = document.createElement("input");
    input3.className = "layui-input";
    input3.id = "exp" + i;
    input3.setAttribute('type', 'text');
    input3.setAttribute("placeholder", "输入经验值");
    input3.setAttribute("oninput", "value=value.replace(/[^\\d]/g,'')");
    div3.appendChild(input3);
    div.appendChild(div3);
    var div4 = document.createElement("div");
    div4.className = "layui-input-inline";
    div4.style.width = "120px";
    var input4 = document.createElement("input");
    input4.className = "layui-btn layui-btn-primary buLeng";
    input4.setAttribute("type", "button");
    input4.value = "未使用";
    input4.id = 'use' + i;
    input4.onclick = selectE;
    div4.appendChild(input4);
    div.appendChild(div4);
    var div5 = document.createElement("div");
    div5.className = "layui-input-inline";
    var button1 = document.createElement("button");
    button1.className = "layui-btn layui-btn-primary buLeng";
    button1.setAttribute("type", "button");
    button1.innerHTML = "删除";
    button1.id = '' + i;
    button1.onclick = removeE;
    div5.appendChild(button1);
    div.appendChild(div5);
    var div6 = document.createElement("div");
    div6.className = "layui-input-inline";
    div6.style.width = "50px";
    div6.style.display = "none";
    var input6 = document.createElement("input");
    input6.className = "layui-btn layui-btn-primary buLeng";
    input6.id = "id" + i;
    input6.value = kVal;
    div6.appendChild(input6);
    div.appendChild(div6);
    fkf.appendChild(div);
    i++;
}

function removeE() {
    var id = this.id;
    document.getElementById("formKongFu").removeChild(document.getElementById("pDiv" + id));
}

function selectE() {
    var id = this.id;
    var a = document.getElementById(id).value;
    if (a === "未使用") {
        document.getElementById(id).value = "使用";
        document.getElementById(id).className = "layui-btn layui-btn-danger buLeng";
    } else {
        document.getElementById(id).value = "未使用";
        document.getElementById(id).className = "layui-btn layui-btn-primary buLeng";
    }
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

function editTypeBut() {
    var type = $("#edit_kfTypes").find("option:selected").val();
    var downs = {};
    downs.type = type;
    $("#edit_kongFu").empty();
    downBox(downs, kongFuBox, "edit_kongFu", "", lSelection);
}

function editKongFu() {
    var kongFu = $("#edit_kongFu").find("option:selected");
    var kText = kongFu.text();
    if (kText === "请选择" || kText === "") {
        layer.msg("请选择武学选项");
        return;
    }
    var typeV = $("#edit_kfTypes").find("option:selected").text();
    alert(typeV)
}

function getSessionValue(a,b) {
    var obj = JSON.parse(sessionStorage.getItem(a));
    if (obj === null) {
        obj = JSON.parse(sessionStorage.getItem(b));
        if(b === "qkf"){
            $.each(obj,function (i,n) {
                delete obj[i].kfName;
                delete obj[i].kfType;
            })
        }
    }
    return obj;
}

function removeSession() {
    sessionStorage.removeItem("evt");
    sessionStorage.removeItem("ekf");
    sessionStorage.removeItem("ewp");
    sessionStorage.removeItem("ept");
    sessionStorage.removeItem("qvt");
    sessionStorage.removeItem("qkf");
    sessionStorage.removeItem("qwp");
    sessionStorage.removeItem("qpt");
}