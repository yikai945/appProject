package com.gdkjxy.service.impl;

import com.gdkjxy.mapper.DataDictionaryMapper;
import com.gdkjxy.pojo.DataDictionary;
import com.gdkjxy.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/28
 * @Version 1.0
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Autowired
    DataDictionaryMapper dataDictionaryMapper;

    @Override
    public List<DataDictionary> findAllAppStatus() {
        return dataDictionaryMapper.findAllAppStatus();
    }

    @Override
    public List<DataDictionary> findAllAppFlatform() {
        return dataDictionaryMapper.findAllAppFlatform();
    }
}
