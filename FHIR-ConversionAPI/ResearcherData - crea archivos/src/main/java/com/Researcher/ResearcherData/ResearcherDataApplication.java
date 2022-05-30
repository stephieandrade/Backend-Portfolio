package com.Researcher.ResearcherData;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.i18n.HapiLocalizer;
import ca.uhn.fhir.util.HapiExtensions;
import com.Researcher.ResearcherData.adapter.AdapterImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootApplication
public class ResearcherDataApplication{


	public static void main(String[] args) throws Exception {


		SpringApplication.run(ResearcherDataApplication.class, args);

		AdapterImpl adapter = new AdapterImpl(); // Instance of Adapter

		ValidateFHIR validateFHIR = new ValidateFHIR(); // Instance of Validation Service

		/** Validation JSON format **/
		String json = validateFHIR.validate(adapter.JSONconverter("src/main/resources/static/DataJSON.json"));


		/** Validation CSV format **/
		//String json = validateFHIR.validate(adapter.CSVconverter("src/main/resources/static/DataCSV.csv"));

		try (PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/static/DataJSON2.json"))) {
			out.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}




	}








}
