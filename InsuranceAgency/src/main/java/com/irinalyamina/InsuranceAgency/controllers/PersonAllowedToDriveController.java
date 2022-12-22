package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.PersonAllowedToDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("personsAllowedToDrive", personAllowedToDriveService.list());
        return "personAllowedToDrive/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        PersonAllowedToDrive personAllowedToDrive = personAllowedToDriveService.getById(id);
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(personAllowedToDrive.getPolicies());

        model.addAttribute("personAllowedToDrive", personAllowedToDrive);
        model.addAttribute("policies", list);
        return "personAllowedToDrive/details";
    }
}