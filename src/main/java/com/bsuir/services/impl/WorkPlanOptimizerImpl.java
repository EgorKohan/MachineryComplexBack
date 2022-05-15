package com.bsuir.services.impl;

import com.bsuir.models.*;
import com.bsuir.services.SelfPropelledMachineService;
import com.bsuir.services.TrailerService;
import com.bsuir.services.WorkPlanOptimizer;
import com.bsuir.utils.FileReader;
import com.gams.api.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static com.bsuir.ApplicationConstants.PATH_TO_MODEL;
import static com.bsuir.models.PrefixEnum.*;

@Slf4j
@Service
public class WorkPlanOptimizerImpl implements WorkPlanOptimizer {

    private final TrailerService trailerService;
    private final SelfPropelledMachineService selfPropelledMachineService;
    private static final String JAVA_LIBRARY_PATH = "java.library.path";
    @Value("${gams.path}")
    private String pathToGams;

    @Autowired
    public WorkPlanOptimizerImpl(
            TrailerService trailerService,
            SelfPropelledMachineService selfPropelledMachineService
    ) {
        this.trailerService = trailerService;
        this.selfPropelledMachineService = selfPropelledMachineService;
    }

    @Data
    private static class GAMSVariables {

        private final Set<String> machines = new HashSet<>();
        private final Set<String> trailers = new HashSet<>();
        private final Set<String> works = new HashSet<>();
        private final Set<String> periods = new HashSet<>();
        private final Map<String, Float> workVolumes = new HashMap<>();
        private final Map<String, Long> machinesCount = new HashMap<>();
        private final Map<String, Long> trailersCount = new HashMap<>();
        private final Map<String, Long> periodsLength = new HashMap<>();
        private final Map<Vector<String>, Float> worksPerShift = new HashMap<>();

    }

    @Override
    public Double calculateMissingEquipmentCost(Collection<WorkPlan> workPlans) {
        log.info("Path to gams: {}", pathToGams);
        String javaLibraryPathValue = System.getProperty(JAVA_LIBRARY_PATH);
        System.setProperty(JAVA_LIBRARY_PATH, pathToGams);

        GAMSVariables gamsVariables = new GAMSVariables();

        workPlans.forEach(workPlan -> fillGAMSObject(gamsVariables, workPlan));

        GAMSWorkspaceInfo wsInfo = new GAMSWorkspaceInfo();
        File workingDirectory = new File(System.getProperty("user.dir"), "MachineryBack2"); //generate file for each user
        boolean isDirCreated = workingDirectory.mkdir();

        log.debug("Directory {} is created: {}", workingDirectory.getName(), isDirCreated);

        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());
        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);
        GAMSDatabase db = ws.addDatabase();

        GAMSSet j = db.addSet("j", 1, "номер марки трактора");
        gamsVariables.getMachines().forEach(j::addRecord);

        GAMSSet i = db.addSet("i", 1, "номер вида механизированных работ");
        gamsVariables.getWorks().forEach(i::addRecord);

        GAMSSet k = db.addSet("k", 1, "номер вида сельскохоз. машины (орудия)");
        gamsVariables.getTrailers().forEach(k::addRecord);

        GAMSSet t = db.addSet("t", 1, "номер периода неизменных условий");
        gamsVariables.getPeriods().forEach(t::addRecord);

        GAMSParameter vParameter = db.addParameter("V", 1, "объём работ вида i");
        gamsVariables.getWorkVolumes().forEach((key, value) -> vParameter.addRecord(key).setValue(value));

        GAMSParameter lParameter = db.addParameter("L", 1, "наличное количество тракторов марки j");
        gamsVariables.getMachinesCount().forEach((key, value) -> lParameter.addRecord(key).setValue(value));

        GAMSParameter rParameter = db.addParameter("R", 1, "наличное количество сельхозорудий марки k");
        gamsVariables.getTrailersCount().forEach((key, value) -> rParameter.addRecord(key).setValue(value));

        GAMSParameter dParameter = db.addParameter("D", 1, "продолжительность периодов неизменных условий");
        gamsVariables.getPeriodsLength().forEach((key, value) -> dParameter.addRecord(key).setValue(value));

        GAMSParameter pParameter = db.addParameter("p", 4, "");
        gamsVariables.getWorksPerShift().forEach((key, value) -> pParameter.addRecord(key).setValue(value));

        String model = FileReader.readFromResourceFile(PATH_TO_MODEL);
        GAMSJob gamsJob = ws.addJobFromString(model);
        GAMSOptions opt = ws.addOptions();
        opt.defines("gdxincname", db.getName());

        gamsJob.run(opt, db);

        double f = gamsJob.OutDB().getVariable("F").getFirstRecord().getLevel();
        log.info("F: {}", f);

        System.setProperty(JAVA_LIBRARY_PATH, javaLibraryPathValue);

        return f;
    }

    private void fillGAMSObject(GAMSVariables gamsVariables, WorkPlan workPlan) {
        SelfPropelledMachineTemplate machineTemplate = workPlan.getMachineTemplate();
        TrailerTemplate trailerTemplate = workPlan.getTrailerTemplate();
        AgriculturalOperation operation = workPlan.getOperation();

        String machineGAMSId = MACHINE.getPrefixLowRegister() + machineTemplate.getId();
        String trailerGAMSId = TRAILER.getPrefixLowRegister() + trailerTemplate.getId();
        String workVolumeGAMSId = OPERATION.getPrefixLowRegister() + operation.getId();
        String periodGAMSId = "p1";

        gamsVariables.getMachines().add(machineGAMSId);
        gamsVariables.getTrailers().add(trailerGAMSId);
        gamsVariables.getWorks().add(workVolumeGAMSId);
        gamsVariables.getPeriods().add(periodGAMSId);

        gamsVariables.getWorkVolumes().putIfAbsent(workVolumeGAMSId, operation.getWorkVolume());
        gamsVariables.getMachinesCount().putIfAbsent(machineGAMSId, selfPropelledMachineService.countByMachineTemplateId(machineTemplate.getId()));
        gamsVariables.getTrailersCount().putIfAbsent(trailerGAMSId, trailerService.countByTrailerTemplateId(trailerTemplate.getId()));
        gamsVariables.getPeriodsLength().putIfAbsent(periodGAMSId, 1L);

        gamsVariables.getWorksPerShift().putIfAbsent(
                new Vector<>(Arrays.asList(workVolumeGAMSId, machineGAMSId, trailerGAMSId, periodGAMSId)), workPlan.getWorkPerShift()
        );

    }

}
