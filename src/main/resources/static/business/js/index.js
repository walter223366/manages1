

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
			}
		});
	}else{
		alert("请传参数")
	}
}