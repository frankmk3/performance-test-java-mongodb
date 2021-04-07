package com.test.performance.mongodb.common;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility to manage reusable constants.
 */
@Getter
@NoArgsConstructor
@Component
public class Constants implements Serializable {

    public static final String AUTHORIZATION_TOKEN = "authorization";
    public static final String STRING = "java.lang.String";
    public static final String HEADER = "header";
    public static final String AUTHORIZATION = "Authorization";
    private static final long serialVersionUID = 1L;

    @Value("${paginator.page}")
    private int paginatorPage;

    @Value("${paginator.size}")
    private int paginatorSize;

    @Value("${paginator.max.size:100}")
    private int paginatorMaxSize;

}