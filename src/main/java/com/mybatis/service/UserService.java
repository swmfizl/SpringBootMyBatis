package com.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.dao.UserDao;
import com.mybatis.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	// 新增业务
	public int insert(User user) {
		return userDao.insert(user);
	}
	// 删除业务
	public int delete(int id) {
		return userDao.delete(id);
	}
	// 修改业务
	public int update(User user) {
		return userDao.update(user);
	}
	// 查询业务
	public List<User> select(){
		return userDao.select();
	}
}
