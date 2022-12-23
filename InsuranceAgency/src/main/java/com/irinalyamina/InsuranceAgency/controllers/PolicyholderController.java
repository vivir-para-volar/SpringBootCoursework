package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
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
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(policyholder.getPolicies());

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
        if (policyholderService.checkTelephone(policyholder.getTelephone())) {
            bindingResult.addError(new FieldError(
                    "policyholder", "telephone",
                    policyholder.getTelephone(),
                    false, null, null,
                    "Данный телефон уже используется")
            );
        }
        if (policyholderService.checkEmail(policyholder.getEmail())) {
            bindingResult.addError(new FieldError(
                    "policyholder", "email",
                    policyholder.getEmail(),
                    false, null, null,
                    "Данный Email уже используется")
            );
        }
        if (policyholderService.checkPassport(policyholder.getPassport())) {
            bindingResult.addError(new FieldError(
                    "policyholder", "passport",
                    policyholder.getPassport(),
                    false, null, null,
                    "Данный паспорт уже используется")
            );
        }
        if (bindingResult.hasErrors()) {
            return "policyholder/create";
        }

        policyholder = policyholderService.create(policyholder);
        return "redirect:/policyholder/details/" + policyholder.getId();
    }
}