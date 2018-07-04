//@ sourceURL=user.js
var userId;
var roleType="%%";//设置一个出是默认值
$(function(){
	console.log("load user");
	findUsers(1);//获取用户所有数据第一页
	$("#role_type button").click(function() {
		roleType=$(this).text();
		if(roleType=="全部"){
			roleType="%%";
		}
		findUsers(1);
	});
	//新增
	$("#updateUser li:eq(2) a").click(function(e) {
		e.preventDefault();
		findAllRoles();
	});
	$("#addUserPanel form").submit(function() {
		return addUser();
	});
	
});
//添加角色
function addUser() {
	//获取要添加的新数据
	var loginName=$("#inputEmail_addUser").val();
	var password1=$("#inputPassword_addUser").val();
	var password2=$("#inputPassword2_addUser").val();
	var nickName=$("#nickName_addUser").val();
	var age=$("#age_addUser").val();
	var roleId=$("#roleCategory").val();
	var sex=$("#sex_radio input[name='user-type_sex']").val();
	if(password1!=password2){
		return false;
	};
	if(age<1){
		return false;
	};
	//发送ajax异步请求
	$.ajaxFileUpload({
		url:basePath+"/user/newAddUser",
		secureuri:false,
		fileElementId:"addHeadPicture",//文件域的id
		type:"post",
		data:{"loginName":loginName,"password":password1,"nickName":nickName,"age":age,"sex":sex,"id":roleId},
		dataType:"text",
		success:function(data,status){
			//alert(data);
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE>",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}
//查询所有角色
function findAllRoles() {
	$.ajax({
		url:basePath+"/role/findAllRoles",
		type:"get",
		datatype:"json",
		success:function(r){
			if(r.status==1){
				$("#roleCategory").html("");
				$(r.data).each(function(index,role) {
					console.log(role.name);
					var option='<option value="'+role.id+'">'+role.name+'</option>';
					$("#roleCategory").append(option);
				});
			}else{
				alert(r.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}
function findUsers(currentPage) {
	//处理模糊关键字
	var userKeyword=$("#user_find").val();
	if(userKeyword==null||userKeyword==""){
		userKeyword="undefined";
	};
	$.ajax({
		url:basePath+"/user/findUsersBypage",
		type:"get",
		data:{"currentPage":currentPage,"userKeyword":userKeyword,"roleType":roleType},
		datatype:"json",
		success:function(r){
			if(r.status==1){
				//清空表格和分页条
				$("#user_t").html("");
				$("#user_ul").html("");
				var page=r.data;
				var users=page.data;
				$(users).each(function(index,user) {
					console.log(user);
					var roles = user.roles;
					var role="";
					$(roles).each(function(n,value) {
						role=role+value.name+",";
						
					});
					if(role==""){
						role="无角色";
					}else{
						role=role.substring(0,role.length-1);
					}
					var tr=' <tr>'+
		                '<td>'+(index+1)+'</td>'+
		                '<td>'+user.loginName+'</td>'+
		                '<td>'+user.nickName+'</td>'+
		                '<td>'+user.loginType+'</td>'+
		               '<td>'+user.score+'</td>'+
		               '<td>'+new Date(user.regDate).toLocaleDateString()+'</td>'+
		                '<td>'+user.isLock+'</td>'+
		                '<td>'+role+'</td>'+
		                '<td>'+
		                  '<a href="" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
		                  '<a href="" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
		                '</td>'+
		             '</tr>';
					$("#user_t").append(tr);
				});
				
				if(page.totalPage>1){
					var previous='<li>'+
			            		'<a href="javascript:findUsers('+page.previousPage+')" aria-label="Previous">'+
			            		'<span aria-hidden="true">&laquo;</span>'+
			            		'</a>'+
			            		'</li>';
					$("#user_ul").append(previous);
					$(page.aNum).each(function(n,value) {
						var middle=' <li><a href="javascript:findUsers('+value+')">'+value+'</a></li>';
						$("#user_ul").append(middle);
					});
					var next='<li>'+
			            	'<a href="javascript:findUsers('+page.nextPage+')" aria-label="Next">'+
			            	'<span aria-hidden="true">&raquo;</span>'+
			            	'</a>'+
			            	'</li>';
					$("#user_ul").append(next);
				}
			}else{
				alert("查询失败");
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}
