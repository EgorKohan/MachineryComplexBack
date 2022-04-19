package com.bsuir.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "agricultural_operations")
public class AgriculturalOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_id")
    private String codeId;

    @Column(name = "name")
    private String name;

    @Column(name = "work_volume")
    private Float workVolume;

    @Column(name = "unit")
    @Enumerated(value = EnumType.STRING)
    private EUnit unit;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

}
