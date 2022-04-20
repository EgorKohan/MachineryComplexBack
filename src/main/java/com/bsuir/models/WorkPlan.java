package com.bsuir.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
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
