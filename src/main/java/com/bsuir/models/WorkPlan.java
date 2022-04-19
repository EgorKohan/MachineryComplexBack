package com.bsuir.models;

import javax.persistence.*;

@Entity
@Table(name = "work_plans")
public class WorkPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private SelfPropelledMachine machine;

    @OneToOne(fetch = FetchType.LAZY)
    private Trailer trailer;

    @OneToOne(fetch = FetchType.LAZY)
    private AgriculturalOperation operation;

    @Column(name = "work_per_shift")
    private Float workPerShift;

}
