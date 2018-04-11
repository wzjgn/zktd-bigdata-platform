package com.zktd.bigdata.common.jpa.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zktd.bigdata.common.jpa.service.CommonService;

public class CommonServiceImpl<M extends JpaRepository<T, ID>, T, ID extends Serializable>
        implements CommonService<T, ID> {

    @Autowired
    protected M baseRepository;

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
    public void delete(@RequestParam ID var1) {

        baseRepository.delete(var1);
    }

    @Override
    public void delete(@RequestBody T var1) {

        baseRepository.delete(var1);
    }


}
