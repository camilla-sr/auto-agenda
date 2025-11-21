package com.autoagenda.auth_api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.autoagenda.auth_api.dto.EmailDTO;

@Component
public class EmailClient {
	private final RestTemplate rt;
	public EmailClient(RestTemplate rt) { this.rt = rt; }
	
	public void enviar(EmailDTO email) {
		String url = "http://localhost:8081/enviarEmail";
		rt.postForObject(url, email, Void.class);
	}
}