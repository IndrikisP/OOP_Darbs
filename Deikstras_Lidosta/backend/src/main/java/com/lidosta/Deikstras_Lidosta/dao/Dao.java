package com.lidosta.Deikstras_Lidosta.dao;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInfo;

import java.util.List;
import java.util.UUID;

public interface Dao<T> {
    T insert(T t);

    List<T> selectAll();

    T selectById(UUID id);

    T checkIfExist(T object);

    List<T> selectByIds(List<UUID> t);
    List<List<T>> selectPaths(List<List<UUID>> t);

    List<FlightsInfo> selectPaths2(List<FlightsInfo> t);

}
