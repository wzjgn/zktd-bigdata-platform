package com.zktd.bigdata.token.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zktd.bigdata.common.jpa.service.page.Pagination;
import com.zktd.bigdata.common.jpa.service.page.PaginationResult;
import com.zktd.bigdata.token.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "user接口")
@FeignClient(value = "token-service")
@RequestMapping("/token/user")
public interface UserService {

    
	@ApiOperation(value = "findUserByUserName", notes = "通过用户名获取用户信息")
    @RequestMapping(value = "/findUserByUserName", method = RequestMethod.POST)
    public User findUserByUserName(String userName) ;
	
	@ApiOperation(value = "findUserByUserId", notes = "通过用户id获取用户信息")
    @RequestMapping(value = "/findUserByUserId", method = RequestMethod.POST)
    public User findUserByUserId(String userId) ;
	
	
	@ApiOperation(value = "save", notes = "添加用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(User user) ;
	
	/**
	 * 用户列表
	 * @return
	 */
	@ApiOperation(value="findAllByPage",notes="获取用户分页列表")
	@RequestMapping(value = "/findAllByPage",method = RequestMethod.POST)
	PaginationResult<User> findAllByPage(@RequestBody Pagination<User> pagination);
	
	@ApiOperation(value = "findUserList", notes = "获取用户列表")
    @RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	public List<User>  findUserList(User user);
	
	
	
}
