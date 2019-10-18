url="/manages/sysAuth/server";
splictUrl="/manages";
eQuery="editQuery";
sDetails="seeDetails";
moveBox="moveBox";
kongFuBox="kongFuBox";
effectBox="effectBox";
schoolBox="schoolBox";
insert="insert";
update="update";
$(function () {
    layui.use(['form','element'],
    function() {
        layer = layui.layer;
        element = layui.element;
    });
    var tab = {
        tabAdd: function(title,url,id){
            //新增一个Tab项
            element.tabAdd('xbs_tab', {
                title: title
                ,content: '<iframe tab-id="'+id+'" frameborder="0" src="'+url+'" scrolling="yes" class="x-iframe"></iframe>'
                ,id: id
            });
        }
        ,tabDelete: function(othis){
            element.tabDelete('xbs_tab', '44');
            othis.addClass('layui-btn-disabled');
        }
        ,tabChange: function(id){
            element.tabChange('xbs_tab', id);
        }
    };
    tableCheck = {
        init:function  () {
            $(".layui-form-checkbox").click(function(event) {
                if($(this).hasClass('layui-form-checked')){
                    $(this).removeClass('layui-form-checked');
                    if($(this).hasClass('header')){
                        $(".layui-form-checkbox").removeClass('layui-form-checked');
                    }
                }else{
                    $(this).addClass('layui-form-checked');
                    if($(this).hasClass('header')){
                        $(".layui-form-checkbox").addClass('layui-form-checked');
                    }
                }
            });
        },
        getData:function  () {
            var obj = $(".layui-form-checked").not('.header');
            var arr=[];
            obj.each(function(index, el) {
                arr.push(obj.eq(index).attr('data-id'));
            });
            return arr;
        }
    };
    //开启表格多选
    tableCheck.init();
    $('.container .left_open i').click(function(event) {
        if($('.left-nav').css('left')=='0px'){
            $('.left-nav').animate({left: '-251px'}, 100);
            $('.page-content').animate({left: '0px'}, 100);
            $('.page-content-bg').hide();
        }else{
            $('.left-nav').animate({left: '0px'}, 100);
            $('.page-content').animate({left: '251px'}, 100);
            if($(window).width()<768){
                $('.page-content-bg').show();
            }
        }
    });
    $('.page-content-bg').click(function(event) {
        $('.left-nav').animate({left: '-251px'}, 100);
        $('.page-content').animate({left: '0px'}, 100);
        $(this).hide();
    });
    $('.layui-tab-close').click(function(event) {
        $('.layui-tab-title li').eq(0).find('i').remove();
    });
   $("tbody.x-cate tr[fid!='0']").hide();
    // 栏目多级显示效果
    $('.x-show').click(function () {
        if($(this).attr('status')=='true'){
            $(this).html('&#xe625;');
            $(this).attr('status','false');
            cateId = $(this).parents('tr').attr('cate-id');
            $("tbody tr[fid="+cateId+"]").show();
        }else{
            cateIds = [];
            $(this).html('&#xe623;');
            $(this).attr('status','true');
            cateId = $(this).parents('tr').attr('cate-id');
            getCateId(cateId);
            for (var i in cateIds) {
                $("tbody tr[cate-id="+cateIds[i]+"]").hide().find('.x-show').html('&#xe623;').attr('status','true');
            }
        }
    });

    //左侧菜单效果
    // $('#content').bind("click",function(event){
	//点击菜单显示效果	
	$(document).ready(function() {
       $('.left-nav #nav li .sub-menu li ').click(function(){
		   $(this).addClass('menu-current').siblings().removeClass('menu-current');
       });
    });
    $('.left-nav #nav li').click(function (event) {
        if($(this).children('.sub-menu').length){
            if($(this).hasClass('open')){
                $(this).removeClass('open');
                $(this).find('.nav_right').html('<i class="layui-icon iconfont">&#xe602;</i>');
                $(this).children('.sub-menu').stop().slideUp();
                $(this).siblings().children('.sub-menu').slideUp();
            }else{
                $(this).addClass('open');
                $(this).children('a').find('.nav_right').html('<i class="layui-icon iconfont">&#xe61a;</i>');
                $(this).children('.sub-menu').stop().slideDown();
                $(this).siblings().children('.sub-menu').stop().slideUp();
                $(this).siblings().find('.nav_right').html('<i class="layui-icon iconfont">&#xe602;</i>');
                $(this).siblings().removeClass('open');
            }
        }else{
            var url = $(this).children('a').attr('_href');
            var title = $(this).find('cite').html();
            var index  = $('.left-nav #nav li').index($(this));
            for (var i = 0; i <$('.x-iframe').length; i++) {
                if($('.x-iframe').eq(i).attr('tab-id')==index+1){
                    tab.tabChange(index+1);
                    event.stopPropagation();
                    return;
                }
            }
            tab.tabAdd(title,url,index+1);
            tab.tabChange(index+1);
        }
        event.stopPropagation();
    });
});
var cateIds = [];
function getCateId(cateId) {
    $("tbody tr[fid=" + cateId + "]").each(function (index, el) {
        id = $(el).attr('cate-id');
        cateIds.push(id);
        getCateId(id);
    });
}
function x_admin_show(title,url,w,h) {
    if (title === null || title === '') {
        title = false;
    }
    if (url === null || url === '') {
        url = "/manages/system/404";
    }
    if (w === null || w === '') {
        w = ($(window).width() * 0.9);
    }
    if (h === null || h === '') {
        h = ($(window).height() - 50);
    }
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: title,
        content: url
    });
}
function x_admin_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function getVal(layero,id) {
    return $(layero).find("iframe")[0].contentWindow.document.getElementById(id).value;
}
function cleanVal(layero,id,msg) {
    //$(layero).find("iframe")[0].contentWindow.document.getElementById(id).value = msg;
    var iframeWindow = $(layero).find('iframe')[0].contentWindow;
    iframeWindow.document.getElementById(id).value = msg;
}
function editVal(layero,id,par) {
    var body = layer.getChildFrame('body', layero);
    body.contents().find("#"+id).val(isNull(par));
}
function parFormat(str){
    var obj = {};
    if(str === null){
        return obj;
    }else{
        return JSON.parse(str);
    }
}
function checkNull(str){
    if (str === null || str === "undefined" || str === "null" || str === "") {
        return false;
    }else{
        return true;
    }
}
function mergeVal(id) {
    var obj = "#";
    var a = obj + id + 1, b = obj + id + 2, c = obj + id + 3;
    return Number($(a).val()) + "," + Number($(b).val()) + "," + Number($(c).val());
}
function clearVal(ids,per) {
    var obj = ids.split(",");
    var id;
    if(per === 1){
        for (var a = 0; a < obj.length; a++) {
            id = "#add_"+obj[a];
            $(id).val('');
        }
    }
    if(per === 2){
        for (var e = 0; e < obj.length; e++) {
            id = "#edit_"+obj[e];
            $(id).val('');
        }
    }
    layui.form.render("select");
}
function isJSON(str) {
    try {
        JSON.parse(str);
        return true;
    } catch (e) {
        return false;
    }
}
function isNull(str) {
    if (str === null || str === "undefined" || str === "null" || str === "") {
        return "";
    } else {
        return str;
    }
}
function postRequest(params,manages,method,callback) {
    var obj = {
        manages: manages,
        method: method,
        params: $.base64.btoa(JSON.stringify(params), charset)
    };
    $.ajax({
        type: "POST",
        dataType: "JSON",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(obj),
        success: function (data) {
            if (typeof callback === "function") {
                callback(data);
            }
        }
    });
}
function layerOpen(type,title,content,width,height,btn1,btn2,success,callback,reset) {
    if (url === null || url === '') {
        url = "/manages/system/404";
    }
    /*if (width === null || width === '') {
        width = ($(window).width() * 0.9);
    }
    if (height === null || height === '') {
        height = ($(window).height() - 50);
    }*/
    layer.open({
        type: type,
        offset: "auto",
        id: 'layerDemo' + 'auto',
        area: [width + 'px', height + 'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        btn: [btn1, btn2],
        shade: 0.4,
        title: title,
        content: content,
        success: function (index, layero) {
            if (typeof success === "function") {
                success(index, layero);
            }
        },
        yes: function (index, layero) {
            if (typeof callback === "function") {
                callback(index, layero);
            }
        },
        btn2: function (index, layero) {
            if (typeof reset === "function") {
                reset(index, layero);
            }
            return false;
        }
    });
}
function pQue(params,manages,title,cole,add,edit,del,see,pagingQuery) {
    var obj = {};
    obj.manages = manages;
    obj.method = "pagingQuery";
    obj.params = $.base64.btoa(JSON.stringify(params), charset);
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer, table = layui.table;
        table.render({
            elem: '#dataInfo',
            url: url,
            method: 'POST',
            contentType: "application/json",
            toolbar: 'default',
            id: '#dataInfo',
            title: title,
            //even:true,
            expandRow: true,
            where: obj,
            page: true,
            limit: 10,
            limits: [10, 20, 30, 40, 50],
            parseData: function (res) {
                var rows = $.base64.atob(res.rows, charset);
                if (isJSON(rows)) {
                    var obj = JSON.parse(rows);
                    return {
                        "code": res.code,
                        "msg": res.msg,
                        "count": obj.total,
                        "data": obj.data
                    }
                }
            },
            cols: [
                cole
            ]
        });
        table.on('toolbar(dataInfo)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch (obj.event) {
                case 'add':
                    add();
                    break;
                case 'update':
                    if (data.length === 0) {
                        layer.msg('请勾选一条数据进行编辑');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一条数据');
                    } else {
                        $.each(data, function (i, n) {
                            edit(n);
                        });
                    }
                    break;
                case 'delete':
                    del(data, manages, pagingQuery);
                    break;
            }
        });
        table.on('tool(dataInfo)', function (obj) {
            var data = obj.data;
            if (obj.event === 'see') {
                see(data);
            } else if (obj.event === 'edit') {
                edit(data);
            } else if (obj.event === 'del') {
                var des = [data];
                del(des, manages, pagingQuery);
            }
        });
    });
}
function aaUp(params,manages,method,res,pagingQuery) {
    layer.confirm('请确认要' + res + '这条数据吗？', {
            btn: ['确定', '取消']
        }, function () {
            postRequest(params, manages, method, function (data) {
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg(res + "成功");
                    pagingQuery();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            });
        }, function () {
            layer.close();
        }
    );
}
function del(data,manages,pagingQuery) {
    if (data.length === 0) {
        layer.msg('请勾选一条数据进行删除');
        return;
    }
    layer.confirm('请确认要删除这' + data.length + '条数据吗？', {
            btn: ['确定', '取消']
        }, function () {
            var params = {
                data: data
            };
            postRequest(params, manages, "delete", function (data) {
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("删除成功");
                    pagingQuery();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            });
        }, function () {
            layer.closeAll();
        }
    );
}
function downBox(downs,method,id,selectId,mlSelection) {
    //document.getElementById(id).options.length = 0;
    postRequest(downs, "downBox", method, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                var ids = "#" + id;
                $(ids).prepend("<option value=''>请选择</option>");
                $.each(obj.data, function (i, n) {
                    if (method === moveBox) {
                        $(ids).append("<option value='" + n.zhaoshi_id + "'>" + n.name + "</option>");
                    } else if (method === kongFuBox) {
                        $(ids).append("<option value='" + n.kongfu_id + "'>" + n.name + "</option>");
                    } else if (method === effectBox) {
                        $(ids).append("<option value='" + n.effect_id + "'>" + n.name + "</option>");
                    } else if (method === schoolBox) {
                        $(ids).append("<option value='" + n.school_id + "'>" + n.name + "</option>");
                    }
                });
                mlSelection(selectId);
            }
        } else {
            layer.msg(data.msg, {icon: 2});
        }
    });
}
function mSelection(selectId){
    layui.formSelects.config(selectId,{direction:'down'});
}
function lSelection(selectId) {
    layui.form.render("select");
}
function cost(yellow,gold,green,blue,purple) {
    return yellow + "," + gold + "," + green + "," + blue + "," + purple;
}
function calAttr(level,num,elementId) {
    var attributes = Number(level) * Number(num);
    var id = "#" + elementId;
    $(id).val(isNull(attributes));
    return attributes;
}
function isDisabled(obj,min) {
    if(parseInt(obj) <= 0){
        min.attr('disabled',true);
    }else{
        min.attr('disabled',false);
    }
}