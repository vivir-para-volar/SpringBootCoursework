package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.modelsForLayout.InsuranceEventExtended;
import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import com.irinalyamina.InsuranceAgency.services.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/insuranceEvent")
public class InsuranceEventController {

    private final InsuranceEventService insuranceEventService;
    private final PolicyService policyService;

    public InsuranceEventController(InsuranceEventService insuranceEventService, PolicyService policyService) {
        this.insuranceEventService = insuranceEventService;
        this.policyService = policyService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<InsuranceEvent> listInsuranceEvents = insuranceEventService.list();
        List<InsuranceEventExtended> list = new ArrayList<>();
        for (var item : listInsuranceEvents) {
            list.add(new InsuranceEventExtended(item));
        }

        model.addAttribute("insuranceEvents", list);
        return "insuranceEvent/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        InsuranceEvent insuranceEvent = insuranceEventService.getById(id);
        model.addAttribute("insuranceEvent", insuranceEvent);
        model.addAttribute("policy", insuranceEvent.getPolicy());
        return "insuranceEvent/details";
    }

    @GetMapping("/create/{policyId}")
    public String createGet(Model model, @PathVariable("policyId") Long policyId) {
        var insuranceEvent = new InsuranceEvent();
        insuranceEvent.setPolicy(policyService.getById(policyId));

        model.addAttribute("insuranceEvent", insuranceEvent);
        return "insuranceEvent/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("insuranceEvent") @Valid InsuranceEvent insuranceEvent, BindingResult bindingResult) {
        checkForErrors(insuranceEvent, bindingResult);
        if (bindingResult.hasErrors()) {
            return "insuranceEvent/create";
        }

        insuranceEvent = insuranceEventService.create(insuranceEvent);
        return "redirect:/policy/details/" + insuranceEvent.getPolicy().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        InsuranceEvent insuranceEvent = insuranceEventService.getById(id);
        model.addAttribute("insuranceEvent", insuranceEvent);
        model.addAttribute("policy", insuranceEvent.getPolicy());
        return "insuranceEvent/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("insuranceEvent") InsuranceEvent insuranceEvent) {
        insuranceEventService.delete(insuranceEvent.getId());
        return "redirect:/insuranceEvent/list";
    }

    private void checkForErrors(InsuranceEvent insuranceEvent, BindingResult bindingResult) {
        if (insuranceEvent.getIncidentDate().isBefore(insuranceEvent.getPolicy().getDateOfConclusion())) {
            bindingResult.addError(new FieldError(
                    "insuranceEvent", "incidentDate",
                    insuranceEvent.getIncidentDate(),
                    false, null, null,
                    "Дата не может быть меньше даты заключения полиса")
            );
        }
        if (insuranceEvent.getIncidentDate().isAfter(insuranceEvent.getPolicy().getExpirationDate())) {
            bindingResult.addError(new FieldError(
                    "insuranceEvent", "incidentDate",
                    insuranceEvent.getIncidentDate(),
                    false, null, null,
                    "Дата не может быть больше даты окончания действия полиса")
            );
        }
        if (insuranceEvent.getPolicy().getInsuranceType().equals("КАСКО") && insuranceEvent.getInsurancePayment() > insuranceEvent.getPolicy().getInsuranceAmount()) {
            bindingResult.addError(new FieldError(
                    "insuranceEvent", "insurancePayment",
                    insuranceEvent.getInsurancePayment(),
                    false, null, null,
                    "Страховая выплата должна быть меньше Страховой суммы")
            );
        }
    }
}