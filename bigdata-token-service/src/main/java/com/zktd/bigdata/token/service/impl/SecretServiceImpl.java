package com.zktd.bigdata.token.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zktd.bigdata.token.service.SecretService;
import com.zktd.bigdata.token.service.UserService;
import com.zktd.bigdata.util.JwtConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private UserService userService;

	@Override
	public String secret(String userId) {
		
		String secret = null;
		
		if(userId!=null){
			
			secret = userService.findUserByUserId(userId).getPassword();
			
		}else{
			secret = jwtConfig.getSecret();
		}
		return secret;

	}
}
