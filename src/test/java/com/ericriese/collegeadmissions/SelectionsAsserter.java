package com.ericriese.collegeadmissions;

import com.ericriese.collegeadmissions.selector.Selections;
import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multisets;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.SoftAssertions;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SelectionsAsserter {
    private final Selections assertee;
    private final SoftAssertions sa = new SoftAssertions();

    public SelectionsAsserter(Selections assertee) {
        this.assertee = assertee;
    }

    void acceptIds(Set<Integer> expectedAcceptedIds) {
        matchIds(assertee.accepted(), "accepted ids", expectedAcceptedIds);
    }

    void matchIds(List<ApplicantEvaluation> applicants, String desc, Set<Integer> expectedIds) {
        HashMultiset<Integer> actualIds = applicants.stream().collect(Multisets.toMultiset(ApplicantEvaluation::id, a -> 1, HashMultiset::create));
        sa.assertThat(actualIds.entrySet()).as(desc + " no duplicates").allMatch(entry -> entry.getCount() == 1, "no dups");
        sa.assertThat(new HashSet<>(actualIds)).as(desc).isEqualTo(expectedIds);
    }

    public SelectionsAsserter accept(Map<Integer, Consumer<ObjectAssert<ApplicantEvaluation>>> asserts) {
        acceptIds(asserts.keySet());
        for (ApplicantEvaluation applicant : assertee.accepted()) {
            Consumer<ObjectAssert<ApplicantEvaluation>> assertConsumer = asserts.get(applicant.id());
            if (assertConsumer != null) {
                ObjectAssert<ApplicantEvaluation> applicantObjectAssert = sa.assertThat(applicant).as("acceptee id:" + applicant.id());
                assertConsumer.accept(applicantObjectAssert);
            } else {
                sa.fail("acceptee missing %s", applicant);
            }
        }
        return this;
    }

    void rejectIds(Set<Integer> expectedRejectedIds) {
        matchIds(assertee.rejected(), "rejected", expectedRejectedIds);
    }

    public SelectionsAsserter reject(Map<Integer, Consumer<ObjectAssert<ApplicantEvaluation>>> asserts) {
        rejectIds(asserts.keySet());
        for (ApplicantEvaluation applicant : assertee.rejected()) {
            Consumer<ObjectAssert<ApplicantEvaluation>> assertConsumer = asserts.get(applicant.id());
            if (assertConsumer != null) {
                ObjectAssert<ApplicantEvaluation> applicantObjectAssert = sa.assertThat(applicant).as("rejectee id:" + applicant.id());
                assertConsumer.accept(applicantObjectAssert);
            } else {
                sa.fail("rejectee missing %s", applicant);
            }
        }
        return this;
    }

    public void assertAll() {
        sa.assertAll();
    }
}
