

function login() {
	var username = $("#username").val();
	var passworld = $("#password").val();
	if(username != "" && passworld != ""){
		$.ajax({
			type:"POST",
			url:"manages/user/userLoad",
			data:{
				"account":username,
				"pass":passworld
			},
			dataType : "json",
			success:function(data){
				var code = data.code;
				if(code == "0"){

				}
				if(data==true){//登陆成功
					$("#loadsub").append("<a style='font-size:12px;color:green;'>登陆成功</a>");
					$("#wecome").append("<a style='font-size:12px;color:gray;'>欢迎"+loadUserName+"进入徕卡官方旗舰店</a>")
				}else{
					$("#loadsub").append("<a style='font-size:12px;color:red;'>登陆失败,请重新登录</a>");
				}
			}
		});
	}else{
		alert("请传参数")
	}
}