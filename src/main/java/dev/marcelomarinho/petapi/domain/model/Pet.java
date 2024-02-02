package dev.marcelomarinho.petapi.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "tb_pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private PetSpecies species;

    private String name;

    private Integer age;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetSpecies getSpecies() {
        return species;
    }

    public void setSpecies(PetSpecies species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return Objects.equals(getId(), pet.getId()) && Objects.equals(getSpecies(), pet.getSpecies()) && Objects.equals(getName(), pet.getName()) && Objects.equals(getAge(), pet.getAge()) && Objects.equals(getWeight(), pet.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSpecies(), getName(), getAge(), getWeight());
    }
}
