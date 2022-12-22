package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<PolicyForList> list = new ArrayList<>();

        for (var i = 0; i < listPolicies.size(); i++){

            list.add(new PolicyForList(
                    listPolicies.get(i),
                    listPolicies.get(i).getPolicyholder().getFullName(),
                    listPolicies.get(i).getCar().getModel()
            ));
        }

        model.addAttribute("policies", list);
        return "policy/list";
    }
}