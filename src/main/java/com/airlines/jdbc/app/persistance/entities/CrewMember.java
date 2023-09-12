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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "crewmember")
@Accessors(chain = true)
@Getter
@Setter
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
    private LocalDate birthday;

    @Column(nullable = false, name = "citizenship")
    private CrewMemberCitizenship citizenship;

    @ManyToMany(mappedBy = "crew_members")
    List<Crew> crews = new ArrayList<Crew>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewMember that = (CrewMember) o;
        return id.equals(that.id) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                position == that.position &&
                birthday.equals(that.birthday) &&
                citizenship == that.citizenship &&
                crews.equals(that.crews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, birthday, citizenship, crews);
    }
}
