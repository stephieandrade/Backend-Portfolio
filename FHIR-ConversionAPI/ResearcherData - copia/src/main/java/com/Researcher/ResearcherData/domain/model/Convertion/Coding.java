package com.Researcher.ResearcherData.domain.model.Convertion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/** Coding info to refer what type of study was made **/
@Getter
@Setter
@NoArgsConstructor
@Document
public class Coding {
    private String system;
    private String code;
    private String display;
    private String condition;
}
