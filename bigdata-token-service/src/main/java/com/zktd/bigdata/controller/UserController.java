package com.zktd.bigdata.controller;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zktd.bigdata.common.RedisUtil;
import com.zktd.bigdata.common.jpa.service.page.Pagination;
import com.zktd.bigdata.common.jpa.service.page.PaginationResult;
import com.zktd.bigdata.common.util.JsonUtil;
import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.service.SecretService;
import com.zktd.bigdata.token.service.UserService;
import com.zktd.bigdata.util.JWTUtil;
import com.zktd.bigdata.util.JwtConfig;

import lombok.extern.slf4j.Slf4j;



//@Controller
@RestController
@Slf4j
public class UserController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JwtConfig jwtConfig;
    
    @Autowired
    private UserService userService;
    @Autowired
    private SecretService secretService;
   

    /**
     * 登录，根据用户密码 生成token。
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean login(@RequestParam("username") String userName,
                              @RequestParam("password") String password) {
    	
    	
    	
    	password = new Md5Hash(password, "www",1024).toBase64();
    	
        User userBean = userService.findUserByUserName(userName);
        ResponseBean responseBean =  new ResponseBean();
        
        if (userBean ==null) {
        	responseBean= new ResponseBean(200,"false","not username");
		}else if (userBean.getPassword().equals(password)) {
			String userId= userBean.getId();
			
			String secret = secretService.secret(userId);
			
			String accessToken = JWTUtil.sign(userId, secret,new Long(jwtConfig.getAccessToken()));
			String refreshToken = JWTUtil.sign(userId, secret,new Long(jwtConfig.getRefreshToken()));
			
			 RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getAccessToken()));
			 RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getRefreshToken()));
			
			 
			responseBean=new ResponseBean(200, "success", accessToken+"__"+refreshToken);
        } else {
        	responseBean= new ResponseBean(200,"false","password wrong");
        }
       return  responseBean;
    }
    
   
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUser(@RequestBody User user) {
    	ResponseBean responseBean =  new ResponseBean();
    	try {
    		String  password = new Md5Hash(user.getPassword(), "www",1024).toBase64();
    		user.setPassword(password);
			userService.save(user);
			responseBean= new ResponseBean(200,"success",user);
		} catch (Exception e) {
			 log.error(e.getMessage());
		}
    	return  responseBean;
    }
    
    /**
	 * @desc  获取用户列表分页
	 * @author wangzhenjiang
	 * @date 2017-12-12 11:03
	 * @param [pagination]
	 * @return java.lang.String
	 */
	@RequestMapping(value = "/user/findArticleListByPage", method = RequestMethod.POST)
	@ResponseBody
	String findArticleListByPage(String extra_search,String pageNum,String pageSize) {

		  Pagination<User> pagination =new Pagination();
		  pagination.setExtra_search(extra_search);
		  pagination.setPageNum(new Integer(pageNum)); //当前页（从 0 开始计数）
		  pagination.setPageSize(new Integer(pageSize));//每页数量
		  PaginationResult<User> pageResonse = userService.findAllByPage(pagination);

		 
		return JsonUtil.toJsonStr(pageResonse);
	}
    
	
	/**
	 * @desc  获取用户列表
	 * @author wangzhenjiang
	 * @date 2017-12-12 11:03
	 * @param [pagination]
	 * @return java.lang.String
	 */
	
	@RequestMapping(value = "/user/findUserList", method = RequestMethod.POST)
	@ResponseBody
	List<User> findArticleList(@RequestBody User user) {

		List <User> list = userService.findUserList(user);
		 
		return list;
	}
	
    
	 
	
	
}
