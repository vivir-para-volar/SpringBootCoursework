package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.Report;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final PolicyService policyService;
    private final InsuranceEventService insuranceEventService;

    public ReportController(PolicyService policyService, InsuranceEventService insuranceEventService) {
        this.policyService = policyService;
        this.insuranceEventService = insuranceEventService;
    }

    @GetMapping()
    public String reportGet(Model model) {
        model.addAttribute("report", new Report());
        return "report";
    }

    @PostMapping()
    public String reportPost(Model model, @ModelAttribute("report") @Valid Report report, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "report";
        }
        if (report.getStartDate().isAfter(report.getEndDate())) {
            bindingResult.addError(new FieldError(
                    "report", "startDate",
                    report.getStartDate(),
                    false, null, null,
                    "Дата начала не может быть больше даты окончания")
            );
            return "report";
        }

        if (report.getInsuranceType().equals("all")) {
            List<Policy> listPolicies = policyService.list();
            List<InsuranceEvent> listInsuranceEvents = insuranceEventService.list();

            listPolicies.removeIf(value -> value.getDateOfConclusion().isBefore(report.getStartDate()) || value.getDateOfConclusion().isAfter(report.getEndDate()));
            int countPolicies = listPolicies.size();
            int sumInsurancePremium = 0;
            for (var item : listPolicies) {
                sumInsurancePremium += item.getInsurancePremium();
            }

            listInsuranceEvents.removeIf(value -> value.getIncidentDate().isBefore(report.getStartDate()) || value.getIncidentDate().isAfter(report.getEndDate()));
            int sumInsurancePayment = 0;
            for (var item : listInsuranceEvents) {
                sumInsurancePayment += item.getInsurancePayment();
            }

            model.addAttribute("report", report);
            model.addAttribute("countPolicies", countPolicies);
            model.addAttribute("sumInsurancePremium", sumInsurancePremium);
            model.addAttribute("sumInsurancePayment", sumInsurancePayment);
            return "report";

        } else {
            List<Policy> listPolicies = policyService.list();
            List<InsuranceEvent> listInsuranceEvents = insuranceEventService.list();

            listPolicies.removeIf(value -> !value.getInsuranceType().equals(report.getInsuranceType()) || value.getDateOfConclusion().isBefore(report.getStartDate()) || value.getDateOfConclusion().isAfter(report.getEndDate()));
            int countPolicies = listPolicies.size();
            int sumInsurancePremium = 0;
            for (var item : listPolicies) {
                sumInsurancePremium += item.getInsurancePremium();
            }

            listInsuranceEvents.removeIf(value -> !value.getPolicy().getInsuranceType().equals(report.getInsuranceType()) || value.getIncidentDate().isBefore(report.getStartDate()) || value.getIncidentDate().isAfter(report.getEndDate()));
            int sumInsurancePayment = 0;
            for (var item : listInsuranceEvents) {
                sumInsurancePayment += item.getInsurancePayment();
            }

            model.addAttribute("report", report);
            model.addAttribute("countPolicies", countPolicies);
            model.addAttribute("sumInsurancePremium", sumInsurancePremium);
            model.addAttribute("sumInsurancePayment", sumInsurancePayment);
            return "report";
        }
    }
}
