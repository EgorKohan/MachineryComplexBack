package com.bsuir.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "self_propelled_machines")
public class SelfPropelledMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_name")
    private String inventoryNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_template_id", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SelfPropelledMachine that = (SelfPropelledMachine) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
