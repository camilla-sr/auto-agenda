package br.com.autoagenda.autoagenda.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import br.com.autoagenda.autoagenda.dto.EmailDto;

@Component
public class EmailClient {
	private final RestTemplate rt;
	
	public EmailClient(RestTemplate rt) { this.rt = rt; }
	
	public void enviar(EmailDto email) {
		String url = "http://localhost:8081/enviarEmail";
		rt.postForObject(url, email, Void.class);
	}
}