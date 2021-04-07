package com.test.performance.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ReportApplication {

    public static void main(final String[] args) {
        final long init = System.currentTimeMillis();
        SpringApplication.run(ReportApplication.class, args);
        log.info(
            String.format(
                "*** --- Application Started | Took: %d milliseconds --- ***",
                System.currentTimeMillis() - init
            )
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
