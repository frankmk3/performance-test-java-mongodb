package com.test.performance.mongodb.controller;

import static org.hamcrest.core.IsEqual.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.performance.mongodb.common.Constants;
import com.test.performance.mongodb.common.ReportDataGenerator;
import com.test.performance.mongodb.dto.ReportCreation;
import com.test.performance.mongodb.model.Report;
import com.test.performance.mongodb.service.ReportService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ReportController.class)
class ReportControllerTest {

    private static final int PAGE = 0;
    private static final int SIZE = 20;
    private static final int MAX_SIZE = 100;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ReportService reportService;

    @MockBean
    private Constants constants;

    @BeforeEach
    void init() {
        Mockito.when(constants.getPaginatorPage())
               .thenReturn(PAGE);
        Mockito.when(constants.getPaginatorSize())
               .thenReturn(SIZE);
        Mockito.when(constants.getPaginatorMaxSize())
               .thenReturn(MAX_SIZE);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 5, 2, 0})
    void canCallReportsEndpoint_whenNoParameterProvided_returnReportsUsingDefaultPager(final int elements) {
        List<Report> reports = IntStream.range(0, elements)
                                        .mapToObj(pos -> ReportDataGenerator.generateReport("gr" + pos))
                                        .collect(
                                            Collectors.toList());
        List<Map> expected = reports.stream()
                                    .map(r -> objectMapper.convertValue(r, Map.class))
                                    .collect(Collectors.toList());
        Mockito.when(reportService.getAll(null, PageRequest.of(PAGE, SIZE)))
               .thenReturn(Flux.fromStream(reports
                   .stream()));

        webTestClient.get()
                     .uri("/reports")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(List.class)
                     .value(response -> response, equalTo(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 5, 2, MAX_SIZE})
    void canCallReportsEndpoint_whenPageSizeIsProvides_returnReportsUsingDefaultPageAndCurrentSize(final int pageSize) {
        List<Report> reports = IntStream.range(0, pageSize)
                                        .mapToObj(pos -> ReportDataGenerator.generateReport("gp" + pageSize))
                                        .collect(
                                            Collectors.toList());
        List<Map> expected = reports.stream()
                                    .map(r -> objectMapper.convertValue(r, Map.class))
                                    .collect(Collectors.toList());
        Mockito.when(reportService.getAll(null, PageRequest.of(PAGE, pageSize)))
               .thenReturn(Flux.fromStream(reports
                   .stream()));

        webTestClient.get()
                     .uri(String.format("/reports?size=%d", pageSize))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(List.class)
                     .value(response -> response, equalTo(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100, MAX_SIZE + 1})
    void canCallReportsEndpoint_whenPageSizeIsInvalid_returnReportsUsingDefaultPageAndSize(final int pageSize) {
        List<Report> reports = IntStream.range(0, 2)
                                        .mapToObj(pos -> ReportDataGenerator.generateReport("group" + pageSize))
                                        .collect(
                                            Collectors.toList());
        List<Map> expected = reports.stream()
                                    .map(r -> objectMapper.convertValue(r, Map.class))
                                    .collect(Collectors.toList());
        Mockito.when(reportService.getAll(null, PageRequest.of(PAGE, SIZE)))
               .thenReturn(Flux.fromStream(reports
                   .stream()));

        webTestClient.get()
                     .uri(String.format("/reports?size=%d", pageSize))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(List.class)
                     .value(response -> response, equalTo(expected));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 100, 1000})
    void canCallReportsEndpoint_whenPageIsProvides_returnReportsUsingDefaultSizeAndCurrentPage(final int page) {
        List<Report> reports = IntStream.range(0, 3)
                                        .mapToObj(pos -> ReportDataGenerator.generateReport("gr-" + page))
                                        .collect(
                                            Collectors.toList());
        List<Map> expected = reports.stream()
                                    .map(r -> objectMapper.convertValue(r, Map.class))
                                    .collect(Collectors.toList());
        Mockito.when(reportService.getAll(null, PageRequest.of(page, SIZE)))
               .thenReturn(Flux.fromStream(reports
                   .stream()));

        webTestClient.get()
                     .uri(String.format("/reports?page=%d", page))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(List.class)
                     .value(response -> response, equalTo(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void canCallReportsEndpoint_whenPagePageIsInvalid_returnReportsUsingDefaultPageAndSize(final int page) {
        List<Report> reports = IntStream.range(0, 2)
                                        .mapToObj(pos -> ReportDataGenerator.generateReport("g" + page))
                                        .collect(
                                            Collectors.toList());
        List<Map> expected = reports.stream()
                                    .map(r -> objectMapper.convertValue(r, Map.class))
                                    .collect(Collectors.toList());
        Mockito.when(reportService.getAll(null, PageRequest.of(PAGE, SIZE)))
               .thenReturn(Flux.fromStream(reports
                   .stream()));

        webTestClient.get()
                     .uri(String.format("/reports?page  =%d", page))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(List.class)
                     .value(response -> response, equalTo(expected));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 10, 5, 2, 0})
    void canCallCreateReportEndpoint_whenGroupV_returnReportsUsingDefaultPager(final long elements) {

        String group = "group" + elements;
        ReportCreation reportCreation = new ReportCreation(elements, group);

        Mockito.when(reportService.create(ArgumentMatchers.any(Report.class)))
               .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        webTestClient.post()
                     .uri("/reports")
                     .bodyValue(reportCreation)
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(ReportCreation.class)
                     .value(r -> r, equalTo(reportCreation));
    }
}