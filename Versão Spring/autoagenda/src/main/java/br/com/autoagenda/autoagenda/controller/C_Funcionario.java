package br.com.autoagenda.autoagenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	
	@Autowired
	private FuncionarioRepository repo;
	
	@PostMapping(value = "/cadastroSistema")
	public String cadastroFuncionario(@Valid Funcionario func, BindingResult result) {
		
		if(result.hasErrors()) {
			return "redirect:/cadastroSistema";
		}
		repo.save(func);
		return "redirect:/login";
	}
	
	@GetMapping("/consulta")
	public List<Funcionario> consultarServico(){
	    List<Funcionario> lista = new ArrayList<>();
	    repo.findAll().forEach(lista::add);
	    return lista;
	}
    
}
