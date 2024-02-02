package dev.marcelomarinho.petapi.service.impl;

import dev.marcelomarinho.petapi.domain.model.Owner;
import dev.marcelomarinho.petapi.domain.repository.OwnerRepository;
import dev.marcelomarinho.petapi.service.OwnerService;
import dev.marcelomarinho.petapi.service.exception.BusinessException;
import dev.marcelomarinho.petapi.service.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository repository;

    @Transactional(readOnly = true)
    public List<Owner> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Owner findById(Long id) {
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Transactional
    public Owner create(Owner newOwner) {
        ofNullable(newOwner).orElseThrow(() -> new BusinessException("Owner must not be null."));

        if (newOwner.getId() != null && repository.existsById(newOwner.getId())) {
            throw new BusinessException("A record of Owner with this Id already exists.");
        }

        return repository.save(newOwner);
    }

    @Transactional
    public Owner update(Long id, Owner owner) {
        Owner dbOwner = repository.findById(id).orElseThrow(RecordNotFoundException::new);
        if (!Objects.equals(dbOwner.getId(), owner.getId())) {
            throw new BusinessException("Changing Ids are not allowed.");
        }

        dbOwner.setName(owner.getName());

        dbOwner.getPets().clear();
        dbOwner.getPets().addAll(owner.getPets());

        return repository.save(dbOwner);
    }

    @Transactional
    public void delete(Long id) {
        Owner owner = findById(id);
        repository.delete(owner);
    }
}
