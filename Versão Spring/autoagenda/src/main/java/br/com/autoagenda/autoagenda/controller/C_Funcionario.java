package br.com.autoagenda.autoagenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;

@Controller
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	@Autowired
	private FuncionarioRepository repo;
	
	@PostMapping(value = "/cadastroSistema")
	public String cadastroFuncionario(@Valid Funcionario func, BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/cadastroSistema?erro=true";
		}
		
		Funcionario existe = repo.findByUsuario(func.getUsuario());
		if(existe != null) {
			return "redirect:/cadastroSistema?erroUsuario=true";
		}
		
		repo.save(func);
		return "redirect:/login?sucesso=true";
	}
	
	@PostMapping(value = "/logar")
	public String logar(@RequestParam String usuario, @RequestParam String senha, HttpSession session) {		
		Funcionario func = repo.findByUsuario(usuario);
		if(func == null || !func.getSenha().equals(senha)) {
			return "redirect:/login?usuarioValido=false";
		}
		session.setAttribute("usuarioLogado", func);
	    return "redirect:/";
	}
	
	@GetMapping("/consulta")
	public List<Funcionario> consultarServico(){
	    List<Funcionario> lista = new ArrayList<>();
	    repo.findAll().forEach(lista::add);
	    return lista;
	}
}
