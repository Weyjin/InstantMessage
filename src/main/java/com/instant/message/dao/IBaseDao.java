package com.instant.message.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T,K> {

    int insert(T object);
    int delete(K id);
    int update(T object);
    List<T> toList(Map<String, Object> map);
    T selectByPrimaryKey(K id);
    T selectByOnly(Map<String,Object> map);
}
