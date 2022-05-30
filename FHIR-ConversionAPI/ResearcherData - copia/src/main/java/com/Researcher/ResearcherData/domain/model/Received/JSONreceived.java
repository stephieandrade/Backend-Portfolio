package com.Researcher.ResearcherData.domain.model.Received;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl7.fhir.r4.model.Coding;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONreceived {

    private String patientId;
    private List<String> differentialDiagnosis;
    private String confirmedDiagnosis;
    private String test_type;
    private Date test_date;

}
