package com.xuecheng.api.system;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="系统数据接口",description = "系统数据接口，提供课程的增、删、改、查")
public interface SysDictionaryControllerApi {
    @ApiOperation("根据类型编号查询数据字典")
    public SysDictionary findByDType(String dType);
}
