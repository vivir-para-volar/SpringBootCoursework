package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Policy> listPolicies = policyService.list();
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(listPolicies);
        model.addAttribute("policies", list);
        return "policy/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Policy policy = policyService.getById(id);
        model.addAttribute("policy", policy);
        model.addAttribute("policyholder", policy.getPolicyholder());
        model.addAttribute("car", policy.getCar());
        model.addAttribute("employee", policy.getEmployee());
        model.addAttribute("personsAllowedToDrive", policy.getPersonsAllowedToDrive());
        model.addAttribute("insuranceEvents", policy.getInsuranceEvents());
        return "policy/details";
    }
}