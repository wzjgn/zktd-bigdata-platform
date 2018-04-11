package com.zktd.bigdata.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.classfile.ElementValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zktd.bigdata.common.RedisUtil;
import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.service.SecretService;
import com.zktd.bigdata.util.JWTUtil;
import com.zktd.bigdata.util.JwtConfig;

import lombok.extern.slf4j.Slf4j;

//@Controller
@RestController
@Slf4j
public class TokenController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private SecretService secretService;

	/**
	 * test
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/test")
	@ResponseBody
	public ResponseBean test(ServletRequest request, ServletResponse response) {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader("token");

		log.info("token====" + token);

		return new ResponseBean(200, "testtesttesttesttest", null);

	}

	/**
	 * 根据refreshToken 创建 accessToken,refreshToken
	 */
	@GetMapping("/refreshToken")
	public ResponseBean refreshToken(ServletRequest request) {

		try {
			log.info("token-service-------refreshToken");

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String refreshToken = httpServletRequest.getHeader("token");
			String accessToken = httpServletRequest.getHeader("accessToken");

			RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getExp()));
			RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getExp()));

			String userId = JWTUtil.getUserId(refreshToken);
			String secret = secretService.secret(userId);

			accessToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getAccessToken()));
			refreshToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getRefreshToken()));

			RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getAccessToken()));
			RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getRefreshToken()));

			return new ResponseBean(200, "success", accessToken + "__" + refreshToken);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
			return new ResponseBean(200, "error", null);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean logout(@RequestParam("accessToken") String accessToken,
			@RequestParam("refreshToken") String refreshToken) {

		/**
		 * 因为token一旦生成，无法让其过期。当主动刷新，更新token时，将旧的token要做失效处理。
		 * 因为存在并发请求，所以不能立即失效，要延后几秒失效。
		 */

		RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getExp()));
		RedisUtil.set(redisTemplate, refreshToken, refreshToken, new Long(jwtConfig.getExp()));

		ResponseBean responseBean = new ResponseBean();
		responseBean = new ResponseBean(200, "success", "");

		return responseBean;
	}

}
