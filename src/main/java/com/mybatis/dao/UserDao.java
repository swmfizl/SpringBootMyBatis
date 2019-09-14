package com.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mybatis.entity.User;

@Mapper
public interface UserDao {
	// 新增
	int insert(User user);
	// 删除
	int delete(int id);
	// 修改
	int update(User user);
	// 查询
	List<User> select();
}
