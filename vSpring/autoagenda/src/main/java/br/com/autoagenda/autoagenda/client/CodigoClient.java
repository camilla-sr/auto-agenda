package br.com.autoagenda.autoagenda.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import br.com.autoagenda.autoagenda.dto.CodigoDto;

@Component
public class CodigoClient {
	@Autowired private RestTemplate rt;
	
	public void requisitar(String email) {
		String url = "http://localhost:8082/2fa/gerar";
		if(!email.isBlank()) { rt.postForObject(url, email, Void.class); }
	}
	
	public boolean validar(CodigoDto cod) {
		String url = "http://localhost:8082/2fa/validar";
		if(cod.getEmail().isEmpty() || cod.getCodigo().isEmpty()) { return false; }
		
		try {
			rt.postForObject(url, cod, Void.class);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}