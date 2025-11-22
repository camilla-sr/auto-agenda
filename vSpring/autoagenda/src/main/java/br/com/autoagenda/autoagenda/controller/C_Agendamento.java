package br.com.autoagenda.autoagenda.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import br.com.autoagenda.autoagenda.service.FotosAgendamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/agendamento-api")
public class C_Agendamento {
	@Autowired private AgendamentoRepository repo;
	@Autowired private ServicoRepository repoServ;
	@Autowired private FotosAgendamentoService fotoService;
	
	@PostMapping("/salvar")
    public String salvar(@Valid Agendamento ag, @RequestParam("idServico") Integer idServico,
    		@RequestParam(value = "fotos", required = false) MultipartFile[] fotos,
    		BindingResult result) {
		
		if(result.hasErrors()) { return "redirect:/agendamentos?erro=true"; }
		Servico servicoSelecionado = repoServ.findById(idServico).orElseThrow();
		
		if(ag.getIdAgendamento() != null) {
			Agendamento existe = repo.findById(ag.getIdAgendamento()).orElse(new Agendamento());
			
			existe.setNomeCliente(ag.getNomeCliente());
			existe.setServico(servicoSelecionado);
			existe.setDataPrevisao(ag.getDataPrevisao());
			existe.setStatusAgendamento(ag.getStatusAgendamento());
			existe.setDataConclusao("concluido".equals(ag.getStatusAgendamento()) ? LocalDate.now() : null);
			existe.setObservacao(ag.getObservacao());
			repo.save(existe);
			
			if(fotos != null && fotos.length > 0) {
				for(MultipartFile foto : fotos) {
					fotoService.salvarFoto(existe.getIdAgendamento(), foto);
				}
			}
			return "redirect:/agendamentos?editado=true";
		} else {
			ag.setServico(servicoSelecionado);
			ag.setDataConclusao("concluido".equals(ag.getStatusAgendamento()) ? LocalDate.now() : null);
			repo.save(ag);
			
			if(fotos != null && fotos.length > 0) {
				for(MultipartFile foto : fotos) {
					fotoService.salvarFoto(ag.getIdAgendamento(), foto);
				}
			}
		}
		return "redirect:/agendamentos?sucesso=true";
    }
	
	@PostMapping(value = "/atualizaStatus")
	public String atualizarStatus(@RequestParam Integer idAgendamento, @RequestParam String status) {
		if(idAgendamento != null) {
			Agendamento ag = repo.findById(idAgendamento).orElseThrow();
			ag.setStatusAgendamento(status);
			repo.save(ag);
			return "redirect:/agendamentos?atualizado=true";
		}
		return "redirect:/agendamentos?atualizado=false";
	}
	
	@PostMapping(value = "/apagar")
	public String apagar(@RequestParam Integer idAgendamento) {
		if(idAgendamento != null) { repo.deleteById(idAgendamento); }
		return "redirect:/agendamentos?apagar=true";
	}

	@PostMapping("/concluir/{id}")
    @ResponseBody
    public Agendamento concluirAgendamento(@PathVariable("id") Integer id) {
        Agendamento agendamento = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        
        agendamento.setStatusAgendamento("Concluido");
        agendamento.setDataConclusao(LocalDate.now());
        
        return repo.save(agendamento);
    }
}