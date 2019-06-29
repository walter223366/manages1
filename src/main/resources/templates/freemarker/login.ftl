<!DOCTYPE html>
<html lang="en">
<head>
    <title>登陆界面</title>
    <link rel="stylesheet"  href="${request.getContextPath()}/static/layui/css/layui.css" />
    <link rel="stylesheet"  href="${request.getContextPath()}/static/bootstrap-3.3.7/dist/css/bootstrap.min.css" />
    <link rel="stylesheet"  href="${request.getContextPath()}/static/bootstrap-3.3.7/dist/css/bootstrap-theme.min.css" />
    <link rel="stylesheet"  href="${request.getContextPath()}/static/rely/css/login.css" />
</head>
<body>

    <#--<div class="modal show" id="loginModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="text-center text-primary">登录</h1>
                </div>
            </div>
        </div>
    </div>-->
    <div class="modal-body" style="width:700px;height:270px;">
        <ul>
            <li style="margin-left: 30px;margin-top: 15px">账号
                <input id="load_name"type="text"  placeholder="请输入账号" style="width:200px;height:30px;margin-left: 30px"></li>
            <li style="margin-top: 30px;margin-left: 30px">密码
                <input id="load_pass"  type="password" placeholder="请输入密码" style="width:200px;height:30px;margin-left: 30px"></li>
        </ul>
    </div>
    <div class="modal-footer">
        <a href="#" style="margin-right: 300px" data-toggle="modal" data-target="#myAlterpass">忘记密码？</a>
        <button onclick="accountLoad()" class="btn btn-primary" style="width:100px;height:30px;">登录</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" style="width:100px;height:30px;">关闭</button>
    </div>

    <script type="text/javascript" src="${request.getContextPath()}/static/jquery/jquery-1.8.0.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/static/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/static/rely/js/login.js"></script>
</body>
</html>
