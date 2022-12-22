package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import com.irinalyamina.InsuranceAgency.services.PersonAllowedToDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/personAllowedToDrive")
public class PersonAllowedToDriveController {

    private final PersonAllowedToDriveService personAllowedToDriveService;

    public PersonAllowedToDriveController(PersonAllowedToDriveService personAllowedToDriveService) {
        this.personAllowedToDriveService = personAllowedToDriveService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<PersonAllowedToDrive> list = personAllowedToDriveService.list();
        model.addAttribute("personsAllowedToDrive", list);
        return "personAllowedToDrive/list";
    }
}