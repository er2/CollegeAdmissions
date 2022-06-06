package com.ericriese.collegeadmissions.selector;

import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import com.ericriese.collegeadmissions.statistics.ClassStatistics;

import java.util.List;

public interface Selector {
    Selections select(ClassStatistics classStatistics, List<ApplicantEvaluation> applicants);
}
