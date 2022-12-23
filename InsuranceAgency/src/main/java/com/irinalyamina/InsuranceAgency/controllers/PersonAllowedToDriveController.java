package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.PersonAllowedToDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("personAllowedToDrive", new PersonAllowedToDrive());
        return "personAllowedToDrive/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("personAllowedToDrive") @Valid PersonAllowedToDrive personAllowedToDrive, BindingResult bindingResult) {
        if (personAllowedToDriveService.checkDriveRepository(personAllowedToDrive.getDrivingLicence())) {
            bindingResult.addError(new FieldError(
                    "personAllowedToDrive", "drivingLicence",
                    personAllowedToDrive.getDrivingLicence(),
                    false, null, null,
                    "Данное водительское удостоверение уже используется")
            );
        }
        if (bindingResult.hasErrors()) {
            return "personAllowedToDrive/create";
        }

        personAllowedToDrive = personAllowedToDriveService.create(personAllowedToDrive);
        return "redirect:/personAllowedToDrive/details/" + personAllowedToDrive.getId();
    }
}