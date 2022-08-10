package com.project.firstproject.model.dao;

import com.project.firstproject.domain.Student;

import java.util.List;

public interface Dao<T> {

    //creat
    T save(T item);

    //read
    T getById(long id) throws Exception;
    List<T> getAll();

    //update
    T update(long id, T item);

    //delete
    T delete(long id) throws Exception;


}
