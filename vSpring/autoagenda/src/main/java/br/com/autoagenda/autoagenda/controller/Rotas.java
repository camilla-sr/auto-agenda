package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class Rotas {
	@Autowired
	private Sessao s;
	
	@Autowired
	private FuncionarioRepository repoFunc;
	
	@ModelAttribute
    public void usuarioGlobal(HttpSession session, Model model) {
        if (s.loginAtivo(session)) {
            model.addAttribute("usuarioLogado", (Funcionario) session.getAttribute("usuarioLogado"));
        }
    }
	
	public String verificaUsuario(HttpSession session, String page) {
		if(!s.loginAtivo(session)) return "login";
		return page;
	}
	
	@GetMapping("/cadastroSistema")
	public String cadastroSistema() {
		return "cadastro";
	}
    @GetMapping("/login")
    public String logar() {
    	return "login";
    }
	
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		return verificaUsuario(session, "dashboard");
	}
	
    @GetMapping("/funcionarios")
    public String funcionarios(HttpSession session, Model model) {
    	if(!s.verificaAcesso(session, "admin")) { return "acesso-negado"; }
    	model.addAttribute("funcionarios", repoFunc.findAll());
    	return verificaUsuario(session, "funcionarios");
    }
	
    @GetMapping("/servicos")
    public String servicos(HttpSession session) {
    	return verificaUsuario(session, "servicos");
    }
    
    @GetMapping("/clientes")
    public String clientes(HttpSession session) {
    	return verificaUsuario(session, "clientes");
    }
    
    @GetMapping("/produtos")
    public String produtos(HttpSession session) {
    	return verificaUsuario(session, "produtos");
    }
    
    @GetMapping("/agendamentos")
    public String agendamentos(HttpSession session) {
    	return verificaUsuario(session, "agendamentos");
    }
}
