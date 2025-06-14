package br.com.autoagenda.autoagenda.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/agendamento-api")
public class C_Agendamento {
	
	@Autowired
	private AgendamentoRepository repo;
	@Autowired
	private ServicoRepository repoServ;
	
	@PostMapping("/salvar")
    public String salvar(@Valid Agendamento ag, @RequestParam("idServico") Integer idServico ,BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/agendamentos?erro=true";
		}
		Servico servicoSelecionado = repoServ.findById(idServico).orElseThrow();
		if(ag.getIdAgendamento() != null) {
			Agendamento existe = repo.findById(ag.getIdAgendamento()).orElse(new Agendamento());
			
			existe.setNomeCliente(ag.getNomeCliente());
			existe.setServico(servicoSelecionado);
			existe.setDataCadastro(LocalDate.now());
			existe.setDataPrevisao(ag.getDataPrevisao());
			existe.setDataConclusao(ag.getDataConclusao());
			existe.setStatusAgendamento(ag.getStatusAgendamento());
			existe.setObservacao(ag.getObservacao());
			repo.save(existe);
			return "redirect:/agendamentos?editado=true";
		} else {
			ag.setDataCadastro(LocalDate.now());
			ag.setServico(servicoSelecionado);
			repo.save(ag);
		}
		return "redirect:/agendamentos?sucesso=true";
    }
	
	@PostMapping(value = "/apagar")
	public String apagar(@RequestParam Integer idAgendamento) {
		if(idAgendamento != null) {
			repo.deleteById(idAgendamento);
		}
		return "redirect:/agendamentos?apagar=true";
	}

}
