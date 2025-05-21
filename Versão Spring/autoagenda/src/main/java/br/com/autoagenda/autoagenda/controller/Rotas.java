package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rotas {
	
	@GetMapping("/")
	public String index() {
	    return "dashboard";
	}
	
	@GetMapping("/cadastroSistema")
	public String cadastroSistema() {
		return "cadastro";
	}
	
    @GetMapping("/servicos")
    public String servicos() {
        return "servicos";
    }
    
    @GetMapping("/login")
    public String logar() {
    	return "login";
    }
    
    @GetMapping("/clientes")
    public String clientes() {
    	return "clientes";
    }
    
    @GetMapping("/produtos")
    public String produtos() {
    	return "produtos";
    }
    
    @GetMapping("/agendamentos")
    public String agendamentos() {
    	return "agendamentos";
    }
}
