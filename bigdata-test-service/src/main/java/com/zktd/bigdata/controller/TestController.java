package com.zktd.bigdata.controller;

import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



@RestController
@Slf4j
@RequestMapping("/testapi")
public class TestController {

	@Autowired
	private TokenService tokenService;






	/**
	 * test
	 * http://localhost:5555/api-test/testapi/test?username=222
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/test")
	@ResponseBody
	public ResponseBean test(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String username = httpServletRequest.getParameter("username");

		log.info("test====" + username);

		User user=new User();
		user.setUserName(username);

		String ss= tokenService.test(user);
		return new ResponseBean(200, ss, null);

	}

}
