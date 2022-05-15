package com.bsuir.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_plans")
public class WorkPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private SelfPropelledMachineTemplate machineTemplate;

    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private TrailerTemplate trailerTemplate;

    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private AgriculturalOperation operation;

    @Column(name = "work_per_shift")
    private Float workPerShift;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkPlan that = (WorkPlan) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
