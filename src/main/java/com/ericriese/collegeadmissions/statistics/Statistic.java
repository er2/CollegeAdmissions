package com.ericriese.collegeadmissions.statistics;

import com.ericriese.collegeadmissions.model.Applicant;

import java.util.function.Consumer;

public interface Statistic extends Consumer<Applicant> {
    void set();
}
