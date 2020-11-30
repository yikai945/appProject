package com.gdkjxy.service;

import com.gdkjxy.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryService {

    /**
     * 查找所有APP_STATUS
     * @return
     */
    List<DataDictionary> findAllAppStatus();

    /**
     * 查找所有APP_FLATFORM
     * @return
     */
    List<DataDictionary> findAllAppFlatform();
}
