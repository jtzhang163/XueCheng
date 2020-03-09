package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryRepository repository;

    public SysDictionary findByDType(String dType) {
        List<SysDictionary> list = repository.findByDType(dType);
        if (list == null || list.size() == 0) {
            ExceptionCast.cast(CommonCode.INVALIDPARAM);
        }
        return list.get(0);
    }
}
