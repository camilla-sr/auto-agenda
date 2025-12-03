package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import br.com.autoagenda.autoagenda.client.CodigoClient;

@Service
public class CodigoService {
    @Autowired private CodigoClient authClient;

    public String solicitarEnvioCodigo(String email) {
        try {
            ResponseEntity<String> response = authClient.gerarCodigo(email);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "OK";
            } else if (response.getStatusCode().value() == 201) {
                return "SEM_ENVIO"; 
            }	
            return "ERRO";
        } catch (ResourceAccessException e) {
            System.err.println("Erro: Microsserviço 2FA inacessível.");
            return "OFFLINE";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO";
        }
    }

    public String validarCodigo(String email, String codigo) {
        try {
            ResponseEntity<String> response = authClient.validarCodigo(email, codigo);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return "OK";
            }
            return "ERRO";

        } catch (HttpClientErrorException.Unauthorized e) {	
            return "INVALIDO";
        } catch (ResourceAccessException e) {
            System.err.println("Erro: Microsserviço 2FA inacessível.");
            return "OFFLINE";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO";
        }
    }
}