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
    var params = {};
    var content = [splictUrl + '/system/effectAdd', 'no'];
    layerOpen(2, "新增", content, 1200, 650, "立即提交", "重置",
        function (index, layero) {
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
            if (verIf(params) === false) {
                return;
            }
            var hp = sessionStorage.getItem("hp");
            var hpObj = JSON.parse(hp);
            if (hpObj != null) {
                params.mp_yellow = hpObj.mp_yellow;
                params.mp_gold = hpObj.mp_gold;
                params.mp_green = hpObj.mp_green;
                params.mp_blue = hpObj.mp_blue;
                params.mp_purple = hpObj.mp_purple;
            }
            var suck = sessionStorage.getItem("suck");
            var suckObj = JSON.parse(suck);
            if (suckObj != null) {
                params.suck_mp_yellow = suckObj.suck_mp_yellow;
                params.suck_mp_gold = suckObj.suck_mp_gold;
                params.suck_mp_green = suckObj.suck_mp_green;
                params.suck_mp_blue = suckObj.suck_mp_blue;
                params.suck_mp_purple = suckObj.suck_mp_purple;
            }
            sessionStorage.removeItem("hp");
            sessionStorage.removeItem("suck");
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset(layero);
        });
}
function aHpInfo() {
    var content = $("#aHpInfo");
    layerOpen(1, "HP色球影响", content, 900, 410, "确定", "重置",
        function (index, layero) {
            var hp = {};
            hp.mp_yellow = splices("add_hpYellow1", "add_hpYellow2", "add_hpYellow3");
            hp.mp_gold = splices("add_hpGold1", "add_hpGold2", "add_hpGold3");
            hp.mp_green = splices("add_hpGreen1", "add_hpGreen2", "add_hpGreen3");
            hp.mp_blue = splices("add_hpBlue1", "add_hpBlue2", "add_hpBlue3");
            hp.mp_purple = splices("add_hpPurple1", "add_hpPurple2", "add_hpPurple3");
            sessionStorage.setItem("hp", JSON.stringify(hp));
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            document.getElementById("add_hpYellow1").value = '';
            document.getElementById("add_hpYellow2").value = '';
            document.getElementById("add_hpYellow3").value = '';
            document.getElementById("add_hpGold1").value = '';
            document.getElementById("add_hpGold2").value = '';
            document.getElementById("add_hpGold3").value = '';
            document.getElementById("add_hpGreen1").value = '';
            document.getElementById("add_hpGreen2").value = '';
            document.getElementById("add_hpGreen3").value = '';
            document.getElementById("add_hpBlue1").value = '';
            document.getElementById("add_hpBlue2").value = '';
            document.getElementById("add_hpBlue3").value = '';
            document.getElementById("add_hpPurple1").value = '';
            document.getElementById("add_hpPurple2").value = '';
            document.getElementById("add_hpPurple3").value = '';
        });
}
function aSuckInfo() {
    var content = $("#aSuckInfo");
    layerOpen(1, "吸血色球影响", content, 900, 410, "确定", "重置",
        function (index, layero) {
            var suck = {};
            suck.suck_mp_yellow = splices("add_suckYellow1", "add_suckYellow2", "add_suckYellow3");
            suck.suck_mp_gold = splices("add_suckGold1", "add_suckGold2", "add_suckGold3");
            suck.suck_mp_green = splices("add_suckGreen1", "add_suckGreen2", "add_suckGreen3");
            suck.suck_mp_blue = splices("add_suckBlue1", "add_suckBlue2", "add_suckBlue3");
            suck.suck_mp_purple = splices("add_suckPurple1", "add_suckPurple2", "add_suckPurple3");
            sessionStorage.setItem("suck", JSON.stringify(suck));
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            document.getElementById("add_suckYellow1").value = '';
            document.getElementById("add_suckYellow2").value = '';
            document.getElementById("add_suckYellow3").value = '';
            document.getElementById("add_suckGold1").value = '';
            document.getElementById("add_suckGold2").value = '';
            document.getElementById("add_suckGold3").value = '';
            document.getElementById("add_suckGreen1").value = '';
            document.getElementById("add_suckGreen2").value = '';
            document.getElementById("add_suckGreen3").value = '';
            document.getElementById("add_suckBlue1").value = '';
            document.getElementById("add_suckBlue2").value = '';
            document.getElementById("add_suckBlue3").value = '';
            document.getElementById("add_suckPurple1").value = '';
            document.getElementById("add_suckPurple2").value = '';
            document.getElementById("add_suckPurple3").value = '';
        });
}
function addReset(layero) {
    cleanVal(layero,"add_name");
    cleanVal(layero,"add_hp");
    cleanVal(layero,"add_aggressivity");
    cleanVal(layero,"add_defense");
    cleanVal(layero,"add_burst");
    cleanVal(layero,"add_injury");
    cleanVal(layero,"add_internal_injury");
    cleanVal(layero,"add_poisoning");
    cleanVal(layero,"add_stun");
    cleanVal(layero,"add_dodge");
    cleanVal(layero,"add_hit_rate");
    cleanVal(layero,"add_crit_rate");
    cleanVal(layero,"add_suck_HP");
    cleanVal(layero,"add_info");
}


function see(data) {
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
    var content = $("#seeInfo");
    layerOpen(1, "查看详情", content, 1050, 500, "明白了", "关闭",
        function (index, layero) {
            content.hide();
            layer.closeAll();
        }, function (index, layero) {
            content.hide();
            layer.closeAll();
        });
}


function edit(data) {
    var params = {name: data.name};
    var content = [splictUrl + '/system/effectUpdate', 'no'];
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                /*document.getElementById("edit_effect_id").value = isNull(obj.effect_id);
                document.getElementById("edit_name").value = isNull(obj.name);
                document.getElementById("edit_hp").value = isNull(obj.hp);
                document.getElementById("edit_mp_yellow").value = isNull(obj.mp_yellow);
                document.getElementById("edit_mp_gold").value = isNull(obj.mp_gold);
                document.getElementById("edit_mp_green").value = isNull(obj.mp_green);
                document.getElementById("edit_mp_blue").value = isNull(obj.mp_blue);
                document.getElementById("edit_mp_purple").value = isNull(obj.mp_purple);
                document.getElementById("edit_aggressivity").value = isNull(obj.Aggressivity);
                document.getElementById("edit_defense").value = isNull(obj.Defense);
                document.getElementById("edit_burst").value = isNull(obj.burst);
                document.getElementById("edit_injury").value = isNull(obj.injury);
                document.getElementById("edit_internal_injury").value = isNull(obj.internal_injury);
                document.getElementById("edit_poisoning").value = isNull(obj.poisoning);
                document.getElementById("edit_stun").value = isNull(obj.stun);
                document.getElementById("edit_dodge").value = isNull(obj.dodge);
                document.getElementById("edit_hit_rate").value = isNull(obj.hit_rate);
                document.getElementById("edit_crit_rate").value = isNull(obj.crit_rate);
                document.getElementById("edit_suck_HP").value = isNull(obj.suck_HP);
                document.getElementById("edit_suck_mp_yellow").value = isNull(obj.suck_mp_yellow);
                document.getElementById("edit_suck_mp_gold").value = isNull(obj.suck_mp_gold);
                document.getElementById("edit_suck_mp_green").value = isNull(obj.suck_mp_green);
                document.getElementById("edit_suck_mp_blue").value = isNull(obj.suck_mp_blue);
                document.getElementById("edit_suck_mp_purple").value = isNull(obj.suck_mp_purple);
                document.getElementById("edit_info").value = isNull(obj.info);
                $("#edit_target").val(String(obj.target));
                layui.form.render("select");*/
                //$(layero).find("iframe")[0].contentWindow.document.getElementById("edit_name").value = obj.name;
                alert(obj.name);
                layerOpen(2,"编辑", content, 1200, 650,"立即提交","重置",
                    function (index, layero) {
                        var body = layer.getChildFrame('body', index);
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                        console.log(body.html()); //得到iframe页的body内容
                        body.find('#edit_name').val('Hi，我是从父页来的');

                        //alert(obj.name);
                        //var pIframe = $('iframe')[0].contentWindow.document;
                        //$(pIframe).find("#edit_name").val(obj.name);

                            //body.contents().find("#edit_name").val(obj.name);
                        alert(obj.name);
                        var params = {
                            effect_id: $("#edit_effect_id").val(),
                            name: $("#edit_name").val(),
                            target: Number($("#edit_target").val()),
                            hp: Number($("#edit_hp").val()),
                            mp_yellow: $("#edit_mp_yellow").val(),
                            mp_gold: $("#edit_mp_gold").val(),
                            mp_green: $("#edit_mp_green").val(),
                            mp_blue: $("#edit_mp_blue").val(),
                            mp_purple: $("#edit_mp_purple").val(),
                            Aggressivity: Number($("#edit_aggressivity").val()),
                            Defense: Number($("#edit_defense").val()),
                            burst: Number($("#edit_burst").val()),
                            injury: Number($("#edit_injury").val()),
                            internal_injury: Number($("#edit_internal_injury").val()),
                            poisoning: Number($("#edit_poisoning").val()),
                            stun: Number($("#edit_stun").val()),
                            dodge: Number($("#edit_dodge").val()),
                            hit_rate: Number($("#edit_hit_rate").val()),
                            crit_rate: Number($("#edit_crit_rate").val()),
                            suck_HP: Number($("#edit_suck_HP").val()),
                            suck_mp_yellow: $("#edit_suck_mp_yellow").val(),
                            suck_mp_gold: $("#edit_suck_mp_gold").val(),
                            suck_mp_green: $("#edit_suck_mp_green").val(),
                            suck_mp_blue: $("#edit_suck_mp_blue").val(),
                            suck_mp_purple: $("#edit_suck_mp_purple").val(),
                            info: $("#edit_info").val()
                        };
                        eTransfer(params, manages, pagingQuery);
                    }, function (index, layero) {
                        editReset();
                    });
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}
function editReset() {
    $("#edit_hp").val('');
    $("#edit_mp_yellow").val('');
    $("#edit_mp_gold").val('');
    $("#edit_mp_green").val('');
    $("#edit_mp_blue").val('');
    $("#edit_mp_purple").val('');
    $("#edit_aggressivity").val('');
    $("#edit_defense").val('');
    $("#edit_burst").val('');
    $("#edit_injury").val('');
    $("#edit_internal_injury").val('');
    $("#edit_poisoning").val('');
    $("#edit_stun").val('');
    $("#edit_dodge").val('');
    $("#edit_hit_rate").val('');
    $("#edit_crit_rate").val('');
    $("#edit_suck_HP").val('');
    $("#edit_suck_mp_yellow").val('');
    $("#edit_suck_mp_gold").val('');
    $("#edit_suck_mp_green").val('');
    $("#edit_suck_mp_blue").val('');
    $("#edit_suck_mp_purple").val('');
    $("#edit_info").val('');
}


function verIf(params) {
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
