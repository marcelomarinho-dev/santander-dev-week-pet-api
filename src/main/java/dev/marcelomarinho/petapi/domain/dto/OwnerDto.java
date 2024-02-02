package dev.marcelomarinho.petapi.domain.dto;

import dev.marcelomarinho.petapi.domain.model.Owner;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record OwnerDto(Long id, @NotBlank String name, List<PetDto> petDtoList) {

    public OwnerDto(Owner model) {
        this (model.getId(),
                model.getName(),
                ofNullable(model.getPets()).orElse(emptyList()).stream().map(PetDto::new).collect(toList()));
    }

    public Owner convertToModel() {
        Owner model = new Owner();
        model.setId(this.id);
        model.setName(this.name);
        model.setPets(ofNullable(this.petDtoList).orElse(emptyList()).stream().map(PetDto::convertToModel).collect(toList()));
        return model;
    }

}
