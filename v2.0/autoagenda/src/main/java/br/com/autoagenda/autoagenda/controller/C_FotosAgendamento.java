package br.com.autoagenda.autoagenda.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/fotos-api")
public class C_FotosAgendamento {
	@Autowired private FotosAgendamentoService service;
	@Autowired private FotosAgendamentoRepository repo;
	
	@PostMapping("/upload/{agendamento}")
	public ResponseEntity<?> uploadFoto(@PathVariable Agendamento ag, @RequestParam("fotos") MultipartFile file){
		try {
			FotosAgendamento foto = service.salvarFoto(ag,  file);
			return ResponseEntity.ok(foto);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
		}
	}
	
	@GetMapping("/listar/{idAgendamento}")
	@ResponseBody
	public ResponseEntity<?> listaFotos (Model model, @PathVariable Integer idAgendamento) {
		List<FotosAgendamento> fotos = service.buscarPorAgendamento(idAgendamento);
	    var resposta = fotos.stream().map(f -> Map.of("id", f.getIdFoto(),"nomeArquivo", f.getNomeArquivo())).toList();
	    return resposta.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resposta);
	}
	
	@GetMapping("/listar-temp/{token}")
    @ResponseBody
    public ResponseEntity<?> listarFotosTemporarias(@PathVariable String token) {
        List<FotosAgendamento> fotos = service.buscarPorToken(token);
        var resposta = fotos.stream()
            .map(f -> Map.of("id", f.getIdFoto(), "nomeArquivo", f.getNomeArquivo())).toList();
        
        return resposta.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resposta);
    }
	
	@GetMapping("/imagem/{idFoto}")
	public ResponseEntity<byte[]> mostrarImagem(@PathVariable Integer idFoto) throws IOException {
	    FotosAgendamento foto = repo.findById(idFoto).orElseThrow(() -> new RuntimeException("Foto não encontrada"));

	    try {
	        Path uploadRoot = Paths.get("uploads").toAbsolutePath().normalize();
	        Path caminho = uploadRoot.resolve(foto.getNomeArquivo().replace("uploads/", "")).normalize();

	        if (!caminho.startsWith(uploadRoot)) { throw new SecurityException("Acesso negado: caminho inválido"); }

	        byte[] bytes = Files.readAllBytes(caminho);

	        String contentType = Files.probeContentType(caminho);
	        if (contentType == null) { contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; }
	        
	        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(bytes);

	    } catch (IOException e) {
	        return ResponseEntity.status(500).build();
	    }
	}
	
	@DeleteMapping("/apagar/{idFoto}")
	@ResponseBody
	public ResponseEntity<?> removerFotoUnica(@PathVariable Integer idFoto) {
	    try {
	        service.apagarFotoPorId(idFoto);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Erro ao excluir");
	    }
	}

}