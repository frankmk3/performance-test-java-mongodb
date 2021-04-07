package com.test.performance.mongodb.common;

import com.test.performance.mongodb.model.Report;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * Utility to generate report object
 */
public class ReportDataGenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Generate a report using random values. It is not a fancy implementation to improve performance
     */
    public static Report generateReport(final String group) {
        Report report = new Report();
        report.setId(generateRandomText("rid"));
        report.setName(generateRandomText("name"));
        report.setGroup(group);
        report.setEnabled(true);
        //field section
        report.setField1(generateRandomText("field1"));
        report.setField2(generateRandomText("field2"));
        report.setField3(generateRandomText("field3"));
        report.setField4(generateRandomText("field4"));
        report.setField5(generateRandomText("field5"));
        report.setField6(generateRandomText("field6"));
        //date section
        report.setDate(new Date());
        report.setDate1(new Date());
        report.setDate2(new Date());
        report.setDate3(new Date());
        report.setDate4(new Date());
        report.setDate5(new Date());
        report.setDate6(new Date());
        report.setDate7(new Date());
        //long section
        Random random = new Random();
        report.setNumeric1(random.nextLong());
        report.setNumeric2(random.nextLong());
        report.setNumeric3(random.nextLong());
        report.setNumeric4(random.nextLong());
        report.setNumeric5(random.nextLong());
        report.setNumeric6(random.nextLong());
        report.setNumeric7(random.nextLong());
        report.setNumeric8(random.nextLong());
        //double section
        report.setDouble1(random.nextDouble());
        report.setDouble2(random.nextDouble());
        report.setDouble3(random.nextDouble());
        report.setDouble4(random.nextDouble());
        report.setDouble5(random.nextDouble());
        report.setDouble6(random.nextDouble());
        report.setDouble7(random.nextDouble());
        report.setDouble8(random.nextDouble());

        return report;
    }

    private static String generateRandomText(@NonNull final String base) {
        return String.format("%s-%s", base, UUID.randomUUID()
                                                .toString());
    }
}