package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.service.LogService;
import br.com.autoagenda.autoagenda.service.ServicoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/{slug}/servico-api")
public class C_Servico {
	@Autowired private LogService log;
    @Autowired private ServicoService service;

    @PostMapping("/salvar")
    public String salvar(@PathVariable("slug") String slug, @SessionAttribute("oficinaAtual") Oficina oficina,
            @Valid Servico serv, BindingResult result) {
        if(result.hasErrors()) { return "redirect:/"+ slug +"/servicos?erroServico=true"; }
        
        boolean edicao = serv.getIdServico() != null;
        service.salvarOuAtualizar(serv, oficina);
        log.registrar(edicao ? "Edição" : "Criação", "Serviço", serv.getIdServico(), "Serviço: " + serv.getNomeServico(), false );
        if(edicao) {
            return "redirect:/"+ slug +"/servicos?editado=true";
        }
        return "redirect:/"+ slug +"/servicos?sucesso=true";
    }

    @PostMapping("/apagar") @ResponseBody
    public ResponseEntity<?> apagar(@RequestParam Integer idServico) {
        if(idServico == null) { return ResponseEntity.badRequest().body("ID inválido"); }
        try {
            service.inativar(idServico);
            
            log.registrar("Inativado", "Serviço", idServico, "Serviço inativado", false );
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível excluir: o serviço possui agendamentos vinculados.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir o serviço.");
        }
    }
}