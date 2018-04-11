package com.zktd.bigdata.token.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zktd.bigdata.token.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User,String > {


	public User findByUserName(String userName);

}