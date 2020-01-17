#### 一、环境准备

- 开发工具：STS
- JDK版本：1.8
- SpringBoot版本：2.1.8
- 数据库：MySQL 8.0.17



#### 二、数据库准备

数据库在此处不过多讨论，按以下描述新建数据库即可

- 新建数据库local
- 新建数据表user，包含主键id{int}、字段name{varchar}、字段prosession{varchar}



#### 三、项目搭建

项目搭建的完整思路如下

- 3.1 新建SpringBoot项目，并加入Spring Web、MySQL Driver和MyBatis Framework依赖
- 3.2 完善项目目录结构
- 3.3 配置application.properties文件的数据库连接配置和MyBatis配置
- 3.4 启动项目，演示增删改查



##### 3.1 新建SpringBoot项目，并加入Spring Web、MySQL Driver和MyBatis Framework依赖

- 新建项目

<img src="https://img-blog.csdnimg.cn/20190915154154438.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">

- 项目pom.xml文件配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.8.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.mybatis</groupId>
	<artifactId>Mybatis</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Mybatis</name>
	<description>Spring Boot Mybatis</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.1.0</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```



##### 3.2 完善项目目录结构

- entity层：实体层，存放与数据库对应的实体类
- dao层：数据层，实现实体的增删改查
- service层：服务层，进行具体的业务操作
- controller层：控制层，处理外部请求
- classpath:mapper：存放MyBatis映射文件

<img src="https://img-blog.csdnimg.cn/20190915163027489.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">


###### 3.2.1 entity层新建User类，对应数据库中的User表

```java
package com.mybatis.entity;

public class User {
	private int id;
	private String name;
	private String profession;

	public User() {

	}

	public User(String name, String profession) {
		this.name = name;
		this.profession = profession;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
}
```



###### 3.2.2 dao层新建UserDao接口，映射classpath:mapper/UserMapper.xml文件

```java
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
```



###### 3.2.3 service层新建UserService类，实现数据库增删改查业务

```java
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
```



###### 3.2.4 controller层新增UserController类，处理请求

```java
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
```



###### 3.2.5 classpath:mapper新增UserMapper.xml文件，映射dao层新建UserDao接口

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.mybatis.dao.UserDao">
	<insert id="insert" parameterType="User">
		insert into user (name, profession) values (#{name}, #{profession})
	</insert>
	<delete id="delete">
		delete from user where id = #{id}
	</delete>
	<update id="update" parameterType="User">
		update user set name = #{name}, profession = #{profession} where id = #{id}
	</update>
	<select id="select" resultType="User">
		SELECT * FROM user
	</select>
</mapper>
```



##### 3.3 配置application.properties文件的数据库连接配置和MyBatis配置

```xml
spring.datasource.url=jdbc:mysql://localhost:3306/local?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=zl0418
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.mybatis.entity
```



##### 3.4 启动项目，演示增删改查



###### 3.4.1 演示新增-返回新增行数1，新增成功

<img src="https://img-blog.csdnimg.cn/20190915154245595.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">



###### 3.4.2 查询演示-成功返回刚刚新增的数据，查询成功

<img src="https://img-blog.csdnimg.cn/20190915154255998.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">



###### 3.4.3 修改演示-返回修改的行数1，修改成功

<img src="https://img-blog.csdnimg.cn/20190915154304151.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">



###### 3.4.4 删除演示-返回删除行数1，删除成功

<img src="https://img-blog.csdnimg.cn/20190915154311808.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N3bWZpemw=,size_16,color_FFFFFF,t_70">

#### 四、Github 地址

<a href="https://github.com/swmfizl/SpringBootMyBatis" style="text-decoration: none">SpringBootMyBatis</a>