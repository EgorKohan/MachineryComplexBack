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

    @Column(name = "residual_value")
    private BigDecimal residualValue;

    //technical readiness factor
    @Column(name = "trk")
    private Float trk;

    @Column(name = "path_to_photo")
    private String pathToPhoto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trailer trailer = (Trailer) o;
        return id != null && Objects.equals(id, trailer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
