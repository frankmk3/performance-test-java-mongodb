package com.test.performance.mongodb.service;

import static org.mockito.Mockito.when;

import com.test.performance.mongodb.common.ReportDataGenerator;
import com.test.performance.mongodb.model.Report;
import com.test.performance.mongodb.repository.ReportRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReportServiceTest {

    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private ReactiveMongoOperations reactiveMongoOperations;

    @BeforeEach
    void init() {
        reportRepository = Mockito.mock(ReportRepository.class);
        reactiveMongoOperations = Mockito.mock(ReactiveMongoOperations.class);
        reportService = new ReportService(reportRepository, reactiveMongoOperations);
    }

    @Test
    void reportCreate_whenReportIsNotNull_returnCreatedReport() {
        String group = "group-1";
        Report report = ReportDataGenerator.generateReport(group);
        when(reportRepository.save(report)).thenReturn(Mono.just(report));

        Mono<Report> reportMono = reportService.create(report);

        Assertions.assertEquals(report, reportMono.block());
    }

    @Test
    void getAllReport_whenIsUnPaged_returnAllEnabledReports() {
        Query query = new Query();
        query.addCriteria(
            Criteria.where("enabled")
                    .is(true)
        );
        List<Report> reports = IntStream.of(0, 2)
                                        .mapToObj(p -> ReportDataGenerator.generateReport("group-" + p))
                                        .collect(Collectors.toList());
        when(reactiveMongoOperations.find(query, Report.class)).thenReturn(Flux.fromStream(reports.stream()));

        Flux<Report> response = reportService.getAll("", Pageable.unpaged());

        StepVerifier.create(response.log())
                    .expectNext(reports.get(0))
                    .expectNext(reports.get(1))
                    .verifyComplete();
    }

    @Test
    void getAllReport_whenIsPagerIsProvides_returnAllEnabledReportsForPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Query query = new Query();
        query.addCriteria(
            Criteria.where("enabled")
                    .is(true)
        );
        query.limit(pageable.getPageSize());
        query.skip(0);
        List<Report> reports = IntStream.of(0, 2)
                                        .mapToObj(p -> ReportDataGenerator.generateReport("g-" + p))
                                        .collect(Collectors.toList());
        when(reactiveMongoOperations.find(query, Report.class)).thenReturn(Flux.fromStream(reports.stream()));

        Flux<Report> response = reportService.getAll("", pageable);

        StepVerifier.create(response.log())
                    .expectNext(reports.get(0))
                    .expectNext(reports.get(1))
                    .verifyComplete();
    }
}