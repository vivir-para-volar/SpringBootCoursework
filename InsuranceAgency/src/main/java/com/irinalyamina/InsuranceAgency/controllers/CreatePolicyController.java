package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/policy/create")
public class CreatePolicyController {

    private final PolicyService policyService;
    private final PolicyholderService policyholderService;
    private final CarService carService;
    private final EmployeeService employeeService;
    private final PersonAllowedToDriveService personAllowedToDriveService;

    public CreatePolicyController(PolicyService policyService, PolicyholderService policyholderService, CarService carService, EmployeeService employeeService, PersonAllowedToDriveService personAllowedToDriveService) {
        this.policyService = policyService;
        this.policyholderService = policyholderService;
        this.carService = carService;
        this.employeeService = employeeService;
        this.personAllowedToDriveService = personAllowedToDriveService;
    }

    @GetMapping()
    public String createGet(Model model) {
        model.addAttribute("policyholders", policyholderService.list());
        return "policy/create/choosePolicyholder";
    }

    @GetMapping("/choosePolicyholder/{policyholderId}")
    public String createChoosePolicyholder(Model model, @PathVariable("policyholderId") Long policyholderId) {
        try {
            Policyholder policyholder = policyholderService.getById(policyholderId);
        } catch (Exception exp) {
            model.addAttribute("policyholders", policyholderService.list());
            return "policy/create/choosePolicyholder";
        }

        model.addAttribute("policyholderId", policyholderId);
        model.addAttribute("cars", carService.list());
        return "policy/create/chooseCar";
    }

    @GetMapping("/chooseCar/{policyholderId}/{carId}")
    public String createChooseCar(Model model, @PathVariable("policyholderId") Long policyholderId, @PathVariable("carId") Long carId) {
        Policyholder policyholder;
        Car car;
        try {
            policyholder = policyholderService.getById(policyholderId);
        } catch (Exception exp) {
            model.addAttribute("policyholders", policyholderService.list());
            return "policy/create/choosePolicyholder";
        }
        try {
            car = carService.getById(carId);
        } catch (Exception exp) {
            model.addAttribute("policyholderId", policyholderId);
            model.addAttribute("cars", carService.list());
            return "policy/create/chooseCar";
        }

        Policy policy = new Policy();
        policy.setExpirationDate(LocalDate.now());
        policy.setPolicyholder(policyholder);
        policy.setCar(car);
        policy.setEmployee(employeeService.getById(1L));
        model.addAttribute("policy", policy);
        return "policy/create/chooseInfo";
    }

    @PostMapping("/chooseInfo")
    public String createChooseInfo(Model model, @ModelAttribute("policy") @Valid Policy policy, BindingResult bindingResult) {
        if (policy.getPolicyholder() == null) {
            model.addAttribute("policyholders", policyholderService.list());
            return "policy/create/choosePolicyholder";
        }
        if (policy.getCar() == null) {
            model.addAttribute("policyholderId", policy.getPolicyholder().getId());
            model.addAttribute("cars", carService.list());
            return "policy/create/chooseCar";
        }
        if (policy.getEmployee() == null) {
            return "redirect:/policy/list";
        }

        if (bindingResult.hasErrors()) {
            return "policy/create/chooseInfo";
        }

        if (policy.getExpirationDate().isEqual(LocalDate.of(2006, 6, 6))) {
            policy.setExpirationDate(policy.getDateOfConclusion().plusMonths(6));
        } else if (policy.getExpirationDate().isEqual(LocalDate.of(2012, 12, 12))) {
            policy.setExpirationDate(policy.getDateOfConclusion().plusYears(1));
        } else {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Некорректная дата заключения")
            );
        }
        checkForErrors(policy, bindingResult);
        if (bindingResult.hasErrors()) {
            return "policy/create/chooseInfo";
        }

        model.addAttribute("policy", policy);
        model.addAttribute("listPersonsAllowedToDrive", personAllowedToDriveService.list());
        return "policy/create/choosePersonsAllowedToDrive";
    }

    @PostMapping("/choosePersonsAllowedToDrive")
    public String editPersonsAllowedToDrivePost(Model model, @ModelAttribute("policy") @Valid Policy policy, BindingResult bindingResult) {
        if (policy.getPolicyholder() == null) {
            model.addAttribute("policyholders", policyholderService.list());
            return "policy/create/choosePolicyholder";
        }
        if (policy.getCar() == null) {
            model.addAttribute("policyholderId", policy.getPolicyholder().getId());
            model.addAttribute("cars", carService.list());
            return "policy/create/chooseCar";
        }
        if (policy.getEmployee() == null) {
            return "redirect:/policy/list";
        }

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
            return "policy/create/choosePersonsAllowedToDrive";
        }

        policy = policyService.create(policy);
        return "redirect:/policy/details/" + policy.getId();
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
        if (policy.getExpirationDate().isBefore(policy.getDateOfConclusion())) {
            bindingResult.addError(new FieldError(
                    "policy", "expirationDate",
                    policy.getExpirationDate(),
                    false, null, null,
                    "Дата окончания действия не может быть меньше Даты заключения")
            );
        }
    }
}