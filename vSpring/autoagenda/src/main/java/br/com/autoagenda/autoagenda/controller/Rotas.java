package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class Rotas {
	@Autowired
	private Sessao s;
	
	@Autowired
	private FuncionarioRepository repoFunc;
	@Autowired
	private ServicoRepository repoServ;
	@Autowired
	private ProdutoRepository repoProd;
	@Autowired
	private AgendamentoRepository repoAg;
	
	@ModelAttribute
    public void usuarioGlobal(HttpSession session, Model model) {
        if (s.loginAtivo(session)) {
            model.addAttribute("usuarioLogado", (Funcionario) session.getAttribute("usuarioLogado"));
        }
    }
	
	public String verificaUsuario(HttpSession session, String page) {
		if(!s.loginAtivo(session)) return "login";
		return page;
	}
	
	public double somaPrecoCusto() {
		Double soma = repoProd.sumTotalPrecoCusto();
		return soma == null ? 0.0: soma;
	}
	
	@GetMapping("/cadastroSistema")
	public String cadastroSistema() {
		return "cadastro";
	}
    @GetMapping("/login")
    public String logar() {
    	return "login";
    }
	
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		model.addAttribute("totalProdutos", repoProd.count());
		model.addAttribute("estoqueBaixo", repoProd.countByEstoqueAtualLessThan(5));	//aqui passa o de corte pra considerar baixo
		model.addAttribute("precoEstoque", somaPrecoCusto());
		model.addAttribute("totalAgenda", repoAg.count());
		return verificaUsuario(session, "dashboard");
	}
	
    @GetMapping("/funcionarios")
    public String funcionarios(HttpSession session, Model model) {
    	if(!s.verificaAcesso(session, "admin")) { return "acesso-negado"; }
    	model.addAttribute("funcionarios", repoFunc.findAll());
    	return verificaUsuario(session, "funcionarios");
    }
	
    @GetMapping("/servicos")
    public String servicos(HttpSession session, Model model) {
    	model.addAttribute("servicos", repoServ.findAll());
    	return verificaUsuario(session, "servicos");
    }
    
    @GetMapping("/clientes")
    public String clientes(HttpSession session) {
    	return verificaUsuario(session, "clientes");
    }
    
    @GetMapping("/produtos")
    public String produtos(HttpSession session, Model model) {
    	model.addAttribute("totalProdutos", repoProd.count());
    	model.addAttribute("estoqueBaixo", repoProd.countByEstoqueAtualLessThan(5));
		model.addAttribute("precoEstoque", somaPrecoCusto());
    	model.addAttribute("produtos", repoProd.findAll());
    	return verificaUsuario(session, "produtos");
    }
    
    @GetMapping("/agendamentos")
    public String agendamentos(HttpSession session) {
    	return verificaUsuario(session, "agendamentos");
    }
}
