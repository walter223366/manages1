layui.use('table', function(){
    var table = layui.table;
    table.render({
        elem: '#accountId'
        ,url:'/demo/table/user/'
        ,cellMinWidth: 80
        ,cols: [[
            {field:'id', width:150, title: 'ID', sort: true}
            ,{field:'username', width:150, title: '用户账号'}
            ,{field:'source', width:150, title: '来源', sort: true}
            ,{field:'tellphone', width:150, title: '电话号码'}
            ,{field:'lrrq', width:150, title: '创建时间', sort: true}
            ,{field:'cancellation', width:150, title: '注销标志', sort: true}
            ,{field:'other', width:150, title: '操作'}
        ]]
    });
});

