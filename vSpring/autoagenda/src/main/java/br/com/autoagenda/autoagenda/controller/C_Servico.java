package br.com.autoagenda.autoagenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;

@RestController
@RequestMapping("/servico-api")
public class C_Servico {
	
	@Autowired
	private ServicoRepository repo;

	@GetMapping("/consulta")
	public List<Servico> consultarServico(){
	    List<Servico> lista = new ArrayList<>();
	    repo.findAll().forEach(lista::add);
	    return lista;
	}

	@PostMapping("/cadastrarServico")
	public String cadastrarServico(@RequestParam String descricao) {
		if (descricao == null || descricao.trim().isEmpty()) return "erro-campo-vazio";
	
		return "sim";
	}
}