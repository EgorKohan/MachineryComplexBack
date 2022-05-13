package com.bsuir.controllers;

import com.bsuir.AbstractTest;
import com.bsuir.models.*;
import com.bsuir.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkPlanOptimizerControllerTest extends AbstractTest {

    @Autowired
    private TrailerService trailerService;

    @Autowired
    private SelfPropelledMachineService selfPropelledMachineService;

    @Autowired
    private TrailerTemplateService trailerTemplateService;

    @Autowired
    private SelfPropelledMachineTemplateService selfPropelledMachineTemplateService;

    @Autowired
    private AgriculturalOperationService agriculturalOperationService;

    @Autowired
    private WorkPlanService workPlanService;

    private static List<String> generateSequence(String prefix, int begin, int end) {
        List<String> list = new ArrayList<>();
        for (int i = begin; i <= end; i++) {
            list.add(prefix + i);
        }
        return list;
    }

    private void savePredefinedTrailerTemplatesInDB() {
        generateSequence("m", 0, 4).forEach(name ->
                trailerTemplateService.save(new TrailerTemplate(null, name)));
    }

    private void savePredefinedMachineTemplatesInDB() {
        generateSequence("t", 1, 4).forEach(name ->
                selfPropelledMachineTemplateService.save(new SelfPropelledMachineTemplate(null, name)));
        selfPropelledMachineTemplateService.save(new SelfPropelledMachineTemplate(null, "t6"));
    }

    private Trailer generateRandomTrailer(TrailerTemplate trailerTemplate) {
        return new Trailer(
                null,
                UUID.randomUUID().toString(),
                trailerTemplate,
                12,
                12,
                BigDecimal.valueOf(10.1),
                BigDecimal.valueOf(10.2),
                0.5f
        );
    }

    private SelfPropelledMachine generateRandomMachine(SelfPropelledMachineTemplate machineTemplate) {
        return new SelfPropelledMachine(
                null,
                UUID.randomUUID().toString(),
                machineTemplate,
                12,
                12,
                BigDecimal.valueOf(10.1),
                BigDecimal.valueOf(10.2),
                0.5f
        );
    }

    private final Map<String, Integer> trailersCount = Map.of(
            "m0", 777,
            "m1", 3,
            "m2", 2,
            "m3", 1,
            "m4", 2
    );

    private void savePredefinedTrailersInDB() {
        List<TrailerTemplate> all = trailerTemplateService.findAll();
        all.forEach(template -> {
            for (int i = 0; i < trailersCount.get(template.getTrailerName()); i++) {
                trailerService.save(generateRandomTrailer(template));
            }
        });
    }

    private final Map<String, Integer> machinesCount = Map.of(
            "t1", 2,
            "t2", 1,
            "t3", 2,
            "t4", 1,
            "t6", 3
    );

    private void savePredefinedMachinesInDB() {
        List<SelfPropelledMachineTemplate> all = selfPropelledMachineTemplateService.findAll();
        all.forEach(template -> {
            for (int i = 0; i < machinesCount.get(template.getMachineName()); i++) {
                selfPropelledMachineService.save(generateRandomMachine(template));
            }
        });
    }

    private static final Map<String, Float> workVolumes = Map.of(
            "r101", 400.0f,
            "r102", 120.0f,
            "r103", 400.0f,
            "r104", 400.0f,
            "r105", 400.0f,
            "r106", 400.0f,
            "r107", 92.0f,
            "r108", 92.0f,
            "r110", 400.0f
    );

    private void savePredefinedOperationsInDB() {
        workVolumes.forEach((name, volume) ->
                agriculturalOperationService.save(new AgriculturalOperation(null, name, volume, EUnit.HECTARE, LocalDateTime.now(), LocalDateTime.now())));
    }

    private void savePredefinedWorksInDB() {
        List<List<Object>> list = new ArrayList<>();
        list.add(Arrays.asList(1L, 1L, 784L, 7.8f));
        list.add(Arrays.asList(1L, 1L, 784L, 7.8f));
        list.add(Arrays.asList(1L, 1L, 784L, 7.8f));
        list.add(Arrays.asList(2L, 1L, 1L, 397.7f));
        list.add(Arrays.asList(3L, 1L, 1L, 22.8f));
        list.add(Arrays.asList(3L, 3L, 783L, 25.4f));
        list.add(Arrays.asList(3L, 3L, 784L, 27.9f));
        list.add(Arrays.asList(4L, 1L, 778L, 4.3f));
        list.add(Arrays.asList(4L, 1L, 778L, 4.3f));
        list.add(Arrays.asList(4L, 1L, 778L, 4.3f));
        list.add(Arrays.asList(4L, 1L, 778L, 4.3f));
        list.add(Arrays.asList(4L, 1L, 781L, 4.45f));
        list.add(Arrays.asList(4L, 1L, 781L, 4.45f));
        list.add(Arrays.asList(4L, 1L, 783L, 4.45f));
        list.add(Arrays.asList(4L, 1L, 781L, 4.45f));
        list.add(Arrays.asList(4L, 1L, 783L, 4.4f));
        list.add(Arrays.asList(4L, 1L, 783L, 4.4f));
        list.add(Arrays.asList(4L, 1L, 783L, 4.4f));
        list.add(Arrays.asList(4L, 1L, 783L, 4.4f));
        list.add(Arrays.asList(4L, 1L, 784L, 6.3f));
        list.add(Arrays.asList(4L, 1L, 784L, 6.3f));
        list.add(Arrays.asList(4L, 1L, 784L, 6.3f));
        list.add(Arrays.asList(4L, 1L, 784L, 6.3f));
        list.add(Arrays.asList(5L, 4L, 781L, 8.1f));
        list.add(Arrays.asList(5L, 3L, 781L, 18.1f));
        list.add(Arrays.asList(5L, 4L, 781L, 19.2f));
        list.add(Arrays.asList(5L, 3L, 781L, 7.8f));
        list.add(Arrays.asList(6L, 6L, 781L, 11.0f));
        list.add(Arrays.asList(6L, 6L, 781L, 11.0f));
        list.add(Arrays.asList(7L, 1L, 1L, 56.0f));
        list.add(Arrays.asList(8L, 3L, 783L, 152.4f));
        list.add(Arrays.asList(8L, 4L, 783L, 167.6f));
        list.add(Arrays.asList(8L, 6L, 783L, 137.2f));
        list.add(Arrays.asList(9L, 1L, 781L, 20.1f));
        list.add(Arrays.asList(9L, 6L, 781L, 20.1f));
        list.add(Arrays.asList(9L, 1L, 781L, 18.6f));
        list.add(Arrays.asList(9L, 6L, 781L, 18.6f));
        list.add(Arrays.asList(9L, 1L, 781L, 10.25f));
        list.forEach(list2 -> workPlanService.save(
                new WorkPlan(null,
                        selfPropelledMachineService.findById((Long) list2.get(1)),
                        trailerService.findById((Long) list2.get(2)),
                        agriculturalOperationService.findById((Long) list2.get(0)),
                        (Float) list2.get(3)
                )));
    }

    @Test
    void givenWorkPlans_whenSendThem_thenOK() {
        savePredefinedTrailerTemplatesInDB();
        savePredefinedMachineTemplatesInDB();
        savePredefinedTrailersInDB();
        savePredefinedMachinesInDB();
        savePredefinedOperationsInDB();
        savePredefinedWorksInDB();

        given()
                .contentType("application/json")
                .accept("application/json")
                .body(workPlanService.findAll().stream().map(WorkPlan::getId).collect(Collectors.toList()))
                .when()
                .log().all()
                .post("/api/v1/optimize")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

}