package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;

@RestController
@RequestMapping("/cliente-api")
public class C_Cliente {
	@Autowired private ClienteRepository repo;

	@PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@RequestParam String nomeCliente, String whatsapp, String veiculo, int veiculoano) {
        if(nomeCliente == null || nomeCliente.trim().isEmpty()) return "campo-vazio";
        if(whatsapp == null || whatsapp.trim().isEmpty()) return "campo-vazio";
        if(veiculo == null || veiculo.trim().isEmpty()) return "campo-vazio";
        
        Cliente cliente = new Cliente();
        boolean resposta = cliente.cadastrarCliente(nomeCliente, whatsapp, veiculo, veiculoano);
        
        return resposta ? "cadastro-completo" : "cadastro-nao-concluido";
    }

	@PostMapping("/editarCliente")
    public String editarCliente(@RequestParam int idCliente, String nomeCliente, String whatsapp, String veiculo, int veiculoano) {
		if(nomeCliente == null || nomeCliente.trim().isEmpty()) return "campo-vazio";
        if(whatsapp == null || whatsapp.trim().isEmpty()) return "campo-vazio";
        if(veiculo == null || veiculo.trim().isEmpty()) return "campo-vazio";
        
        Cliente cliente = new Cliente();
        
        return "foi";
    }

	@PostMapping("/apagarCliente")
    public String apagarCliente(@RequestParam int idCliente) {
        Cliente cliente = new Cliente();
        return "foi";
    }
}