package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.controllers.assemblers.DeveloperModelAssembler;
import com.mbarcovschii.game_library.model.Developer;
import com.mbarcovschii.game_library.model.dto.DeveloperDTO;
import com.mbarcovschii.game_library.model.dto.DeveloperPartialUpdate;
import com.mbarcovschii.game_library.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DeveloperController {

    private DeveloperService developerService;
    private DeveloperModelAssembler assembler;

    @Autowired
    public void setDeveloperService(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Autowired
    public void setDeveloperModelAssembler(DeveloperModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("/developers")
    public CollectionModel<EntityModel<Developer>> getAllDevelopers() {

        return CollectionModel.of(developerService.getAllDevelopers().stream().
                        map(assembler::toModel).
                        collect(Collectors.toList()),
                linkTo(methodOn(DeveloperController.class).getAllDevelopers()).withSelfRel());
    }

    @PostMapping("/developers")
    public ResponseEntity<EntityModel<Developer>> postOneDeveloper(@RequestBody DeveloperDTO newDeveloperDTO) {

        EntityModel<Developer> entityModel = assembler.toModel(
                developerService.createDeveloper(newDeveloperDTO.getDeveloper(),
                        newDeveloperDTO.getGameIds()));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @GetMapping("/developers/{developerId}")
    public EntityModel<Developer> getOneDeveloperById(@PathVariable Long developerId) {
        return assembler.toModel(developerService.getDeveloperById(developerId));
    }

    @PatchMapping("developers/{developerId}")
    public EntityModel<Developer> patchOneDeveloperById(@RequestBody DeveloperPartialUpdate developerPartialUpdate,
                                                        @PathVariable Long developerId) {

        developerService.updateDeveloper(developerId, developerPartialUpdate.getDeveloperName());
        return assembler.toModel(
                developerService.addNewDeveloperGames(developerId, developerPartialUpdate.getGameIds()));
    }

    @DeleteMapping("/developers/{developerId}")
    public ResponseEntity<?> deleteOneDeveloperById(@PathVariable Long developerId,
                                                    @RequestParam(required = false) List<Long> gameIds) {

        if (gameIds == null) {
            developerService.deleteDeveloperById(developerId);
        } else {
            developerService.deleteDeveloperGames(developerId, gameIds);
        }

        return ResponseEntity.noContent().build();
    }
}
