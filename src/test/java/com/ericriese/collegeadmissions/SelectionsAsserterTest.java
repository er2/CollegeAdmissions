package com.ericriese.collegeadmissions;

import com.ericriese.collegeadmissions.selector.Selections;
import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectionsAsserterTest {
    @Test
    void acceptIdsCatchesDups() {
        Selections selections = Selections.builder()
                .accepted(List.of(
                        ApplicantEvaluation.builder().id(1).build(),
                        ApplicantEvaluation.builder().id(2).build(),
                        ApplicantEvaluation.builder().id(1).build()))
                .build();
        SelectionsAsserter sa = new SelectionsAsserter(selections);
        sa.acceptIds(Set.of(1, 2));
        assertThatThrownBy(sa::assertAll)
                .hasMessageContaining("no dups")
                .hasMessageContaining("1 x 2");
    }

}