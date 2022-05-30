package com.Researcher.ResearcherData.service;

import com.Researcher.ResearcherData.domain.model.DiagnosticReport;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, K> {
    List<T> getList();
    T getById(K k);
    void save(T t);
    void delete(K k);
    void update(T t, K k);



}
