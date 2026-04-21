package br.com.autoagenda.autoagenda.controller.mobile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.FotosAgendamentoRepository;
import br.com.autoagenda.autoagenda.service.FotosAgendamentoService;

@RestController
@RequestMapping("/mobile/fotos-api")
public class C_FotosMobile {
    @Value("${app.upload.dir}") private String pastaFotos;

    @Autowired private FotosAgendamentoService service;
    @Autowired private FotosAgendamentoRepository repo;
    @Autowired private AgendamentoRepository agRepo;

    @GetMapping("/listar/{idAgendamento}")
    public ResponseEntity<?> listarFotos(@PathVariable Integer idAgendamento, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Agendamento> ag = agRepo.findById(idAgendamento);
        
        if (ag.isEmpty() || !ag.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado."));
        }
        List<FotosAgendamento> fotos = service.buscarPorAgendamento(idAgendamento);
        var resposta = fotos.stream().map(f -> Map.of("id", f.getIdFoto(), "nomeArquivo", f.getNomeArquivo())).toList();
        
        return resposta.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resposta);
    }

    @GetMapping("/imagem/{idFoto}")
    public ResponseEntity<?> mostrarImagem(@PathVariable Integer idFoto) {
        Optional<FotosAgendamento> fotoOpt = repo.findById(idFoto);
        if (fotoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            FotosAgendamento foto = fotoOpt.get();
            Path uploadRoot = Paths.get(pastaFotos).toAbsolutePath().normalize();
            Path caminho = uploadRoot.resolve(foto.getNomeArquivo()).normalize();

            if (!caminho.startsWith(uploadRoot)) throw new SecurityException("Acesso negado.");
            if (!Files.exists(caminho)) return ResponseEntity.notFound().build();

            byte[] bytes = Files.readAllBytes(caminho);
            String contentType = Files.probeContentType(caminho);
            if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(bytes);
                    
        } catch (IOException | SecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idFoto}")
    public ResponseEntity<?> removerFoto(@PathVariable Integer idFoto, @RequestHeader("idOficina") Integer idOficina) {
        
        Optional<FotosAgendamento> fotoOpt = repo.findById(idFoto);
        
        if (fotoOpt.isEmpty() || !fotoOpt.get().getAgendamento().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Foto não encontrada ou não pertence a sua oficina."));
        }

        try {
            service.apagarFotoPorId(idFoto);
            return ResponseEntity.ok(Map.of("mensagem", "Foto excluída com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao excluir a foto."));
        }
    }
}