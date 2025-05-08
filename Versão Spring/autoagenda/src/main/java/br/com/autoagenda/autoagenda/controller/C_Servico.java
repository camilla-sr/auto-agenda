package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.M_Servico;
import br.com.autoagenda.autoagenda.include.Helper;

@Controller
@RequestMapping("/servicos")
public class C_Servico {

	@PostMapping("/cadastrarServico")
	public String cadastrarServico(@RequestParam String descricao) {
		if (descricao == null || descricao.trim().isEmpty()) return "erro-campo-vazio";
	
		M_Servico novoServico = new M_Servico();
		boolean salvou = novoServico.cadastrarServico(descricao);
		if(!salvou) return "algo-deu-errado";

		return "cadastro-concluido";
	}

	@PostMapping("/editarServico")
	public String editarServico(@RequestParam String novaDescricao, String idServico) {
		if (novaDescricao == null || novaDescricao.trim().isEmpty()) return "erro-campo-descricao-vazio";
		if (idServico == null || idServico.trim().isEmpty()) return "erro-campo-id-vazio";
		if ((novaDescricao == null || novaDescricao.trim().isEmpty()) ||
			(idServico == null || idServico.trim().isEmpty())) return "erro-campos-vazios";
		int id;
		if(!Helper.isNumeric(idServico)) {
			return "id-nao-e-numerico";
		} else {
			id = Helper.conversorNumero(idServico);
		}
		
		M_Servico servicoEdi = new M_Servico();
		boolean edicao = servicoEdi.editarServico(id, novaDescricao);
		if(!edicao) return "algo-deu-errado";
		
		return "edicao-concluida";
	}
    
	@PostMapping("/apagarServico")
	public String apagarServico(@RequestParam String idServico) {
		if (idServico == null || idServico.trim().isEmpty()) return "erro-campo-id-vazio";
		int id;
		if(!Helper.isNumeric(idServico)) {
			return "id-nao-e-numerico";
		} else {
			id = Helper.conversorNumero(idServico);
		}
		
		M_Servico servicoEdi = new M_Servico();
		boolean edicao = servicoEdi.apagarServico(id);
		if(!edicao) return "algo-deu-errado";
		
		return "servico-apagado";
	}

}
