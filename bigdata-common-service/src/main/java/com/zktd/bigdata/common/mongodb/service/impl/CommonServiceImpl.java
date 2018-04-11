package com.zktd.bigdata.common.mongodb.service.impl;

import com.zktd.bigdata.common.mongodb.service.CommonService;
import com.zktd.bigdata.common.mongodb.utils.ReflectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

public class CommonServiceImpl<M extends MongoRepository<T, ID>, T, ID extends Serializable>
        implements CommonService<T, ID> {

	protected Class<T> entityClass;
	
    @Autowired
    protected M baseRepository;
    
    @Autowired
    protected  MongoTemplate mt;

    @Override
    public <S extends T> S save(@RequestBody S var1) {
        return baseRepository.save(var1);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public T findOneById(@RequestParam ID id) {
        return baseRepository.findOne(id);
    }

    @Override
    public T findOneByExample(T t) {
        Example<T> example = Example.of(t);
        return baseRepository.findOne(example);
    }

    @Override
    public List<T> findByExample(@RequestBody T t) {
        Example<T> example = Example.of(t);
        return baseRepository.findAll(example);
    }

    @Override
    public void delete(@RequestParam ID id) {
        baseRepository.delete(id);
    }

    @Override
    public void delete(@RequestBody T var1) {
        baseRepository.delete(var1);
    }

    public CommonServiceImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass(),1);
	}
    
    @Override
    public Page<T> searchPage(@RequestParam("query") final Query query,@RequestParam("pageNumber")  final int pageNumber, @RequestParam("pageSize")  final int pageSize, @RequestParam("sort")  final Sort sort){
    	//PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		
		//PageRequest 是Pageable的实现  ,,,PageImpl<T> 是Page的实现
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sort);
		query.with(sort);
		long total = mt.count(query, entityClass);
	 
		List<T> list = mt.find(query.with(pageRequest), entityClass);
		
		return new PageImpl<T>(list,pageRequest,total);
	}
    @Override
    public List<T> search(@RequestParam("query")  final Query query,@RequestParam("sort")  final Sort sort){
	    query.with(sort);
		List<T> list = mt.find(query, entityClass);
		return list;
	}


}
