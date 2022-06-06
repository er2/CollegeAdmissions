package com.ericriese.collegeadmissions;

import com.ericriese.collegeadmissions.model.Applicant;
import com.ericriese.collegeadmissions.model.LegacyStatus;
import com.ericriese.collegeadmissions.statistics.ClassStatistics;
import com.ericriese.collegeadmissions.statistics.Analyzer;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnalyzerTests {
    @Test
    void testSatScores() {
        List<Applicant> applicants = List.of(
                new Applicant(1, 1600, LegacyStatus.NON_LEGACY, "check out my SAT score", 5),
                new Applicant(2, 1100, LegacyStatus.LEGACY, "I have a boat", 5),
                new Applicant(3, 1250, LegacyStatus.NON_LEGACY, "Let me tell you the story of how I saved a life.", 13)
        );
        Analyzer evaluator = new Analyzer();
        ClassStatistics classStatistics = evaluator.analyzeClass(applicants);
    }
}
