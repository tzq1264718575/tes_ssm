package com.tarena.dao;

import java.util.List;

import com.tarena.entity.User;
import com.tarena.vo.Page;

public interface UserMapper {
	public String login(User user);
	//分页查询带角色类型
	public int getCount(Page page);
	public List<User> findUsersBypage_roleName(Page page);
	public List<User> findUsersBypage_roleAll(Page page);
}
