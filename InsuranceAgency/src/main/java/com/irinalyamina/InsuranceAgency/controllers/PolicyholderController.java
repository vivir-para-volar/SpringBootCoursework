package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.PolicyholderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}