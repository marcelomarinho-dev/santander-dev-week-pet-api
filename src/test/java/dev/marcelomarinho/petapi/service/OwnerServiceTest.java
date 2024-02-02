package dev.marcelomarinho.petapi.service;

import dev.marcelomarinho.petapi.domain.dto.OwnerDto;
import dev.marcelomarinho.petapi.domain.model.Owner;
import dev.marcelomarinho.petapi.domain.repository.OwnerRepository;
import dev.marcelomarinho.petapi.service.impl.OwnerServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @InjectMocks
    private OwnerServiceImpl service;

    @Mock
    private OwnerRepository repository;

    @Test
    public void testestestes() {
        Owner newOwner = new OwnerDto(1L, "owner", new ArrayList<>()).convertToModel();
        given(repository.findAll()).willReturn(Lists.newArrayList(newOwner));

        List<Owner> owners = service.findAll();

        System.out.println(owners.get(0).getId());
        System.out.println(owners.get(0).getName());

        then(repository).should().findAll();
    }

}