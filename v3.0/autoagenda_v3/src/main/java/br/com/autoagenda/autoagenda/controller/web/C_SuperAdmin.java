package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.SuperAdminRepository;
import br.com.autoagenda.autoagenda.service.SuperAdminService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/superadmin-api")
public class C_SuperAdmin {
	@Autowired SuperAdminService service;
	@Autowired SuperAdminRepository repo;
	
	@PostMapping(value = "/logar")
    public String logar(@RequestParam String usuario, @RequestParam String senha, HttpSession session) {
        SuperAdmin admin = service.autenticar(usuario, senha);
        
        if(admin == null) return "redirect:/superadmin/login?erro=true";
        
        session.setAttribute("primeiroLogin", admin.isPrimeiroLogin());
        session.setAttribute("superAdminLogado", admin);
        
        return "redirect:/autoagenda";
    }
	
	@PostMapping(value = "/salvar")
    public String salvar(@ModelAttribute SuperAdmin admin, RedirectAttributes ra) {
        try {
            service.salvarOuAtualizar(admin);
            ra.addFlashAttribute("sucesso", "Conta Mestre criada com sucesso! Faça seu login.");
            return "redirect:/superadmin/login";
            
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/superadmin/cadastro";
        }
    }
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("superAdminLogado");
        return "redirect:/superadmin/login";
    }
}