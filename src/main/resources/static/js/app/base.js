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
            params.enable = Number(getVal(layero, "add_enable"));
            params.sex = Number(getVal(layero, "add_sex"));
            params.level = Number(getVal(layero, "add_level"));
            params.attitude = Number(getVal(layero, "add_attitude"));
            params.characters = Number(getVal(layero, "add_character"));
            params.popularity = Number(getVal(layero, "add_popularity"));
            params.coin = Number(getVal(layero, "add_coin"));
            params.gold = Number(getVal(layero, "add_gold"));
            params.school_contribution = Number(getVal(layero, "add_school_contribution"));
            params.experience = Number(getVal(layero, "add_experience"));
            params.is_npc = 1;
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
                virtue.physique = Number($("#add_mphysique").val());
                virtue.forces = Number($("#add_force").val());
                virtue.muscles = Number($("#add_muscles").val());
                virtue.chakra = Number($("#add_chakra").val());
                virtue.sensitivity = Number($("#add_sensitivity").val());
                virtue.willpower = Number($("#add_willpower").val());
                virtue.knowledge = Number($("#add_knowledge").val());
                virtue.lucky = Number($("#add_lucky").val());
                var sum = Number(virtue.physique) + Number(virtue.forces);
                sum += Number(virtue.muscles);
                sum += Number(virtue.chakra);
                sum += Number(virtue.sensitivity);
                sum += Number(virtue.willpower);
                sum += Number(virtue.knowledge);
                sum += Number(virtue.lucky);
                var attNum = ($("#add_attributes").val());
                if (sum > attNum) {
                    var a = Number(sum) - Number(attNum);
                    layer.msg("各项属性值总和超出总属值的："+a);
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

function calAttNum(a,b,c,d,e,f,m,n) {
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
    layerOpen(1, "武学信息", content, 1000, 400, "确定", "关闭", "",
        function (layero, index) {
            var kongFu = [];
            var forms = document.getElementById("formKongFu");
            var div = forms.getElementsByClassName("inp");
            for (var i = 1; i <= div.length; i++) {
                var diValue = {};
                diValue.type = document.getElementById("type" + i).value;
                diValue.kongFuName = document.getElementById("name" + i).value;
                diValue.experience = Number(document.getElementById("exp" + i).value);
                var a = document.getElementById("use" + i).value;
                diValue.use = (a = "未使用" ? 0 : 1);
                diValue.kongfu_id = document.getElementById("id" + i).value;
                kongFu.push(diValue);
            }
            sessionStorage.setItem("akf", JSON.stringify(kongFu));
            layer.closeAll();
        }, function (index, layero) {
            layer.closeAll();
        });
}

function addTypeBut() {
    var type = $("#add_kfTypes").find("option:selected").val();
    var downs = {};
    downs.type = type;
    $("#add_kongFu").empty();
    downBox(downs, kongFuBox, "add_kongFu", "", lSelection);
}

var i = 1;
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

function createE(typeV,kText,kVal) {
    var fkf = document.getElementById("formKongFu");
    var div = document.createElement("div");
    div.id = "pDiv" + i;
    div.className = "layui-form-item inp";
    var div1 = document.createElement("div");
    div1.className = "layui-input-inline";
    div1.style.width = "50px";
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
        document.getElementById(id).className = "layui-btn buLeng";
    } else {
        document.getElementById(id).value = "未使用";
        document.getElementById(id).className = "layui-btn layui-btn-primary buLeng";
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
            weapon.melee_status = Number(document.getElementById("add_melee_status").value);
            weapon.sword_status = Number(document.getElementById("add_sword_status").value);
            weapon.axe_status = Number(document.getElementById("add_axe_status").value);
            weapon.javelin_status = Number(document.getElementById("add_javelin_status").value);
            weapon.hidden_weapons_status = Number(document.getElementById("add_hidden_weapons_status").value);
            weapon.sorcery_status = Number(document.getElementById("add_sorcery_status").value);
            weapon.dodge_skill_status = Number(document.getElementById("add_dodge_skill_status").value);
            weapon.chakra_status = Number(document.getElementById("add_chakra_status").value);
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
            potent.wisdom1 = Number(document.getElementById("add_wisdom1").value);
            potent.wisdom2 = Number(document.getElementById("add_wisdom2").value);
            potent.wisdom3 = Number(document.getElementById("add_wisdom3").value);
            potent.healthy1 = Number(document.getElementById("add_healthy1").value);
            potent.healthy2 = Number(document.getElementById("add_healthy2").value);
            potent.healthy3 = Number(document.getElementById("add_healthy3").value);
            potent.mellow1 = Number(document.getElementById("add_mellow1").value);
            potent.mellow2 = Number(document.getElementById("add_mellow2").value);
            potent.mellow3 = Number(document.getElementById("add_mellow3").value);
            potent.mellow4 = Number(document.getElementById("add_mellow4").value);
            potent.mellow5 = Number(document.getElementById("add_mellow5").value);
            potent.fineness1 = Number(document.getElementById("add_fineness1").value);
            potent.fineness2 = Number(document.getElementById("add_fineness2").value);
            potent.burst1 = Number(document.getElementById("add_burst1").value);
            potent.burst2 = Number(document.getElementById("add_burst2").value);
            potent.sharp1 = Number(document.getElementById("add_sharp1").value);
            potent.sharp2 = Number(document.getElementById("add_sharp2").value);
            potent.tenacity1 = Number(document.getElementById("add_tenacity1").value);
            potent.tenacity2 = Number(document.getElementById("add_tenacity2").value);
            potent.tenacity3 = Number(document.getElementById("add_tenacity3").value);
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
    $(layero).find("iframe")[0].contentWindow.document.getElementById("add_enable").value = '1';
    $(layero).find("iframe")[0].contentWindow.document.getElementById("add_sex").value = '1';
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
                        if(obj.potent.length > 0){
                            var potent = obj.potent[0];
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