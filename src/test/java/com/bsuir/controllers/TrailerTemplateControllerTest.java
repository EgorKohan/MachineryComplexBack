package com.bsuir.controllers;

import com.bsuir.AbstractTest;
import com.bsuir.models.TrailerTemplate;
import com.bsuir.repositories.TrailerTemplateRepository;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.bsuir.TestApplicationConstants.MULTIPART_FORM_DATA_WITH_BOUNDARY;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrailerTemplateControllerTest extends AbstractTest {

    @Autowired
    private TrailerTemplateRepository trailerTemplateRepository;

    private TrailerTemplate generateRandomTemplate() {
        return new TrailerTemplate(null, UUID.randomUUID().toString());
    }

    private Map<String, Object> convertTemplateToFormMap(TrailerTemplate template) {
        Map<String, Object> map = new HashMap<>();
        if (template.getId() != null) map.put("id", template.getId());
        map.put("trailerName", template.getTrailerName());
        return map;
    }

    @BeforeAll
    void setup(){
        RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT));
    }

    @AfterEach
    void cleanupDb() {
        trailerTemplateRepository.deleteAll();
    }

    @Test
    void savedTemplates_findAll_OK() {
        trailerTemplateRepository.save(generateRandomTemplate());
        when()
                .get("/api/v1/trailer-templates")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .assertThat().body("$", hasSize(1));
    }

    @Test
    void savedTemplate_findByTemplateId_thenOK() {
        TrailerTemplate savedTemplate = trailerTemplateRepository.save(generateRandomTemplate());
        when()
                .get("/api/v1/trailer-templates/" + savedTemplate.getId())
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .assertThat().body("trailerName", is(savedTemplate.getTrailerName()));
    }

    @Test
    void findNotExistByTemplateId_thenNotFound() {
        when()
                .get("/api/v1/trailer-templates/1")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void generatedTemplate_save_thenOK() {
        given()
                .contentType(MULTIPART_FORM_DATA_WITH_BOUNDARY)
                .accept("application/json")
                .queryParams(convertTemplateToFormMap(generateRandomTemplate())) //formParams doesn't work
                .log().all()
                .when()
                .post("/api/v1/trailer-templates")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .assertThat().body("id", notNullValue());
    }

}