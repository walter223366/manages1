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
        {type: 'numbers', title: '序号', align: 'center', fixed: 'felt'},
        {field: 'name', title: '效果名称', sort: true, width: 120},
        {field: 'targetValue', title: '执行目标'},
        {field: 'Aggressivity', title: '攻击力'},
        {field: 'Defense', title: '防御力'},
        {field: 'burst', title: '暴击力'},
        {field: 'treatment', title: 'HP影响'},
        {field: 'injury', title: '外伤'},
        {field: 'internal_injury', title: '内伤'},
        {field: 'poisoning', title: '毒伤', sort: true},
        {fixed: 'right', title: '操作', width: 300, align: 'center', toolbar: '#operational'}
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
            if (verIfy(params) === false) {
                return;
            }
            var hp = JSON.parse(sessionStorage.getItem("ahp"));
            if (hp != null) {
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
    layerOpen(1, "HP色球影响", content, 700, 400, "保存", "重置", "",
        function (index, layero) {
            var hp = {};
            hp.mp_yellow = $("#add_mp_yellow").val();
            hp.mp_gold = $("#add_mp_gold").val();
            hp.mp_green = $("#add_mp_green").val();
            hp.mp_blue = $("#add_mp_blue").val();
            hp.mp_purple = $("#add_mp_purple").val();
            sessionStorage.setItem("ahp", JSON.stringify(hp));
            layer.closeAll();
        }, function (index, layero) {
            clearVal(hpID,1);
        });
}

function aSuckInfo() {
    var content = $("#aSuckInfo");
    layerOpen(1, "吸血色球影响", content, 700, 400, "保存", "重置", "",
        function (index, layero) {
            var suck = {};
            suck.suck_mp_random = $("#add_suck_mp_random").val();
            suck.suck_mp_yellow = $("#add_suck_mp_yellow").val();
            suck.suck_mp_gold = $("#add_suck_mp_gold").val();
            suck.suck_mp_green = $("#add_suck_mp_green").val();
            suck.suck_mp_blue = $("#add_suck_mp_blue").val();
            suck.suck_mp_purple = $("#add_suck_mp_purple").val();
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
                        if (verIfy(params) === false) {
                            return;
                        }
                        var hp = getSessionValue("ehp", "eff");
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
            $("#edit_mp_yellow").val(obj.mp_yellow);
            $("#edit_mp_gold").val(obj.mp_gold);
            $("#edit_mp_green").val(obj.mp_green);
            $("#edit_mp_blue").val(obj.mp_blue);
            $("#edit_mp_purple").val(obj.mp_purple);
        }, function (index, layero) {
            var hp = {};
            hp.mp_yellow = $("#edit_mp_yellow").val();
            hp.mp_gold = $("#edit_mp_gold").val();
            hp.mp_green = $("#edit_mp_green").val();
            hp.mp_blue = $("#edit_mp_blue").val();
            hp.mp_purple = $("#edit_mp_purple").val();
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
            suck.suck_mp_random = $("#edit_suck_mp_random").val();
            suck.suck_mp_yellow = $("#edit_suck_mp_yellow").val();
            suck.suck_mp_gold = $("#edit_suck_mp_gold").val();
            suck.suck_mp_green = $("#edit_suck_mp_green").val();
            suck.suck_mp_blue = $("#edit_suck_mp_blue").val();
            suck.suck_mp_purple = $("#edit_suck_mp_purple").val();
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

hpID = "mp_yellow,mp_gold,mp_green,mp_blue,mp_purple";
mpID = "suck_mp_random,suck_mp_yellow,suck_mp_gold,suck_mp_green,suck_mp_blue,suck_mp_purple";


function assignmentValue(obj,id) {
    for(var i=0; i<4; i++){
        var a = id+1+i;
        alert(a);
        document.getElementById(a).value = Number(obj[i]);
    }
}