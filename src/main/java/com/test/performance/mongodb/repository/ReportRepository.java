package com.test.performance.mongodb.repository;

import com.test.performance.mongodb.model.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportRepository extends ReactiveMongoRepository<Report, String> {
    @Query("{'$and':[{'$or':[{'name':{$ne:null}}, {'enabled':true}]}]}")
    Flux<Report> findAllPageable(Pageable page);

    Mono<Report> findByIdAndEnabledIsTrue(String id);

    @Query("{'$and':[ {_id:'?0'},{'$or':[{'name':{$ne:null}}, {'enabled':true}]}]}")
    Mono<Report> findByIdAndEnabledIsTrueOrNameNotNull(String id);

    Mono<Report> findOneById(String id);

}
