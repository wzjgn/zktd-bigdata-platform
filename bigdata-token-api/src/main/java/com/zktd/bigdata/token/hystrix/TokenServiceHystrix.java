package com.zktd.bigdata.token.hystrix;

import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.service.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangxinwei
 * @desc
 * @company 北京鑫为科技
 * @create 2018-12-10 00:12
 */
@Component
@RequestMapping("fallback")
public class TokenServiceHystrix implements TokenService {


    @Override
    public String test(@RequestBody User user){

        return "fallback";
    }

    @Override
    public ResponseBean chekcToken(@RequestParam("token") String token){
        return null;
    }

}
