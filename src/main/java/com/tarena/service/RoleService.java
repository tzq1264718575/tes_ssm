package com.tarena.service;

import com.tarena.entity.Role;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface RoleService {
	/**
	 * 分页查询的业务方法
	 * @param page currentPage roleKeyword
	 * @return Result对象
	 */
	public Result findRolesByPage(Page page);

	public Result addRole(String roleName);

	public Result updateRole(Role role);

	public Result deleteRole(String roleId);
	//查询所有信息,给用户的角色下拉框
	public Result findAllRoles();

}
