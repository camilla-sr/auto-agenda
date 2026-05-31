package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.service.ClienteService;
import br.com.autoagenda.autoagenda.service.LogService;

@Controller
@RequestMapping("/{slug}/cliente-api")
public class C_Cliente {
    @Autowired private ClienteService service;
    @Autowired private LogService log;

    @PostMapping("/salvar") @ResponseBody
    public ResponseEntity<Cliente> salvar(@PathVariable("slug") String slug,
            @SessionAttribute("oficinaAtual") Oficina oficina,
            @RequestBody Cliente cl) {

        boolean novo = cl.getIdCliente() == null;
        Cliente clienteSalvo = service.salvarOuAtualizar(cl, oficina);

        log.registrar(novo ? "Criação" : "Edição", "Cliente", clienteSalvo.getIdCliente(), "Nome: " + clienteSalvo.getNomeCliente(), false);
        return ResponseEntity.ok(clienteSalvo);
    }

    @PostMapping("/apagar") @ResponseBody
    public ResponseEntity<?> apagarCliente(@PathVariable("slug") String slug, @RequestParam Integer idCliente,
            @SessionAttribute("oficinaAtual") Oficina oficina) {
        if (idCliente == null) return ResponseEntity.badRequest().body("ID inválido");
        try {
            service.inativar(idCliente);
            
            log.registrar("Exclusão", "Cliente", idCliente, "Cliente inativado: " + idCliente, false);
                
            return ResponseEntity.ok().build(); 
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível excluir: o cliente possui agendamentos vinculados.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir o cliente.");
        }
    }
}