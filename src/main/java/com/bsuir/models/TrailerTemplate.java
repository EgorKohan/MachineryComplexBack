package com.bsuir.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@ToString
@Table(name = "trailer_templates")
public class TrailerTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trailer_name", unique = true)
    private String trailerName;

    @Column(name = "code_id", unique = true)
    private String codeId;

    @ToString.Exclude
    @OneToMany(mappedBy = "trailerTemplate", fetch = FetchType.LAZY)
    private Set<Trailer> trailers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TrailerTemplate that = (TrailerTemplate) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
