package com.autoagenda.auth_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autoagenda.auth_api.client.EmailClient;
import com.autoagenda.auth_api.dto.EmailDTO;
import com.autoagenda.auth_api.model.RequisicaoCodigo;
import com.autoagenda.auth_api.model.ValidacaoCodigo;
import com.autoagenda.auth_api.service.AuthService;

@RestController
@RequestMapping("/2fa")
public class AuthController {
	@Autowired private AuthService service;
	@Autowired private EmailClient client;
	
	@PostMapping("/gerar")
	public ResponseEntity<?> gerarCodigo(@RequestBody RequisicaoCodigo req){
		service.registro(req.getEmail(), false);
		
		EmailDTO email = new EmailDTO();
		email.setDestinatario(req.getEmail());
		email.setAssunto("Seu código de verificação chegou");
		email.setTexto("Seu código é: " + service.ultimoCodigo(req.getEmail()));
		
		try {
			client.enviar(email);
			service.atualizarStatusEnvio(req.getEmail(), true);
			
			return ResponseEntity.ok("Código enviado");
		}catch(Exception e) {
			service.atualizarStatusEnvio(req.getEmail(), false);
			return ResponseEntity.status(201).body("Envio indisponível. Tente novamente mais tarde");
		}
		
	}
	
	@PostMapping("/validar")
	public ResponseEntity<?> validarCodigo(@RequestBody ValidacaoCodigo dados){
		boolean valido = service.validarCodigo(dados);
		if(!valido) { return ResponseEntity.status(401).body("Código inválido"); }
		return ResponseEntity.ok("Código validado");
	}
}