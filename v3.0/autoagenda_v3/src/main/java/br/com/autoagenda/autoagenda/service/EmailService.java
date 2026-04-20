package br.com.autoagenda.autoagenda.service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.client.EmailClient;
import br.com.autoagenda.autoagenda.dto.EmailDto;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.model.SuperAdmin;

@Service
public class EmailService {
	@Autowired private EmailClient client;

    @Async
    public void enviarEmailConclusao(Agendamento agendamento) {
    	String emailCliente = agendamento.getCliente().getEmail();
    	String nomeCliente = agendamento.getCliente().getNomeCliente();
    	String assunto = "AutoAgenda: Seu serviço foi concluído!";
    	
    	String nomesServicos = agendamento.getServicos().stream().map(Servico::getNomeServico).collect(Collectors.joining(", "));
    	String dataFormatada = agendamento.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    	String texto = String.format(
		"Olá, %s!\n\nSeu agendamento para os serviços: '%s' foi concluído com sucesso em %s.\n\nObrigado por escolher nossa oficina!",
		nomeCliente,
		nomesServicos,
		dataFormatada);
    	
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
    	String assunto = String.format("%s: Novo Login!", func.getOficina().getNomeFantasia());
    	
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
    
    @Async
    public void redefirSenhaAdmin(SuperAdmin admin) {
    	String emailAdmin = admin.getEmail();
    	String nomeAdmin = admin.getNome();
    	String assunto = "AutoAgenda: Novo Login!";
    	
    	String texto = String.format(
		"Olá, %s!\n\nSeu novo acesso ao sistema foi criado. Use as credenciais abaixo para o primeiro acesso:"
		+ "\n\t\tUsuário: %s"
		+ "\n\t\tSenha provisória: %s"
		+ "\n\n*Importante! Esta é uma senha temporária gerada internamente. Ao efetuar o login, você será instruído"
		+ " a atualizar a senha com uma própria."
		+ "\nBem-vindo ao time!!",
		nomeAdmin,
		admin.getUsuario(),
		admin.getSenha());

    	EmailDto email = new EmailDto();
    	email.setDestinatario(emailAdmin);
    	email.setAssunto(assunto);
    	email.setTexto(texto);
    	
        try {
            client.enviar(email);
        } catch (Exception e) {
            System.err.println("Erro ao solicitar envio de e-mail: " + e.getMessage());
        }
    }
}