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
    $("#query_target").val("");
    layui.form.render("select");
    pagingQuery();
}


function add() {
    addReset();
    var content = splictUrl + "/system/effect_add";
    layerOpen(2, "新增", content, 1200, 650, "立即提交", "重置",
        function () {
            var params = {
                name: $("#add_name").val(),
                target: Number($("#add_target").val()),
                hp: Number($("#add_hp").val()),
                Aggressivity: Number($("#add_aggressivity").val()),
                Defense: Number($("#add_defense").val()),
                burst: Number($("#add_burst").val()),
                injury: Number($("#add_injury").val()),
                internal_injury: Number($("#add_internal_injury").val()),
                poisoning: Number($("#add_poisoning").val()),
                stun: Number($("#add_stun").val()),
                dodge: Number($("#add_dodge").val()),
                hit_rate: Number($("#add_hit_rate").val()),
                crit_rate: Number($("#add_crit_rate").val()),
                suck_HP: Number($("#add_suck_HP").val()),
                /*suck_mp_yellow: $("#add_suck_mp_yellow").val(),
                suck_mp_gold: $("#add_suck_mp_gold").val(),
                suck_mp_green: $("#add_suck_mp_green").val(),
                suck_mp_blue: $("#add_suck_mp_blue").val(),
                suck_mp_purple: $("#add_suck_mp_purple").val(),*/
                info: $("#add_info").val()
            };
            if (verIf(params) === false) {
                return;
            }
            //var hp = hps();
            //var iframeWin = window[layero.find('iframe')[0]['name']];
            alert(iframeWin);
            aaUp(params, manages, insert, "新增", pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}

function hpInfo() {
    var content = $("#hpInfo");
    layerOpen(1, "HP色球影响", content, 900, 410, "确定", "重置",
        function () {
            var hp = {
                mp_yellow: splices("add_hpYellow1", "add_hpYellow2", "add_hpYellow3"),
                mp_gold: splices("add_hpGold1", "add_hpGold2", "add_hpGold3"),
                mp_green: splices("add_hpGreen1", "add_hpGreen2", "add_hpGreen3"),
                mp_blue: splices("add_hpBlue1", "add_hpBlue2", "add_hpBlue3"),
                mp_purple: splices("add_hpPurple1", "add_hpPurple2", "add_hpPurple3")
            };
            alert(hp.mp_yellow);
            content.hide();layer.closeAll();
        }, function () {
            cleanHpInfo();
        });
}

function suckInfo() {
    var content = $("#suckInfo");
    layerOpen(1, "吸血色球影响", content, 900, 400, "确定", "重置",
        function () {
            var suck = {
                mp_yellow: splices("add_suckYellow1", "add_suckYellow2", "add_suckYellow3"),
                mp_gold: splices("add_suckGold1", "add_suckGold2", "add_suckGold3"),
                mp_green: splices("add_suckGreen1", "add_suckGreen2", "add_suckGreen3"),
                mp_blue: splices("add_suckBlue1", "add_suckBlue2", "add_suckBlue3"),
                mp_purple: splices("add_suckPurple1", "add_suckPurple2", "add_suckPurple3")
            };
        }, function () {
            cleanSuckInfo();
        });
}
function addReset() {
    $("#add_name").val('');
    $("#add_hp").val('');
    $("#add_mp_yellow").val('');
    $("#add_mp_gold").val('');
    $("#add_mp_green").val('');
    $("#add_mp_blue").val('');
    $("#add_mp_purple").val('');
    $("#add_aggressivity").val('');
    $("#add_defense").val('');
    $("#add_burst").val('');
    $("#add_injury").val('');
    $("#add_internal_injury").val('');
    $("#add_poisoning").val('');
    $("#add_stun").val('');
    $("#add_dodge").val('');
    $("#add_hit_rate").val('');
    $("#add_crit_rate").val('');
    $("#add_suck_HP").val('');
    $("#add_suck_mp_yellow").val('');
    $("#add_suck_mp_gold").val('');
    $("#add_suck_mp_green").val('');
    $("#add_suck_mp_blue").val('');
    $("#add_suck_mp_purple").val('');
    $("#add_info").val('');
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
        function () {
            content.hide();layer.closeAll();
        }, function () {
            content.hide();layer.closeAll();
        });
}


function edit(data) {
    var params = {name: data.name};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_effect_id").value = isNull(obj.effect_id);
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
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen(1,"编辑", content, 1200, 650,"立即提交","重置",
                    function () {
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

function cleanHpInfo() {
    $("#add_hpYellow1").val('');
    $("#add_hpYellow2").val('');
    $("#add_hpYellow3").val('');
    $("#add_hpGold1").val('');
    $("#add_hpGold2").val('');
    $("#add_hpGold3").val('');
    $("#add_hpGreen1").val('');
    $("#add_hpGreen2").val('');
    $("#add_hpGreen3").val('');
    $("#add_hpBlue1").val('');
    $("#add_hpBlue2").val('');
    $("#add_hpBlue3").val('');
    $("#add_hpPurple1").val('');
    $("#add_hpPurple2").val('');
    $("#add_hpPurple3").val('');
}

function cleanSuckInfo() {
    $("#add_suckYellow1").val('');
    $("#add_suckYellow2").val('');
    $("#add_suckYellow3").val('');
    $("#add_suckGold1").val('');
    $("#add_suckGold2").val('');
    $("#add_suckGold3").val('');
    $("#add_suckGreen1").val('');
    $("#add_suckGreen2").val('');
    $("#add_suckGreen3").val('');
    $("#add_suckBlue1").val('');
    $("#add_suckBlue2").val('');
    $("#add_suckBlue3").val('');
    $("#add_suckPurple1").val('');
    $("#add_suckPurple2").val('');
    $("#add_suckPurple3").val('');
}