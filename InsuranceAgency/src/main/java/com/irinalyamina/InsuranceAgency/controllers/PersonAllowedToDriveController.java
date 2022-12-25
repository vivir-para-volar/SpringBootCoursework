package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import com.irinalyamina.InsuranceAgency.models.Policy;
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
        List<Policy> list = personAllowedToDrive.getPolicies();

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
        checkForUniqueness(personAllowedToDrive, bindingResult);
        if (bindingResult.hasErrors()) {
            return "personAllowedToDrive/create";
        }

        personAllowedToDrive = personAllowedToDriveService.create(personAllowedToDrive);
        return "redirect:/personAllowedToDrive/details/" + personAllowedToDrive.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("personAllowedToDrive", personAllowedToDriveService.getById(id));
        return "personAllowedToDrive/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("personAllowedToDrive") @Valid PersonAllowedToDrive personAllowedToDrive, BindingResult bindingResult) {
        checkForUniqueness(personAllowedToDrive, bindingResult);
        if (bindingResult.hasErrors()) {
            return "personAllowedToDrive/edit";
        }

        personAllowedToDriveService.edit(personAllowedToDrive);
        return "redirect:/personAllowedToDrive/details/" + personAllowedToDrive.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        PersonAllowedToDrive personAllowedToDrive = personAllowedToDriveService.getById(id);
        model.addAttribute("personAllowedToDrive", personAllowedToDrive);
        model.addAttribute("hasPolicies", personAllowedToDrive.getPolicies().size() != 0);
        return "personAllowedToDrive/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("personAllowedToDrive") PersonAllowedToDrive personAllowedToDrive) {
        PersonAllowedToDrive personAllowedToDriveDelete = personAllowedToDriveService.getById(personAllowedToDrive.getId());
        if (personAllowedToDriveDelete.getPolicies().size() != 0) {
            model.addAttribute("personAllowedToDrive", personAllowedToDriveDelete);
            model.addAttribute("hasPolicies", true);
            return "personAllowedToDrive/delete";
        }

        personAllowedToDriveService.delete(personAllowedToDriveDelete.getId());
        return "redirect:/personAllowedToDrive/list";
    }

    private void checkForUniqueness(PersonAllowedToDrive personAllowedToDrive, BindingResult bindingResult) {
        if (checkDrivingLicence(personAllowedToDrive)) {
            bindingResult.addError(new FieldError(
                    "personAllowedToDrive", "drivingLicence",
                    personAllowedToDrive.getDrivingLicence(),
                    false, null, null,
                    "Данное Водительское удостоверение уже используется")
            );
        }
    }

    private boolean checkDrivingLicence(PersonAllowedToDrive personAllowedToDrive) {
        if (personAllowedToDrive.getId() == null) {
            return personAllowedToDriveService.checkDrivingLicence(personAllowedToDrive.getDrivingLicence());
        } else {
            return personAllowedToDriveService.checkDrivingLicenceExceptId(personAllowedToDrive.getId(), personAllowedToDrive.getDrivingLicence());
        }
    }
}