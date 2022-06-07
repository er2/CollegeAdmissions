package com.ericriese.collegeadmissions;

import com.ericriese.collegeadmissions.selector.Selections;
import com.ericriese.collegeadmissions.selector.Selector;
import com.ericriese.collegeadmissions.selector.SelectorImpl;
import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import com.ericriese.collegeadmissions.statistics.ClassStatistics;
import com.ericriese.collegeadmissions.statistics.Distribution;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.condition.AllOf.allOf;
import static org.mockito.Mockito.mock;

public class SelectionsTests {

    @Test
    public void minimumOverallScore() {
        // Given
        List<ApplicantEvaluation> applicants = List.of(
                ApplicantEvaluation.builder().id(1).sat(5).essay(6).overall(7).build(),
                ApplicantEvaluation.builder().id(2).sat(1).essay(1).overall(1).build(),
                ApplicantEvaluation.builder().id(3).sat(9).essay(9).overall(9).build()
        );
        ClassStatistics stats = null;
        double minimumOverallScore = 5;
        Selector selector = new SelectorImpl(minimumOverallScore);

        // When
        Selections select = selector.select(stats, applicants);

        // Then
        new SelectionsAsserter(select)
                .accept(Map.of(
                        1, acceptee -> acceptee.has(minimumOverallScore(minimumOverallScore)),
                        3, acceptee -> acceptee.has(minimumOverallScore(minimumOverallScore))
                ))
                .reject(Map.of(
                        2, rejectee -> rejectee.doesNotHave(minimumOverallScore(minimumOverallScore))
                ))
                .assertAll();
    }

    Condition<ApplicantEvaluation> minimumOverallScore(double minOverallScore) {
        return new Condition<>((eval -> eval.overall() > minOverallScore), "minimum overall score %f", minOverallScore);
    }
}
