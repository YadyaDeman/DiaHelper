package de.ait_tr.DiaHelper.domain.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
    @Table(name = "role")
    public class Role {

       @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(title, role.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return String.format("Role: ID - %d, title - %s",
                id, title == null ? null : title.substring(5));
    }
}
