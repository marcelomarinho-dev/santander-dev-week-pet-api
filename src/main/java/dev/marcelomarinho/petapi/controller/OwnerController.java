package dev.marcelomarinho.petapi.controller;

import dev.marcelomarinho.petapi.domain.dto.OwnerDto;
import dev.marcelomarinho.petapi.domain.model.Owner;
import dev.marcelomarinho.petapi.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/owners")
@Tag(name = "Owner controller", description = "Endpoint for managing Owner records.")
public class OwnerController {

    @Autowired
    private OwnerService service;

    @GetMapping
    @Operation(summary = "Get all owners", description = "Returns a list of all owner records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<OwnerDto>> listAll() {
        List<Owner> ownerList = service.findAll();
        List<OwnerDto> ownerDtoList = ownerList.stream().map(OwnerDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(ownerDtoList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get owner by Id", description = "Return a owner by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Owner not found with provided Id")
    })
    public ResponseEntity<OwnerDto> findById(@PathVariable Long id) {
        var owner = service.findById(id);
        return ResponseEntity.ok(new OwnerDto(owner));
    }

    @PostMapping
    @Operation(summary = "Create a new owner", description = "Create new owner and return data if successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid data provided")
    })
    public ResponseEntity<OwnerDto> create(@RequestBody @Valid OwnerDto ownerDto) {
        var owner = service.create(ownerDto.convertToModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(owner.getId())
                .toUri();
        return ResponseEntity.created(location).body(new OwnerDto(owner));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an owner", description = "Update owner based on provided Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found with provided Id"),
            @ApiResponse(responseCode = "422", description = "Invalid data provided")
    })
    public ResponseEntity<OwnerDto> update(@PathVariable Long id, @RequestBody @Valid OwnerDto ownerDto) {
        Owner owner = service.update(id, ownerDto.convertToModel());
        return ResponseEntity.ok(new OwnerDto(owner));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an owner", description = "Delete owner based on provided Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found with provided Id")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
