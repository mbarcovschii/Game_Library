package com.mbarcovschii.game_library.controllers;

import com.mbarcovschii.game_library.entities.Developer;
import com.mbarcovschii.game_library.entities.DeveloperDTO;
import com.mbarcovschii.game_library.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeveloperController {

    private DeveloperService developerService;

    @Autowired
    public void setDeveloperService(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/developers")
    List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    @PostMapping("/developers")
    Developer postOneDeveloper(@RequestBody DeveloperDTO newDeveloperDTO) {
        return developerService.createDeveloper(newDeveloperDTO.getDeveloper(),
                newDeveloperDTO.getDevelopedGamesIds());
    }

    @GetMapping("/developers/{developerId}")
    Developer getOneDeveloperById(@PathVariable Long developerId) {
        return developerService.getDeveloperById(developerId);
    }

    @DeleteMapping("/developers/{developerId}")
    void deleteOneDeveloperById(@PathVariable Long developerId) {
        developerService.deleteDeveloperById(developerId);
    }
}
