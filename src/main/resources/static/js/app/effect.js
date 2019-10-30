charset="utf-8";
manages="effect";
$(function(){
    pagingQuery()
});

function pagingQuery() {
    $("#dataInfo").empty();
    var params = {};
    params.name = $("#query_name").val();
    params.target = $("#query_target").val();
    var cole = [
        {type: 'checkbox', fixed: 'felt'},
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt', width: 60},
        {field: 'name', title: '效果名称'},
        {field: 'targetValue', title: '执行目标'},
        {field: 'Aggressivity', title: '攻击力'},
        {field: 'Defense', title: '防御力'},
        {field: 'burst', title: '暴击力'},
        {field: 'treatment', title: 'HP影响', sort: true},
        {field: 'injury', title: '外伤'},
        {field: 'internal_injury', title: '内伤'},
        {field: 'poisoning', title: '毒伤'},
        {fixed: 'right', title: '操作', width: 240, align: 'center', toolbar: '#operational'}
    ];
    pQue(params, manages, "效果管理", cole, add, edit, del, see, pagingQuery);
}

function cleanUp() {
    $("#query_name").val('');
    $("#query_target").val('');
    layui.form.render("select");
    pagingQuery();
}

function add() {
    var content = [splictUrl + '/system/effectAdd'];
    layerOpen(2, "新增", content, 1100, 600, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.name = getVal(layero, "add_name");
            params.target = getVal(layero, "add_target");
            params.injury = Number(getVal(layero, "add_injury"));
            params.internal_injury = Number(getVal(layero, "add_internal_injury"));
            params.poisoning = Number(getVal(layero, "add_poisoning"));
            params.preserve = Number(getVal(layero, "add_preserve"));
            params.PowerWall = Number(getVal(layero, "add_PowerWall"));
            params.Aggressivity = Number(getVal(layero, "add_Aggressivity"));
            params.Defense = Number(getVal(layero, "add_Defense"));
            params.burst = Number(getVal(layero, "add_burst"));
            params.stun = Number(getVal(layero, "add_stun"));
            params.dodge = Number(getVal(layero, "add_dodge"));
            params.hit_rate = Number(getVal(layero, "add_hit_rate"));
            params.crit_rate = Number(getVal(layero, "add_crit_rate"));
            params.Counterattack = Number(getVal(layero, "add_Counterattack"));
            params.treatment = Number(getVal(layero, "add_treatment"));
            params.suck_HP = Number(getVal(layero, "add_suck_HP"));
            params.info = getVal(layero, "add_info");
            params.Acupoint_su = Number(getVal(layero,"add_Acupoint_su"));
            params.Acupoint_zhong = Number(getVal(layero,"add_Acupoint_zhong"));
            params.Acupoint_qiao = Number(getVal(layero,"add_Acupoint_qiao"));
            params.Acupoint_shan = Number(getVal(layero,"add_Acupoint_shan"));
            params.Acupoint_tiao = Number(getVal(layero,"add_Acupoint_tiao"));
            params.Acupoint_fang= Number(getVal(layero,"add_Acupoint_fang"));
            params.getweapon = Number(getVal(layero,"add_getweapon"));
            params.Basic_attribute_physique = Number(getVal(layero,"add_Basic_attribute_physique"));
            params.Basic_attribute_forces = Number(getVal(layero,"add_Basic_attribute_forces"));
            params.Basic_attribute_muscles = Number(getVal(layero,"add_Basic_attribute_muscles"));
            params.Basic_attribute_mp = Number(getVal(layero,"add_Basic_attribute_mp"));
            params.Basic_attribute_sensitivity = Number(getVal(layero,"add_Basic_attribute_sensitivity"));
            params.Basic_attribute_willpower = Number(getVal(layero,"add_Basic_attribute_willpower"));
            params.Basic_attribute_knowledge = Number(getVal(layero,"add_Basic_attribute_knowledge"));
            params.Basic_attribute_lucky= Number(getVal(layero,"add_Basic_attribute_lucky"));
            params.penetrate_defense = Number(getVal(layero,"add_penetrate_defense"));
            params.penetrate_powerWall = Number(getVal(layero,"add_penetrate_powerWall"));
            params.Aggressivity_powerWall= Number(getVal(layero,"add_Aggressivity_powerWall"));
            if (verIfy(params) === false) {
                return;
            }
            var hp = JSON.parse(sessionStorage.getItem("ahp"));
            if (hp != null) {
                params.mp_random = hp.mp_random;
                params.mp_yellow = hp.mp_yellow;
                params.mp_gold = hp.mp_gold;
                params.mp_green = hp.mp_green;
                params.mp_blue = hp.mp_blue;
                params.mp_purple = hp.mp_purple;
            }
            var suck = JSON.parse(sessionStorage.getItem("amp"));
            if (suck != null) {
                params.suck_mp_random = suck.suck_mp_random;
                params.suck_mp_yellow = suck.suck_mp_yellow;
                params.suck_mp_gold = suck.suck_mp_gold;
                params.suck_mp_green = suck.suck_mp_green;
                params.suck_mp_blue = suck.suck_mp_blue;
                params.suck_mp_purple = suck.suck_mp_purple;
            }
            aaUp(params, manages, insert, "新增", pagingQuery);
            sessionStorage.removeItem("ahp");
            sessionStorage.removeItem("amp");
        }, function (index, layero) {
            addReset(layero);
        });
}

function aHpInfo() {
    var content = $("#aHpInfo");
    layerOpen(1, "色球影响", content, 700, 400, "保存", "重置", "",
        function (index, layero) {
            var hp = {};
            hp.mp_random = String($("#add_mp_random").val());
            hp.mp_yellow = String($("#add_mp_yellow").val());
            hp.mp_gold = String($("#add_mp_gold").val());
            hp.mp_green = String($("#add_mp_green").val());
            hp.mp_blue = String($("#add_mp_blue").val());
            hp.mp_purple = String($("#add_mp_purple").val());
            sessionStorage.setItem("ahp", JSON.stringify(hp));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(hpID,1);
        });
}

function aSuckInfo() {
    var content = $("#aSuckInfo");
    layerOpen(1, "吸血率色球影响", content, 700, 400, "保存", "重置", "",
        function (index, layero) {
            var suck = {};
            suck.suck_mp_random = String($("#add_suck_mp_random").val());
            suck.suck_mp_yellow = String($("#add_suck_mp_yellow").val());
            suck.suck_mp_gold = String($("#add_suck_mp_gold").val());
            suck.suck_mp_green = String($("#add_suck_mp_green").val());
            suck.suck_mp_blue = String($("#add_suck_mp_blue").val());
            suck.suck_mp_purple = String($("#add_suck_mp_purple").val());
            sessionStorage.setItem("amp", JSON.stringify(suck));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(mpID, 1);
        });
}

function addReset(layero) {
    cleanVal(layero, "add_name", '');
    cleanVal(layero, "add_injury", '');
    cleanVal(layero, "add_internal_injury", '');
    cleanVal(layero, "add_poisoning", '');
    cleanVal(layero, "add_preserve", '');
    cleanVal(layero, "add_PowerWall", '');
    cleanVal(layero, "add_Aggressivity", '');
    cleanVal(layero, "add_Defense", '');
    cleanVal(layero, "add_burst", '');
    cleanVal(layero, "add_stun", '');
    cleanVal(layero, "add_dodge", '');
    cleanVal(layero, "add_suck_HP", '');
    cleanVal(layero, "add_hit_rate", '');
    cleanVal(layero, "add_crit_rate", '');
    cleanVal(layero, "add_hit_rate", '');
    cleanVal(layero, "add_Counterattack", '');
    cleanVal(layero, "add_treatment", '');
    cleanVal(layero, "add_suck_HP", '');
    cleanVal(layero, "add_info", '');
    cleanVal(layero, "add_Acupoint_su", '');
    cleanVal(layero, "add_Acupoint_zhong", '');
    cleanVal(layero, "add_Acupoint_qiao", '');
    cleanVal(layero, "add_Acupoint_shan", '');
    cleanVal(layero, "add_Acupoint_tiao", '');
    cleanVal(layero, "add_Acupoint_fang", '');
    cleanVal(layero, "add_getweapon", '');
    cleanVal(layero, "add_Basic_attribute_physique", '');
    cleanVal(layero, "add_Basic_attribute_forces", '');
    cleanVal(layero, "add_Basic_attribute_muscles", '');
    cleanVal(layero, "add_Basic_attribute_mp", '');
    cleanVal(layero, "add_Basic_attribute_sensitivity", '');
    cleanVal(layero, "add_Basic_attribute_willpower", '');
    cleanVal(layero, "add_Basic_attribute_knowledge", '');
    cleanVal(layero, "add_Basic_attribute_lucky", '');
    cleanVal(layero, "add_penetrate_defense", '');
    cleanVal(layero, "add_penetrate_powerWall", '');
    cleanVal(layero, "add_Aggressivity_powerWall", '');
}

function see(data) {
    var content = $("#seeInfo");
    layerOpen(1, "查看详情", content, 1000, 500, "明白了", "关闭",
        function (index, layero) {
            $("#see_name").val(isNull(data.name));
            $("#see_target").val(isNull(data.target));
            $("#see_injury").val(isNull(data.injury));
            $("#see_internal_injury").val(isNull(data.internal_injury));
            $("#see_poisoning").val(isNull(data.poisoning));
            $("#see_preserve").val(isNull(data.preserve));
            $("#see_PowerWall").val(isNull(data.PowerWall));
            $("#see_Aggressivity").val(isNull(data.Aggressivity));
            $("#see_Defense").val(isNull(data.Defense));
            $("#see_burst").val(isNull(data.burst));
            $("#see_stun").val(isNull(data.stun));
            $("#see_dodge").val(isNull(data.dodge));
            $("#see_hit_rate").val(isNull(data.hit_rate));
            $("#see_crit_rate").val(isNull(data.crit_rate));
            $("#see_Counterattack").val(isNull(data.Counterattack));
            $("#see_treatment").val(isNull(data.treatment));
            $("#see_mp_random").val(isNull(data.mp_random));
            $("#see_mp_yellow").val(isNull(data.mp_yellow));
            $("#see_mp_gold").val(isNull(data.mp_gold));
            $("#see_mp_green").val(isNull(data.mp_green));
            $("#see_mp_blue").val(isNull(data.mp_blue));
            $("#see_mp_purple").val(isNull(data.mp_purple));
            $("#see_suck_HP").val(isNull(data.suck_HP));
            $("#see_suck_mp_random").val(isNull(data.suck_mp_random));
            $("#see_suck_mp_yellow").val(isNull(data.suck_mp_yellow));
            $("#see_suck_mp_gold").val(isNull(data.suck_mp_gold));
            $("#see_suck_mp_green").val(isNull(data.suck_mp_green));
            $("#see_suck_mp_blue").val(isNull(data.suck_mp_blue));
            $("#see_suck_mp_purple").val(isNull(data.suck_mp_purple));
            $("#see_info").val(isNull(data.info));
            $("#see_Acupoint_su").val(isNull(data.Acupoint_su));
            $("#see_Acupoint_zhong").val(isNull(data.Acupoint_zhong));
            $("#see_Acupoint_qiao").val(isNull(data.Acupoint_qiao));
            $("#see_Acupoint_shan").val(isNull(data.Acupoint_shan));
            $("#see_Acupoint_tiao").val(isNull(data.Acupoint_tiao));
            $("#see_Acupoint_fang").val(isNull(data.Acupoint_fang));
            $("#see_getweapon").val(isNull(data.getweapon));
            $("#see_Basic_attribute_physique").val(isNull(data.Basic_attribute_physique));
            $("#see_Basic_attribute_forces").val(isNull(data.Basic_attribute_forces));
            $("#see_Basic_attribute_muscles").val(isNull(data.Basic_attribute_muscles));
            $("#see_Basic_attribute_mp").val(isNull(data.Basic_attribute_mp));
            $("#see_Basic_attribute_sensitivity").val(isNull(data.Basic_attribute_sensitivity));
            $("#see_Basic_attribute_willpower").val(isNull(data.Basic_attribute_willpower));
            $("#see_Basic_attribute_knowledge").val(isNull(data.Basic_attribute_knowledge));
            $("#see_Basic_attribute_lucky").val(isNull(data.Basic_attribute_lucky));
            $("#see_penetrate_defense").val(isNull(data.penetrate_defense));
            $("#see_penetrate_powerWall").val(isNull(data.penetrate_powerWall));
            $("#see_Aggressivity_powerWall").val(isNull(data.Aggressivity_powerWall));
        }, function (index, layero) {
            layer.closeAll();
        }, function (index, layero) {
            layer.closeAll();
        });
}

function edit(data) {
    var params = {name: data.name};
    postRequest(params, manages, sQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var content = [splictUrl + '/system/effectUpdate'];
                layerOpen(2, "编辑", content, 1100, 600, "立即提交", "重置",
                    function (index, layero) {
                        editVal(layero, "edit_name", obj.name);
                        editVal(layero, "edit_target", obj.target);
                        editVal(layero, "edit_injury", obj.injury);
                        editVal(layero, "edit_internal_injury", obj.internal_injury);
                        editVal(layero, "edit_poisoning", obj.poisoning);
                        editVal(layero, "edit_preserve", obj.preserve);
                        editVal(layero, "edit_PowerWall", obj.PowerWall);
                        editVal(layero, "edit_Aggressivity", obj.Aggressivity);
                        editVal(layero, "edit_Defense", obj.Defense);
                        editVal(layero, "edit_burst", obj.burst);
                        editVal(layero, "edit_stun", obj.stun);
                        editVal(layero, "edit_dodge", obj.dodge);
                        editVal(layero, "edit_hit_rate", obj.hit_rate);
                        editVal(layero, "edit_crit_rate", obj.crit_rate);
                        editVal(layero, "edit_Counterattack", obj.Counterattack);
                        editVal(layero, "edit_treatment", obj.treatment);
                        editVal(layero, "edit_suck_HP", obj.suck_HP);
                        editVal(layero, "edit_info", obj.info);
                        editVal(layero, "edit_Acupoint_su", obj.Acupoint_su);
                        editVal(layero, "edit_Acupoint_zhong", obj.Acupoint_zhong);
                        editVal(layero, "edit_Acupoint_qiao", obj.Acupoint_qiao);
                        editVal(layero, "edit_Acupoint_shan", obj.Acupoint_shan);
                        editVal(layero, "edit_Acupoint_tiao", obj.Acupoint_tiao);
                        editVal(layero, "edit_Acupoint_fang", obj.Acupoint_fang);
                        editVal(layero, "edit_getweapon", obj.getweapon);
                        editVal(layero, "edit_Basic_attribute_physique", obj.Basic_attribute_physique);
                        editVal(layero, "edit_Basic_attribute_forces", obj.Basic_attribute_forces);
                        editVal(layero, "edit_Basic_attribute_muscles", obj.Basic_attribute_muscles);
                        editVal(layero, "edit_Basic_attribute_mp", obj.Basic_attribute_mp);
                        editVal(layero, "edit_Basic_attribute_sensitivity", obj.Basic_attribute_sensitivity);
                        editVal(layero, "edit_Basic_attribute_willpower", obj.Basic_attribute_willpower);
                        editVal(layero, "edit_Basic_attribute_knowledge", obj.Basic_attribute_knowledge);
                        editVal(layero, "edit_Basic_attribute_lucky", obj.Basic_attribute_lucky);
                        editVal(layero, "edit_penetrate_defense", obj.penetrate_defense);
                        editVal(layero, "edit_penetrate_powerWall", obj.penetrate_powerWall);
                        editVal(layero, "edit_Aggressivity_powerWall", obj.Aggressivity_powerWall);
                        sessionStorage.setItem("eff", JSON.stringify(obj));
                    }, function (index, layero) {
                        var params = {};
                        params.effect_id = obj.effect_id;
                        params.name = getVal(layero, "edit_name");
                        params.target = getVal(layero, "edit_target");
                        params.injury = Number(getVal(layero, "edit_injury"));
                        params.internal_injury = Number(getVal(layero, "edit_internal_injury"));
                        params.poisoning = Number(getVal(layero, "edit_poisoning"));
                        params.preserve = Number(getVal(layero, "edit_preserve"));
                        params.PowerWall = Number(getVal(layero, "edit_PowerWall"));
                        params.Aggressivity = Number(getVal(layero, "edit_Aggressivity"));
                        params.Defense = Number(getVal(layero, "edit_Defense"));
                        params.burst = Number(getVal(layero, "edit_burst"));
                        params.stun = Number(getVal(layero, "edit_stun"));
                        params.dodge = Number(getVal(layero, "edit_dodge"));
                        params.hit_rate = Number(getVal(layero, "edit_hit_rate"));
                        params.crit_rate = Number(getVal(layero, "edit_crit_rate"));
                        params.Counterattack = Number(getVal(layero, "edit_Counterattack"));
                        params.treatment = Number(getVal(layero, "edit_treatment"));
                        params.suck_HP = Number(getVal(layero, "edit_suck_HP"));
                        params.info = getVal(layero, "edit_info");
                        params.Acupoint_su = Number(getVal(layero,"edit_Acupoint_su"));
                        params.Acupoint_zhong = Number(getVal(layero,"edit_Acupoint_zhong"));
                        params.Acupoint_qiao = Number(getVal(layero,"edit_Acupoint_qiao"));
                        params.Acupoint_shan = Number(getVal(layero,"edit_Acupoint_shan"));
                        params.Acupoint_tiao = Number(getVal(layero,"edit_Acupoint_tiao"));
                        params.Acupoint_fang= Number(getVal(layero,"edit_Acupoint_fang"));
                        params.getweapon = Number(getVal(layero,"edit_getweapon"));
                        params.Basic_attribute_physique = Number(getVal(layero,"edit_Basic_attribute_physique"));
                        params.Basic_attribute_forces = Number(getVal(layero,"edit_Basic_attribute_forces"));
                        params.Basic_attribute_muscles = Number(getVal(layero,"edit_Basic_attribute_muscles"));
                        params.Basic_attribute_mp = Number(getVal(layero,"edit_Basic_attribute_mp"));
                        params.Basic_attribute_sensitivity = Number(getVal(layero,"edit_Basic_attribute_sensitivity"));
                        params.Basic_attribute_willpower = Number(getVal(layero,"edit_Basic_attribute_willpower"));
                        params.Basic_attribute_knowledge = Number(getVal(layero,"edit_Basic_attribute_knowledge"));
                        params.Basic_attribute_lucky= Number(getVal(layero,"edit_Basic_attribute_lucky"));
                        params.penetrate_defense = Number(getVal(layero,"edit_penetrate_defense"));
                        params.penetrate_powerWall = Number(getVal(layero,"edit_penetrate_powerWall"));
                        params.Aggressivity_powerWall= Number(getVal(layero,"edit_Aggressivity_powerWall"));
                        if (verIfy(params) === false) {
                            return;
                        }
                        var hp = getSessionValue("ehp", "eff");
                        params.mp_random = hp.mp_random;
                        params.mp_yellow = hp.mp_yellow;
                        params.mp_gold = hp.mp_gold;
                        params.mp_green = hp.mp_green;
                        params.mp_blue = hp.mp_blue;
                        params.mp_purple = hp.mp_purple;
                        var suck = getSessionValue("emp", "eff");
                        params.suck_mp_random = suck.suck_mp_random;
                        params.suck_mp_yellow = suck.suck_mp_yellow;
                        params.suck_mp_gold = suck.suck_mp_gold;
                        params.suck_mp_green = suck.suck_mp_green;
                        params.suck_mp_blue = suck.suck_mp_blue;
                        params.suck_mp_purple = suck.suck_mp_purple;
                        alert(JSON.stringify(params));
                        aaUp(params, manages, update, "修改", pagingQuery);
                        sessionStorage.removeItem("ehp");
                        sessionStorage.removeItem("emp");
                        sessionStorage.setItem("eff", JSON.stringify(params));
                    }, function (index, layero) {
                        editReset(layero);
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}

function eHpInfo() {
    var content = $("#eHpInfo");
    layerOpen(1, "HP色球影响", content, 700, 400, "保存", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("ehp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            $("#edit_mp_random").val(obj.mp_random);
            $("#edit_mp_yellow").val(obj.mp_yellow);
            $("#edit_mp_gold").val(obj.mp_gold);
            $("#edit_mp_green").val(obj.mp_green);
            $("#edit_mp_blue").val(obj.mp_blue);
            $("#edit_mp_purple").val(obj.mp_purple);
        }, function (index, layero) {
            var hp = {};
            hp.mp_random = String($("#edit_mp_random").val());
            hp.mp_yellow = String($("#edit_mp_yellow").val());
            hp.mp_gold = String($("#edit_mp_gold").val());
            hp.mp_green = String($("#edit_mp_green").val());
            hp.mp_blue = String($("#edit_mp_blue").val());
            hp.mp_purple = String($("#edit_mp_purple").val());
            sessionStorage.setItem("ehp", JSON.stringify(hp));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(hpID, 2);
        }
    );
}

function eSuckInfo() {
    var content = $("#eSuckInfo");
    layerOpen(1, "吸血色球影响", content, 700, 400, "保存", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("emp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            $("#edit_suck_mp_random").val(obj.suck_mp_random);
            $("#edit_suck_mp_yellow").val(obj.suck_mp_yellow);
            $("#edit_suck_mp_gold").val(obj.suck_mp_gold);
            $("#edit_suck_mp_green").val(obj.suck_mp_green);
            $("#edit_suck_mp_blue").val(obj.suck_mp_blue);
            $("#edit_suck_mp_purple").val(obj.suck_mp_purple);
        }, function (index, layero) {
            var suck = {};
            suck.suck_mp_random = String($("#edit_suck_mp_random").val());
            suck.suck_mp_yellow = String($("#edit_suck_mp_yellow").val());
            suck.suck_mp_gold = String($("#edit_suck_mp_gold").val());
            suck.suck_mp_green = String($("#edit_suck_mp_green").val());
            suck.suck_mp_blue = String($("#edit_suck_mp_blue").val());
            suck.suck_mp_purple = String($("#edit_suck_mp_purple").val());
            sessionStorage.setItem("emp", JSON.stringify(suck));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(mpID, 2);
        }
    );
}

function editReset(layero) {
    cleanVal(layero, "edit_name", '');
    cleanVal(layero, "edit_target", '');
    cleanVal(layero, "edit_injury", '');
    cleanVal(layero, "edit_internal_injury", '');
    cleanVal(layero, "edit_poisoning", '');
    cleanVal(layero, "edit_preserve", '');
    cleanVal(layero, "edit_PowerWall", '');
    cleanVal(layero, "edit_Aggressivity", '');
    cleanVal(layero, "edit_Defense", '');
    cleanVal(layero, "edit_burst", '');
    cleanVal(layero, "edit_stun", '');
    cleanVal(layero, "edit_dodge", '');
    cleanVal(layero, "edit_hit_rate", '');
    cleanVal(layero, "edit_crit_rate", '');
    cleanVal(layero, "edit_Counterattack", '');
    cleanVal(layero, "edit_treatment", '');
    cleanVal(layero, "edit_suck_HP", '');
    cleanVal(layero, "edit_info", '');
    cleanVal(layero, "edit_Acupoint_su", '');
    cleanVal(layero, "edit_Acupoint_zhong", '');
    cleanVal(layero, "edit_Acupoint_qiao", '');
    cleanVal(layero, "edit_Acupoint_shan", '');
    cleanVal(layero, "edit_Acupoint_tiao", '');
    cleanVal(layero, "edit_Acupoint_fang", '');
    cleanVal(layero, "edit_getweapon", '');
    cleanVal(layero, "edit_Basic_attribute_physique", '');
    cleanVal(layero, "edit_Basic_attribute_forces", '');
    cleanVal(layero, "edit_Basic_attribute_muscles", '');
    cleanVal(layero, "edit_Basic_attribute_mp", '');
    cleanVal(layero, "edit_Basic_attribute_sensitivity", '');
    cleanVal(layero, "edit_Basic_attribute_willpower", '');
    cleanVal(layero, "edit_Basic_attribute_knowledge", '');
    cleanVal(layero, "edit_Basic_attribute_lucky", '');
    cleanVal(layero, "edit_penetrate_defense", '');
    cleanVal(layero, "edit_penetrate_powerWall", '');
    cleanVal(layero, "edit_Aggressivity_powerWall", '');
}

function verIfy(params) {
    if (params.name === null || params.name === "") {
        layer.msg("效果名称不能为空", {icon: 2});
        return false;
    }
    if (params.target === null || params.target === "") {
        layer.msg("效果执行目标不能为空", {icon: 2});
        return false;
    }
    return true;
}

function getSessionValue(a,b) {
    var obj = JSON.parse(sessionStorage.getItem(a));
    if (obj === null) {
        obj = JSON.parse(sessionStorage.getItem(b));
    }
    return obj;
}

function clearValue(ids) {
    var obj = ids.split(",");
    for (var i = 0; i < obj.length; i++) {
        for (var j = 1; j < 4; j++) {
            var id = obj[i] + j;
            document.getElementById(id).value = '';
        }
    }
}

hpID = "mp_random,mp_yellow,mp_gold,mp_green,mp_blue,mp_purple";
mpID = "suck_mp_random,suck_mp_yellow,suck_mp_gold,suck_mp_green,suck_mp_blue,suck_mp_purple";


function assignmentValue(obj,id) {
    for(var i=0; i<4; i++){
        var a = id+1+i;
        alert(a);
        document.getElementById(a).value = Number(obj[i]);
    }
}