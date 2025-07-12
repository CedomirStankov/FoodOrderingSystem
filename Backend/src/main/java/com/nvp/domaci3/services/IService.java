package com.nvp.domaci3.services;

import com.nvp.domaci3.model.User;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    <S extends T> S save(S var1);

    Optional<T> findById(ID var1);

    List<T> findAll();

    void deleteById(ID var1);

    <S extends T> S update(Integer var1, S var2);
}