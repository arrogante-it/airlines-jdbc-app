package com.airlines.jdbc.app.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(name = "code_name_idx", columnList = "codeName"))
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codeName;

    @Column(nullable = false)
    private String model;

    @Column(name = "manufacture_date")
    private String manufactureDate;

    private int capacity;

    @Column(name = "flight_range")
    private int flightRange;

    @JoinColumn(name = "crew_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @PostPersist
    public void generateCodeName() {
        if (codeName == null) {
            codeName = StringUtils.joinWith("-", model, id);
        }
    }
}
