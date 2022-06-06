package com.ericriese.collegeadmissions.statistics;

public class Analyzer extends AbstractAnalyzer<ClassStatistics> {
    ClassStatistics.ClassStatisticsBuilder builder = new ClassStatistics.ClassStatisticsBuilder();

    public Analyzer() {
        add(new SatScores(builder::satScores));
    }

    @Override
    public ClassStatistics build() {
        return builder.build();
    }
}
