package com.instant.message.service;

import java.util.List;
import java.util.Map;

public interface IBaseService<T,K> {

    boolean insert(T object);
    boolean delete(K id);
    boolean update(T object);
    List<T> toList(Map<String, Object> map);
    T selectByPrimaryKey(K id);
    T selectByOnly(Map<String,Object> map);

}