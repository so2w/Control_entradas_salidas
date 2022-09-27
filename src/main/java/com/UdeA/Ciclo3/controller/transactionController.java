package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Employee;
import com.UdeA.Ciclo3.modelos.Enterprise;
import com.UdeA.Ciclo3.modelos.Transaction;
import com.UdeA.Ciclo3.repo.TransactionRepository;
import com.UdeA.Ciclo3.service.EmployeeService;
import com.UdeA.Ciclo3.service.EnterpriseService;
import com.UdeA.Ciclo3.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class transactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EnterpriseService enterpriseService;

    @RequestMapping ("/ViewTransaction")
    public String viewTransaction(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Transaction> transactionList=transactionService.getAllMovement();
        model.addAttribute("transList",transactionList);
        model.addAttribute("mensaje",mensaje);
        Long sumAmount = transactionService.getSumAmount();
        model.addAttribute("SumAmount", sumAmount);
        return "viewTransaction";
    }

    @GetMapping("/AddTransaction")
    public String newMovement(Model model, @ModelAttribute("mensaje")String mensaje){
        Transaction move = new Transaction();
        model.addAttribute("move", move);
        model.addAttribute("mensaje",mensaje);
        //long idEmpleado=transactionService.IdPorCorreo(correo);
        //model.addAttribute("idEmpleado",idEmpleado);
        List<Enterprise> listaEmpresas= enterpriseService.getAllEnterprise();
        model.addAttribute("emprelist",listaEmpresas);
        List<Employee> listaEmpleados=employeeService.getAllEmployee();
        model.addAttribute("emplelist",listaEmpleados);
        return "addTransaction";
    }
    @PostMapping("/SaveTransaction")
    public String saveMovement(Transaction move, RedirectAttributes redirectAttributes){
        if(transactionService.saveOrUpdateMovement(move)){
            redirectAttributes.addFlashAttribute("mensaje","Transacción guardada correctamente");
            return "redirect:/ViewTransaction";
        }
        redirectAttributes.addFlashAttribute("mensaje","Error al guardad");
        return "redirect:/AddTransaction";
    }

    @GetMapping("/EditTransaction/{id}")
    public String editTransaction(Model model,@PathVariable Long id, @ModelAttribute("mensaje")String mensaje){
        Transaction move = transactionService.getMovementById(id);
        model.addAttribute("move", move);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> listaEmpresas= enterpriseService.getAllEnterprise();
        model.addAttribute("emprelist",listaEmpresas);
        List<Employee> listEmployee = employeeService.getAllEmployee();
        model.addAttribute("employeelist", listEmployee);
        return "editTransaction";
    }

    @PostMapping("/UpdateTransaction")
    public String updateTransaction(@ModelAttribute("move") Transaction move, RedirectAttributes redirectAttributes){
        if(transactionService.saveOrUpdateMovement(move)){
            redirectAttributes.addAttribute("mensaje", "Movimiento Actualizado correctamente");
            return "redirect:/ViewTransaction";
        }
        redirectAttributes.addFlashAttribute("mensaje","No se pudo Actualizar");
        return  "redirect:/EditTransaction";
    }

    @GetMapping("/DeleteTransaction/{id}")
    public String deleteTransaction(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (transactionService.deleteMovement(id)){
            redirectAttributes.addFlashAttribute("mensaje","Transacción borrada satisfactoriamente");
            return "redirect:/ViewTransaction";
        }
        redirectAttributes.addFlashAttribute("mensaje", "No se puedo eliminar");
        return "redirect:/ViewTransaction";
    }

    @GetMapping("/Employee/{id}/Transaction")
    public String transactionByEmployee(@PathVariable("id")Long id, Model model){
        List<Transaction> movelist = transactionService.getByEmployee(id);
        model.addAttribute("transList",movelist);
        Long sumAmount = transactionService.sumByEmployee(id);
        model.addAttribute("SumAmount", sumAmount);
        return "viewTransaction";
    }

    @GetMapping("/Enterprise/{id}/Transaction")
    public String transactionByEnterprise(@PathVariable("id")Long id, Model model) {
        List<Transaction> movelist = transactionService.getByEnterprise(id);
        model.addAttribute("transList", movelist);
        Long sumAmount = transactionService.sumByEmterprise(id);
        model.addAttribute("SumAmount", sumAmount);
        return "viewTransaction";
    }
    @RequestMapping(value = "/Denied")
    public String accessDenied(){
        return "accessDenied";
    }
}
