package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.service.AgendamentoService;
import br.com.autoagenda.autoagenda.service.LogService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/{slug}/agendamento-api")
public class C_Agendamento {
    @Autowired private AgendamentoService service;
    @Autowired private LogService log;

    @PostMapping("/salvar")
    public String salvar(@PathVariable("slug") String slug,
                    @SessionAttribute("oficinaAtual") Oficina oficina,
                    @SessionAttribute("usuarioLogado") Funcionario usuarioLogado,
                    @Valid Agendamento ag,  @RequestParam("idServicos") java.util.List<Integer> idServicos,
                    @RequestParam("idCliente") Integer idCliente, @RequestParam("idVeiculo") Integer idVeiculo,
                    @RequestParam(value = "tokenMobile", required = false) String tokenMobile,
                    @RequestParam(value = "imagens", required = false) MultipartFile[] fotos, BindingResult result) {
        if (result.hasErrors()) { return "redirect:/"+ slug +"/agendamentos?erro=true"; }

        try {
            boolean isEdicao = ag.getIdAgendamento() != null;
            ag.setOficina(oficina);
            ag.setFuncionario(usuarioLogado);
            
            service.salvarAgendamento(ag, idServicos, idCliente, idVeiculo, fotos, tokenMobile);            
            log.registrar(isEdicao ? "Edição" : "Criação", "Agendamento", ag.getIdAgendamento(), "Agendamento do Cliente ID: " + idCliente, false);
            return isEdicao 
                    ? "redirect:/"+ slug +"/agendamentos?editado=true" 
                    : "redirect:/"+ slug +"/agendamentos?sucesso=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/"+ slug +"/agendamentos?erro=true";
        }
    }

    @PostMapping("/atualizaStatus")
    public String atualizarStatus(@PathVariable("slug") String slug, @RequestParam Integer idAgendamento, @RequestParam String status) {
        if (idAgendamento != null) {
            service.atualizarStatus(idAgendamento, status);
            log.registrar("Atualização", "Agendamento", idAgendamento, "Status alterado para: " + status, false);
            return "redirect:/"+ slug +"/agendamentos?atualizado=true";
        }
        return "redirect:/"+ slug +"/agendamentos?atualizado=false";
    }

    @PostMapping("/apagar")
    public String apagar(@PathVariable("slug") String slug, @RequestParam Integer idAgendamento) {
        service.excluirAgendamento(idAgendamento);
        log.registrar("Exclusão", "Agendamento", idAgendamento, "Agendamento excluído do sistema", false);
        return "redirect:/"+ slug +"/agendamentos?apagar=true";
    }

    @PostMapping("/concluir/{id}")
    @ResponseBody
    public Agendamento concluirAgendamento(@PathVariable("id") Integer id) {
        Agendamento ag = service.concluirAgendamento(id);
        log.registrar("Atualização", "Agendamento", id, "Agendamento marcado como concluído", false);
        return ag;
    }
}