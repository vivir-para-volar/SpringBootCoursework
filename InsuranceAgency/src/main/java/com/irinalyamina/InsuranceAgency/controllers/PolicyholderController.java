package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.services.PolicyholderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Policyholder> list = policyholderService.list();
        model.addAttribute("policyholders", list);
        return "policyholder/list";
    }
}