charset="utf-8";
manages="baseNpc";
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
    addReset();
    downBox(schoolBox, "add_school", "", mlSelection);
    var content = $("#addInfo");
    layerOpen("新增", content, 1000, 500,
        function () {
            var params = {
                nickname: $("#add_nickname").val(),
                school_id: $("#add_school").val(),
                sex: Number($("#add_sex").val()),
                level: Number($("#add_level").val()),
                attitude: Number($("#add_attitude").val()),
                character: Number($("#add_character").val()),
                popularity: Number($("#add_popularity").val()),
                coin: Number($("#add_coin").val()),
                gold: Number($("#add_gold").val()),
                school_contribution: Number($("#add_school_contribution").val()),
                enable: $("#add_enable").val()
            };
            if (params.nickname === null || params.nickname === "") {
                layer.msg("人物名称不能为空", {icon: 2});
                return;
            }
            if (params.school_id === null || params.school_id === "") {
                layer.msg("所属门派不能为空", {icon: 2});
                return;
            }
            if (params.level <= 0) {
                layer.msg("等级不能为空或小于1", {icon: 2});
                return;
            }
            params.experience = Number(calExperience(params.level));
            aTransfer(params, manages, pagingQuery);
        }, function (index, layero) {
            addReset();
        });
}
function but(){
    $(".attInfo").html($("#attInfo").html());
    $(".weaponInfo").html($("#weaponInfo").html());
    $(".artsInfo").html($("#artsInfo").html());
}
var mphysique;
$(function (){
    but();
    $("#add_level").on("input",function (e) {
        var obj = e.delegateTarget.value;
        calExperience(obj);
        calAttributes(obj);
    });
    $("#add_mphysique").on("input",function (e) {
        mphysique = e.delegateTarget.value;
    });
});
function calAdd(obj,min){
    obj = (Math.abs(parseInt(obj)) + 1);
    alert(obj);
    if (parseInt(obj) > 0) {
        min.attr('disabled',false);
    }
    return obj;
}
function calMin(obj,min){
    obj = (Math.abs(parseInt(obj)) - 1);
    if(parseInt(obj) <= 0){
        min.attr('disabled',true);
    }
    return obj;
}
$(document).on("click","#mphysMin",function () {
    var min = $("#mphysMin");
    var mphys = calMin(mphysique,min);
    document.getElementById("add_mphysique").value = isNull(mphys);
});
$(document).on("click","#mphysAdd",function () {
    alert("455");
    $("#add_mphysique").on("input",function (e) {
        var data = e.delegateTarget.value;
        alert(data);
    })
});
//分配属性点
function attDistribution(){

}
//根据等级计算经验值（TODO 暂时为1等级为100经验值）
function calExperience(level){
    var experience = level*100;
    document.getElementById("add_experience").value = isNull(experience);
    return experience;
}
//根据等级计算属性分值(TODO 1等级5点属性值)
function calAttributes(level){
    var attributes = level*5;
    document.getElementById("add_attributes").value = isNull(attributes);
    return attributes;
}
function attributeAll(attributes){
    var attr = {};
    var mphysique=Number($("#add_mphysique").val());
    var force=Number($("#add_force").val());
    var muscles=Number($("#add_muscles").val());
    var chakra=Number($("#add_chakra").val());
    var sensitivity=Number($("#add_sensitivity").val());
    var willpower=Number($("#add_willpower").val());
    var knowledge=Number($("#add_knowledge").val());
    var lucky=Number($("#add_lucky").val());
}

//新增-门派下拉框（单选）
function addSchool(){
    document.getElementById("add_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#add_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}
$(function (){
    //获取武学下拉框
    var params = {};
    postRequest(params,"move",mKongFuBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                layui.use('transfer', function(){
                    var transfer = layui.transfer;
                    transfer.render({
                        elem: '#addKongFu'
                        ,data:obj.data
                        ,parseData:function (res) {
                            return {
                                "value": res.kongfu_id,
                                "title": res.name,
                                "disabled": res.disabled,
                                "checked": res.checked
                            }
                        }
                        ,title: ['武学选项', '武学添加']
                        ,id: 'demo1' //定义索引
                        ,width:304
                        ,height:340
                        ,onchange:function () {

                        }
                    });
                });
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
});
//新增-重置
function addReset(){
    $("#add_nickname").val('');
    $("#add_level").val('');
    $("#add_attitude").val('');
    $("#add_character").val('');
    $("#add_popularity").val('');
    $("#add_coin").val('');
    $("#add_gold").val('');
    $("#add_experience").val('');
    $("#add_school_contribution").val('');
    $("#add_img").val('');
    $("#add_enable").val(1);
    $("#add_sex").val(1);
    layui.form.render("select");
}


function see(data) {
    var params = {nickname: data.nickname};
    postRequest(params, manages, sDetails, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
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
                var content = $("#seeInfo");
                layerSeeOpen("查看详情", content, 1000, 500);
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}


function edit(data) {
    editSchool();
    var params = {nickname: data.nickname};
    postRequest(params, manages, eQuery, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                document.getElementById("edit_id").value = isNull(obj.id);
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
                layui.form.render("select");
                var content = $("#editInfo");
                layerOpen("编辑", content, 1000, 500,
                    function () {
                        editRequest();
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
//编辑-门派下拉框（单选）
function editSchool(){
    document.getElementById("edit_school").options.length = 0;
    var params = {};
    postRequest(params,manages,nSchoolBox,function (data){
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows,charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data,function (i,n) {
                    $("#edit_school").append("<option value='"+n.school_id+"'>"+n.name+"</option>");
                });
                layui.form.render("select");
            }
        } else {
            layer.msg(data.msg,{icon:2});
        }
    });
}