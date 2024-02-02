package dev.marcelomarinho.petapi.domain.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "tb_owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    private List<Pet> pets;

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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner owner)) return false;
        return Objects.equals(getId(), owner.getId()) && Objects.equals(getName(), owner.getName()) && Objects.equals(getPets(), owner.getPets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPets());
    }
}
