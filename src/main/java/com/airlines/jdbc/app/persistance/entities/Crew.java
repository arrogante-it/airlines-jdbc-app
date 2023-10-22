package com.airlines.jdbc.app.persistance.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Crew {
    private Long id;
    private String name;
    private List<CrewMember> crewMembers;

    private Crew(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.crewMembers = builder.crewMembers;
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<CrewMember> crewMembers = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder crewMembers(List<CrewMember> crewMembers) {
            this.crewMembers = crewMembers;
            return this;
        }

        public Crew build() {
            return new Crew(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return id.equals(crew.id) &&
                name.equals(crew.name) &&
                crewMembers.equals(crew.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, crewMembers);
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crewMembers=" + crewMembers +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
