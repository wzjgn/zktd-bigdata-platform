package com.zktd.bigdata.token.service;


import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.hystrix.TokenServiceHystrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by ouburikou on 17/11/2.
 */
@Api(description = "token接口")
@FeignClient(name = "token-service",fallback = TokenServiceHystrix.class)

//@FeignClient(name = "token-service",fallbackFactory = TokenServiceFallbackFactory.class)

//@FeignClient(value = "token-service")

@RequestMapping("/token/api")
public interface  TokenService {

    @ApiOperation(value = "chekcToken", notes = "验证token")
    @RequestMapping(value = "/chekcToken", method = RequestMethod.POST)
      ResponseBean chekcToken(@RequestParam("token") String token);



    @ApiOperation(value = "test", notes = "test")
    @RequestMapping(value = "/test222", method = RequestMethod.POST)
      String test(@RequestBody User user);


}
