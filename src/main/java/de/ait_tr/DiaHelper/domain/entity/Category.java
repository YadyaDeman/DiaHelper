package de.ait_tr.DiaHelper.domain.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
    @Table(name = "category")
    public class Category {

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

    public String getProductTitle() {
        return title;
    }

    public void setProductTitle(String productTitle) {
        this.title = productTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(title, category.title);
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
