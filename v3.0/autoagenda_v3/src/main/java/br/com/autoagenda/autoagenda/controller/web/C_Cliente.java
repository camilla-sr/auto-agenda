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
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;

@Controller
@RequestMapping("/{slug}/cliente-api")
public class C_Cliente {
	@Autowired private ClienteRepository repo;

	@PostMapping("/salvar")
	@ResponseBody
    public ResponseEntity<Cliente> salvar(@PathVariable("slug") String slug,
    									@SessionAttribute("oficinaAtual") Oficina oficina, @RequestBody Cliente cl) {
		Cliente clienteSalvo;
		
		if(cl.getIdCliente() != null) {
			Cliente existe = repo.findById(cl.getIdCliente()).orElse(null);
			if(existe != null) {
				existe.setOficina(oficina);
				existe.setNomeCliente(cl.getNomeCliente());
				existe.setTelefone(cl.getTelefone());
				existe.setEmail(cl.getEmail());
				clienteSalvo = repo.save(existe);	
			}else {
				clienteSalvo = repo.save(cl);
			}
		} else {
			cl.setOficina(oficina);
			clienteSalvo = repo.save(cl);
		}
		return ResponseEntity.ok(clienteSalvo);
    }

	@PostMapping("/apagar")
	@ResponseBody
    public ResponseEntity<?> apagarCliente(@PathVariable("slug") String slug, @RequestParam Integer idCliente) {
        if(idCliente == null) { return ResponseEntity.badRequest().body("ID inválido"); }
        try {
            repo.deleteById(idCliente);
            return ResponseEntity.ok().build(); 
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            		.body("Não é possível excluir: o cliente possui agendamentos vinculados.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            		.body("Erro ao tentar excluir o cliente.");
        }
    }
}