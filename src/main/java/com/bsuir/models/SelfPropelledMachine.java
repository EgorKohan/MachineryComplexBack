package com.bsuir.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "self_propelled_machine")
public class SelfPropelledMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_name")
    private String inventoryNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "self_propelled_machine_id", nullable = false)
    private SelfPropelledMachineTemplate machineTemplate;

    @Column(name = "actual_service_life")
    private Integer actualServiceLifeInMonth;

    @Column(name = "normative_service_life")
    private Integer normativeServiceLifeInMonth;

    @Column(name = "initial_cost")
    private BigDecimal initialCost;

    @Column(name = "residual_value")
    private BigDecimal residualValue;

    //technical readiness factor
    @Column(name = "trk")
    private Float trk;

}
