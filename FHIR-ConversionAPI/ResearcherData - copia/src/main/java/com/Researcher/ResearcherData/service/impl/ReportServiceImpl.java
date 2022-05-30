package com.Researcher.ResearcherData.service.impl;

import com.Researcher.ResearcherData.domain.model.DiagnosticReport;
import com.Researcher.ResearcherData.repository.ReportRepository;
import com.Researcher.ResearcherData.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements IGenericService<DiagnosticReport, String> {

    @Autowired
    private ReportRepository repository;

    @Override
    public List<DiagnosticReport> getList() {
        return repository.findAll();
    }

    @Override
    public DiagnosticReport getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(DiagnosticReport report) {
        repository.save(report);
    }

    @Override
    public void delete(String id) {
        DiagnosticReport reportFound = repository.findById(id).orElse(null);
        repository.delete(reportFound);
    }
    @Override
    public void update(DiagnosticReport report, String id) {
    }

}
