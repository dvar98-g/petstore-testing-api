package com.qa.testing.api;

import com.qa.testing.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Clase base para las clases de api de cada recurso (UserApi, PetApi,
 * StoreApi). Centraliza la construccion del RequestSpecification
 * compartido: base URL, timeouts, content type, todos leidos desde
 * Config (nunca hardcodeados aca).
 */
public abstract class BaseApi {

    private static final RequestSpecification BASE_REQUEST_SPEC = buildBaseRequestSpec();

    protected static RequestSpecification baseRequestSpec() {
        return BASE_REQUEST_SPEC;
    }

    private static RequestSpecification buildBaseRequestSpec() {
        int timeoutMillis = Config.getDefaultTimeoutSeconds() * 1000;

        RestAssuredConfig restAssuredConfig = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeoutMillis)
                        .setParam("http.socket.timeout", timeoutMillis));

        return new RequestSpecBuilder()
                .setBaseUri(Config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setConfig(restAssuredConfig)
                .build();
    }
}