package com.ericriese.collegeadmissions.selector;

import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import lombok.Builder;

import java.util.List;

@Builder
public record Selections(
        List<ApplicantEvaluation> accepted,
        List<ApplicantEvaluation> waitlisted,
        List<ApplicantEvaluation> rejected
) {
}
