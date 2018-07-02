package com.tarena.service;

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

}
