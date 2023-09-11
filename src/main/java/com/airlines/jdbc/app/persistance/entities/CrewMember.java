package com.airlines.jdbc.app.persistance.entities;

import com.airlines.jdbc.app.persistance.entities.enamFields.CrewMemberCitizenship;
import com.airlines.jdbc.app.persistance.entities.enamFields.CrewMemberPosition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crewmember")
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "position")
    private CrewMemberPosition position;

    @Column(nullable = false, name = "birthday")
    private String birthday;

    @Column(nullable = false, name = "citizenship")
    private CrewMemberCitizenship citizenship;

    @ManyToMany(mappedBy = "crew_members")
    List<Crew> crews = new ArrayList<Crew>();
}
