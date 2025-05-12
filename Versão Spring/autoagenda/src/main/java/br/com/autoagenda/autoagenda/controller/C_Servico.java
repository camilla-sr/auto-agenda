package br.com.autoagenda.autoagenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import br.com.autoagenda.autoagenda.include.Helper;

@RestController
@RequestMapping("/servico-api")
public class C_Servico {
	
	@Autowired
	private ServicoRepository repo;

	@GetMapping("/consulta")
	public List<Servico> consultarServico(){
		return repo.findAll();
	}
	
	@PostMapping("/cadastrarServico")
	public String cadastrarServico(@RequestParam String descricao) {
		if (descricao == null || descricao.trim().isEmpty()) return "erro-campo-vazio";
	
		Servico novoServico = new Servico();
		boolean salvou = novoServico.cadastrarServico(descricao);
		return salvou ? "cadastro-completo" : "cadastro-nao-concluido";
	}
}