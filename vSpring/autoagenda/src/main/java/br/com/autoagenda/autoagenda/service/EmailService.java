package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.client.EmailClient;
import br.com.autoagenda.autoagenda.dto.EmailDto;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Funcionario;

@Service
public class EmailService {
	@Autowired private EmailClient client;

    @Async
    public void enviarEmailConclusao(Agendamento agendamento) {
    	String emailCliente = agendamento.getCliente().getEmail();
    	String nomeCliente = agendamento.getCliente().getNomeCliente();
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
    
    @Async
    public void redefirSenha(Funcionario func) {
    	String emailFuncionario = func.getEmail();
    	String nomeFuncionario = func.getNomeFuncionario();
    	String assunto = "AutoAgenda: Novo Login!";
    	
    	String texto = String.format(
		"Olá, %s!\n\nSeu novo acesso ao sistema foi criado. Use as credenciais abaixo para o primeiro acesso:"
		+ "\n\t\tUsuário: %s"
		+ "\n\t\tSenha provisória: %s"
		+ "\n\n*Importante! Esta é uma senha temporária gerada internamente. Ao efetuar o login, você será instruído"
		+ " a atualizar a senha com uma própria."
		+ "\nObrigado por escolher nossa oficina!",
		nomeFuncionario,
		func.getUsuario(),
		func.getSenha());

    	EmailDto email = new EmailDto();
    	email.setDestinatario(emailFuncionario);
    	email.setAssunto(assunto);
    	email.setTexto(texto);
    	
        try {
            client.enviar(email);
        } catch (Exception e) {
            System.err.println("Erro ao solicitar envio de e-mail: " + e.getMessage());
        }
    }
}