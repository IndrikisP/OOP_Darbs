package com.lidosta.Deikstras_Lidosta.dao;

import java.util.List;
import java.util.UUID;

public interface Dao <T> {
    T insert(UUID id, T t);
    List<T> selectAll();

}
