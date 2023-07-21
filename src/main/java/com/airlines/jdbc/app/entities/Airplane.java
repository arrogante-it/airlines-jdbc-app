package com.airlines.jdbc.app.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table()
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
}
