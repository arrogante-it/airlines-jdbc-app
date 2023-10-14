package com.airlines.jdbc.app.persistance.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Accessors(chain = true)
public class Crew {
    private Long id;

    private String name;

    List<CrewMember> crewMembers = new ArrayList<CrewMember>();

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

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crewMembers=" + crewMembers +
                '}';
    }
}
