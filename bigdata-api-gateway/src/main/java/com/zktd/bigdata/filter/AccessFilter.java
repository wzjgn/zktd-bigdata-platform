package com.zktd.bigdata.filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.ValueToken;

import com.netflix.infix.lang.infix.antlr.EventFilterParser.null_predicate_return;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zktd.bigdata.common.util.ResponseBean;
import com.zktd.bigdata.token.service.TokenService;

@Service
public class AccessFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private  FilterConfig filterConfig;
    
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
    	
    	String ignoresParam = filterConfig.getIgnores();
    	
    	String[] ignoreArray = ignoresParam.split(",");  
    	
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        
        log.info("=========="+String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        
        Object token = request.getHeader("token");
        if(token==null || token.equals("null")){
        	token = null;
        }
        
        boolean flag=false;
        
        for(int i=0;i<ignoreArray.length;i++){
        	
        	if(request.getRequestURL().toString().contains(ignoreArray[i])){
        		flag = true;
            }
        }
        
          
//        if(!request.getRequestURL().toString().contains("addUser")&&!request.getRequestURL().toString().contains("login")&&
//        		!request.getRequestURL().toString().contains("logout")&&token == null) {
        
        
        if(!flag&&token == null){
        	
            log.info("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("token is empty");
            return null;
            
        }else if(!flag&&token != null){
        	
        	ResponseBean responseBean=null;
			try {
				responseBean = tokenService.chekcToken(token.toString());
				
				if(!responseBean.getMsg().equals("success")){
					
					log.info("filter ========="+responseBean.getMsg());
					  ctx.setSendZuulResponse(false);
			          ctx.setResponseStatusCode(401);
			          ctx.setResponseBody(responseBean.getMsg());
			          
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	log.info("chekcToken==="+responseBean.getMsg());
        	
        }
        log.info("access token ok");
        return null;
    }

}
