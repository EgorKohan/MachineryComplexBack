package com.bsuir.services.impl;

import com.bsuir.mappers.AgriculturalOperationMapper;
import com.bsuir.mappers.PeriodMapper;
import com.bsuir.mappers.SelfPropelledMachineTemplateMapper;
import com.bsuir.mappers.TrailerTemplateMapper;
import com.bsuir.models.*;
import com.bsuir.services.*;
import com.bsuir.utils.FileReader;
import com.gams.api.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.bsuir.ApplicationConstants.PATH_TO_MODEL;
import static com.bsuir.models.PrefixEnum.*;

@Slf4j
@Service
public class WorkPlanOptimizerImpl implements WorkPlanOptimizer {

    private final TrailerService trailerService;
    private final SelfPropelledMachineService selfPropelledMachineService;
    private final TrailerTemplateService trailerTemplateService;
    private final SelfPropelledMachineTemplateService selfPropelledMachineTemplateService;
    private final AgriculturalOperationService agriculturalOperationService;
    private final PeriodService periodService;
    private final SelfPropelledMachineTemplateMapper machineTemplateMapper;
    private final AgriculturalOperationMapper operationMapper;
    private final TrailerTemplateMapper trailerTemplateMapper;
    private final PeriodMapper periodMapper;

    private static final String JAVA_LIBRARY_PATH = "java.library.path";
    @Value("${gams.path}")
    private String pathToGams;

    @Autowired
    public WorkPlanOptimizerImpl(
            TrailerService trailerService,
            SelfPropelledMachineService selfPropelledMachineService,
            TrailerTemplateService trailerTemplateService,
            SelfPropelledMachineTemplateService selfPropelledMachineTemplateService,
            AgriculturalOperationService agriculturalOperationService,
            PeriodService periodService,
            SelfPropelledMachineTemplateMapper machineTemplateMapper,
            AgriculturalOperationMapper operationMapper,
            TrailerTemplateMapper trailerTemplateMapper,
            PeriodMapper periodMapper
    ) {
        this.trailerService = trailerService;
        this.selfPropelledMachineService = selfPropelledMachineService;
        this.trailerTemplateService = trailerTemplateService;
        this.selfPropelledMachineTemplateService = selfPropelledMachineTemplateService;
        this.agriculturalOperationService = agriculturalOperationService;
        this.periodService = periodService;
        this.machineTemplateMapper = machineTemplateMapper;
        this.operationMapper = operationMapper;
        this.trailerTemplateMapper = trailerTemplateMapper;
        this.periodMapper = periodMapper;
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
    public Map<String, Object> calculateMissingEquipmentCost(Collection<WorkPlan> workPlans) {
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

        GAMSSet j = db.addSet("j", 1, "номер марки самоходной техники");
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

        GAMSParameter pParameter = db.addParameter("p", 4, "Выработка для связки агрегатов");
        gamsVariables.getWorksPerShift().forEach((key, value) -> pParameter.addRecord(key).setValue(value));

        String model = FileReader.readFromResourceFile(PATH_TO_MODEL);
        GAMSJob gamsJob = ws.addJobFromString(model);
        GAMSOptions opt = ws.addOptions();
        opt.defines("gdxincname", db.getName());

        gamsJob.run(opt, db);

        GAMSVariable xVar = gamsJob.OutDB().getVariable("x");
        GAMSVariable percentVar = gamsJob.OutDB().getVariable("procent");
        GAMSVariable tpVar = gamsJob.OutDB().getVariable("tp");
        GAMSVariable mpVar = gamsJob.OutDB().getVariable("mp");

        List<Map<String, Object>> xLevelsList = getXLevels(xVar);
        List<Map<String, Object>> percentsList = getPercentsList(percentVar);
        List<Map<String, Object>> neededMachinesList = getNeededMachinesList(tpVar);
        List<Map<String, Object>> neededTrailersList = getNeededTrailersList(mpVar);

        System.setProperty(JAVA_LIBRARY_PATH, javaLibraryPathValue);

        return Map.of(
                "usedTechnics", xLevelsList,
                "percents", percentsList,
                "neededMachines", neededMachinesList,
                "neededTrailers", neededTrailersList
        );
    }

    private void fillGAMSObject(GAMSVariables gamsVariables, WorkPlan workPlan) {
        SelfPropelledMachineTemplate machineTemplate = workPlan.getMachineTemplate();
        TrailerTemplate trailerTemplate = workPlan.getTrailerTemplate();
        AgriculturalOperation operation = workPlan.getOperation();

        String machineGAMSId = MACHINE.getPrefixLowRegister() + machineTemplate.getId();
        String trailerGAMSId = TRAILER.getPrefixLowRegister() + trailerTemplate.getId();
        String workVolumeGAMSId = OPERATION.getPrefixLowRegister() + operation.getId();
        Map<String, Long> periodNameToDuration = new HashMap<>();
        workPlan.getPeriods().forEach(period -> {
                    Date startDate = period.getStartDate();
                    Date endDate = period.getEndDate();
                    long difference = endDate.getTime() - startDate.getTime();
                    long diffInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                    periodNameToDuration.put(
                            PERIOD.getPrefixLowRegister() + period.getId(), diffInDays);
                }
        );

        Map<String, Float> periodNameToWorkPerShift = new HashMap<>();
        workPlan.getPeriods().forEach(period -> periodNameToWorkPerShift.put(
                PERIOD.getPrefixLowRegister() + period.getId(), period.getWorkPerShift()
        ));

        gamsVariables.getMachines().add(machineGAMSId);
        gamsVariables.getTrailers().add(trailerGAMSId);
        gamsVariables.getWorks().add(workVolumeGAMSId);
        gamsVariables.getPeriods().addAll(periodNameToDuration.keySet());

        gamsVariables.getWorkVolumes().putIfAbsent(workVolumeGAMSId, operation.getWorkVolume());
        gamsVariables.getMachinesCount().putIfAbsent(machineGAMSId, selfPropelledMachineService.countByMachineTemplateId(machineTemplate.getId()));
        gamsVariables.getTrailersCount().putIfAbsent(trailerGAMSId, trailerService.countByTrailerTemplateId(trailerTemplate.getId()));
        gamsVariables.getPeriodsLength().putAll(periodNameToDuration);

        periodNameToWorkPerShift.forEach((id, work) -> gamsVariables.getWorksPerShift().putIfAbsent(
                new Vector<>(Arrays.asList(workVolumeGAMSId, machineGAMSId, trailerGAMSId, id)), work
        ));

    }

    private List<Map<String, Object>> getXLevels(GAMSVariable x) {
        final List<Map<String, Object>> xLevelsList = new LinkedList<>();
        for (GAMSVariableRecord variableRecord : x) {
            double level = variableRecord.getLevel();
            if (level != 0) {
                String operationKey = variableRecord.getKey(0);
                String machineKey = variableRecord.getKey(1);
                String trailerKey = variableRecord.getKey(2);
                String dateKey = variableRecord.getKey(3);

                AgriculturalOperation operation = getOperationByVariable(operationKey);
                SelfPropelledMachineTemplate machineTemplate = getMachineTemplateByVariable(machineKey);
                TrailerTemplate trailerTemplate = getTrailerTemplateByVariable(trailerKey);
                Period period = getPeriodByVariable(dateKey);

                Map<String, Object> newMap = new LinkedHashMap<>();
                newMap.put("machine", machineTemplateMapper.toDto(machineTemplate));
                newMap.put("trailer", trailerTemplateMapper.toDto(trailerTemplate));
                newMap.put("operation", operationMapper.toDto(operation));
                newMap.put("period", periodMapper.toDto(period));
                newMap.put("level", level);
                xLevelsList.add(newMap);
            }
        }
        return xLevelsList;
    }

    private List<Map<String, Object>> getPercentsList(GAMSVariable percent) {
        List<Map<String, Object>> percentsList = new LinkedList<>();
        for (GAMSVariableRecord variableRecord : percent) {
            String operationKey = variableRecord.getKey(0);
            double level = variableRecord.getLevel();

            AgriculturalOperation operation = getOperationByVariable(operationKey);

            Map<String, Object> newMap = new LinkedHashMap<>();
            newMap.put("operation", operationMapper.toDto(operation));
            newMap.put("level", level);
            percentsList.add(newMap);
        }
        return percentsList;
    }

    List<Map<String, Object>> getNeededMachinesList(GAMSVariable tp) {
        List<Map<String, Object>> neededMachinesList = new LinkedList<>();
        for (GAMSVariableRecord variableRecord : tp) {
            String machineKey = variableRecord.getKey(0);
            double level = variableRecord.getLevel();

            SelfPropelledMachineTemplate machineTemplate = getMachineTemplateByVariable(machineKey);

            Map<String, Object> newMap = new LinkedHashMap<>();
            newMap.put("machine", machineTemplateMapper.toDto(machineTemplate));
            newMap.put("level", level);
            neededMachinesList.add(newMap);
        }
        return neededMachinesList;
    }

    List<Map<String, Object>> getNeededTrailersList(GAMSVariable mp) {
        List<Map<String, Object>> neededTrailersList = new LinkedList<>();
        for (GAMSVariableRecord variableRecord : mp) {
            String trailerKey = variableRecord.getKey(0);
            double level = variableRecord.getLevel();

            TrailerTemplate trailerTemplate = getTrailerTemplateByVariable(trailerKey);

            Map<String, Object> newMap = new LinkedHashMap<>();
            newMap.put("trailer", trailerTemplateMapper.toDto(trailerTemplate));
            newMap.put("level", level);
            neededTrailersList.add(newMap);
        }
        return neededTrailersList;
    }

    private SelfPropelledMachineTemplate getMachineTemplateByVariable(String variable) {
        String prefix = variable.substring(0, 1);
        if (!MACHINE.getPrefixLowRegister().equals(prefix))
            throw new IllegalArgumentException("Illegal prefix!");
        String value = variable.substring(1);
        long machineId = Integer.parseInt(value);
        return selfPropelledMachineTemplateService.findById(machineId);
    }

    private TrailerTemplate getTrailerTemplateByVariable(String variable) {
        String prefix = variable.substring(0, 1);
        if (!TRAILER.getPrefixLowRegister().equals(prefix))
            throw new IllegalArgumentException("Illegal prefix!");
        String value = variable.substring(1);
        long trailerId = Integer.parseInt(value);
        return trailerTemplateService.findById(trailerId);
    }

    private AgriculturalOperation getOperationByVariable(String variable) {
        String prefix = variable.substring(0, 1);
        if (!OPERATION.getPrefixLowRegister().equals(prefix))
            throw new IllegalArgumentException("Illegal prefix!");
        String value = variable.substring(1);
        long operationId = Integer.parseInt(value);
        return agriculturalOperationService.findById(operationId);
    }

    private Period getPeriodByVariable(String variable) {
        String prefix = variable.substring(0, 1);
        if (!PERIOD.getPrefixLowRegister().equals(prefix))
            throw new IllegalArgumentException("Illegal prefix!");
        String value = variable.substring(1);
        long periodId = Integer.parseInt(value);
        return periodService.findById(periodId);
    }

}
