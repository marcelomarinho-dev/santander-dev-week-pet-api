package dev.marcelomarinho.petapi.service;

import dev.marcelomarinho.petapi.domain.model.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> findAll();
    Owner findById(Long id);
    Owner create(Owner owner);
    Owner update(Long id, Owner owner);
    void delete(Long id);

}
