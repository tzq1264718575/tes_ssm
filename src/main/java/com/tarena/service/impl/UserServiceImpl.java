package com.tarena.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.dao.UserMapper;
import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.entity.UserRole;
import com.tarena.service.UserService;
import com.tarena.util.CommonValue;
import com.tarena.util.PageUtil;
import com.tarena.util.PrintWriterUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.util.UploadUtil;
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
		List<User> users=null;
		int totalCount=0; 
		if(page.getRoleType().equals("%%%%")){
			totalCount=this.userMapper.getCountByAll(page);
		}else{
			totalCount=this.userMapper.getCountByRoleType(page);
		}
		page.setTotalCount(totalCount);
		int totalPage=totalCount%page.getPageSize()==0?(totalCount/page.getPageSize()):(totalCount/page.getPageSize()+1);
		page.setTotalPage(totalPage);
		int previousPage = page.getCurrentPage()==1?(page.getCurrentPage()):(page.getCurrentPage()-1);
		page.setPreviousPage(previousPage);
		int nextPage = page.getCurrentPage()==page.getTotalPage()?(page.getCurrentPage()):(page.getCurrentPage()+1);
		page.setNextPage(nextPage);
		List<Integer> fenYe_a_Num = pageUtil.getFenYe_a_Num(page.getCurrentPage(),page.getPageSize(),totalCount,page.getTotalPage());
		page.setaNum(fenYe_a_Num);
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
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addUser(User user, String roleId, HttpServletResponse rep, HttpServletRequest req,
			MultipartFile fileName) {
		//获取上传的服务器的路径
		String realPath = req.getServletContext().getRealPath("/head");
		File realPathFile=new File(realPath);
		if(!realPathFile.exists()){
			realPathFile.mkdirs();
		}
		String uuid=UUIDUtil.getUUID();
		String originalExtName=null;
		//判斷文件是否存在
		if(fileName==null||fileName.isEmpty()){
			PrintWriterUtil.printMessageToClient(rep, "请选择文件");
			user.setHead("default.png");
			return ;
		}else{
			//文件存在,获取文件中的相关信息
			String originalFilename = fileName.getOriginalFilename();//原始文件名
			String name = fileName.getName();//
			String contentType = fileName.getContentType();//内容类型
			long size = fileName.getSize();
			try {
				//处理文件类型
				if(!CommonValue.contentTypes.contains(contentType)){
					PrintWriterUtil.printMessageToClient(rep, "文件类型不匹配");
					return;
				}
				//处理文件大小
				if(size>4194304){
					PrintWriterUtil.printMessageToClient(rep, "文件应该小于4M");
					return;
				}
				//开始文件上传
				boolean flag=UploadUtil.uploadImage(fileName, uuid, true,64, realPath);
				if(flag==false){
					PrintWriterUtil.printMessageToClient(rep, "上传文件失败");
					return;
				}
				//上传成功
				originalExtName= originalFilename.substring(originalFilename.lastIndexOf("."));
				user.setHead(uuid+originalExtName);
				user.setId(uuid);
				//用户的数据进数据库
				this.userMapper.addUser(user);
				//构建use_role对象
				UserRole ur=new UserRole();
				ur.setUserId(uuid);
				ur.setRoleId(roleId);;
				//存储数据在user_role表
				this.userMapper.addUserRole(ur);
			} catch (Exception e) {
				e.printStackTrace();
				//异常了,要删除上传的图片
				File file=new File(realPath+File.separator+originalExtName);
				if(file.exists()){
					file.delete();
				}
				throw new RuntimeException(e);
			}
			
		}
		return ;
	}
}
