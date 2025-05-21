package br.com.autoagenda.autoagenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.model.Produto;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;

@RestController
@RequestMapping("/produto-api")
public class C_Produto {

	@Autowired
	private ProdutoRepository repo;
	
	@PostMapping("/cadastrarProduto")
	public String cadastrarProduto(@RequestParam int idProduto, String codigoProduto, String categoria, String nomeProduto, 
									double preco, String fornecedor, int estoqueAtual, int estoqueMinimo, String descricao) {
		
		if(idProduto == 0 || codigoProduto.trim().isEmpty() || categoria.trim().isEmpty() || nomeProduto.trim().isEmpty() || preco == 0
				|| fornecedor.trim().isEmpty() || estoqueAtual == 0 || estoqueMinimo == 0) {
			return "campo-nao-informado";
		}
		
		Produto produto = new Produto();
		boolean resposta = produto.cadastrarProduto(idProduto, codigoProduto, categoria, nomeProduto, preco, fornecedor, estoqueAtual, estoqueMinimo, descricao);
		
		return resposta ? "cadastro-realizado" : "cadastro-nao-realizado";
	}
	
	@GetMapping("/consulta")
	public List<Produto> consultarServico(){
	    List<Produto> lista = new ArrayList<>();
	    repo.findAll().forEach(lista::add);
	    return lista;
	}

	
//	@PostMapping("/editarProduto")
//    public String editarCliente(@RequestParam int idProduto, String codigoProduto, String categoria, String nomeProduto, 
//								double preco, String fornecedor, int estoqueAtual, int estoqueMinimo, String descricao) {
//		
//		if(idProduto == 0 || codigoProduto.trim().isEmpty() || categoria.trim().isEmpty() || nomeProduto.trim().isEmpty() || preco == 0
//				|| fornecedor.trim().isEmpty() || estoqueAtual == 0 || estoqueMinimo == 0) {
//			return "campo-nao-informado";
//		}
//        
//		M_Produto produto = new M_Produto();
//        boolean resposta = produto.editarProduto(idCliente, nomeCliente, whatsapp, veiculo, veiculoano);
//        
//        return resposta ? "edicao-realizada" : "edicao-nao-realizadaa";
//    }
}