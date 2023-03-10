package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Employee;
import com.irinalyamina.InsuranceAgency.models.Policy;
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
        List<Policy> list = employee.getPolicies();

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
        checkForUniqueness(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/create";
        }

        employee = employeeService.create(employee);
        return "redirect:/employee/details/" + employee.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        checkForUniqueness(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/edit";
        }

        employeeService.edit(employee);
        return "redirect:/employee/details/" + employee.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("hasPolicies", employee.getPolicies().size() != 0);
        return "employee/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("employee") Employee employee) {
        Employee employeeDelete = employeeService.getById(employee.getId());
        if (employeeDelete.getPolicies().size() != 0) {
            model.addAttribute("employee", employeeDelete);
            model.addAttribute("hasPolicies", employeeDelete.getPolicies().size() != 0);
            return "employee/delete";
        }

        employeeService.delete(employeeDelete.getId());
        return "redirect:/employee/list";
    }

    private void checkForUniqueness(Employee employee, BindingResult bindingResult) {
        if (checkTelephone(employee)) {
            bindingResult.addError(new FieldError(
                    "employee", "telephone",
                    employee.getTelephone(),
                    false, null, null,
                    "???????????? ?????????????? ?????? ????????????????????????")
            );
        }
        if (checkEmail(employee)) {
            bindingResult.addError(new FieldError(
                    "employee", "email",
                    employee.getEmail(),
                    false, null, null,
                    "???????????? Email ?????? ????????????????????????")
            );
        }
        if (checkPassport(employee)) {
            bindingResult.addError(new FieldError(
                    "employee", "passport",
                    employee.getPassport(),
                    false, null, null,
                    "???????????? ?????????????? ?????? ????????????????????????")
            );
        }
    }

    private boolean checkTelephone(Employee employee) {
        if (employee.getId() == null) {
            return employeeService.checkTelephone(employee.getTelephone());
        } else {
            return employeeService.checkTelephoneExceptId(employee.getId(), employee.getTelephone());
        }
    }

    private boolean checkEmail(Employee employee) {
        if (employee.getId() == null) {
            return employeeService.checkEmail(employee.getEmail());
        } else {
            return employeeService.checkEmailExceptId(employee.getId(), employee.getEmail());
        }
    }

    private boolean checkPassport(Employee employee) {
        if (employee.getId() == null) {
            return employeeService.checkPassport(employee.getPassport());
        } else {
            return employeeService.checkPassportExceptId(employee.getId(), employee.getPassport());
        }
    }
}