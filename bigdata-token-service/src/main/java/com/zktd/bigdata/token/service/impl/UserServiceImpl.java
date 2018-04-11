package com.zktd.bigdata.token.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zktd.bigdata.common.jpa.service.ResponseJson;
import com.zktd.bigdata.common.jpa.service.page.Pagination;
import com.zktd.bigdata.common.jpa.service.page.PaginationResult;
import com.zktd.bigdata.token.entity.User;
import com.zktd.bigdata.token.repository.UserRepository;
import com.zktd.bigdata.token.service.UserService;

import lombok.extern.slf4j.Slf4j;





/**
 * 
 * @author wzj
 *
 */

@RestController
@RefreshScope
@RequestMapping("/token/user")
@Slf4j
public class UserServiceImpl  implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    protected MongoTemplate mt;


    @Override
    public User  findUserByUserName(@RequestParam("userName") String  userName){

        ResponseJson responseJson = new ResponseJson();
        responseJson.setMsg("false");
        User user = userRepository.findByUserName(userName);
        return user;
        
    }

    
    @Override
    public User  findUserByUserId(@RequestParam("userId") String  userId){
        
        User user = userRepository.findOne(userId);
        return user;
        
    }

    
    @Override
    public List<User>  findUserList(User user){
    	 Query query=new Query();
    	  if(user.getUserName()!=null&&!user.getUserName().equals("")){
              query.addCriteria(Criteria.where("userName").is(user.getUserName()));
          }
    	  
    	 List<User> list = mt.find(query, User.class);
        return list;
    }
    
    
    @Override
    public void  save(User user){
         userRepository.save(user);
    }


     
    @RequestMapping(value = "/findAllByPage",method = RequestMethod.POST)
    public PaginationResult<User> findAllByPage(@RequestBody Pagination<User> pagination){
        Query query=new Query();
 
        List<String> properties = new ArrayList<String>();
        properties.add("id");
        Sort sort = new Sort(Sort.Direction.DESC, properties);

       
        String  extra_search = pagination.getExtra_search();//查询条件
        log.debug("extra_search="+extra_search);


        String paramArray[] = extra_search.split("&");
        Map map = new HashMap();
        for(int i =0;i<paramArray.length;i++){
            String name = paramArray[i].split("=")[0];
            if(paramArray[i].split("=").length==2){
                String value = paramArray[i].split("=")[1];
                map.put(name, value);
            }else{
                map.put(name, "");
            }
        }

        if(!map.get("status").equals("")){

            query.addCriteria(Criteria.where("status").is(new Integer(map.get("status").toString())));
        }

        if(!map.get("title").equals("")){

            // 以后修改成走全文检索

            query.addCriteria(Criteria.where("title").regex(map.get("title").toString()));
        }



        int pageNum= pagination.getPageNum();

        PageRequest pageRequest = new PageRequest(pageNum, pagination.getPageSize(), sort);
        query.with(sort);
        long total = mt.count(query, User.class);
        List<User> list = mt.find(query.with(pageRequest), User.class);

        
        Page<User> page =  new PageImpl<User>(list,pageRequest,total);

        PaginationResult<User> pa=new PaginationResult();

        pa.setData(page.getContent());
        pa.setRecordsFiltered(page.getTotalElements());
        pa.setRecordsTotal(page.getTotalElements());
        pa.setDraw(new Integer(pagination.getDraw()));
        return pa;
    }
}
