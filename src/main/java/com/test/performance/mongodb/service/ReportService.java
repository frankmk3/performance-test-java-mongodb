package com.test.performance.mongodb.service;

import com.test.performance.mongodb.model.Report;
import com.test.performance.mongodb.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    private final ReactiveMongoOperations reactiveMongoOperations;


    @Autowired
    public ReportService(final ReportRepository reportRepository,
                         final ReactiveMongoOperations reactiveMongoOperations
    ) {
        this.reportRepository = reportRepository;
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    public Mono<Report> create(final Report report) {
        return reportRepository.save(report);
    }

    public Flux<Report> getAll(final String group, final Pageable pageable) {
        Query query = new Query();
        if (pageable.isPaged()) {
            query.skip(pageable.getPageNumber() * pageable.getPageSize());
            query.limit(pageable.getPageSize());
        }
        query.addCriteria(Criteria.where("enabled")
                                  .is(true));
        if (StringUtils.hasText(group)) {
            query.addCriteria(Criteria.where("group")
                                      .is(group));
        }
        return reactiveMongoOperations.find(query, Report.class);
    }
}