package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.vo.Page;

public interface RoleMapper {
	//分页查询角色信息,带有模糊条件的
	public int getCount(Page page);
	public List<Role> getRolesByPage(Page page);
	public int addRole(Role role);
	
}
