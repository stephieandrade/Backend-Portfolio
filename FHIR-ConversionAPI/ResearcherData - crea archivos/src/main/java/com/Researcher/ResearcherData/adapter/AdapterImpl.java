package com.Researcher.ResearcherData.adapter;

import com.Researcher.ResearcherData.domain.model.CSVreceived;
import com.Researcher.ResearcherData.domain.model.JSONreceived;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class AdapterImpl {

    ObjectMapper objectMapper = new ObjectMapper(); // Create ObjectMapper instance

    public List<String> resolveSNOMEDCode(String str){
        HashMap<String, Integer> codes = new HashMap<>();
        codes.put("gitelman syndrome", 707756004 );
        codes.put("IgA glomerulonephritis", 236407003 );
        codes.put("Gitelman's syndrome", 707756004 );
        codes.put("distal renal tubular acidosis", 236461000 );
        codes.put("familial nephrotic syndrome", 48796009 ); //Congenital nephrotic syndrome


        HashMap<Integer, String> codesCT = new HashMap<>();
        codesCT.put( 707756004, "Gitelman syndrome" );
        codesCT.put(236407003 , "IgA glomerulonephritis"  );
        codesCT.put(236461000, "Distal renal tubular acidosis" );
        codesCT.put( 48796009, "Congenital nephrotic syndrome"  );

        Integer getCode = codes.get(str);
        List<String> code = new ArrayList<>();
        if(codesCT.containsKey(getCode)){
            code.add(String.valueOf(getCode));
            code.add(codesCT.get(getCode));
        }else{
            code.add("Not found");
            code.add("Not found");
        }
        return code;



    }

    public String resolveLOINCCode(String str){
        HashMap<String, String> codes = new HashMap<>();
        codes.put("exome", "LP248469-1" );
        codes.put("whole exome", "LP248469-1" );
        codes.put("gene panel", "LP94241-4" );
        codes.put("panel", "LP94241-4" );
        codes.put("wgs", "LP248470-9" );


        HashMap<String, String>  codesCT = new HashMap<>();
        codesCT.put( "LP248469-1", "Whole exome sequence analysis " );
        codesCT.put("LP94241-4" , "Genetic analysis discrete result panel"  );
        codesCT.put("LP248470-9" , "Whole genome sequence analysis"  );

        String getCode = codes.get(str);
        String code = "";
        if(codesCT.containsKey(getCode)){
            code = codesCT.get(getCode);
        }else{
            code = "Not found";
        }
        return code;




    }

    public List<DiagnosticReport> CSVconverter(String filePath) throws IOException, ParseException {

        /** CSV to POJO Converter **/

        String delimiter = ",";
        List<CSVreceived> CSVreceived = new ArrayList<>();

        Scanner read = null;

        try {
            read = new Scanner(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = read.nextLine();

        while(read.hasNextLine()) {

            line = read.nextLine();

            String[] userData = line.split(delimiter, -1);

            CSVreceived.add(new CSVreceived(userData[0], userData[1],userData[2], userData[3], userData[4], userData[5], userData[6], userData[7]));
        }

        /** POJO to FHIR Format Converter **/

        List<DiagnosticReport> diagnosticReports = new ArrayList<>();

        for ( CSVreceived data: CSVreceived
        ) {


            org.hl7.fhir.r4.model.DiagnosticReport diagnosticReport = new org.hl7.fhir.r4.model.DiagnosticReport(); //Create FHIR diagnostic Report

            diagnosticReport.setImplicitRules("https://www.hl7.org/fhir/diagnosticreport.html"); //Add FHIR resource

            diagnosticReport.setLanguage("english"); //LANGUAGE



            //diagnosticReport.setCode(new CodeableConcept().addCoding(new org.hl7.fhir.r4.model.Coding().setCode(data.getDifferentialDiagnosis().toString()))); //DIFFERENTIAL DIAGNOSIS - NEEDED

            CodeableConcept codeableConcept = new CodeableConcept();

            List<String> CTcode = resolveSNOMEDCode(data.getFinal_diag());

            //codeableConcept.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay(CTcode.get(1)).setCode(CTcode.get(0)).setSystem("https://www.snomed.org/snomed-ct")); //DIFFERENTIAL DIAGNOSIS - NEEDED
            codeableConcept.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay("Differential Diagnosis").setSystem("http://snomed.info/sct")); //DIFFERENTIAL DIAGNOSIS - NEEDED

            //diagnosticReport.setConclusionCode()
            diagnosticReport.setCode(codeableConcept);

            CodeableConcept codeableConceptCategory = new CodeableConcept();
            List<CodeableConcept> codeableConceptsCategory = new ArrayList<>();
            codeableConceptsCategory.add(codeableConceptCategory);
            codeableConceptCategory.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay(CTcode.get(1)).setCode(CTcode.get(0)).setSystem("http://snomed.info/sct"));

            diagnosticReport.setCategory(codeableConceptsCategory);

            if (data.getFinal_diag().isEmpty()) {
                diagnosticReport.setConclusion("Not found"); //CONFIRMED DIAGNOSIS - NEEDED
                diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.UNKNOWN);
            } else {

                diagnosticReport.setConclusion(CTcode.get(1));//CONFIRMED DIAGNOSIS - NEEDED
                diagnosticReport.setStatus(org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus.FINAL); //Established this as final result
            }

            String[] formattedDate = data.getTest_date().split("/");

            for (int i = 0; i < formattedDate.length; i++) {
                if(formattedDate[i].length() < 2){
                    formattedDate[i] = "0" + formattedDate[i];
            }
            }

            String str = String.join("-", formattedDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");

            Date date = formatter.parse(str);

            diagnosticReport.setIssued(date); //TEST_DATE

            IdType idType = new IdType();
            idType.setValue("1");

        Reference referenceSubject = new Reference();
        referenceSubject.setId(data.getPat_id());
        diagnosticReport.setSubject(referenceSubject); //PATIENID

            Reference reference = new Reference();
            reference.setReference(data.getTest_type());
            List<Reference> arrayList = new ArrayList<>();
            arrayList.add(reference);
            diagnosticReport.setSpecimen(arrayList); //TEST_TYPE - NEEDED
            diagnosticReports.add(diagnosticReport);

        }

        return diagnosticReports;

    }

    public List<DiagnosticReport> JSONconverter(String pathFile) throws IOException {

        /** JSON to POJO Converter **/

        List<JSONreceived> jsoNreceiveds = objectMapper.readValue(new File(pathFile), new TypeReference<List<JSONreceived>>(){});

        /** POJO to FHIR Format Converter **/

        List<DiagnosticReport> diagnosticReports = new ArrayList<>();
        for ( JSONreceived data: jsoNreceiveds) {

            org.hl7.fhir.r4.model.DiagnosticReport diagnosticReport = new org.hl7.fhir.r4.model.DiagnosticReport(); //Create FHIR diagnostic Report

            diagnosticReport.setImplicitRules("https://www.hl7.org/fhir/diagnosticreport.html"); //Add FHIR resource

            diagnosticReport.setLanguage("english"); //LANGUAGE

            //diagnosticReport.setCode(new CodeableConcept().addCoding(new org.hl7.fhir.r4.model.Coding().setCode(data.getDifferentialDiagnosis().toString()))); //DIFFERENTIAL DIAGNOSIS - NEEDED

            List<String> CTcode = resolveSNOMEDCode(data.getConfirmedDiagnosis());

            CodeableConcept codeableConcept = new CodeableConcept();

            //codeableConcept.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay(CTcode.get(1)).setCode(CTcode.get(0)).setSystem("https://www.snomed.org/snomed-ct")); //DIFFERENTIAL DIAGNOSIS - NEEDED
            codeableConcept.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay("Differential Diagnosis").setSystem("http://snomed.info/sct")); //DIFFERENTIAL DIAGNOSIS - NEEDED

            //diagnosticReport.setConclusionCode()
            diagnosticReport.setCode(codeableConcept);


            CodeableConcept codeableConceptCategory = new CodeableConcept();
            List<CodeableConcept> codeableConceptsCategory = new ArrayList<>();
            codeableConceptsCategory.add(codeableConceptCategory);
            codeableConceptCategory.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay(CTcode.get(1)).setCode(CTcode.get(0)).setSystem("http://snomed.info/sct"));

            diagnosticReport.setCategory(codeableConceptsCategory);

            if (data.getConfirmedDiagnosis().isEmpty()) {
                diagnosticReport.setConclusion("Not found"); //CONFIRMED DIAGNOSIS - NEEDED
                diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.UNKNOWN);
            } else {

                diagnosticReport.setConclusion(CTcode.get(1));//CONFIRMED DIAGNOSIS - NEEDED
                diagnosticReport.setStatus(org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus.FINAL); //Established this as final result
            }

            diagnosticReport.setIssued(data.getTest_date()); //TEST_DATE

            IdType idType = new IdType();
            idType.setValue("1");

            //        Coding coding = new Coding();
//        coding.setCode("Genetics");
//        List<Coding> codings = new ArrayList<>();
//        CodeableConcept codeableConcept = new CodeableConcept();
//        codeableConcept.setCoding(codings);
//        List<CodeableConcept> codeableConcepts = new ArrayList<>();
//        codeableConcepts.add(codeableConcept);
//        diagnosticReport.setCategory(codeableConcepts);



            Reference referenceSubject = new Reference();
            referenceSubject.setId(data.getPatientId());
            diagnosticReport.setSubject(referenceSubject); //PATIENID

            String testCodeLOINC = resolveLOINCCode(data.getTest_type());

            Reference reference = new Reference();
            reference.setReference(testCodeLOINC);
            List<Reference> arrayList = new ArrayList<>();
            arrayList.add(reference);
            diagnosticReport.setSpecimen(arrayList); //TEST_TYPE - NEEDED




            diagnosticReports.add(diagnosticReport);

        }

        return diagnosticReports;

    }

}
