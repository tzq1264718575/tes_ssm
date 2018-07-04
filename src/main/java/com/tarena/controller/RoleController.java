package com.tarena.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Role;
import com.tarena.service.RoleService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("role/")
public class RoleController {
	@Resource(name="roleService")
	private RoleService roleService;
	@RequestMapping(value="findRolesByPage",method=RequestMethod.GET)
	@ResponseBody
	public Result findRolesByPage(Page page){
		System.out.println(page.getCurrentPage()+"---"+page.getRoleKeyword());
		Result r=new Result();
		r=this.roleService.findRolesByPage(page);
		return r;
	}
	@RequestMapping(value="addRole/{roleName}",method=RequestMethod.POST)
	@ResponseBody
	public Result addRole(@PathVariable("roleName") String roleName) {
		Result r=null;
		r=this.roleService.addRole(roleName);
		return r;
	}
	@RequestMapping(value="updateRole",method=RequestMethod.POST)
	@ResponseBody
	public Result updateRole(Role role){
		System.out.println(role.getId()+"---"+role.getName());
		Result r=new Result();
		r=this.roleService.updateRole(role);
		return r;
	}
	@RequestMapping(value="deleteRole/{roleId}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteRole(@PathVariable("roleId") String roleId){
		System.out.println(roleId);
		Result r=new Result();
		r=this.roleService.deleteRole(roleId);
		return r;
	}
	@RequestMapping(value="findAllRoles",method=RequestMethod.GET)
	@ResponseBody
	public Result findAllRoles() {
		Result r=null;
		r=this.roleService.findAllRoles();
		return r;
	}
}
