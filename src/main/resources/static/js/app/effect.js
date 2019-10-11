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
            document.getElementById("see_name").value = isNull(data.name);
            document.getElementById("see_target").value = isNull(data.target);
            document.getElementById("see_hp").value = isNull(data.hp);
            document.getElementById("see_mp_yellow").value = isNull(data.mp_yellow);
            document.getElementById("see_mp_gold").value = isNull(data.mp_gold);
            document.getElementById("see_mp_green").value = isNull(data.mp_green);
            document.getElementById("see_mp_blue").value = isNull(data.mp_blue);
            document.getElementById("see_mp_purple").value = isNull(data.mp_purple);
            document.getElementById("see_aggressivity").value = isNull(data.Aggressivity);
            document.getElementById("see_defense").value = isNull(data.Defense);
            document.getElementById("see_burst").value = isNull(data.burst);
            document.getElementById("see_injury").value = isNull(data.injury);
            document.getElementById("see_internal_injury").value = isNull(data.internal_injury);
            document.getElementById("see_poisoning").value = isNull(data.poisoning);
            document.getElementById("see_stun").value = isNull(data.stun);
            document.getElementById("see_dodge").value = isNull(data.dodge);
            document.getElementById("see_hit_rate").value = isNull(data.hit_rate);
            document.getElementById("see_crit_rate").value = isNull(data.crit_rate);
            document.getElementById("see_suck_HP").value = isNull(data.suck_HP);
            document.getElementById("see_suck_mp_yellow").value = isNull(data.suck_mp_yellow);
            document.getElementById("see_suck_mp_gold").value = isNull(data.suck_mp_gold);
            document.getElementById("see_suck_mp_green").value = isNull(data.suck_mp_green);
            document.getElementById("see_suck_mp_blue").value = isNull(data.suck_mp_blue);
            document.getElementById("see_suck_mp_purple").value = isNull(data.suck_mp_purple);
            document.getElementById("see_info").value = isNull(data.info);
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
            if(obj === null){
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            var yellow = obj.mp_yellow.split(",");
            //assignmentValue(yellow,"edit_hpYellow");
            document.getElementById("edit_hpYellow1").value = (yellow[0]);
            document.getElementById("edit_hpYellow2").value = (yellow[1]);
            document.getElementById("edit_hpYellow3").value = (yellow[2]);
            var gold = obj.mp_gold.split(",");
            //assignmentValue(gold,"edit_hpGold");
            document.getElementById("edit_hpGold1").value = (gold[0]);
            document.getElementById("edit_hpGold2").value = (gold[1]);
            document.getElementById("edit_hpGold3").value = (gold[2]);
            var green = obj.mp_green.split(",");
            //assignmentValue(green,"edit_hpGreen");
            document.getElementById("edit_hpGreen1").value = (green[0]);
            document.getElementById("edit_hpGreen2").value = (green[1]);
            document.getElementById("edit_hpGreen3").value = (green[2]);
            var blue = obj.mp_blue.split(",");
            //assignmentValue(blue,"edit_hpBlue");
            document.getElementById("edit_hpBlue1").value = (blue[0]);
            document.getElementById("edit_hpBlue2").value = (blue[1]);
            document.getElementById("edit_hpBlue3").value = (blue[2]);
            var purple = obj.mp_purple.split(",");
            //assignmentValue(purple,"edit_hpPurple");
            document.getElementById("edit_hpPurple1").value = (purple[0]);
            document.getElementById("edit_hpPurple2").value = (purple[1]);
            document.getElementById("edit_hpPurple3").value = (purple[2]);
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

function eSuckInfo(){
    var content = $("#eSuckInfo");
    layerOpen(1, "吸血色球影响", content, 900, 410, "确定", "重置",
        function (index, layero) {
            var obj = JSON.parse(sessionStorage.getItem("emp"));
            if(obj === null){
                obj = JSON.parse(sessionStorage.getItem("eff"));
            }
            var yellow = obj.suck_mp_yellow.split(",");
            //assignmentValue(yellow,"edit_suckYellow");
            document.getElementById("edit_suckYellow1").value = (yellow[0]);
            document.getElementById("edit_suckYellow2").value = (yellow[1]);
            document.getElementById("edit_suckYellow3").value = (yellow[2]);
            var gold = obj.suck_mp_gold.split(",");
            //assignmentValue(gold,"edit_suckGold");
            document.getElementById("edit_suckGold1").value = (gold[0]);
            document.getElementById("edit_suckGold2").value = (gold[1]);
            document.getElementById("edit_suckGold3").value = (gold[2]);
            var green = obj.suck_mp_green.split(",");
            //assignmentValue(green,"edit_suckGreen");
            document.getElementById("edit_suckGreen1").value = (green[0]);
            document.getElementById("edit_suckGreen2").value = (green[1]);
            document.getElementById("edit_suckGreen3").value = (green[2]);
            var blue = obj.suck_mp_blue.split(",");
            //assignmentValue(blue,"edit_suckBlue");
            document.getElementById("edit_suckBlue1").value = (blue[0]);
            document.getElementById("edit_suckBlue2").value = (blue[1]);
            document.getElementById("edit_suckBlue3").value = (blue[2]);
            var purple = obj.suck_mp_purple.split(",");
            //assignmentValue(purple,"edit_suckPurple");
            document.getElementById("edit_suckPurple1").value = (purple[0]);
            document.getElementById("edit_suckPurple2").value = (purple[1]);
            document.getElementById("edit_suckPurple3").value = (purple[2]);
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
