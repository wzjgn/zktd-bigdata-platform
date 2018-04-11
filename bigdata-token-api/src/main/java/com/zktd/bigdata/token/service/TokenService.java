package com.zktd.bigdata.token.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zktd.bigdata.common.util.ResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * Created by ouburikou on 17/11/2.
 */
@Api(description = "token接口")
@FeignClient(value = "token-service")
@RequestMapping("/token/api")
public interface TokenService {

    @ApiOperation(value = "chekcToken", notes = "验证token")
    @RequestMapping(value = "/chekcToken", method = RequestMethod.POST)
    public ResponseBean chekcToken(@RequestParam("token") String token);
     


}
