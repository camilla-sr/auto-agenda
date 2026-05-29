package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.dto.mobile.ProdutoDto;
import br.com.autoagenda.autoagenda.dto.mobile.ServicoDto;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import br.com.autoagenda.autoagenda.service.ServicoService;

@RestController
@RequestMapping("/mobile/servico-api")
public class C_ServicoMobile {
    @Autowired private ServicoRepository repo;
    @Autowired private ServicoService serv;
    @Autowired private OficinaRepository oficinaRepo;
    
    @GetMapping
    public ResponseEntity<?> listarServicos(@RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada."));
            List<Servico> servico = repo.findByOficinaAndAtivoTrue(oficina);
            ServicoDto.OficinaResumo resumo = new ServicoDto.OficinaResumo(idOficina);
            
            List<ServicoDto> dto = servico.stream().map(serv -> new ServicoDto(
            		serv.getIdServico(), resumo, serv.getNomeServico(), serv.getDescServico(), serv.isAtivo()
            		))
            		.toList();
            
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Servico> servOpt = repo.findById(id);
        
        if (servOpt.isEmpty() || !servOpt.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Serviço não encontrado."));
        }
        Servico serv = servOpt.get();
        ServicoDto.OficinaResumo resumo = new ServicoDto.OficinaResumo(idOficina);
        
        ServicoDto dto = new ServicoDto(serv.getIdServico(), resumo, serv.getNomeServico(), 
                					serv.getDescServico(), serv.isAtivo());
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Servico servico, @RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina inválida."));
            serv.salvarOuAtualizar(servico, oficina);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Dados salvos com sucesso!"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro de integridade nos dados do serviço."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarServico(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Servico> servico = repo.findById(id);
        
        if (servico.isEmpty() || !servico.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Serviço não encontrado."));
        }
        try {
            serv.inativar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Serviço inativado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro ao tentar inativar o serviço."));
        }
    }
}