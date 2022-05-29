package com.bsuir.controllers;

import com.bsuir.AbstractTest;
import com.bsuir.models.*;
import com.bsuir.services.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Slf4j
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
        generateSequence("m", 1, 5).forEach(name ->
                trailerTemplateService.save(new TrailerTemplate(null, name)));
    }

    private void savePredefinedMachineTemplatesInDB() {
        generateSequence("t", 1, 5).forEach(name ->
                selfPropelledMachineTemplateService.save(new SelfPropelledMachineTemplate(null, name)));
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
                0.5f, ""
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
                0.5f, ""
        );
    }

    private final Map<String, Integer> trailersCount = Map.of(
            "m1", 777,
            "m2", 3,
            "m3", 2,
            "m4", 1,
            "m5", 2
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
            "t5", 3
    );

    private final Map<String, Date> periodsStartDate = Map.of(
            "1", Date.from(Instant.parse("2022-05-05T00:00:00.00Z")),
            "2", Date.from(Instant.parse("2022-03-05T00:00:00.00Z")),
            "3", Date.from(Instant.parse("2022-07-01T00:00:00.00Z")),
            "4", Date.from(Instant.parse("2022-04-15T00:00:00.00Z")),
            "5", Date.from(Instant.parse("2022-01-05T00:00:00.00Z")),
            "6", Date.from(Instant.parse("2022-03-18T00:00:00.00Z"))
    );

    private final Map<String, Date> periodsEndDate = Map.of(
            "1", Date.from(Instant.parse("2022-05-10T00:00:00.00Z")),
            "2", Date.from(Instant.parse("2022-03-09T00:00:00.00Z")),
            "3", Date.from(Instant.parse("2022-07-07T00:00:00.00Z")),
            "4", Date.from(Instant.parse("2022-04-19T00:00:00.00Z")),
            "5", Date.from(Instant.parse("2022-01-07T00:00:00.00Z")),
            "6", Date.from(Instant.parse("2022-03-27T00:00:00.00Z"))
    );

    private void savePredefinedMachinesInDB() {
        List<SelfPropelledMachineTemplate> all = selfPropelledMachineTemplateService.findAll();
        all.forEach(template -> {
            for (int i = 0; i < machinesCount.get(template.getMachineName()); i++) {
                selfPropelledMachineService.save(generateRandomMachine(template));
            }
        });
    }

    private static final Map<String, Float> workVolumes = linkedMapOfEntries(
            new AbstractMap.SimpleEntry<>("r1", 400.0f),
            new AbstractMap.SimpleEntry<>("r2", 120.0f),
            new AbstractMap.SimpleEntry<>("r3", 400.0f),
            new AbstractMap.SimpleEntry<>("r4", 400.0f),
            new AbstractMap.SimpleEntry<>("r5", 400.0f),
            new AbstractMap.SimpleEntry<>("r6", 400.0f),
            new AbstractMap.SimpleEntry<>("r7", 92.0f),
            new AbstractMap.SimpleEntry<>("r8", 92.0f),
            new AbstractMap.SimpleEntry<>("r9", 400.0f)
    );

    private void savePredefinedOperationsInDB() {
        workVolumes.forEach((name, volume) ->
                agriculturalOperationService.save(
                        new AgriculturalOperation(
                                null,
                                name,
                                volume,
                                EUnit.HECTARE,
                                Date.from(Instant.parse("1970-01-01T00:00:00.00Z")),
                                Date.from(Instant.parse("2555-01-01T00:00:00.00Z"))
                        )
                )
        );
    }

    private void savePredefinedWorksInDB() {
        List<List<Object>> list = new ArrayList<>();
        list.add(Arrays.asList(1L, 1L, 5L, "2", 7.8F));
        list.add(Arrays.asList(2L, 1L, 1L, "3", 397.7F));
        list.add(Arrays.asList(3L, 1L, 1L, "4", 22.8F));
        list.add(Arrays.asList(3L, 2L, 5L, "6", 27.9F));
        list.add(Arrays.asList(4L, 1L, 2L, "6", 4.3F));
        list.add(Arrays.asList(4L, 1L, 5L, "2", 6.3F));
        list.add(Arrays.asList(5L, 3L, 3L, "3", 19.2F));
        list.add(Arrays.asList(6L, 4L, 3L, "1", 11.0F));
        list.add(Arrays.asList(6L, 5L, 3L, "2", 11.0F));
        list.add(Arrays.asList(7L, 1L, 1L, "3", 56.0F));
        list.forEach(list2 -> {
            WorkPlan plan = new WorkPlan(null,
                    selfPropelledMachineTemplateService.findById((Long) list2.get(1)),
                    trailerTemplateService.findById((Long) list2.get(2)),
                    agriculturalOperationService.findById((Long) list2.get(0)),
                    new HashSet<>()
            );
            plan.setPeriods(
                    Set.of(
                            new Period(null, plan, periodsStartDate.get(list2.get(3)), periodsEndDate.get(list2.get(3)), (Float) list2.get(4))
                    )
            );
            workPlanService.save(plan);
        });
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
                .accept("*/*")
                .body(workPlanService.findAll().stream().map(WorkPlan::getId).collect(Collectors.toList()))
                .when()
                .log().all()
                .post("/api/v1/optimize")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.OK.value());
    }

    @SafeVarargs
    private static <T1, T2> Map<T1, T2> linkedMapOfEntries(Map.Entry<T1, T2>... entries) {
        Map<T1, T2> map = new LinkedHashMap<>();
        for (Map.Entry<T1, T2> entry : entries) {
            map.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return map;
    }

}