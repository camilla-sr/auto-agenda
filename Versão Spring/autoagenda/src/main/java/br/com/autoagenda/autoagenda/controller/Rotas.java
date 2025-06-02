package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class Rotas {
	
	@GetMapping("/cadastroSistema")
	public String cadastroSistema() {
		return "cadastro";
	}
	
    @GetMapping("/login")
    public String logar() {
    	return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/login";
    }
	
	@GetMapping("/")
	public String index(HttpSession session) {
	    if(session.getAttribute("usuarioLogado") == null) {
	    	return "redirect:/login";
	    }
		return "dashboard";
	}
	
    @GetMapping("/funcionarios")
    public String funcionarios(HttpSession session) {
    	if(session.getAttribute("usuarioLogado") == null) {
    		return "redirect:/login";
    	}
    	return "funcionarios";
    }
	
    @GetMapping("/servicos")
    public String servicos(HttpSession session) {
    	if(session.getAttribute("usuarioLogado") == null) {
    		return "redirect:/login";
    	}
        return "servicos";
    }
    
    @GetMapping("/clientes")
    public String clientes(HttpSession session) {
    	if(session.getAttribute("usuarioLogado") == null) {
    		return "redirect:/login";
    	}
    	return "clientes";
    }
    
    @GetMapping("/produtos")
    public String produtos(HttpSession session) {
    	if(session.getAttribute("usuarioLogado") == null) {
    		return "redirect:/login";
    	}
    	return "produtos";
    }
    
    @GetMapping("/agendamentos")
    public String agendamentos(HttpSession session) {
    	if(session.getAttribute("usuarioLogado") == null) {
    		return "redirect:/login";
    	}
    	return "agendamentos";
    }
}
