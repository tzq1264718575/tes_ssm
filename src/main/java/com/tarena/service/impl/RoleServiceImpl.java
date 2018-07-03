package com.tarena.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.RoleMapper;
import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.service.RoleService;
import com.tarena.util.PageUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	@Override
	public Result findRolesByPage(Page page) {
		Result r=new Result();
		//处理模糊关键字 
		String roleKw = page.getRoleKeyword().equals("undefined")?"%%":"%"+page.getRoleKeyword()+"%";
		page.setRoleKeyword(roleKw);
		page.setPageSize(pageUtil.getPageSize());
		System.out.println(page.getRoleKeyword()+"---"+page.getPageSize());
		//获取所有的记录数
		int totalCount=this.roleMapper.getCount(page);
		page.setTotalCount(totalCount);
		//计算总页数
		int totalPage=totalCount%page.getPageSize()==0?totalCount/page.getPageSize():totalCount/page.getPageSize()+1;
		page.setTotalPage(totalPage);
		//计算前一页
		if(page.getCurrentPage()==1){
			page.setPreviousPage(1);
		}else{
			page.setPreviousPage(page.getCurrentPage()-1);
		}
		//计算后一页
		if(page.getCurrentPage()==totalPage){
			page.setNextPage(totalPage);
		}else{
			page.setNextPage(page.getCurrentPage()+1);
		}
		//获取当前页数据
		List<Role> roles=this.roleMapper.getRolesByPage(page);
		page.setData(roles);
		//获取分组中超链接的个数
		page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		System.out.println(page);
		r.setStatus(1);
		r.setData(page);
		return r;
	}
	@Override
	public Result addRole(String roleName) {
		Result r=new Result();
		Role role=new Role();
		role.setId(UUIDUtil.getUUID());
		role.setName(roleName);
		System.out.println(roleName);
		int row=roleMapper.addRole(role);
		r.setStatus(1);
		r.setMessage("添加角色成功");
		return r;
	}
	@Override
	public Result updateRole(Role role) {
		Result r=new Result();
		int row=this.roleMapper.updateRole(role);
		if(row==1){
			r.setStatus(1);
		}else{
			r.setStatus(0);
		}
		return r;
	}
	@Override
	public Result deleteRole(String roleId) {
		Result r=new Result();
		int row=roleMapper.deleteRole(roleId);
		if(row==1){
			r.setStatus(1);
		}else{
			r.setStatus(0);
			r.setMessage("删除失败");
		}
		return r;
	}
	
}
