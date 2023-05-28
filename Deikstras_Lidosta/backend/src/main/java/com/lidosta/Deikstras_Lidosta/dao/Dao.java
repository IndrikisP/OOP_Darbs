package com.lidosta.Deikstras_Lidosta.dao;

import java.util.List;

public interface Dao<T> {
    T insert(T t);

    List<T> selectAll();

    T checkIfExist(T object);

}
