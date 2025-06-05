package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/servico-api")
public class C_Servico {
	@Autowired
	private ServicoRepository repo;

	@PostMapping("/salvar")
	public String salvar(@Valid Servico serv, BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/servicos?erroServico=true";			
		}
		
		if(serv.getIdServico() != null) {
			Servico existe = repo.findById(serv.getIdServico()).orElse(new Servico());
			existe.setNomeServico(serv.getNomeServico());
			existe.setDescServico(serv.getDescServico());
			
			repo.save(existe);
			return "redirect:/servicos?editado=true";			
		} else {
			repo.save(serv);
		}
	  return "redirect:/servicos?sucesso=true";
	}

	@PostMapping(value = "/apagar")
	public String apagar(@RequestParam Integer idServico) {
		if(idServico!= null) {
			repo.deleteById(idServico);
		}
		return "redirect:/servicos?apagar=true";
	}
}