package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.autoagenda.autoagenda.dto.EmailDto;
import br.com.autoagenda.autoagenda.model.Agendamento;

@Service
public class EmailService {
    @Autowired private RestTemplate restTemplate;
    @Value("${autoagenda.email.remetente}") private String emailRemetente;
    private final String URL = "http://localhost:8081/enviarEmail";

    @Async
    public void enviarEmailConclusao(Agendamento agendamento) {
        try {
            String emailCliente = agendamento.getNomeCliente();
            String nomeCliente = agendamento.getNomeCliente();
            String assunto = "AutoAgenda: Seu serviço foi concluído!";
            String texto = String.format(
                "Olá, %s!\n\nSeu agendamento para o serviço '%s' foi concluído com sucesso em %s.\n\nObrigado por escolher nossa oficina!",
                nomeCliente,
                agendamento.getServico(),
                agendamento.getDataConclusao().toString()
            );

            EmailDto emailRequest = new EmailDto(emailCliente, emailRemetente, assunto, texto);

            restTemplate.postForEntity(URL, emailRequest, String.class);
        } catch (Exception e) {
            System.err.println("Erro ao solicitar envio de e-mail: " + e.getMessage());
        }
    }
}