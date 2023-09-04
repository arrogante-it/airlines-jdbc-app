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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String position;

    private String birthday;

    private String citizenship;

    @ManyToMany(mappedBy = "crew_members")
    List<Crew> crews = new ArrayList<Crew>();
}
