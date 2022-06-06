package com.ericriese.collegeadmissions.model;

public record Applicant(
        int id,
        int satScore,
        LegacyStatus legacyStatus,
        String essay,
        int personalityScore
) {
}
