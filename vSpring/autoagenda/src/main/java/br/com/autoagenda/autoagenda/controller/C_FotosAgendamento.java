package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;
import br.com.autoagenda.autoagenda.service.FotosAgendamentoService;

@RestController
@RequestMapping("/fotos")
public class C_FotosAgendamento {
	@Autowired private FotosAgendamentoService service;
	
	@PostMapping("/upload/{agendamentoId}")
	public ResponseEntity<?> uploadFoto(@PathVariable Integer agId, @RequestParam("fotos") MultipartFile file){
		try {
			FotosAgendamento foto = service.salvarFoto(agId,  file);
			return ResponseEntity.ok(foto);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
		}
	}
}