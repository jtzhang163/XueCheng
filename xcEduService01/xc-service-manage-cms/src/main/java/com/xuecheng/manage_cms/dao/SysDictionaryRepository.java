package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    public List<SysDictionary> findByDType(String dType);
}
