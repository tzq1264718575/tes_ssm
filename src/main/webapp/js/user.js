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
});
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
