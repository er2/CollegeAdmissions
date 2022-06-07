package com.ericriese.collegeadmissions.statistics;

import lombok.Builder;

@Builder
public record ApplicantEvaluation(
        int id,
        double sat,
        double essay,
        double overall
) {
}
