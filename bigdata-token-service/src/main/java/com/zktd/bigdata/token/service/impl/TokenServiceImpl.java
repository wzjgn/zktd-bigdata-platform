package com.zktd.bigdata.token.service.impl;

import com.zktd.bigdata.common.RedisUtil;
import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.service.SecretService;
import com.zktd.bigdata.token.service.TokenService;
import com.zktd.bigdata.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/token/api")
@Slf4j
public class TokenServiceImpl implements TokenService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private SecretService secretService;

	@Override
	public ResponseBean chekcToken(@RequestParam("token") String token) {

		String userId = JWTUtil.getUserId(token);
		String secret = secretService.secret(userId);
		String flag = JWTUtil.verify(token, userId, secret);

		if (RedisUtil.get(redisTemplate, token) == null) {

			return new ResponseBean(200, "RedisTokenExpired", null);
		} else {
			return new ResponseBean(200, flag, null);
		}

	}
	@Override
	public String test(@RequestBody User user){


		log.info("test=="+user.toString());
		return  user.toString();
	}
}
