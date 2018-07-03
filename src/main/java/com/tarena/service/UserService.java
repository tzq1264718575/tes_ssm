package com.tarena.service;

import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface UserService {
	public Result login(String loginName,String password);

	public Result findUsersBypage(Page page);
}
