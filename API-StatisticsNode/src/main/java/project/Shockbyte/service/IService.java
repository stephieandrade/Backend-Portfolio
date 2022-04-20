package project.Shockbyte.service;

import project.Shockbyte.exceptions.BadRequestException;

import java.util.List;

public interface IService <T, K>{

    List<T> findAll();
    T findById(K k) throws BadRequestException;
    void save();
    T update(T t, K k) throws BadRequestException;
    void delete(K k);
    //void purgeData();

}
