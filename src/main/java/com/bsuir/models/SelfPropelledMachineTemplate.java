package com.bsuir.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "self_propelled_machine_templates")
public class SelfPropelledMachineTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_name", unique = true)
    private String machineName;

    @Column(name = "code_id", unique = true)
    private String codeId;

    @OneToMany(mappedBy = "machineTemplate", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<SelfPropelledMachine> selfPropelledMachines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SelfPropelledMachineTemplate that = (SelfPropelledMachineTemplate) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
