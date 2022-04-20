package com.bsuir.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "trailers")
public class Trailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trailer_template_id", nullable = false)
    private TrailerTemplate trailerTemplate;

    @Column(name = "actual_service_life")
    private Integer actualServiceLifeInMonth;

    @Column(name = "normative_service_life")
    private Integer normativeServiceLifeInMonth;

    @Column(name = "initial_cost")
    private BigDecimal initialCost;

    @Column(name = "residualValue")
    private BigDecimal residualValue;

    //technical readiness factor
    @Column(name = "trk")
    private Float trk;

}
