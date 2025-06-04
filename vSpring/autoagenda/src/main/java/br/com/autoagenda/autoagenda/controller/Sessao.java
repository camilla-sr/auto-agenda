package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.autoagenda.autoagenda.model.Funcionario;
import jakarta.servlet.http.HttpSession;

@Controller
public class Sessao {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/login";
    }
    
    public boolean loginAtivo(HttpSession session) {
    	return session.getAttribute("usuarioLogado") != null;
    }
    
    public boolean verificaAcesso(HttpSession session, String acessoNecessario) {
        if (!loginAtivo(session)) {
            return false;
        }
        return ((Funcionario) session.getAttribute("usuarioLogado")).getAcesso().equals(acessoNecessario);
    }
}
