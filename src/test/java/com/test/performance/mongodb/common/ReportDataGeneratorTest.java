package com.test.performance.mongodb.common;

import com.test.performance.mongodb.model.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

class ReportDataGeneratorTest {

    @ParameterizedTest
    @ValueSource(strings = {"group1", "group2", "group3", "group4", "group5", ""})
    void reportGenerated_whenGroupIsProvided_returnGroupInformationWithSameGroup(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertEquals(group, report.getGroup());
    }

    @ParameterizedTest
    @ValueSource(strings = {"g1", "g2", "g3", "g4", "g5", "g6"})
    void whenReportIsGenerated_returnReportWithEnabledTrue(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertTrue(report.isEnabled());
    }

    @ParameterizedTest
    @ValueSource(strings = {"g1", "g2", "g3", "g4", "g5", "g6"})
    void whenReportIsGenerated_returnReportWithNameValue(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertTrue(StringUtils.isNotBlank(report.getName()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"g1", "g2", "g3", "g4", "g5", "g6"})
    void whenReportIsGenerated_returnReportWithAllFieldValues(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertTrue(StringUtils.isNotBlank(report.getField1()));
        Assertions.assertTrue(StringUtils.isNotBlank(report.getField2()));
        Assertions.assertTrue(StringUtils.isNotBlank(report.getField3()));
        Assertions.assertTrue(StringUtils.isNotBlank(report.getField4()));
        Assertions.assertTrue(StringUtils.isNotBlank(report.getField5()));
        Assertions.assertTrue(StringUtils.isNotBlank(report.getField6()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a1", "a2", "a3"})
    void whenReportIsGenerated_returnReportWithAllDateValues(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertNotNull(report.getDate1());
        Assertions.assertNotNull(report.getDate2());
        Assertions.assertNotNull(report.getDate3());
        Assertions.assertNotNull(report.getDate4());
        Assertions.assertNotNull(report.getDate5());
        Assertions.assertNotNull(report.getDate6());
        Assertions.assertNotNull(report.getDate7());
    }

    @ParameterizedTest
    @ValueSource(strings = {"b1", "c2", "d3"})
    void whenReportIsGenerated_returnReportWithAllDoubleValues(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertNotNull(report.getDouble1());
        Assertions.assertNotNull(report.getDouble2());
        Assertions.assertNotNull(report.getDouble3());
        Assertions.assertNotNull(report.getDouble4());
        Assertions.assertNotNull(report.getDouble5());
        Assertions.assertNotNull(report.getDouble6());
        Assertions.assertNotNull(report.getDouble7());
        Assertions.assertNotNull(report.getDouble8());
    }

    @ParameterizedTest
    @ValueSource(strings = {"r1", "t2", "y3"})
    void whenReportIsGenerated_returnReportWithAllNumericValues(final String group) {
        Report report = ReportDataGenerator.generateReport(group);

        Assertions.assertNotNull(report.getNumeric1());
        Assertions.assertNotNull(report.getNumeric2());
        Assertions.assertNotNull(report.getNumeric3());
        Assertions.assertNotNull(report.getNumeric4());
        Assertions.assertNotNull(report.getNumeric5());
        Assertions.assertNotNull(report.getNumeric6());
        Assertions.assertNotNull(report.getNumeric7());
        Assertions.assertNotNull(report.getNumeric8());
    }

    @ParameterizedTest
    @ValueSource(strings = {"rr1", "er2", "ere3"})
    void whenReportIsGenerated_EqualsIsFalseForConsecutiveObject(final String group) {
        Report firstReport = ReportDataGenerator.generateReport(group);
        Report secondReport = ReportDataGenerator.generateReport(group);

        boolean result = secondReport.equals(firstReport);

        Assertions.assertFalse(result);
    }
}