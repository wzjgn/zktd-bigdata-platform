package com.zktd.bigdata.common.mongodb.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.zktd.bigdata.common.mongodb.dao.BaseDao;
import com.zktd.bigdata.common.mongodb.service.EntityService;
import com.zktd.bigdata.common.mongodb.utils.ReflectionUtils;



/**
 * 
 * 所有业务查询实现类的基类
 */
public abstract class EntityServiceImpl<T, PK extends Serializable, EntityDao extends BaseDao<T, PK>> implements
		EntityService<T, PK> {

	protected Class<T> entityClass;

	protected EntityDao entityDao;
	
    /** 
     * 注入mongodbTemplate 
     *  
     * @param mongoTemplate 
     */  
    protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);  
	
	
    /** 
     * spring mongodb　集成操作类　 
     */  
    protected MongoTemplate mt;

	@Override
	public T get(PK id) {

		return entityDao.findOne(id);
	}

	@Override
	public void delete(PK id) {
		entityDao.delete(id);
	}
	
	public void delete(T entity){
		entityDao.delete(entity);
	}

	@Override
	public void save(T t) {
		entityDao.save(t);
	}

	@Override
	public Iterable<T> getAll() {

		return entityDao.findAll();
	}

	public EntityServiceImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}
	
	
	public abstract void setEntityDao(EntityDao entityDao);

	@Override
	public void delete(Iterable<T> entities) {
		entityDao.delete(entities);
	}
	



	

	@Override
	public Page<T> search(Query query, int pageNumber, int pageSize, Sort sort) {
		//PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		
		//PageRequest 是Pageable的实现  ,,,PageImpl<T> 是Page的实现
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
		query.with(sort);
		long total = mt.count(query, entityClass);
		
		List<T> list = mt.find(query.with(pageRequest), entityClass);
		
		return new PageImpl<T>(list,pageRequest,total);
	}
	@Override
	public List<T> search(Query query, Sort sort) {
		query.with(sort);
		List<T> list = mt.find(query, entityClass);
		return list;
	}

	


	
	

}
