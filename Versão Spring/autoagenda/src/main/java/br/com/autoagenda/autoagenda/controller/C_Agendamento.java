package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.include.Helper;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;

@RestController
@RequestMapping("/agendamento-api")
public class C_Agendamento {
	
	@Autowired
	private AgendamentoRepository repo;
	
	@PostMapping("/cadastrarAgendamento")
    public String cadastrarAgendamento(@RequestParam int idCliente, int idServico, String dataPrevisao, String status, String observacao) {
       if(idCliente == 0 || idServico == 0 || status.trim().isEmpty() || observacao.trim().isEmpty()) {
    	   return "erro-campo-vazio";
       }
       
       Cliente cliente = new Cliente();
       cliente.setIdCliente(idCliente);
       
       Servico servico = new Servico();
       servico.setIdServico(idServico);
       
       Agendamento agenda = new Agendamento();
       agenda.setCliente(cliente);
       agenda.setServico(servico);
       boolean resposta = agenda.cadastrarAgendamento(Helper.dataPadraoBanco(dataPrevisao), status, observacao);
       
       return resposta ? "agendamentos" : "cadastro-nao-realizado";
    }

//	@PostMapping("/editarAgendamento")
//    public void editarAgendamento() {
//        
//    }
//
//	@PostMapping("/apagarAgendamento")
//    public void apagarAgendamento() {
//        
//    }
//
//	@PostMapping("/finalizarAgendamento")
//    public void finalizar() {
//        
//    }
}
