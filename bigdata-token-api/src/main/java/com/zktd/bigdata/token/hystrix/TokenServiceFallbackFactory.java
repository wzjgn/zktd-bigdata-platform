package com.zktd.bigdata.token.hystrix;

import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.service.TokenService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class TokenServiceFallbackFactory implements FallbackFactory<TokenService> {
 
  @Override
  public TokenService create(Throwable cause) {
    return new TokenService() {
      @Override
      public String test(@RequestBody User user){

        return "fallback"+cause.getMessage();
      }

      @Override
      public ResponseBean chekcToken(@RequestParam("token") String token){
        return null;
      }

    };
  }
}