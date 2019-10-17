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
        {field: 'target', title: '执行目标'},
        {field: 'Aggressivity', title: '攻击力'},
        {field: 'Defense', title: '防御力'},
        {field: 'burst', title: '暴击力'},
        {field: 'hp', title: 'HP影响', sort: true, width: 120},
        {field: 'injury', title: '外伤'},
        {field: 'internal_injury', title: '内伤'},
        {field: 'poisoning', title: '毒伤'},
        {field: 'suck_HP', title: '吸血', sort: true},
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
    var content = [splictUrl + '/system/effectAdd', 'no'];
    layerOpen(2, "新增", content, 1200, 650, "立即提交", "重置", "",
        function (index, layero) {
            var params = {};
            params.name = getVal(layero, "add_name");
            params.target = getVal(layero, "add_target");
            params.hp = Number(getVal(layero, "add_hp"));
            params.Aggressivity = Number(getVal(layero, "add_aggressivity"));
            params.Defense = Number(getVal(layero, "add_defense"));
            params.burst = Number(getVal(layero, "add_burst"));
            params.injury = Number(getVal(layero, "add_injury"));
            params.internal_injury = Number(getVal(layero, "add_internal_injury"));
            params.poisoning = Number(getVal(layero, "add_poisoning"));
            params.stun = Number(getVal(layero, "add_stun"));
            params.dodge = Number(getVal(layero, "add_dodge"));
            params.hit_rate = Number(getVal(layero, "add_hit_rate"));
            params.crit_rate = Number(getVal(layero, "add_crit_rate"));
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
                params.suck_mp_yellow = suck.suck_mp_yellow;
                params.suck_mp_gold = suck.suck_mp_gold;
                params.suck_mp_green = suck.suck_mp_green;
                params.suck_mp_blue = suck.suck_mp_blue;
                params.suck_mp_purple = suck.suck_mp_purple;
            }
            sessionStorage.removeItem("ahp");
            sessionStorage.removeItem("amp");
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset(layero);
        });
}

function aHpInfo() {
    var content = $("#aHpInfo");
    layerOpen(1, "HP色球影响", content, 900, 410, "确定", "重置", "",
        function (index, layero) {
            var hp = {};
            hp.mp_yellow = mergeVal("add_hpYellow");
            hp.mp_gold = mergeVal("add_hpGold");
            hp.mp_green = mergeVal("add_hpGreen");
            hp.mp_blue = mergeVal("add_hpBlue");
            hp.mp_purple = mergeVal("add_hpPurple");
            sessionStorage.setItem("ahp", JSON.stringify(hp));
            layer.closeAll();
        }, function (index, layero) {
            clearValue("add_hpYellow,add_hpGold,add_hpGreen,add_hpBlue,add_hpPurple");
        });
}

function aSuckInfo() {
    var content = $("#aSuckInfo");
    layerOpen(1, "吸血色球影响", content, 900, 410, "确定", "重置", "",
        function (index, layero) {
            var suck = {};
            suck.suck_mp_yellow = mergeVal("add_suckYellow");
            suck.suck_mp_gold = mergeVal("add_suckGold");
            suck.suck_mp_green = mergeVal("add_suckGreen");
            suck.suck_mp_blue = mergeVal("add_suckBlue");
            suck.suck_mp_purple = mergeVal("add_suckPurple");
            sessionStorage.setItem("amp", JSON.stringify(suck));
            layer.closeAll();
        }, function (index, layero) {
            clearValue("add_suckYellow,add_suckGold,add_suckGreen,add_suckBlue,add_suckPurple");
        });
}

function addReset(layero) {
    cleanVal(layero, "add_name");
    cleanVal(layero, "add_hp");
    cleanVal(layero, "add_aggressivity");
    cleanVal(layero, "add_defense");
    cleanVal(layero, "add_burst");
    cleanVal(layero, "add_injury");
    cleanVal(layero, "add_internal_injury");
    cleanVal(layero, "add_poisoning");
    cleanVal(layero, "add_stun");
    cleanVal(layero, "add_dodge");
    cleanVal(layero, "add_hit_rate");
    cleanVal(layero, "add_crit_rate");
    cleanVal(layero, "add_suck_HP");
    cleanVal(layero, "add_info");
}

function see(data) {
    var content = $("#seeInfo");
    layerOpen(1, "查看详情", content, 1050, 500, "明白了", "关闭",
        function (index, layero) {
            $("#see_name").val(isNull(data.name));
            $("#see_target").val(isNull(data.target));
            $("#see_hp").val(isNull(data.hp));
            $("#see_mp_yellow").val(isNull(data.mp_yellow));
            $("#see_mp_gold").val(isNull(data.mp_gold));
            $("#see_mp_green").val(isNull(data.mp_green));
            $("#see_mp_blue").val(isNull(data.mp_blue));
            $("#see_mp_purple").val(isNull(data.mp_purple));
            $("#see_aggressivity").val(isNull(data.Aggressivity));
            $("#see_defense").val(isNull(data.Defense));
            $("#see_burst").val(isNull(data.burst));
            $("#see_injury").val(isNull(data.injury));
            $("#see_internal_injury").val(isNull(data.internal_injury));
            $("#see_poisoning").val(isNull(data.poisoning));
            $("#see_stun").val(isNull(data.stun));
            $("#see_dodge").val(isNull(data.dodge));
            $("#see_hit_rate").val(isNull(data.hit_rate));
            $("#see_crit_rate").val(isNull(data.crit_rate));
            $("#see_suck_HP").val(isNull(data.suck_HP));
            $("#see_suck_mp_yellow").val(isNull(data.suck_mp_yellow));
            $("#see_suck_mp_gold").val(isNull(data.suck_mp_gold));
            $("#see_suck_mp_green").val(isNull(data.suck_mp_green));
            $("#see_suck_mp_blue").val(isNull(data.suck_mp_blue));
            $("#see_suck_mp_purple").val(isNull(data.suck_mp_purple));
            $("#see_info").value = isNull(data.info);
        }, function (index, layero) {
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
                var content = [splictUrl + '/system/effectUpdate', 'no'];
                layerOpen(2, "编辑", content, 1200, 650, "立即提交", "重置",
                    function (index, layero) {
                        editVal(layero, "edit_name", obj.name);
                        editVal(layero, "edit_hp", obj.hp);
                        editVal(layero, "edit_aggressivity", obj.Aggressivity);
                        editVal(layero, "edit_defense", obj.Defense);
                        editVal(layero, "edit_burst", obj.burst);
                        editVal(layero, "edit_injury", obj.injury);
                        editVal(layero, "edit_internal_injury", obj.internal_injury);
                        editVal(layero, "edit_poisoning", obj.poisoning);
                        editVal(layero, "edit_stun", obj.stun);
                        editVal(layero, "edit_dodge", obj.dodge);
                        editVal(layero, "edit_hit_rate", obj.hit_rate);
                        editVal(layero, "edit_crit_rate", obj.crit_rate);
                        editVal(layero, "edit_suck_HP", obj.suck_HP);
                        editVal(layero, "edit_info", obj.info);
                        editVal(layero, "edit_target", String(obj.target));
                        layui.form.render("select");
                        sessionStorage.setItem("eff", JSON.stringify(obj));
                    }, function (index, layero) {
                        var body =  $(layero).find("iframe")[0].contentWindow;
                        var params = {};
                        params.effect_id = obj.effect_id;
                        params.name = body.document.getElementById("edit_name").value;
                        params.target = Number(body.document.getElementById("edit_target").value);
                        params.hp = Number(body.document.getElementById("edit_hp").value);
                        params.Aggressivity = Number(body.document.getElementById("edit_aggressivity").value);
                        params.Defense = Number(body.document.getElementById("edit_defense").value);
                        params.burst = Number(body.document.getElementById("edit_burst").value);
                        params.injury = Number(body.document.getElementById("edit_injury").value);
                        params.internal_injury = Number(body.document.getElementById("edit_internal_injury").value);
                        params.poisoning = Number(body.document.getElementById("edit_poisoning").value);
                        params.stun = Number(body.document.getElementById("edit_stun").value);
                        params.dodge = Number(body.document.getElementById("edit_dodge").value);
                        params.hit_rate = Number(body.document.getElementById("edit_hit_rate").value);
                        params.crit_rate = Number(body.document.getElementById("edit_crit_rate").value);
                        params.suck_HP = Number(body.document.getElementById("edit_suck_HP").value);
                        params.info = body.document.getElementById("edit_info").value;
                        if (verIfy(params) === false) {
                            return;
                        }
                        var hp = JSON.parse(sessionStorage.getItem("ehp"));
                        if (hp != null) {
                            params.mp_yellow = hp.mp_yellow;
                            params.mp_gold = hp.mp_gold;
                            params.mp_green = hp.mp_green;
                            params.mp_blue = hp.mp_blue;
                            params.mp_purple = hp.mp_purple;
                        }
                        var suck = JSON.parse(sessionStorage.getItem("emp"));
                        if (suck != null) {
                            params.suck_mp_yellow = suck.suck_mp_yellow;
                            params.suck_mp_gold = suck.suck_mp_gold;
                            params.suck_mp_green = suck.suck_mp_green;
                            params.suck_mp_blue = suck.suck_mp_blue;
                            params.suck_mp_purple = suck.suck_mp_purple;
                        }
                        sessionStorage.removeItem("ehp");
                        sessionStorage.removeItem("emp");
                        aaUp(params, manages, update, "修改", pagingQuery)
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
    layerOpen(1, "HP色球影响", content, 900, 410, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("ehp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            var yellow = obj.mp_yellow.split(",");
            //assignmentValue(yellow,"edit_hpYellow");
            $("#edit_hpYellow1").val(yellow[0]);
            $("#edit_hpYellow2").val(yellow[1]);
            $("#edit_hpYellow3").val(yellow[2]);
            var gold = obj.mp_gold.split(",");
            //assignmentValue(gold,"edit_hpGold");
            $("#edit_hpGold1").val(gold[0]);
            $("#edit_hpGold2").val(gold[1]);
            $("#edit_hpGold3").val(gold[2]);
            var green = obj.mp_green.split(",");
            //assignmentValue(green,"edit_hpGreen");
            $("#edit_hpGreen1").val(green[0]);
            $("#edit_hpGreen2").val(green[1]);
            $("#edit_hpGreen3").val(green[2]);
            var blue = obj.mp_blue.split(",");
            //assignmentValue(blue,"edit_hpBlue");
            $("#edit_hpBlue1").val(blue[0]);
            $("#edit_hpBlue2").val(blue[1]);
            $("#edit_hpBlue3").val(blue[2]);
            var purple = obj.mp_purple.split(",");
            //assignmentValue(purple,"edit_hpPurple");
            $("#edit_hpPurple1").val(purple[0]);
            $("#edit_hpPurple2").val(purple[1]);
            $("#edit_hpPurple3").val(purple[2]);
        }, function (index, layero) {
            var hp = {};
            hp.mp_yellow = mergeVal("edit_hpYellow");
            hp.mp_gold = mergeVal("edit_hpGold");
            hp.mp_green = mergeVal("edit_hpGreen");
            hp.mp_blue = mergeVal("edit_hpBlue");
            hp.mp_purple = mergeVal("edit_hpPurple");
            sessionStorage.setItem("ehp", JSON.stringify(hp));
            layer.closeAll();
        }, function (index, layero) {
            clearValue("edit_hpYellow,edit_hpGold,edit_hpGreen,edit_hpBlue,edit_hpPurple");
        }
    );
}

function eSuckInfo() {
    var content = $("#eSuckInfo");
    layerOpen(1, "吸血色球影响", content, 900, 410, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("emp"));
            if (obj === null) {
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            var yellow = obj.suck_mp_yellow.split(",");
            //assignmentValue(yellow,"edit_suckYellow");
            $("#edit_suckYellow1").val(yellow[0]);
            $("#edit_suckYellow2").val(yellow[1]);
            $("#edit_suckYellow3").val(yellow[2]);
            var gold = obj.suck_mp_gold.split(",");
            //assignmentValue(gold,"edit_suckGold");
            $("#edit_suckGold1").val(gold[0]);
            $("#edit_suckGold2").val(gold[1]);
            $("#edit_suckGold3").val(gold[2]);
            var green = obj.suck_mp_green.split(",");
            //assignmentValue(green,"edit_suckGreen");
            $("#edit_suckGreen1").val(green[0]);
            $("#edit_suckGreen2").val(green[1]);
            $("#edit_suckGreen3").val(green[2]);
            var blue = obj.suck_mp_blue.split(",");
            //assignmentValue(blue,"edit_suckBlue");
            $("#edit_suckBlue1").val(blue[0]);
            $("#edit_suckBlue2").val(blue[1]);
            $("#edit_suckBlue3").val(blue[2]);
            var purple = obj.suck_mp_purple.split(",");
            //assignmentValue(purple,"edit_suckPurple");
            $("#edit_suckPurple1").val(purple[0]);
            $("#edit_suckPurple2").val(purple[1]);
            $("#edit_suckPurple3").val(purple[2]);
        }, function (index, layero) {
            var suck = {};
            suck.suck_mp_yellow = mergeVal("edit_suckYellow");
            suck.suck_mp_gold = mergeVal("edit_suckGold");
            suck.suck_mp_green = mergeVal("edit_suckGreen");
            suck.suck_mp_blue = mergeVal("edit_suckBlue");
            suck.suck_mp_purple = mergeVal("edit_suckPurple");
            sessionStorage.setItem("emp", JSON.stringify(suck));
            layer.closeAll();
        }, function (index, layero) {
            clearValue("edit_hpYellow,edit_hpGold,edit_hpGreen,edit_hpBlue,edit_hpPurple");
        }
    );
}

function assignmentValue(obj,id) {
    alert(obj);
    for(var i=0; i<4; i++){
        var a = id+1+i;
        alert(a);
        document.getElementById(a).value = Number(obj[i]);
    }
}

function editReset(layero) {
    cleanVal(layero, "edit_hp");
    cleanVal(layero, "edit_aggressivity");
    cleanVal(layero, "edit_defense");
    cleanVal(layero, "edit_burst");
    cleanVal(layero, "edit_injury");
    cleanVal(layero, "edit_internal_injury");
    cleanVal(layero, "edit_poisoning");
    cleanVal(layero, "edit_stun");
    cleanVal(layero, "edit_dodge");
    cleanVal(layero, "edit_hit_rate");
    cleanVal(layero, "edit_crit_rate");
    cleanVal(layero, "edit_suck_HP");
    cleanVal(layero, "edit_info");
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

function clearValue(ids) {
    var obj = ids.split(",");
    for (var i = 0; i < obj.length; i++) {
        for (var j = 1; j < 4; j++) {
            var id = obj[i] + j;
            document.getElementById(id).value = '';
        }
    }
}
