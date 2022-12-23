package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Employee;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employees", employeeService.list());
        return "employee/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(employee.getPolicies());

        model.addAttribute("employee", employee);
        model.addAttribute("policies", list);
        return "employee/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (employeeService.checkTelephone(employee.getTelephone())) {
            bindingResult.addError(new FieldError(
                    "employee", "telephone",
                    employee.getTelephone(),
                    false, null, null,
                    "Данный телефон уже используется")
            );
        }
        if (employeeService.checkEmail(employee.getEmail())) {
            bindingResult.addError(new FieldError(
                    "employee", "email",
                    employee.getEmail(),
                    false, null, null,
                    "Данный Email уже используется")
            );
        }
        if (employeeService.checkPassport(employee.getPassport())) {
            bindingResult.addError(new FieldError(
                    "employee", "passport",
                    employee.getPassport(),
                    false, null, null,
                    "Данный паспорт уже используется")
            );
        }
        if (bindingResult.hasErrors()) {
            return "employee/create";
        }

        employee = employeeService.create(employee);
        return "redirect:/employee/details/" + employee.getId();
    }
}