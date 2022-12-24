package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
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

    public PolicyController(PolicyService policyService, InsuranceEventService insuranceEventService) {
        this.policyService = policyService;
        this.insuranceEventService = insuranceEventService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("policies", policyService.list());
        return "policy/list";
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
        Policy policyOld = policyService.getById(policy.getId());
        policy.setPolicyholder(policyOld.getPolicyholder());
        policy.setCar(policyOld.getCar());
        policy.setEmployee(policyOld.getEmployee());
        policy.setPersonsAllowedToDrive(policyOld.getPersonsAllowedToDrive());

        checkForErrorsDuringEdit(policy, policyOld, bindingResult);
        if (bindingResult.hasErrors()) {
            return "policy/edit";
        }

        policyService.edit(policy);
        return "redirect:/policy/details/" + policy.getId();
    }

    private void checkForErrors(Policy policy, BindingResult bindingResult){
        if (policy.getInsurancePremium() <= 0) {
            bindingResult.addError(new FieldError(
                    "policy", "insurancePremium",
                    policy.getInsurancePremium(),
                    false, null, null,
                    "Страховая премия не может быть меньше или равна 0")
            );
        }
        if (policy.getInsuranceAmount() <= 0) {
            bindingResult.addError(new FieldError(
                    "policy", "insuranceAmount",
                    policy.getInsuranceAmount(),
                    false, null, null,
                    "Страховая сумма не может быть меньше или равна 0")
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
        if(policy.getExpirationDate().isBefore(policy.getDateOfConclusion()))
        {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Дата окончания действия не может быть меньше Даты заключения")
            );
        }
    }

    private void checkForErrorsDuringEdit(Policy policy, Policy policyOld, BindingResult bindingResult){
        checkForErrors(policy, bindingResult);

        if (policy.getExpirationDate().isAfter(policyOld.getExpirationDate())) {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Срок действия полиса нельзя увеличить")
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