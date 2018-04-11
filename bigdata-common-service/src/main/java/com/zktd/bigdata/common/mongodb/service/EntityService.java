package com.zktd.bigdata.common.mongodb.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * 所有业务查询接口的基类
 */
public interface EntityService<T, PK extends Serializable> {

	/**
	 * 
	 * 根据ID 获取实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);

	/**
	 * 
	 * 根据主键删除实体对象
	 * 
	 * @param id
	 */
	public void delete(PK id);
	
	public void delete(Iterable<T> entities);
	/**
	 * 
	 * 根据主键删除实体对象
	 * 
	 * @param id
	 */
	public void delete(T entity);

	/**
	 * 
	 * 添加实体对象
	 * 
	 * @param t
	 * @return
	 */
	public void save(T t);

	/**
	 * 获取所有实体对象
	 */
	public Iterable<T> getAll();

	/**
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	Page<T> search(Query query, int pageNumber, int pageSize, Sort sort);
	List<T> search(Query query,Sort sort);

	//public List<T> findAll(Map<String, Object> searchParams);

	//public List<T> findAll(Map<String, Object> searchParams, Sort sort);

}
