package com.Researcher.ResearcherData.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.ValidationResult;
import com.Researcher.ResearcherData.domain.model.DiagnosticReport;
import com.Researcher.ResearcherData.domain.model.Received.JSONreceived;
import com.Researcher.ResearcherData.service.IGenericService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jdk.jshell.Diag;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.validation.ValidationEngine;
import org.hl7.fhir.validation.ValidationRecord;
import org.hl7.fhir.validation.ValidatorCli;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    IGenericService reportService;


    //create ObjectMapper instance
    ObjectMapper objectMapper = new ObjectMapper();

    private org.hl7.fhir.r4.model.DiagnosticReport diagnosticReport;

    /** Falta hacer **/
    @GetMapping("/")
    public ResponseEntity<List<DiagnosticReport>> getAllReports(){
        return ResponseEntity.ok().body(reportService.getList());
    }

    /** Falta hacer **/
    @PostMapping("/save")
    public String saveReport(@RequestBody DiagnosticReport report){
        reportService.save(report);
        return "Success!";
    }

    @GetMapping("/converter")
    public ResponseEntity<?> converter() throws IOException {
        List<JSONObject> jsonObjects = new ArrayList<>();
        //JSONreceived data = objectMapper.readValue(new File("src/main/resources/static/DataJSON.json"), JSONreceived.class);

        List<JSONreceived> jsoNreceiveds = objectMapper.readValue(new File("src/main/resources/static/DataJSON.json"), new TypeReference<List<JSONreceived>>(){});

        for ( JSONreceived data: jsoNreceiveds
             ) {



        org.hl7.fhir.r4.model.DiagnosticReport diagnosticReport = new org.hl7.fhir.r4.model.DiagnosticReport(); //Create FHIR diagnostic Report

        diagnosticReport.setImplicitRules("https://www.hl7.org/fhir/diagnosticreport.html"); //Add FHIR resource

        diagnosticReport.setLanguage("english"); //LANGUAGE

        diagnosticReport.setStatus(org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus.FINAL); //Established this as final result

        //diagnosticReport.setCode(new CodeableConcept().addCoding(new org.hl7.fhir.r4.model.Coding().setCode(data.getDifferentialDiagnosis().toString()))); //DIFFERENTIAL DIAGNOSIS - NEEDED

        CodeableConcept codeableConcept = new CodeableConcept();

        codeableConcept.addCoding(new org.hl7.fhir.r4.model.Coding().setDisplay("Differential Diagnosis").setCode("differential")); //DIFFERENTIAL DIAGNOSIS - NEEDED

        diagnosticReport.setCode(codeableConcept);

        if(data.getConfirmedDiagnosis().isEmpty()) {
            diagnosticReport.setConclusion("None"); //CONFIRMED DIAGNOSIS - NEEDED
        }else{
            diagnosticReport.setConclusion(data.getConfirmedDiagnosis());//CONFIRMED DIAGNOSIS - NEEDED
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

//        Reference referenceSubject = new Reference();
//        referenceSubject.setReference(data.getPatientId());
//        referenceSubject.se
//        referenceSubject.setId(data.getPatientId());
//        diagnosticReport.setSubject(referenceSubject); //PATIENID

        Reference reference = new Reference();
        reference.setReference(data.getTest_type());
        List<Reference> arrayList = new ArrayList<>();
        arrayList.add(reference);
        diagnosticReport.setSpecimen(arrayList); //TEST_TYPE - NEEDED


        FhirContext ctx = FhirContext.forR4();
        FhirValidator validator = ctx.newValidator();

        ValidationResult result = validator.validateWithResult(diagnosticReport);


        /** SERIALIZATION METHOD **/
        IParser parser = ctx.newJsonParser();

        parser.setPrettyPrint(true); // Indent the output

        String serialized = parser.encodeResourceToString(diagnosticReport); // Serialize it

        JSONObject json = new JSONObject(serialized);

        /** SHOW RESULTS VALIDATED **/
           if (result.isSuccessful()){

               jsonObjects.add(json);

            }else{

                //return ResponseEntity.ok(result.toString());
            }



        }



        return ResponseEntity.ok(jsonObjects.toString());

    }

//    @GetMapping("/withvalidation")
//    public String Validation() {
//
//
//
//        FhirContext ctx = FhirContext.forR4();
//
//// Ask the context for a validator
//        FhirValidator validator = ctx.newValidator();
//
//// Create a validation module and register it
//        IValidatorModule module = new FhirInstanceValidator(ctx);
//        validator.registerValidatorModule(module);
//
//// Pass a resource instance as input to be validated
//        Reference reference = new Reference();
//        reference.addChild("exome");
//
//
//
//        org.hl7.fhir.r4.model.DiagnosticReport resource = new org.hl7.fhir.r4.model.DiagnosticReport();
//        resource.setSpecimen(List.of(reference));
//        resource.setCode(new CodeableConcept().addCoding(new Coding().setCode("Gitelman's syndrome").setSystem("http://hl7.org/fhir/sid/icd-10").setDisplay("Gitelman's syndrome")));
//        ValidationResult result = validator.validateWithResult(resource);
//
//// The input can also be a raw string (this mechanism can
//// potentially catch syntax issues that would have been missed
//// otherwise, since the HAPI FHIR Parser is forgiving about
//// its input.
//        String resourceText = "<Patient.....>";
//        ValidationResult result2 = validator.validateWithResult(resourceText);
//
//// The result object now contains the validation results
//        for (SingleValidationMessage next : result.getMessages()) {
//            System.out.println(next.getLocationString() + " " + next.getMessage());
//        }





//        InputStream schemaAsStream = DiagnosticReport.class.getClassLoader().getResourceAsStream("model/fhir.schema.json");
//        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V6).getSchema(schemaAsStream);
//
//        ObjectMapper om = new ObjectMapper();
//        //om.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
//        JsonNode jsonNode = om.readTree(requestStr);
//
//        Set<ValidationMessage> errors = schema.validate(jsonNode);
//        String errorsCombined = "";
//        for (ValidationMessage error : errors) {
//            log.error("Validation Error: {}", error);
//            errorsCombined += error.toString() + "\n";
//        }
//
//        if (errors.size() > 0)
//            throw new RuntimeException("Please fix your json! " + errorsCombined);
//
//        DiagnosticReport request = om.readValue(requestStr, DiagnosticReport.class);
//        log.info("Return this request: {}", request);
//        return request;
//    }
//
//
//    /** Falta hacer **/
//    @DeleteMapping("/delete/{id}")
//    public String deleteReport(@PathVariable String id){
//
//        return "Success!";
//
//    }




}
