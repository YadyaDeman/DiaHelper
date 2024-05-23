package de.ait_tr.DiaHelper.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "User name can not be empty")
    @Pattern(
            regexp = "[A-Z][a-z]{2,}",
            message = "User name should be at least 3 character length, " +
                    " start with capital letter and may contain only latin characters"
    )
    private String name;

    @Column(name = "password")
    @NotNull(message = "Password can not be null")
    @NotEmpty(message = "Password can not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character (@$!%*?&)"
    )
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "email")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format: email should contain only latin letters, digits, '@', '-', '_', and have a valid domain"
    )
    @NotNull(message = "Email can not be null")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @Column(name = "glucose_level")
    @NotNull(message = "Password can not be null")
    @NotEmpty(message = "Password can not be null")
    private BigDecimal glucoseLevel;

    @Column(name = "weight")
    private double weight;

    @Column(name = "height")
    private double height;

    @Column(name = "age")
    private int age;


    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(BigDecimal glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive && Double.compare(user.weight, weight) == 0 && Double.compare(user.height, height) == 0 && age == user.age && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(email, user.email) && Objects.equals(glucoseLevel, user.glucoseLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, isActive, role, email, glucoseLevel, weight, height, age);
    }


    @Override
    public String toString() {
        return String.format("User: ID - %d, name - %s, password - %s, active - %s, role - %s, email - %s, glucoseLevel - %.2f, weight - %.2f, height - %.2f, age - %d",
                id, name, password, isActive ? "yes" : "no", role, email, glucoseLevel, weight, height, age);
    }
}
