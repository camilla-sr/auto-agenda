package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.include.Helper;
import br.com.autoagenda.autoagenda.model.M_Cliente;

@Controller
@RequestMapping("/clientes")
public class C_Cliente {

	@PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@RequestParam String nomeCliente, String whatsapp, String veiculo, int veiculoano) {
        if(nomeCliente == null || nomeCliente.trim().isEmpty()) return "campo-vazio";
        if(whatsapp == null || whatsapp.trim().isEmpty()) return "campo-vazio";
        if(veiculo == null || veiculo.trim().isEmpty()) return "campo-vazio";
        if(Helper.isNumeric(veiculoano + "")) return "campo-vazio";
        
        M_Cliente cliente = new M_Cliente();
        boolean resposta = cliente.cadastrarCliente(nomeCliente, whatsapp, veiculo, veiculoano);
        
        if(!resposta) return "cadastro-nao-concluido";
        return "cadastro-completo";
    }

	@PostMapping("/editarCliente")
    public String editarCliente(@RequestParam int idCliente, String nomeCliente, String whatsapp, String veiculo, int veiculoano) {
		if(nomeCliente == null || nomeCliente.trim().isEmpty()) return "campo-vazio";
        if(whatsapp == null || whatsapp.trim().isEmpty()) return "campo-vazio";
        if(veiculo == null || veiculo.trim().isEmpty()) return "campo-vazio";
        if(Helper.isNumeric(veiculoano + "")) return "campo-vazio";
        
        M_Cliente cliente = new M_Cliente();
        boolean resposta = cliente.editarCliente(idCliente, nomeCliente, whatsapp, veiculo, veiculoano);
        
        if(!resposta) return "cadastro-nao-concluido";
        return "cadastro-completo";
    }

//    public void consCliente() {
//        cl.listarCliente();
//    }

	@PostMapping("/apagarCliente")
    public String apagarCliente(@RequestParam int idCliente) {
        M_Cliente cliente = new M_Cliente();
        boolean resposta = cliente.apagarCliente(idCliente);
        
        if(!resposta) return "algo-deu-errado";
        return "apagou";
    }
}
