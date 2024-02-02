package dev.marcelomarinho.petapi.domain.dto;

import dev.marcelomarinho.petapi.domain.model.Pet;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

public record PetDto(Long id, String name, PetSpeciesDto petSpeciesDto, Integer age, BigDecimal weight) {

    public PetDto(Pet model) {
        this(model.getId(),
                model.getName(),
                ofNullable(model.getSpecies()).map(PetSpeciesDto::new).orElse(null),
                model.getAge(),
                model.getWeight());
    }

    public Pet convertToModel() {
        Pet model = new Pet();
        model.setId(this.id);
        model.setName(this.name);
        model.setSpecies(ofNullable(this.petSpeciesDto).map(PetSpeciesDto::convertToModel).orElse(null));
        model.setAge(this.age);
        model.setWeight(this.weight);
        return model;
    }
}
