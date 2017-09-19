package com.ntech.service.inf;

import com.ntech.model.Log;

import java.util.List;

public interface ILogService {
    //根据姓名查询日志
    public List<Log> findByName(String name);
    //添加日志
    public void add(Log info);
    List<Log> findAll();

    long totalCount();
//    List<Log> findPage(int limit,int offset);
}
