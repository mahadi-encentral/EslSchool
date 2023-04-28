package com.mamt4real.interfaces;

import java.util.List;

public interface CrudOperations<T> {
    long createOne(T data);
    List<T> getAll();
    T getOne(long id);
    T update(T data);
    void delete(T data);
}
