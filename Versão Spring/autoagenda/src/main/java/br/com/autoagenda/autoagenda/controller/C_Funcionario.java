package br.com.autoagenda.autoagenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	
	@Autowired
	private FuncionarioRepository repo;
	
	@GetMapping("/consulta")
	public List<Funcionario> consultaFuncionario(){
		return repo.findAll();
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String usuario, 
						@RequestParam String senha, HttpSession session) {
		if(usuario == null || usuario.trim().isEmpty()) return "usuario-nao-informado";
		if(senha == null || senha.trim().isEmpty()) return "senha-nao-informada";
		
		Funcionario func = new Funcionario().logar(usuario, senha);
		
		if(func == null) return "redirect:/login";
		
		session.setAttribute("usuarioLogado", func);
		return "redirect:/index";
	}
	
	
	@PostMapping("/cadastrarFuncionario")
    public String addFuncionario(@RequestParam String nome, String usuario, String senha) {
		if(nome == null || nome.trim().isEmpty()) return "usuario-nao-informado";
		if(usuario == null || usuario.trim().isEmpty()) return "usuario-nao-informado";
		if(senha == null || senha.trim().isEmpty()) return "usuario-nao-informado";
		
		Funcionario func = new Funcionario();
		boolean resposta = func.cadastrarFuncionario(nome , usuario, senha);
		
		return resposta ? "redirect:/login" : "cadastrarFuncionario";
    }
}
