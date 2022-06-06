package com.ericriese.collegeadmissions.statistics;

public record ApplicantEvaluation(
        int id,
        double sat,
        double essay,
        double overall
) {
}
