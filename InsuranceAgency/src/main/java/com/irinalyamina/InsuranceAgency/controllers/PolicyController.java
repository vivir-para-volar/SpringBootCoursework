package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import com.irinalyamina.InsuranceAgency.services.PersonAllowedToDriveService;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
import com.irinalyamina.InsuranceAgency.services.PolicyholderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;
    private final InsuranceEventService insuranceEventService;
    private final PersonAllowedToDriveService personAllowedToDriveService;
    private final PolicyholderService policyholderService;

    public PolicyController(PolicyService policyService, InsuranceEventService insuranceEventService, PersonAllowedToDriveService personAllowedToDriveService, PolicyholderService policyholderService) {
        this.policyService = policyService;
        this.insuranceEventService = insuranceEventService;
        this.personAllowedToDriveService = personAllowedToDriveService;
        this.policyholderService = policyholderService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("policies", policyService.list());
        return "policy/list";
    }

    @GetMapping("/listUser")
    public String listUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Policyholder policyholder = policyholderService.getByEmail(email);
        model.addAttribute("policies", policyholder.getPolicies());
        return "policy/listUser";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        model.addAttribute("policy", policyService.getById(id));
        return "policy/details";
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("policy", policyService.getById(id));
        return "policy/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("policy") @Valid Policy policy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "policy/edit";
        }
        checkForErrors(policy, bindingResult);
        if (bindingResult.hasErrors()) {
            return "policy/edit";
        }

        policyService.edit(policy);
        return "redirect:/policy/details/" + policy.getId();
    }

    @GetMapping("/editPersonsAllowedToDrive/{policyId}")
    public String editPersonsAllowedToDriveGet(Model model, @PathVariable("policyId") Long policyId) {
        model.addAttribute("policy", policyService.getById(policyId));
        model.addAttribute("listPersonsAllowedToDrive", personAllowedToDriveService.list());
        return "policy/editPersonsAllowedToDrive";
    }

    @PostMapping("/editPersonsAllowedToDrive")
    public String editPersonsAllowedToDrivePost(Model model, @ModelAttribute("policy") Policy policy, BindingResult bindingResult) {
        if (policy.getPersonsAllowedToDrive().size() == 0) {
            bindingResult.addError(new FieldError(
                    "policy", "personsAllowedToDrive",
                    policy.getPersonsAllowedToDrive(),
                    false, null, null,
                    "Выберите хотя бы одно лицо, допущенное к управлению")
            );
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("policy", policy);
            model.addAttribute("listPersonsAllowedToDrive", personAllowedToDriveService.list());
            return "policy/editPersonsAllowedToDrive";
        }

        Policy policyEdit = policyService.getById(policy.getId());
        policyEdit.setPersonsAllowedToDrive(policy.getPersonsAllowedToDrive());
        policyService.edit(policyEdit);
        return "redirect:/policy/details/" + policyEdit.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Policy policy = policyService.getById(id);
        model.addAttribute("policy", policy);
        model.addAttribute("hasInsuranceEvents", policy.getInsuranceEvents().size() != 0);
        return "policy/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("policy") Policy policy) {
        Policy policyDelete = policyService.getById(policy.getId());
        if (policyDelete.getInsuranceEvents().size() != 0) {
            model.addAttribute("policy", policyDelete);
            model.addAttribute("hasInsuranceEvents", true);
            return "policy/delete";
        }

        policyService.delete(policyDelete.getId());
        return "redirect:/policy/list";
    }

    private void checkForErrors(Policy policy, BindingResult bindingResult) {
        if (policy.getInsurancePremium() <= 0) {
            bindingResult.addError(new FieldError(
                    "policy", "insurancePremium",
                    policy.getInsurancePremium(),
                    false, null, null,
                    "Страховая премия не может быть меньше или равна 0")
            );
        }
        if (policy.getInsurancePremium() >= policy.getInsuranceAmount()) {
            bindingResult.addError(new FieldError(
                    "policy", "insurancePremium",
                    policy.getInsurancePremium(),
                    false, null, null,
                    "Страховая премия не может быть больше или равна Страховой сумме")
            );
        }

        Policy policyOld = policyService.getById(policy.getId());
        if (policy.getExpirationDate().isAfter(policyOld.getExpirationDate())) {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Срок действия полиса нельзя увеличить")
            );
        }
        if (policy.getExpirationDate().isBefore(policy.getDateOfConclusion())) {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Дата окончания действия не может быть меньше Даты заключения")
            );
        }

        LocalDate maxIncidentDate = insuranceEventService.searchMaxIncidentDateByPolicyId(policy.getId());
        if (maxIncidentDate != null) {
            if (policy.getExpirationDate().isBefore(maxIncidentDate)) {
                bindingResult.addError(new FieldError(
                        "policy", "expirationDate",
                        policy.getExpirationDate(),
                        false, null, null,
                        "Дата окончания действия не может быть меньше даты последнего страхового случая")
                );
            }
        }
    }
}