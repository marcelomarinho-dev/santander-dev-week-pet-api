package dev.marcelomarinho.petapi.service.impl;

import dev.marcelomarinho.petapi.domain.dto.OwnerDto;
import dev.marcelomarinho.petapi.domain.model.Owner;
import dev.marcelomarinho.petapi.domain.repository.OwnerRepository;
import dev.marcelomarinho.petapi.service.exception.BusinessException;
import dev.marcelomarinho.petapi.service.exception.RecordNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @InjectMocks
    private OwnerServiceImpl service;

    @Mock
    private OwnerRepository repository;

    @Test
    @DisplayName("findAll should call repository.findAll")
    public void findAllShouldCallRepositoryFindAll() {
        List<Owner> ownerList = new ArrayList<Owner>();
        given(repository.findAll()).willReturn(ownerList);

        service.findAll();

        then(repository).should().findAll();
    }

    @Test
    @DisplayName("findById should call repository.findById with provided Id")
    public void findByIdShouldCallRepositoryFindById() {
        Owner owner = new OwnerDto(1L, "Owner", new ArrayList<>()).convertToModel();
        given(repository.findById(1L)).willReturn(Optional.of(owner));

        service.findById(1L);

        then(repository).should().findById(1L);
    }

    @Test
    @DisplayName("findById should throw RecordNotFoundException when provided invalid Id")
    public void findByIdShouldThrowRecordNotFoundException() {
        given(repository.findById(1L)).willThrow(RecordNotFoundException.class);

        assertThrowsExactly(RecordNotFoundException.class,
                () -> service.findById(1L));
    }

    @Test
    @DisplayName("create should throw BusinessException when Owner is null")
    public void createShouldThrowBusinessExceptionWhenOwnerIsNull() {
        Owner nullOwner = null;
        assertThrowsExactly(BusinessException.class, () -> service.create(nullOwner));
    }

    @Test
    @DisplayName("create should throw BusinessException when trying to create new Owner with existing Id")
    public void createShouldThrowBusinessExceptionWhenTryingToCreateOwnerWithExistingId() {
        Owner newOwner = new OwnerDto(1L, "New Owner", new ArrayList<>()).convertToModel();

        given(repository.existsById(1L)).willReturn(true);

        assertThrowsExactly(BusinessException.class, () -> service.create(newOwner));
    }

    @Test
    @DisplayName("create should call repository.save")
    public void createShouldCallRepositorySave() {
        Owner newOwner = new OwnerDto(1L, "New Owner", new ArrayList<>()).convertToModel();

        given(repository.existsById(1L)).willReturn(false);

        service.create(newOwner);

        then(repository).should().save(newOwner);
    }

    @Test
    @DisplayName("update should throw RecordNotFoundException when providing invalid Id")
    public void updateShouldThrowRecordNotFoundExceptionWhenProvidingInvalidId() {
        given(repository.findById(1L)).willThrow(RecordNotFoundException.class);

        Owner owner = new OwnerDto(1L, "Owner", new ArrayList<>()).convertToModel();
        assertThrowsExactly(RecordNotFoundException.class,
                () -> service.update(1L, owner));
    }

    @Test
    @DisplayName("update should throw BusinessException when trying to change Owner Id")
    public void updateShouldThrowBusinessExceptionWhenTryingToChangeOwnerId() {
        Owner existingOwner = new OwnerDto(1L, "Owner", new ArrayList<>()).convertToModel();
        given(repository.findById(1L)).willReturn(Optional.of(existingOwner));

        Owner editedOwner = new OwnerDto(2L, "Owner edited", new ArrayList<>()).convertToModel();
        assertThrowsExactly(BusinessException.class,
                () -> service.update(1L, editedOwner));
    }

    @Test
    @DisplayName("update should call repository.save")
    public void updateShouldCallRepositorySave() {
        Owner existingOwner = new OwnerDto(1L, "Owner", new ArrayList<>()).convertToModel();
        given(repository.findById(1L)).willReturn(Optional.of(existingOwner));

        Owner editedOwner = new OwnerDto(1L, "Owner edited", new ArrayList<>()).convertToModel();
        service.update(1L, editedOwner);

        then(repository).should().save(editedOwner);
    }

    @Test
    @DisplayName("delete should throw RecordNotFoundException when provided invalid Id")
    public void deleteShouldThrowRecordNotFoundExceptionWhenProvidedInvalidId() {
        given(repository.findById(1L)).willThrow(RecordNotFoundException.class);

        assertThrowsExactly(RecordNotFoundException.class,
                () -> service.delete(1L));
    }

    @Test
    @DisplayName("delete should call repository.delete")
    public void deleteShouldCallRepositoryDelete() {
        Owner owner = new OwnerDto(1L, "Owner", new ArrayList<>()).convertToModel();
        given(repository.findById(1L)).willReturn(Optional.of(owner));

        service.delete(1L);

        then(repository).should().delete(owner);
    }


}