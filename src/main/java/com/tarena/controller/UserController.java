package com.tarena.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.service.UserService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("user/")
public class UserController {
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="login/{loginName}/{password}",method=RequestMethod.GET)
	@ResponseBody
	public Result login(@PathVariable("loginName") String loginName,@PathVariable("password") String password)throws Exception{
		System.out.println(loginName+"---- "+password);
		Result result=null;
		result=userService.login(loginName,password);
		return result;
	}
	@RequestMapping(value="findUsersBypage",method=RequestMethod.GET)
	@ResponseBody
	public Result findUsersBypage(Page page){
		Result result=new Result();
		result.setStatus(1);
		result=userService.findUsersBypage(page);
		return result;
	}
	@RequestMapping(value="newAddUser",method=RequestMethod.POST)
	@ResponseBody
	public Result addUser(User user,Role role,HttpServletResponse rep,HttpServletRequest req,
			MultipartFile fileName){
		Result result=new Result();
		userService.addUser(user,role.getId(),rep,req,fileName);
		return result;
	}
}










