package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.services.PolicyholderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/policyholder")
public class PolicyholderController {

    private final PolicyholderService policyholderService;

    public PolicyholderController(PolicyholderService policyholderService) {
        this.policyholderService = policyholderService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("policyholders", policyholderService.list());
        return "policyholder/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Policyholder policyholder = policyholderService.getById(id);
        List<Policy> list = policyholder.getPolicies();

        model.addAttribute("policyholder", policyholder);
        model.addAttribute("policies", list);
        return "policyholder/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("policyholder", new Policyholder());
        return "policyholder/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("policyholder") @Valid Policyholder policyholder, BindingResult bindingResult) {
        checkForUniqueness(policyholder, bindingResult);
        if (bindingResult.hasErrors()) {
            return "policyholder/create";
        }

        policyholder = policyholderService.create(policyholder);
        return "redirect:/policyholder/details/" + policyholder.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("policyholder", policyholderService.getById(id));
        return "policyholder/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("policyholder") @Valid Policyholder policyholder, BindingResult bindingResult) {
        checkForUniqueness(policyholder, bindingResult);
        if (bindingResult.hasErrors()) {
            return "policyholder/edit";
        }

        policyholderService.edit(policyholder);
        return "redirect:/policyholder/details/" + policyholder.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Policyholder policyholder = policyholderService.getById(id);
        model.addAttribute("policyholder", policyholder);
        model.addAttribute("hasPolicies", policyholder.getPolicies().size() != 0);
        return "policyholder/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("policyholder") Policyholder policyholder) {
        Policyholder policyholderDelete = policyholderService.getById(policyholder.getId());
        if (policyholderDelete.getPolicies().size() != 0) {
            model.addAttribute("policyholder", policyholderDelete);
            model.addAttribute("hasPolicies", true);
            return "policyholder/delete";
        }

        policyholderService.delete(policyholderDelete.getId());
        return "redirect:/policyholder/list";
    }

    private void checkForUniqueness(Policyholder policyholder, BindingResult bindingResult) {
        if (checkTelephone(policyholder)) {
            bindingResult.addError(new FieldError(
                    "policyholder", "telephone",
                    policyholder.getTelephone(),
                    false, null, null,
                    "Данный Телефон уже используется")
            );
        }
        if (checkEmail(policyholder)) {
            bindingResult.addError(new FieldError(
                    "policyholder", "email",
                    policyholder.getEmail(),
                    false, null, null,
                    "Данный Email уже используется")
            );
        }
        if (checkPassport(policyholder)) {
            bindingResult.addError(new FieldError(
                    "policyholder", "passport",
                    policyholder.getPassport(),
                    false, null, null,
                    "Данный Паспорт уже используется")
            );
        }
    }

    private boolean checkTelephone(Policyholder policyholder) {
        if (policyholder.getId() == null) {
            return policyholderService.checkTelephone(policyholder.getTelephone());
        } else {
            return policyholderService.checkTelephoneExceptId(policyholder.getId(), policyholder.getTelephone());
        }
    }

    private boolean checkEmail(Policyholder policyholder) {
        if (policyholder.getId() == null) {
            return policyholderService.checkEmail(policyholder.getEmail());
        } else {
            return policyholderService.checkEmailExceptId(policyholder.getId(), policyholder.getEmail());
        }
    }

    private boolean checkPassport(Policyholder policyholder) {
        if (policyholder.getId() == null) {
            return policyholderService.checkPassport(policyholder.getPassport());
        } else {
            return policyholderService.checkPassportExceptId(policyholder.getId(), policyholder.getPassport());
        }
    }
}