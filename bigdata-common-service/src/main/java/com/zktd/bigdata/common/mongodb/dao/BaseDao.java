package com.zktd.bigdata.common.mongodb.dao;


import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T, pk extends Serializable> extends MongoRepository<T, Serializable> {

}
