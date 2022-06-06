package com.ericriese.collegeadmissions.statistics;

import com.ericriese.collegeadmissions.model.Applicant;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnalyzer<E> {
    
    private final List<Statistic> statistics = new ArrayList<>();

    protected void add(Statistic Statistic) {
        statistics.add(Statistic);
    }

    public E analyzeClass(List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            for (Statistic statistic : statistics) {
                statistic.accept(applicant);
            }
        }
        for (Statistic statistic : statistics) {
            statistic.set();
        }
        return build();
    }

    abstract E build();
}
