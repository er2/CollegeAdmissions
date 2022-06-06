package com.ericriese.collegeadmissions.statistics;

import java.util.TreeSet;

public class Distribution {
    private final TreeSet<Integer> scores;

    public Distribution(TreeSet<Integer> scores) {
        this.scores = scores;
    }

    int percentile(int percentile) {
        return 5; // TODO
    }
}
