package com.airlines.jdbc.app.persistance.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CrewMember {
    private Long id;
    private String firstName;
    private String lastName;
    private Position position;
    private LocalDate birthday;
    private Citizenship citizenship;
    private List<Crew> crews;

    private CrewMember(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.position = builder.position;
        this.birthday = builder.birthday;
        this.citizenship = builder.citizenship;
        this.crews = builder.crews;
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Position position;
        private LocalDate birthday;
        private Citizenship citizenship;
        private List<Crew> crews;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public Builder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder citizenship(Citizenship citizenship) {
            this.citizenship = citizenship;
            return this;
        }

        public Builder crews(List<Crew> crews) {
            this.crews = crews;
            return this;
        }

        public CrewMember build() {
            return new CrewMember(this);
        }
    }

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
                citizenship == that.citizenship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, birthday, citizenship);
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position=" + position +
                ", birthday=" + birthday +
                ", citizenship=" + citizenship +
                ", crews=" + crews +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }
}
