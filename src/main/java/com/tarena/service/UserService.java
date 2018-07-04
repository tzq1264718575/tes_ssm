package com.tarena.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface UserService {
	public Result login(String loginName,String password);

	public Result findUsersBypage(Page page);


	public void addUser(User user, String roleId, HttpServletResponse rep, HttpServletRequest req, MultipartFile fileName);
}
