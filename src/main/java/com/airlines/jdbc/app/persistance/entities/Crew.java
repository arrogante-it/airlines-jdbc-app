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

@Entity
@Table(name = "crew")
@Accessors(chain = true)
@Getter
@Setter
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @OneToMany(mappedBy = "airplane")
    List<Airplane> airplanes = new ArrayList<Airplane>();

    @ManyToMany()
    @JoinTable(
            name = "crew_crew_member",
            joinColumns = @JoinColumn(name = "crew_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_member_id")
    )
    List<CrewMember> crewMembers = new ArrayList<CrewMember>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return id.equals(crew.id) &&
                name.equals(crew.name) &&
                airplanes.equals(crew.airplanes) &&
                crewMembers.equals(crew.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, airplanes, crewMembers);
    }
}
