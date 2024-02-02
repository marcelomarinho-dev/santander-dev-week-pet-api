package dev.marcelomarinho.petapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marcelomarinho.petapi.domain.dto.OwnerDto;
import dev.marcelomarinho.petapi.domain.model.Owner;
import dev.marcelomarinho.petapi.service.OwnerService;
import dev.marcelomarinho.petapi.service.exception.BusinessException;
import dev.marcelomarinho.petapi.service.exception.RecordNotFoundException;
import dev.marcelomarinho.petapi.utils.PetApiTestingUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService service;

    @Test
    @DisplayName("GET /owners - 200")
    public void listAllShouldReturnAListOfAllOwnerRecords() throws Exception {
        Owner newOwner = new OwnerDto(1L, "owner", new ArrayList<>()).convertToModel();

        given(service.findAll()).willReturn(Lists.list(newOwner));

        MockHttpServletResponse response = mockMvc.perform(
            get("/owners")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String json = response.getContentAsString();
        List<OwnerDto> ownerDtoList = PetApiTestingUtils.fromJson(json, new TypeReference<>() {});

        assertEquals(200, response.getStatus());
        assertEquals(1, ownerDtoList.size());
    }

    @Test
    @DisplayName("GET /owners/{id} - 200")
    public void findByIdShouldReturnOwnerRecordAccordingToInformedId() throws Exception {
        Owner existingOwner = new OwnerDto(1L, "owner", new ArrayList<>()).convertToModel();

        given(service.findById(existingOwner.getId())).willReturn(existingOwner);

        MockHttpServletResponse response = mockMvc.perform(
                get("/owners/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String json = response.getContentAsString();
        OwnerDto ownerDto = PetApiTestingUtils.fromJson(json, new TypeReference<>() {});

        assertEquals(200, response.getStatus());
        assertEquals(ownerDto, new OwnerDto(existingOwner));
    }

    @Test
    @DisplayName("GET /owners/{id} - 404")
    public void findByIdShouldReturnStatusCode404WhenNoRecordsMatchInformedId() throws Exception {
        given(service.findById(1L)).willThrow(RecordNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get("/owners/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("POST /owners - 201")
    public void createShouldReturnStatusCode201WhenSuccessfullyCreatingNewRecord() throws Exception {
        OwnerDto newOwnerDto = new OwnerDto(1L, "New Owner", new ArrayList<>());
        Owner model = newOwnerDto.convertToModel();
        given(service.create(model)).willReturn(model);

        MockHttpServletResponse response = mockMvc.perform(
                post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newOwnerDto))
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("http://localhost/owners/1", response.getHeader("Location"));
    }

    @Test
    @DisplayName("POST /owners - 500")
    public void createShouldReturnStatusCode500WhenProvidingInvalidData() throws Exception {
        OwnerDto newOwnerDto = new OwnerDto(1L, "", new ArrayList<>());
        Owner model = newOwnerDto.convertToModel();
        given(service.create(model)).willReturn(model);

        MockHttpServletResponse response = mockMvc.perform(
                post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newOwnerDto))
        ).andReturn().getResponse();

        assertEquals(500, response.getStatus());
    }

    @Test
    @DisplayName("POST /owners - 422")
    public void createShouldReturnStatusCode422WhenBusinessExceptionIsThrown() throws Exception {
        OwnerDto newOwnerDto = new OwnerDto(1L, "New Owner", new ArrayList<>());
        Owner model = newOwnerDto.convertToModel();
        given(service.create(model)).willThrow(BusinessException.class);

        MockHttpServletResponse response = mockMvc.perform(
                post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newOwnerDto))
        ).andReturn().getResponse();

        assertEquals(422, response.getStatus());
    }

    @Test
    @DisplayName("PUT /owners/{id} - 200")
    public void updateShouldReturnStatusCode200WhenSuccessfullyUpdated() throws Exception {
        OwnerDto ownerDto = new OwnerDto(1L, "Owner", new ArrayList<>());
        given(service.update(1L, ownerDto.convertToModel())).willReturn(ownerDto.convertToModel());

        MockHttpServletResponse response = mockMvc.perform(
                put("/owners/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ownerDto))
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("PUT /owners/{id} - 404")
    public void updateShouldReturnStatusCode404WhenRecordNotFoundExceptionIsThrown() throws Exception {
        OwnerDto ownerDto = new OwnerDto(1L, "Owner", new ArrayList<>());
        given(service.update(1L, ownerDto.convertToModel())).willThrow(RecordNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                put("/owners/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ownerDto))
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("PUT /owners/{id} - 422")
    public void updateShouldReturnStatusCode422WhenBusinessExceptionIsThrown() throws Exception {
        OwnerDto ownerDto = new OwnerDto(1L, "Owner", new ArrayList<>());
        given(service.update(1L, ownerDto.convertToModel())).willThrow(BusinessException.class);

        MockHttpServletResponse response = mockMvc.perform(
                put("/owners/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ownerDto))
        ).andReturn().getResponse();

        assertEquals(422, response.getStatus());
    }

    @Test
    @DisplayName("DELETE /owners/{id} - 204")
    public void deleteShouldReturnStatusCode204WhenSuccessfullyDeletedRecord() throws Exception {
        doNothing().when(service).delete(1L);

        MockHttpServletResponse response = mockMvc.perform(
                delete("/owners/{id}", 1)
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("DELETE /owners/{id} - 404")
    public void deleteShouldReturnStatusCode404WhenRecordNotFoundExceptionIsThrown() throws Exception {
        doThrow(RecordNotFoundException.class).when(service).delete(1L);

        MockHttpServletResponse response = mockMvc.perform(
                delete("/owners/{id}", 1)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

}