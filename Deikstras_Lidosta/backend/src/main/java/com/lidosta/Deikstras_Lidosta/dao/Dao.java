package com.lidosta.Deikstras_Lidosta.dao;

import java.util.List;
import java.util.UUID;

public interface Dao <T> {
    T insert(T t);
    List<T> selectAll();
    T selectById(UUID id);
    T checkIfExist(T object);

}
