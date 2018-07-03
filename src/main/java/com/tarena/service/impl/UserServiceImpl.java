package com.tarena.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.UserMapper;
import com.tarena.entity.User;
import com.tarena.service.UserService;
import com.tarena.util.PageUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource(name="userMapper")
	private UserMapper userMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	public Result login(String loginName,String password){
		Result result=new Result();
		User user=new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		String id= userMapper.login(user);
		if(id!=null){
			result.setMessage("登陆成功");
			result.setStatus(1);
			result.setData(user);
		}else{
			result.setStatus(0);
			result.setMessage("登录失败");
		}
		return result;
	}
	@Override
	public Result findUsersBypage(Page page) {
		//处理用户查询
		Result r=new Result();
		page.setPageSize(pageUtil.getPageSize());
		String userKw=page.getUserKeyword().equals("undefined")?"%%":"%"+page.getUserKeyword()+"%";
		page.setUserKeyword(userKw);
		int totalCount=this.userMapper.getCount(page);
		page.setTotalCount(totalCount);
		int totalPage=totalCount%page.getPageSize()==0?(totalCount/page.getPageSize()):(totalCount/page.getPageSize()+1);
		page.setTotalPage(totalPage);
		int previousPage = page.getCurrentPage()==1?(page.getCurrentPage()):(page.getCurrentPage()-1);
		page.setPreviousPage(previousPage);
		int nextPage = page.getCurrentPage()==page.getTotalPage()?(page.getCurrentPage()):(page.getCurrentPage()+1);
		page.setNextPage(nextPage);
		List<Integer> fenYe_a_Num = pageUtil.getFenYe_a_Num(page.getCurrentPage(),page.getPageSize(),totalCount,page.getTotalPage());
		page.setaNum(fenYe_a_Num);
		List<User> users=null;
		if(page.getRoleType().equals("%%%%")){
			users=this.userMapper.findUsersBypage_roleAll(page);
		}else{
			users=this.userMapper.findUsersBypage_roleName(page);
		}
		page.setData(users);
		r.setStatus(1);
		r.setData(page);
		return r;
	}
}
