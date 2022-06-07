package com.ericriese.collegeadmissions.selector;

import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import com.ericriese.collegeadmissions.statistics.ClassStatistics;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectorImpl implements Selector {

    private final double minimumOverallScore;

    public SelectorImpl(@Value("${minimumOverallScore}") double minimumOverallScore) {
        this.minimumOverallScore = minimumOverallScore;
    }

    @Override
    public Selections select(ClassStatistics classStatistics, List<ApplicantEvaluation> applicants) {
        Map<Boolean, List<ApplicantEvaluation>> byOverall = applicants.stream()
                .collect(Collectors.partitioningBy(applicant -> applicant.overall() >= minimumOverallScore));
        return Selections.builder()
                .accepted(byOverall.getOrDefault(true, List.of()))
                .rejected(byOverall.getOrDefault(false, List.of()))
                .build();
    }
}
