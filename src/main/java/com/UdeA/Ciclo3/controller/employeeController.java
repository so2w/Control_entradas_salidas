package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Employee;
import com.UdeA.Ciclo3.modelos.Enterprise;
import com.UdeA.Ciclo3.service.EmployeeService;
import com.UdeA.Ciclo3.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class employeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping("/ViewEmployee")
    public String viewEmployee (Model model, @ModelAttribute("mensaje")String mensaje){
        List<Employee> listEmployee = employeeService.getAllEmployee();
        model.addAttribute("employeelist",listEmployee);
        model.addAttribute("mensaje",mensaje);
        return "viewEmployee";
    }

    @GetMapping ("/AddEmployee")
    public String newEmployee (Model model, @ModelAttribute("mensaje")String mensaje){
        Employee worker = new Employee();
        model.addAttribute("worker",worker);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> listEnterprise = enterpriseService.getAllEnterprise();
        model.addAttribute("enterpriselist",listEnterprise);
        return "addEmployee";
    }

    @PostMapping("/SaveEmployee")
    public String saveEmployee (Employee worker, RedirectAttributes redirectAttributes){
       if(employeeService.saveOrUpdateEmployee(worker) == true){
           redirectAttributes.addFlashAttribute("mensaje","Guardado correctamente");
           return "redirect:/AddEmployee";
       }
       redirectAttributes.addFlashAttribute("mensaje","Error al Guardar");
       return "redirect:/AddEmployee";
    }

    @GetMapping("/EditEmployee/{id}")
    public String editEmployee (Model model, @PathVariable Long id,@ModelAttribute("mensaje")String mensaje){
        Employee worker = employeeService.getEmployeeById(id).get();
        model.addAttribute("worker",worker);
        model.addAttribute("mensaje",mensaje);
        List<Enterprise> listEnterprise = enterpriseService.getAllEnterprise();
        model.addAttribute("complist",listEnterprise);
        return "editEmployee";
    }

    @PostMapping("/UpdateEmployee")
    public String updateEmployee (@ModelAttribute("worker")Employee worker, RedirectAttributes redirectAttributes){
        Long id = worker.getId();
        if(employeeService.saveOrUpdateEmployee(worker)){
            redirectAttributes.addFlashAttribute("mensaje","Actualización satisfactoria");
            return "redirect:/ViewEmployee";
        }
        redirectAttributes.addFlashAttribute("mensaje","No se pudo actualizar");
        return "redirect:/EditEmployee";
    }

    @GetMapping("/DeleteEmployee/{id}")
    public String deleteEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (employeeService.deleteEmployee(id)){
            redirectAttributes.addFlashAttribute("mensaje","Empleado borrado satisfactoriamente");
            return "redirect:/ViewEmployee";
        }
        redirectAttributes.addFlashAttribute("mensaje","No se puede eliminar");
        return "redirect:/ViewEmployee";
    }

    @GetMapping("/Enterprise/{id}/Employee")
    public String viewEmployeeByEnterprise(@PathVariable("id")Long id, Model model){
        List<Employee> listEmployee = employeeService.buscaPorEmpresa(id);
        model.addAttribute("employeelist",listEmployee);
        return "viewEmployee";
    }

    @GetMapping({"/", "/Login"})
    public String getLogin(Model model){
        model.addAttribute("login", new Employee());
        return "login";
    }

    @PostMapping("/Login")
    public String login (@ModelAttribute Employee employee, Model model){
        Employee authenticated=employeeService.authenticate(employee.getEmail(),employee.getProfile());
        if(authenticated !=null){
            model.addAttribute("email", authenticated.getEmail());
            return "redirect:/ViewEnterprise";
        }else{
            return "editEmployee";
        }
    }
    @RequestMapping(value="/Logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/logout"; //mandamos otra vez al login screen.
    }
}
