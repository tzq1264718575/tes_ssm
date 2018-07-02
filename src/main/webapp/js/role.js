//@ sourceURL=role.js
$(function() {
	findRoles(1);//第一次执行时候要查询出第一页数据
	$("#ssuo").click(function() {
		findRoles(1);
	});
	//给新增角色框里面的确认按钮添加clinck事件
	$("#f").submit(function() {
		return addRole();
	});
});
function addRole() {
	//获取新角色数据
	var roleName=$("#roleName").val();
	$.ajax({
		url:basePath+"/role/addRole/"+roleName,
		type:"post",
		datatype:"json",
		success:function(r){
			if(r.status==1){
				alert(r.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
	return false;
};
function findRoles(currentPage) {
	//处理模糊关键
	var roleKeyword=$("#sousuo").val();
	if(roleKeyword==null||roleKeyword==""){
		roleKeyword="undefined";
	};
	$.ajax({
		url:basePath+"/role/findRolesByPage",
		type:"get",
		data:{"currentPage":currentPage,"roleKeyword":roleKeyword},
		success:function(r){
			if(r.status==1){
				//查询成功
				$("#role_table tbody").html("");
				$("#role_page").html("");
				//给表格添加数据
				var page=r.data;
				var roles=page.data;
				$(roles).each(function(n,value) {
					//n:循环到第几个了对象,是一个数字,从0开始
					//value是一个对象的,循环到的第n个对象
					if(value.name!='超级管理员'&&value.name!='学员'&&value.name!='讲师'){
						//需要加上超链接修改,删除
						var tr='<tr>'+
							  '<td>'+(n+1)+'</td>'+
				              '<td>'+value.id+'</td>'+
				              '<td>'+value.name+'</td>'+
				              '<td>'+
				                '<a href="" data-toggle="modal" data-target="#editRole" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
				                '<a href="" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
				              '</td>'+
				              '</tr>';
						$("#role_table tbody").append(tr);
					}else{
						//不需要加上超链接修改,删除
						var tr='<tr>'+
				               '<td>'+(n+1)+'</td>'+
				               '<td>'+value.id+'</td>'+
				               '<td>'+value.name+'</td>'+
				               '<td></td>'+
				               '</tr>';
						$("#role_table tbody").append(tr);
					}
				});
				//开始构建分页组件条
				if(page.totalPage>1){
					//大于一页才显示分页条
					//处理前一页
					var previous='<li>'+
					'<a href="javascript:findRoles('+page.previousPage+')" aria-label="Previous">'+
					'<span aria-hidden="true">&laquo;</span>'+
					'</a>'+
					'</li>';
					$("#role_page").append(previous);
					//处理中间的超链接
					$(page.aNum).each(function(n,value) {
						var middle='<li><a href="javascript:findRoles('+value+')">'+value+'</a></li>';
						$("#role_page").append(middle);
					});
					//处理后一页
					var next=' <li>'+
					'<a href="javascript:findRoles('+page.nextPage+')" aria-label="Next">'+
					'<span aria-hidden="true">&raquo;</span>'+
					'</a>'+
					'</li>';
					$("#role_page").append(next);
				}
			}else if(r.status){
				alert("查询失败");
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}

