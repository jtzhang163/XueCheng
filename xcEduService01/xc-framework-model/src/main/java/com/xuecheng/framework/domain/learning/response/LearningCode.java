package com.xuecheng.framework.domain.learning.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.ResultCode;

public enum LearningCode implements ResultCode {

    LEARNING_GETMEDIA_ERROR(false, 23001, "获取学习地址失败！"),
    LEARNING_GETMEDIA_ERROR2(false, 23002, "XXXXX失败！");


    boolean success;

    int code;
    //提示信息

    String message;

    private LearningCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private static final ImmutableMap<Integer, LearningCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, LearningCode> builder = ImmutableMap.builder();
        for (LearningCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
