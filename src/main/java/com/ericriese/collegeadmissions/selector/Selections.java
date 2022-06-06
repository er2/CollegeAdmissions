package com.ericriese.collegeadmissions.selector;

import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;

import java.util.List;

public record Selections(
        List<ApplicantEvaluation> accepted,
        List<ApplicantEvaluation> waitlisted,
        List<ApplicantEvaluation> rejected
) {
}
