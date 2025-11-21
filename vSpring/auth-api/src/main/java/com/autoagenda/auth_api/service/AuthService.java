package com.autoagenda.auth_api.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.autoagenda.auth_api.model.Codigo2FA;
import com.autoagenda.auth_api.model.ValidacaoCodigo;
import com.autoagenda.auth_api.repositories.Codigo2FARepository;

@Service
public class AuthService {
	private final Codigo2FARepository repo;
	private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final SecureRandom random = new SecureRandom();
	
	public AuthService (Codigo2FARepository repo) { this.repo = repo; }
	
	private String gerarCodigo() {
		StringBuilder sb = new StringBuilder(6);
		
		for(int i = 0; i < 6; i++) {
			int index = random.nextInt(CARACTERES.length());
			sb.append(CARACTERES.charAt(index));
		}
		return sb.toString();
	}
	
	public String ultimoCodigo(String email) {
		Optional<Codigo2FA> ultimo = repo.findTopByEmailOrderByHoraGeracaoDesc(email);
		if(ultimo.isEmpty()) { return null; }
		return ultimo.get().getCodigo();
	}
	
	public boolean validarCodigo(ValidacaoCodigo dados) {
		Optional<Codigo2FA> ultimo = repo.findTopByEmailOrderByHoraGeracaoDesc(dados.getEmail());
		if(ultimo.isEmpty()) { return false; }
		
		Codigo2FA registro = ultimo.get();
		if(!registro.getCodigo().equals(dados.getCodigoDigitado())) { return false; }
		
		registro.setValidado(true);
		repo.save(registro);
		return true;
	}
	
	public void registro(String email, boolean enviado) {
		String codigo = gerarCodigo();
		Codigo2FA registro = new Codigo2FA(email, codigo, LocalDateTime.now(), false);
		registro.setEnviado(enviado);
		repo.save(registro);
	}
	
	public void atualizarStatusEnvio(String email, boolean enviado) {
		Optional<Codigo2FA> ultimo = repo.findTopByEmailOrderByHoraGeracaoDesc(email);
		if(ultimo.isPresent()) {
			Codigo2FA registro = ultimo.get();
			registro.setEnviado(enviado);
			repo.save(registro);
		}
	}
}