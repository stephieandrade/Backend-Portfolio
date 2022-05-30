package com.Researcher.ResearcherData.service.impl;

import com.Researcher.ResearcherData.domain.model.Convertion.Coding;
import com.Researcher.ResearcherData.domain.model.Convertion.ConclusionCode;
import com.Researcher.ResearcherData.domain.model.DiagnosticReport;
import com.Researcher.ResearcherData.repository.CodingRepository;
import com.Researcher.ResearcherData.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodingServiceImpl implements IGenericService<Coding, String> {

    @Autowired
    private CodingRepository codingRepository;

    @Override
    public List<Coding> getList() {
        return codingRepository.findAll();
    }

    @Override
    public Coding getById(String s) {
        return null;
    }

    @Override
    public void save(Coding coding) {
        codingRepository.save(coding);
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(Coding coding, String s) {

    }

    public void savelistCoding(){
        /** This is saved in database **/
        Coding coding = new Coding();
        coding.setCode("707756004");
        coding.setSystem("http://snomed.info/sct");
        coding.setCondition("Gitelman's syndrome");


        Coding coding2 = new Coding();
        coding2.setCode("83901003");
        coding2.setSystem("http://snomed.info/sct");
        coding2.setCondition("Sjögren's syndrome");

        Coding coding3 = new Coding();
        coding3.setCode("236461000");
        coding3.setSystem("http://snomed.info/sct");
        coding3.setCondition("Distal renal tubular acidosis");

        codingRepository.save(coding);
        codingRepository.save(coding2);
        codingRepository.save(coding3);


        //        Set<Coding> codings = new HashSet<>();
//        codings.add(coding);
//
//
//
//        ConclusionCode conclusionCodes = new ConclusionCode();
//        conclusionCodes.setCoding(codings);
//
//        Set<ConclusionCode> conclusionCodes1 = new HashSet<>();

    }
}
