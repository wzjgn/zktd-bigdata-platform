package com.zktd.bigdata.common.mongodb.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface CommonService<T, ID extends Serializable> {
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    <S extends T> S save(S var1);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<T> findAll();

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    T findOneById(ID id);

    @RequestMapping(value = "/findOneByExample", method = RequestMethod.GET)
    T findOneByExample(T t);

    @RequestMapping(value = "/findByExample", method = RequestMethod.GET)
    List<T> findByExample(T t);

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    void delete( ID id);

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    void delete(T var1);
    /**
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
     * 
	 * @return
	 */
    @RequestMapping(value = "/searchPage", method = RequestMethod.POST)
	public Page<T> searchPage(@RequestParam("query") final Query query,@RequestParam("pageNumber")  final int pageNumber, @RequestParam("pageSize")  final int pageSize, @RequestParam("sort")  final Sort sort);
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<T> search(@RequestParam("query")  final Query query,@RequestParam("sort")  final Sort sort);
}
