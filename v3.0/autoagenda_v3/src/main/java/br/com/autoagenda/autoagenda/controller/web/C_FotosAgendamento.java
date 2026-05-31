package br.com.autoagenda.autoagenda.controller.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;
import br.com.autoagenda.autoagenda.repositorios.FotosAgendamentoRepository;
import br.com.autoagenda.autoagenda.service.FotosAgendamentoService;
import br.com.autoagenda.autoagenda.service.LogService;

@Controller
@RequestMapping("/{slug}/fotos-api")
public class C_FotosAgendamento {
    @Value("${app.upload.dir}") private String pastaFotos;
    @Autowired private FotosAgendamentoService service;
    @Autowired private FotosAgendamentoRepository repo;
    @Autowired private LogService log;
    
    @PostMapping("/upload/{agendamento}")
    public ResponseEntity<?> uploadFoto(@PathVariable("agendamento") Agendamento ag, @RequestParam("fotos") MultipartFile file){
        try {
            FotosAgendamento foto = service.salvarFoto(ag,  file);
            String idAgendamento = (ag != null && ag.getIdAgendamento() != null) ? ag.getIdAgendamento().toString() : "Temporário";
            log.registrar("Upload", "Foto", foto.getIdFoto(), "Foto anexada. Agendamento: " + idAgendamento + " | Arquivo: " + foto.getNomeArquivo(), false);
            return ResponseEntity.ok(foto);
        }catch(Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
        }
    }
    
    @GetMapping("/listar/{idAgendamento}") @ResponseBody
    public ResponseEntity<?> listaFotos (Model model, @PathVariable Integer idAgendamento) {
        List<FotosAgendamento> fotos = service.buscarPorAgendamento(idAgendamento);
        var resposta = fotos.stream().map(f -> Map.of("id", f.getIdFoto(),"nomeArquivo", f.getNomeArquivo())).toList();
        return resposta.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resposta);
    }
    
    @GetMapping("/listar-temp/{token}") @ResponseBody
    public ResponseEntity<?> listarFotosTemporarias(@PathVariable String token) {
        List<FotosAgendamento> fotos = service.buscarPorToken(token);
        var resposta = fotos.stream()
            .map(f -> Map.of("id", f.getIdFoto(), "nomeArquivo", f.getNomeArquivo())).toList();
        
        return resposta.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resposta);
    }
    
    @GetMapping("/imagem/{idFoto}")
    public ResponseEntity<byte[]> mostrarImagem(@PathVariable Integer idFoto) {
        FotosAgendamento foto = repo.findById(idFoto).orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        try {
            Path uploadRoot = Paths.get(pastaFotos).toAbsolutePath().normalize();
            Path caminho = uploadRoot.resolve(foto.getNomeArquivo()).normalize();

            if (!caminho.startsWith(uploadRoot)) throw new SecurityException("Acesso negado: caminho inválido");
            
            if (!Files.exists(caminho)) return ResponseEntity.notFound().build();

            byte[] bytes = Files.readAllBytes(caminho);

            String contentType = Files.probeContentType(caminho);
            if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(bytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/apagar/{idFoto}") @ResponseBody
    public ResponseEntity<?> removerFotoUnica(@PathVariable Integer idFoto) {
        try {
            FotosAgendamento foto = repo.findById(idFoto).orElseThrow();
            String nomeArquivo = foto.getNomeArquivo();
            String idAgendamento = (foto.getAgendamento() != null) ? foto.getAgendamento().getIdAgendamento().toString() : "Temporário";
            
            service.apagarFotoPorId(idFoto);
            
            log.registrar("Exclusão", "Foto", idFoto, "Foto removida. Agendamento: " + idAgendamento + " | Arquivo: " + nomeArquivo, false);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir");
        }
    }
}