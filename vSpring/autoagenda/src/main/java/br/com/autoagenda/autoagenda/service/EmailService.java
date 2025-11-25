package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.client.EmailClient;
import br.com.autoagenda.autoagenda.dto.EmailDto;
import br.com.autoagenda.autoagenda.model.Agendamento;

@Service
public class EmailService {
	@Autowired private EmailClient client;

    @Async
    public void enviarEmailConclusao(Agendamento agendamento) {
    	String emailCliente = agendamento.getNomeCliente();
    	String nomeCliente = agendamento.getNomeCliente();
    	String assunto = "AutoAgenda: Seu serviço foi concluído!";
    	String texto = String.format(
		"Olá, %s!\n\nSeu agendamento para o serviço '%s' foi concluído com sucesso em %s.\n\nObrigado por escolher nossa oficina!",
		nomeCliente,
		agendamento.getServico().getNomeServico(),
		agendamento.getDataConclusao().toString());
    	
    	EmailDto email = new EmailDto();
    	email.setDestinatario(emailCliente);
    	email.setAssunto(assunto);
    	email.setTexto(texto);
    	
        try {
            client.enviar(email);
        } catch (Exception e) {
            System.err.println("Erro ao solicitar envio de e-mail: " + e.getMessage());
        }
    }
}