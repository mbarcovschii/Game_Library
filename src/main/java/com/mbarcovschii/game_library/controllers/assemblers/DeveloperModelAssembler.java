package com.mbarcovschii.game_library.controllers.assemblers;

import com.mbarcovschii.game_library.controllers.DeveloperController;
import com.mbarcovschii.game_library.model.Developer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DeveloperModelAssembler implements RepresentationModelAssembler<Developer, EntityModel<Developer>> {

    @Override
    public EntityModel<Developer> toModel(Developer developer) {

        return EntityModel.of(developer,
                linkTo(methodOn(DeveloperController.class).getOneDeveloperById(developer.getDeveloperId())).
                        withSelfRel(),
                linkTo(methodOn(DeveloperController.class).getAllDevelopers()).
                        withRel("developers"));
    }
}
