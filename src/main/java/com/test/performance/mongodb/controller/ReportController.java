package com.test.performance.mongodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.performance.mongodb.common.Constants;
import com.test.performance.mongodb.common.ReportDataGenerator;
import com.test.performance.mongodb.dto.ReportCreation;
import com.test.performance.mongodb.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.stream.LongStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller class to manage reports information
 */
@Slf4j
@RestController
@Api(tags = {ReportController.LABEL})
@RequestMapping("/reports")
public class ReportController {

    public static final String LABEL = "Reports";

    private final ReportService reportService;

    private final Constants constants;

    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(
        final ReportService reportService,
        final Constants constants,
        final ObjectMapper objectMapper
    ) {
        this.reportService = reportService;
        this.constants = constants;
        this.objectMapper = objectMapper;
    }

    /**
     * Get reports information
     *
     * @return a paged response with status 200 and the resultant entity collection. In case of bad query parameter,
     * 400. In case of bad credentials, 401
     */
    @ApiOperation(value = "Get reports", tags = LABEL)
    @GetMapping
    public Flux<Map> getReports(
        @RequestParam(name = "group", required = false) final String group,
        @RequestParam(name = "page", required = false, defaultValue = "-1") final int page,
        @RequestParam(name = "size", required = false, defaultValue = "-1") final int size) {
        final int requestPage = page < 0 ? constants.getPaginatorPage() : page;
        final int requestSize =
            size > 0 && size <= constants.getPaginatorMaxSize() ? size : constants.getPaginatorSize();
        final Pageable pageable = PageRequest.of(requestPage, requestSize);
        return reportService.getAll(
            group,
            pageable
        ).map(user -> objectMapper.convertValue(user, Map.class));
    }

    /**
     * Get reports information
     *
     * @return a paged response with status 200 and the resultant entity collection. In case of bad query parameter,
     * 400. In case of bad credentials, 401
     */
    @ApiOperation(value = "Add report", tags = LABEL)
    @PostMapping
    public Mono createReports(
        @RequestBody final ReportCreation reportCreation
    ) {
        long startTime = System.currentTimeMillis();
        LongStream.range(0, reportCreation.getAmount())
                  .forEach(c ->
                      reportService.create(
                          ReportDataGenerator.generateReport(reportCreation.getGroup())
                      )
                                   .subscribe()
                  );
        long endTime = System.currentTimeMillis() - startTime;
        log.info(String.format("Time elapsed for (%d): %d", reportCreation.getAmount(), endTime));
        return Mono.just(reportCreation);
    }

}