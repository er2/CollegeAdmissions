package com.ericriese.collegeadmissions.statistics;

import com.ericriese.collegeadmissions.model.Applicant;

import java.util.TreeSet;
import java.util.function.Consumer;

public class SatScores implements Statistic {

    private final TreeSet<Integer> scores = new TreeSet<>();
    private final Consumer<Distribution> setter;

    public SatScores(Consumer<Distribution> setter) {
        this.setter = setter;
    }

    @Override
    public void set() {
        setter.accept(new Distribution(scores));
    }

    @Override
    public void accept(Applicant applicant) {
        scores.add(applicant.satScore());
    }
}
