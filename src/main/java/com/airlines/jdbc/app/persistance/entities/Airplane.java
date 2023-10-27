package com.airlines.jdbc.app.persistance.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Airplane {
    private Long id;
    private String codeName;
    private Model model;
    private LocalDate manufactureDate;
    private int capacity;
    private int flightRange;
    private Crew crew;

    private Airplane(Builder builder) {
        this.id = builder.id;
        this.codeName = builder.codeName;
        this.model = builder.model;
        this.manufactureDate = builder.manufactureDate;
        this.capacity = builder.capacity;
        this.flightRange = builder.flightRange;
        this.crew = builder.crew;
    }

    public static class Builder {
        private Long id;
        private Model model;
        private String codeName;
        private LocalDate manufactureDate;
        private int capacity;
        private int flightRange;
        private Crew crew;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder flightRange(int flightRange) {
            this.flightRange = flightRange;
            return this;
        }

        public Builder codeName(String codeName) {
            this.codeName = codeName;
            return this;
        }

        public Builder manufactureDate(LocalDate manufactureDate) {
            this.manufactureDate = manufactureDate;
            return this;
        }

        public Builder crew(Crew crew) {
            this.crew = crew;
            return this;
        }

        public Airplane build() {
            return new Airplane(this);
        }
    }

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

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", codeName='" + codeName + '\'' +
                ", model=" + model +
                ", manufactureDate=" + manufactureDate +
                ", capacity=" + capacity +
                ", flightRange=" + flightRange +
                ", crew=" + crew +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(int flightRange) {
        this.flightRange = flightRange;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }
}
