package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.service.ClienteService;

@RestController
@RequestMapping("/mobile/cliente-api")
public class C_ClienteMobile {
	@Autowired private ClienteRepository repo;
	@Autowired private ClienteService serv;
	@Autowired private OficinaRepository oficinaRepo;
	
	@GetMapping
    public ResponseEntity<?> listarClientes(@RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada."));
            List<Cliente> lista = repo.findByOficinaAndAtivoTrue(oficina);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Cliente> funcOpt = repo.findById(id);
        if (funcOpt.isEmpty() || !funcOpt.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Funcionário não encontrado."));
        }
        return ResponseEntity.ok(funcOpt.get());
    }
	
	@PostMapping
    public ResponseEntity<?> salvarCliente(@RequestBody Cliente cliente, @RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina inválida."));
            
            serv.salvarOuAtualizar(cliente, oficina);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Dados salvos com sucesso!"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Este e-mail ou usuário já está em uso."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        
        Optional<Cliente> cliente = repo.findById(id);
        if (cliente.isEmpty() || !cliente.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Funcionário não encontrado."));
        }
        Cliente cl = cliente.get();
        cl.setAtivo(!cl.isAtivo());
        repo.save(cl);
        return ResponseEntity.ok(Map.of("mensagem", "Status atualizado!", "ativo", cl.isAtivo()
        ));
    }
}