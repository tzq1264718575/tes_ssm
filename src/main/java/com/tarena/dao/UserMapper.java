package com.tarena.dao;

import java.util.List;

import com.tarena.entity.User;
import com.tarena.entity.UserRole;
import com.tarena.vo.Page;

public interface UserMapper {
	public String login(User user);
	//分页查询带角色类型
	public int getCountByRoleType(Page page);
	public int getCountByAll(Page page);
	public List<User> findUsersBypage_roleName(Page page);
	public List<User> findUsersBypage_roleAll(Page page);
	public void addUser(User user);
	public void addUserRole(UserRole ur);
	//查询所有用户数据,用于导出excel文件
	public List<User> findUsers();
	//根据用户名查询用户信息
	public User findUserByUserName(String username);
}
