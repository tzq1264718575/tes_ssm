//@sourceURL=login.js
$(function(){
	console.log(basePath);
	//每次加载login.html,都要从cookie去除用户名,复制给输入框
	$("#inputName").val(getCookie("loginName"));
	$(".container form").submit(function(){
		return login();
		
	});
});
function login(){
	var loginName=$("#inputName").val();
	var password=$("#inputPassword").val();
	var remember=$(".container form input[type=checkbox]").get(0).checked;
	//根据页面的数据做异步请求
	$.ajax({
		url:basePath+"/user/login/"+loginName+"/"+password,
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==1){
				if(remember){
					addCookie("loginName",loginName,5);
				}
				//登录成功
				window.location.href="index.jsp";
			}else if(result.status==0){
				//登录失败
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
	return false;
}