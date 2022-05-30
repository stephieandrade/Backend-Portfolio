package com.Researcher.ResearcherData.repository;

import com.Researcher.ResearcherData.domain.model.Convertion.Coding;
import com.Researcher.ResearcherData.domain.model.DiagnosticReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodingRepository extends MongoRepository<Coding, String> {
}
