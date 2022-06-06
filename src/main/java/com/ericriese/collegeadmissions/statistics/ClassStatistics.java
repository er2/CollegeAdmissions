package com.ericriese.collegeadmissions.statistics;

import lombok.Builder;

@Builder
public record ClassStatistics(
        Distribution satScores,
        Distribution essayScores
) {
}
