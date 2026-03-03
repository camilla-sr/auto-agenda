package br.com.autoagenda.autoagenda.client;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CodigoClient {
	private final String URL_BASE = "http://localhost:8082/2fa";
    private final RestTemplate rt = new RestTemplate();

    public ResponseEntity<String> gerarCodigo(String email) {
        return rt.postForEntity(URL_BASE + "/gerar", Map.of("email", email), String.class);
    }

    public ResponseEntity<String> validarCodigo(String email, String codigo) {
        return rt.postForEntity(URL_BASE + "/validar", Map.of("email", email, "codigo", codigo), String.class);
    }
}