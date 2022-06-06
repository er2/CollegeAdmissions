package com.ericriese.collegeadmissions;

import com.ericriese.collegeadmissions.selector.Selections;
import com.ericriese.collegeadmissions.selector.Selector;
import com.ericriese.collegeadmissions.selector.SelectorImpl;
import com.ericriese.collegeadmissions.statistics.ApplicantEvaluation;
import com.ericriese.collegeadmissions.statistics.ClassStatistics;
import com.ericriese.collegeadmissions.statistics.Distribution;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.assertj.core.condition.AllOf.allOf;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {
        ObjectMapper.class, JacksonAutoConfiguration.class
})
@TestPropertySource("classpath:application.yaml")
public class SelectionsTests {

    @Inject
    ObjectMapper objectMapper;

    /**<!DOCTYPE html>
     <html>
     <head>
     <title></title>
     <meta charset="UTF-8">
     </head>
     <body>
     <table border="1" style="border-collapse:collapse">
     <tr>
     <th>id</th>
     <th> sat</th>
     <th> essay</th>
     <th> overall</th>
     </tr>
     <tr>
     <td>1</td>
     <td>5</td>
     <td>6</td>
     <td>7</td>
     </tr>
     <tr>
     <td>2</td>
     <td>1</td>
     <td>1</td>
     <td>1</td>
     </tr>
     <tr>
     <td>3</td>
     <td>9</td>
     <td>9</td>
     <td>9</td>
     </tr>
     </table>
     </body>
     </html>

     */
    @Test
    public void selections() throws JsonProcessingException {
        // Given
        List<ApplicantEvaluation> applicants = List.of(
                //                     id, sat, essay, overall
                new ApplicantEvaluation(1, 5, 6, 7),
                new ApplicantEvaluation(2, 1, 1, 1),
                new ApplicantEvaluation(3, 9, 9, 9)
        );
        // language=markdown
        var applicantTable = """
                # Applicants
                | id |  sat |  essay |  overall |
                | :--- | :--- | :--- | :--- |
                | 1 | 5 | 6 | 7 |
                | 2 | 1 | 1 | 1 |
                | 3 | 9 | 9 | 9 |
                """;
        // language=html
        var applicants2 = """
                <!DOCTYPE html>
                <html>
                <head>
                <title></title>
                <meta charset="UTF-8">
                </head>
                <body>
                <table border="1" style="border-collapse:collapse">
                <tr><th>id</th><th> sat</th><th> essay</th><th> overall</th></tr>
                <tr><td>1</td><td>5</td><td>6</td><td>7</td></tr>
                <tr><td>2</td><td>1</td><td>1</td><td>1</td></tr>
                <tr><td>3</td><td>9</td><td>9</td><td>9</td></tr>
                </table>
                </body>
                </html>                
                """;
        // language=json
        var applicants3 = """
                [
                  {
                    "id": "1",
                    "sat": "5",
                    "essay": "6",
                    "overall": "7"
                  },
                  {
                    "id": "2",
                    "sat": "1",
                    "essay": "1",
                    "overall": "1"
                  },
                  {
                    "id": "3",
                    "sat": "9",
                    "essay": "9",
                    "overall": "9"
                  }
                ]
                """;
        List<ApplicantEvaluation> applicants4 = objectMapper.readValue(applicants3, new TypeReference<List<ApplicantEvaluation>>() {
        });
        ClassStatistics stats = new ClassStatistics(
                mock(Distribution.class),
                mock(Distribution.class)
        );
        Selector selector = new SelectorImpl();

        // When
        Selections select = selector.select(stats, applicants4);

        // Then
        new SelectionsAsserter(select)
                .accept(Map.of(
                        1, asserter -> asserter.has(allOf(minimumOverallScore()))
                ))
                .assertAll();
    }

    @Value("${minimumOverallScore}")
    double minimumOverallScore;

    Condition<ApplicantEvaluation> minimumOverallScore() {
        return new Condition<>((eval -> eval.overall() > minimumOverallScore), "minimum overall score %s", minimumOverallScore);
    }
}
