package com.Researcher.ResearcherData.repository;

import com.Researcher.ResearcherData.domain.model.DiagnosticReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<DiagnosticReport, String> {

}
