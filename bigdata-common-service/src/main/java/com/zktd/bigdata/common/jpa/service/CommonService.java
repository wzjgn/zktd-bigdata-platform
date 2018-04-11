package com.zktd.bigdata.common.jpa.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

public interface CommonService<T, ID extends Serializable> {
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    <S extends T> S save(S var1);

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    List<T> findAll();

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    T findOneById(ID id);

    @RequestMapping(value = "/findOneByExample", method = RequestMethod.GET)
    T findOneByExample(T t);

    @RequestMapping(value = "/findByExample", method = RequestMethod.GET)
    List<T> findByExample(T t);

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    void delete(ID var1);

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    void delete(T var1);

}
