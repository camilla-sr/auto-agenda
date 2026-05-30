package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.autoagenda.autoagenda.dto.mobile.AgendamentoDto;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.service.AgendamentoService;

@RestController
@RequestMapping("/mobile/agendamento-api")
public class C_AgendamentoMobile {
    @Autowired private AgendamentoRepository repo;
    @Autowired private AgendamentoService serv;
    @Autowired private OficinaRepository oficinaRepo;

    @GetMapping
    public ResponseEntity<?> listarAgendamentos(@RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada."));
            List<Agendamento> agendamentos = repo.findByOficinaAndAtivoTrue(oficina);            
            AgendamentoDto.OficinaResumo resumoOficina = new AgendamentoDto.OficinaResumo(idOficina);

            List<AgendamentoDto> dto = agendamentos.stream().map(ag -> new AgendamentoDto(
                    ag.getIdAgendamento(),
                    resumoOficina,
                    ag.getCliente() != null ? new AgendamentoDto.ClienteResumo(ag.getCliente().getIdCliente(), ag.getCliente().getNomeCliente(), ag.getCliente().getTelefone()) : null,
                    ag.getVeiculo() != null ? new AgendamentoDto.VeiculoResumo(ag.getVeiculo().getIdVeiculo(), ag.getVeiculo().getPlaca(), ag.getVeiculo().getModelo()) : null,
                    ag.getFuncionario() != null ? new AgendamentoDto.FuncionarioResumo(ag.getFuncionario().getIdFuncionario(), ag.getFuncionario().getNomeFuncionario()) : null,
                    ag.getServicos() == null ? List.of() : ag.getServicos().stream()
                        .map(s -> new AgendamentoDto.ServicoResumo(s.getIdServico(), s.getDescServico()))
                        .toList(),
                    ag.getDataCadastro(),
                    ag.getDataPrevisao(),
                    ag.getHoraPrevisao(),
                    ag.getDataConclusao(),
                    ag.getStatusAgendamento(),
                    ag.getObservacao(),
                    ag.isAtivo()
            )).toList();

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Agendamento> agendamento = repo.findById(id);
        
        if (agendamento.isEmpty() || !agendamento.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado."));
        }
        return ResponseEntity.ok(agendamento.get());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> salvar(@RequestPart("agendamento") Agendamento agendamento, @RequestParam("idServicos") List<Integer> idServicos,
            @RequestParam("idCliente") Integer idCliente, @RequestParam("idVeiculo") Integer idVeiculo,
            @RequestPart(value = "fotos", required = false) MultipartFile[] fotos, @RequestParam(value = "tokenMobile", required = false) String tokenMobile,
            @RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina inválida."));
            
            agendamento.setOficina(oficina);
            serv.salvarAgendamento(agendamento, idServicos, idCliente, idVeiculo, fotos, tokenMobile);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Agendamento salvo com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao salvar agendamento: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Integer id, @RequestParam String novoStatus, 
            								@RequestHeader("idOficina") Integer idOficina) {
        
        Optional<Agendamento> ag = repo.findById(id);
        if (ag.isEmpty() || !ag.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado."));
        }
        
        try {
            serv.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(Map.of("mensagem", "Status atualizado para: " + novoStatus));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<?> concluirAgendamento(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Agendamento> ag = repo.findById(id);
        if (ag.isEmpty() || !ag.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado."));
        }
        
        try {
            Agendamento concluido = serv.concluirAgendamento(id);
            return ResponseEntity.ok(concluido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao concluir agendamento: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarAgendamento(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Agendamento> ag = repo.findById(id);
        
        if (ag.isEmpty() || !ag.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado."));
        }
        try {
            serv.excluirAgendamento(id);
            return ResponseEntity.ok(Map.of("mensagem", "Agendamento inativado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro ao tentar inativar o agendamento."));
        }
    }
}