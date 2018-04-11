package com.zktd.bigdata.token.service;


import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * Created by ouburikou on 17/11/2.
 */
@Api(description = "Secret接口")
@Service
public interface SecretService {

    @ApiOperation(value = "secret", notes = "获取secret")
    public String secret(String userId);
     


}
