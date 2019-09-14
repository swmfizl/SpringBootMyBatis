package com.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mybatis.entity.User;
import com.mybatis.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("insert")
	public int insert() {
		// 返回新增成功的行数
		return userService.insert(new User("钟力", "Java高级工程师"));
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public int delete() {
		// 返回删除成功的行数
		return userService.delete(1);
	}
	
	@ResponseBody
	@RequestMapping("update")
	public int update() {
		User user = new User("钟力", "Java架构师");
		user.setId(1);
		// 返回修改成功的行数
		return userService.update(user);
	}
	
	@ResponseBody
	@RequestMapping("select")
	public List<User> select() {
		return userService.select();
	}
}
