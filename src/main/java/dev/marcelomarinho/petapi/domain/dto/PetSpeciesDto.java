package dev.marcelomarinho.petapi.domain.dto;

import dev.marcelomarinho.petapi.domain.model.PetSpecies;

public record PetSpeciesDto(Long id, String species) {

    public PetSpeciesDto(PetSpecies model) {
        this(model.getId(),
                model.getSpecies());
    }

    public PetSpecies convertToModel() {
        PetSpecies model = new PetSpecies();
        model.setId(this.id);
        model.setSpecies(this.species);
        return model;
    }
}
