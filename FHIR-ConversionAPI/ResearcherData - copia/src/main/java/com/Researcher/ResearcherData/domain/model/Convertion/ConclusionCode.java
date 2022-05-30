package com.Researcher.ResearcherData.domain.model.Convertion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class ConclusionCode {
    private Set<Coding> coding;
}
