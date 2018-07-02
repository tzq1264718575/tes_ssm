package com.tarena.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.RoleMapper;
import com.tarena.service.RoleService;
@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	
}
