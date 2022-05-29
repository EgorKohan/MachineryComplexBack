package com.bsuir.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private SelfPropelledMachineTemplate machineTemplate;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private TrailerTemplate trailerTemplate;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private AgriculturalOperation operation;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, mappedBy = "workPlan")
    private Set<Period> periods;

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
