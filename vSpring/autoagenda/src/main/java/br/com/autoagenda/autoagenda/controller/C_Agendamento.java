package br.com.autoagenda.autoagenda.controller;

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
import br.com.autoagenda.autoagenda.service.AgendamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/agendamento-api")
public class C_Agendamento {
	@Autowired private AgendamentoService service;

    @PostMapping("/salvar")
    public String salvar(@Valid Agendamento ag,  @RequestParam("idServico") Integer idServico,
                         @RequestParam("idCliente") Integer idCliente, @RequestParam("idVeiculo") Integer idVeiculo,
                         @RequestParam(value = "tokenMobile", required = false) String tokenMobile,
                         @RequestParam(value = "imagens", required = false) MultipartFile[] fotos, BindingResult result) {
        if (result.hasErrors()) { return "redirect:/agendamentos?erro=true"; }

        try {
            service.salvarAgendamento(ag, idServico, idCliente, idVeiculo, fotos, tokenMobile);
            boolean isEdicao = ag.getIdAgendamento() != null;
            return isEdicao ? "redirect:/agendamentos?editado=true" : "redirect:/agendamentos?sucesso=true";
        } catch (Exception e) {
            return "redirect:/agendamentos?erro=true";
        }
    }

    @PostMapping("/atualizaStatus")
    public String atualizarStatus(@RequestParam Integer idAgendamento, @RequestParam String status) {
        if (idAgendamento != null) {
            service.atualizarStatus(idAgendamento, status);
            return "redirect:/agendamentos?atualizado=true";
        }
        return "redirect:/agendamentos?atualizado=false";
    }

    @PostMapping("/apagar")
    public String apagar(@RequestParam Integer idAgendamento) {
        service.excluirAgendamento(idAgendamento);
        return "redirect:/agendamentos?apagar=true";
    }

    @PostMapping("/concluir/{id}")
    @ResponseBody
    public Agendamento concluirAgendamento(@PathVariable("id") Integer id) {
        return service.concluirAgendamento(id);
    }
}