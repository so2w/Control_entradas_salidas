package com.UdeA.Ciclo3.controller;

import com.UdeA.Ciclo3.modelos.Enterprise;
import com.UdeA.Ciclo3.service.EmployeeService;
import com.UdeA.Ciclo3.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class enterpriseController {
    @Autowired
    EnterpriseService enterpriseService;


    @GetMapping("/ViewEnterprise")
    public String viewEnterprises(Model model, @ModelAttribute("mensaje")String mensaje){
        List<Enterprise> listEnterprises = enterpriseService.getAllEnterprise();
        model.addAttribute("entlist",listEnterprises);
        model.addAttribute("mensaje",mensaje);
        return "viewEnterprise"; // llama al HTML
    }

    @GetMapping("/AddEnterprise")
    public String newEnterprise (Model model, @ModelAttribute("mensaje")String mensaje){
        Enterprise ent = new Enterprise();
        model.addAttribute("ent", ent);
        model.addAttribute("mensaje", mensaje);
        return "addEnterprise";
    }

    @PostMapping ("/SaveEnterprise")
    public String saveEnterprise (Enterprise ent, RedirectAttributes redirectAttributes){
        if(enterpriseService.saveOrUpdateEnterprise(ent) == true){
            redirectAttributes.addFlashAttribute("mensaje","guardado correctamente");
            return "redirect:/ViewEnterprise";
        }
        redirectAttributes.addFlashAttribute("mensaje","error al guardar");
        return "redirect:/AddEnterprise";
    }

    @GetMapping ("/EditEnterprise/{id}")
    public String editEnterprise (Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Enterprise comp = enterpriseService.getEnterpriseById(id);
        model.addAttribute("comp",comp);
        model.addAttribute("mensaje",mensaje);
        return "editEnterprise";
    }

    @PostMapping("/UpdateEnterprise")
    public String updateEnterprise(@ModelAttribute("comp") Enterprise comp, RedirectAttributes redirectAttributes){
        if(enterpriseService.saveOrUpdateEnterprise(comp)){
            redirectAttributes.addFlashAttribute("mensaje","Actualización satisfactoria");
            return "redirect:/ViewEnterprise";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Error al Actualizar");
        return "redirect:/EditEnterprise/" + comp.getId();
    }

    @GetMapping ("/DeleteEnterprise/{id}")
    public String deleteEnterprise(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (enterpriseService.deleteEnterprise(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "Empresa eliminada satisfactoriamente");
            return "redirect:/ViewEnterprise";
        }
        redirectAttributes.addFlashAttribute("mensaje","No se puede eliminar");
        return "redirect:/ViewEnterprise";
    }
}
