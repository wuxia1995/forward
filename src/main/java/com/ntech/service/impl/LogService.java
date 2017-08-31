package com.ntech.service.impl;

import com.ntech.dao.LogMapper;
import com.ntech.model.Log;
import com.ntech.model.LogExample;
import com.ntech.service.inf.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogService implements ILogService {
    @Autowired
    private LogMapper logMapper;

    public List<Log> findByName(String name) {
        LogExample example = new LogExample();
        example.createCriteria().andUserNameEqualTo(name);
        List<Log> logs= logMapper.selectByExample(example);
        return logs;
    }

    @Transactional
    public void add(Log log) {
        logMapper.insert(log);
    }
}
