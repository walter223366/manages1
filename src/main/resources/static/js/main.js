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
            $('.left-nav').animate({left: '-221px'}, 100);
            $('.page-content').animate({left: '0px'}, 100);
            $('.page-content-bg').hide();
        }else{
            $('.left-nav').animate({left: '0px'}, 100);
            $('.page-content').animate({left: '221px'}, 100);
            if($(window).width()<768){
                $('.page-content-bg').show();
            }
        }
    });
    $('.page-content-bg').click(function(event) {
        $('.left-nav').animate({left: '-221px'}, 100);
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
function isJSON(str) {
    try {
        JSON.parse(str);
        return true;
    } catch (e) {
        return false;
    }
}
function isNull(str) {
    if (str === null || str === "") {
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
function layerOpen(title,content,width,height,callback,Reset) {
    layer.open({
        type: 1,
        offset: "auto",
        id: 'layerDemo' + 'auto',
        area: [width + 'px', height + 'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        btn: ['立即提交', '重置'],
        shade: 0.4,
        title: title,
        content: content,
        yes: function () {
            if (typeof callback === "function") {
                callback();
            }
        },
        btn2: function (index, layero) {
            if (typeof Reset === "function") {
                Reset(index, layero);
            }
            return false;
        }
    });
}
function layerSeeOpen(title,content,width,height) {
    layer.open({
        type: 1,
        offset: "auto",
        id: 'layerDemo' + 'auto',
        area: [width + 'px', height + 'px'],
        fix: false,
        maxmin: true,
        shadeClose: true,
        btn: ['明白了'],
        shade: 0.4,
        title: title,
        content: content,
        yes: function () {
            content.hide();
            layer.closeAll();
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
function aTransfer(params,manages,pagingQuery) {
    layer.confirm('请确认要新增这条数据吗？', {
            btn: ['确定', '取消']
        }, function () {
            postRequest(params, manages, "insert", function (data) {
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("新增成功");
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
function eTransfer(params,manages,pagingQuery) {
    layer.confirm('请确认要修改这条数据吗？', {
            btn: ['确定', '取消']
        }, function () {
            postRequest(params, manages, "update", function (data) {
                if (data.code === "0" && data.result === "SUCCESS") {
                    layer.msg("修改成功");
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
function del(data,manages,Func) {
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
                    Func();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            });
        }, function () {
            layer.closeAll();
        }
    );
}
function downBox(method,id,selectId,mlSelection) {
    document.getElementById(id).options.length = 0;
    var params = {};
    postRequest(params, "downBox", method, function (data) {
        if (data.code === "0" && data.result === "SUCCESS") {
            var rows = $.base64.atob(data.rows, charset);
            if (isJSON(rows)) {
                var obj = JSON.parse(rows);
                $.each(obj.data, function (i, n) {
                    var ids = "#" + id;
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
function lSelection(selectId){
    layui.form.render("select");
}
url="/manages/sysAuth/server";
eQuery="editQuery"; sDetails="seeDetails"; moveBox="moveBox"; kongFuBox="kongFuBox"; effectBox="effectBox"; schoolBox="schoolBox";

function calAttr(level,num,elementId){
    var attributes = level*num;
    document.getElementById(elementId).value = isNull(attributes);
    return attributes;
}
function calAdd(obj,min){
    isDisabled(obj,min);
    obj = (Math.abs(parseInt(obj)) + 1);
    isDisabled(obj,min);
    return obj;
}
function calMin(obj,min){
    isDisabled(obj,min);
    obj = (Math.abs(parseInt(obj)) - 1);
    isDisabled(obj,min);
    return obj;
}
function isDisabled(obj,min) {
    if(parseInt(obj) <= 0){
        min.attr('disabled',true);
    }else{
        min.attr('disabled',false);
    }
}