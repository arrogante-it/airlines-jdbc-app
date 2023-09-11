package com.airlines.jdbc.app.persistance.entities;

import com.airlines.jdbc.app.persistance.entities.enamFields.AirplaneModel;
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
import java.util.Objects;

@Entity
@Table(name = "airplane")
@Accessors(chain = true)
@Getter
@Setter
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "code_name")
    private String codeName;

    @Column(nullable = false, name = "model")
    private AirplaneModel model;

    @Column(nullable = false, name = "manufacture_date")
    private String manufactureDate;

    @Column(nullable = false, name = "capacity")
    private int capacity;

    @Column(nullable = false, name = "flight_range")
    private int flightRange;

    @JoinColumn(name = "crew_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return capacity == airplane.capacity &&
                flightRange == airplane.flightRange &&
                id.equals(airplane.id) &&
                codeName.equals(airplane.codeName) &&
                model == airplane.model &&
                manufactureDate.equals(airplane.manufactureDate) &&
                crew.equals(airplane.crew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeName, model, manufactureDate, capacity, flightRange, crew);
    }
}
