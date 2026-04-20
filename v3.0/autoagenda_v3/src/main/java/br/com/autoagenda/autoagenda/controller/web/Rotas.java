package br.com.autoagenda.autoagenda.controller.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.autoagenda.autoagenda.config.Sessao;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Rotas {
	@Autowired private Sessao s;
	@Autowired private FuncionarioRepository repoFunc;
	@Autowired private ServicoRepository repoServ;
	@Autowired private ProdutoRepository repoProd;
	@Autowired private AgendamentoRepository repoAg;
	@Autowired private ClienteRepository repoCl;
	@Autowired private VeiculoRepository repoVe;
	@Autowired private OficinaRepository oficinaRepo;

	@GetMapping("/{slug}/logout")
    public String logoutOficina(@PathVariable String slug, HttpSession session) {
        s.logout(session); 
        return "redirect:/" + slug + "/login";
    }
	
	@ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
	
	@ModelAttribute
	public void carregarOficinaAtual(@PathVariable(value = "slug", required = false) String slug, HttpSession session, Model model) {
		if (slug != null && !slug.isEmpty()) {
			SuperAdmin superAdmin = (SuperAdmin) session.getAttribute("superAdminLogado");
			
			Optional<Oficina> of = oficinaRepo.findBySlug(slug);
			if (of.isPresent()) {
				if (!of.get().getAtivo() && superAdmin == null) {
					session.removeAttribute("oficinaAtual");
					return; 
				}
				session.setAttribute("oficinaAtual", of.get());
				model.addAttribute("oficinaContexto", of.get());
			} else {
				session.removeAttribute("oficinaAtual");
			}
		}
	}
	
	@ModelAttribute
    public void usuarioGlobal(HttpSession session, Model model) {
        if (s.loginAtivo(session)) { model.addAttribute("usuarioLogado", (Funcionario) session.getAttribute("usuarioLogado")); }
    }
	
	//		UTILITÁRIOS
	private String verificaUsuario(HttpSession session, String page, String slug) {
        if(!s.loginAtivo(session)) {
            return "redirect:/" + slug + "/login"; 
        }
        Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
        Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
        
        if (oficinaAtual == null) return "redirect:/erro/oficina-nao-encontrada";
        
        if (!logado.getOficina().getIdOficina().equals(oficinaAtual.getIdOficina())) {
             return "redirect:/acesso-negado"; 
        }
        return page;
    }
	
	private String somaPrecoCusto(Oficina oficina) {
		Float soma = repoProd.sumTotalPrecoCustoByOficina(oficina);
		if(soma == null) return "0.00";

		BigDecimal valor = BigDecimal.valueOf(soma).setScale(2, RoundingMode.HALF_UP);

	    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
	    nf.setMinimumFractionDigits(2);
	    nf.setMaximumFractionDigits(2);
	    return nf.format(valor);
	}
	
	//		PÁGINAS PÚBLICAS	
	@GetMapping("/superadmin/login")
	public String loginAdmin(){
        return "superadmin/login";
	}
	
	@GetMapping("/superadmin/cadastro")
	public String cadastroSuperAdmin() {
		return "superadmin/cadastro";
	}
	
	@GetMapping("/")
    public String redirecionarRaiz() {
        return "redirect:/autoagenda"; 
    }
	
	@GetMapping("/autoagenda")
    public String indexAdmin(HttpSession session, Model model) {
        SuperAdmin adminLogado = (SuperAdmin) session.getAttribute("superAdminLogado");
        
        if(adminLogado == null) return "redirect:/superadmin/login";
        
        model.addAttribute("superadmin", adminLogado);
        model.addAttribute("listaOficinas", oficinaRepo.findAll());
        model.addAttribute("totalOficinas", oficinaRepo.count());
        model.addAttribute("oficinasAtivas", oficinaRepo.countByAtivoTrue());
        model.addAttribute("totalUsuariosGlobais", repoFunc.count());
        
        return "superadmin/painel";
    }
	
	//		PÁGINAS DE OFICINAS 
	@GetMapping("/{slug}/cadastroSistema")
	public String cadastroSistema() {
		if (oficinaRepo.count() == 0) return "redirect:/setup/oficina";
		return "cadastro";
	}
	
	@GetMapping("/{slug}/login")
	public String logar(@PathVariable String slug, HttpSession session) {
		if (oficinaRepo.count() == 0) return "redirect:/setup/oficina";
        if (session.getAttribute("oficinaAtual") == null) return "redirect:/";
        
        return "login";
	}
	
	@GetMapping("/{slug}")
	public String index(@PathVariable String slug, HttpSession session, Model model) {		
		if(s.loginAtivo(session)) {
			Boolean primeiroLogin = (Boolean) session.getAttribute("primerioLogin");			
			model.addAttribute("primerioLogin", primeiroLogin != null ? primeiroLogin : false);
		}
		Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
		
		model.addAttribute("totalProdutos", repoProd.countByOficina(oficinaAtual));
		model.addAttribute("estoqueBaixo", repoProd.countEstoqueBaixoByOficina(oficinaAtual));
		model.addAttribute("estoqueZerado", repoProd.countByEstoqueAtualAndOficina(0, oficinaAtual));
		model.addAttribute("precoEstoque", somaPrecoCusto(oficinaAtual));
		model.addAttribute("totalAgenda", repoAg.countByOficina(oficinaAtual));
		model.addAttribute("agendaPendente", repoAg.countPendentesByOficina(oficinaAtual));
		model.addAttribute("agendaAndamento", repoAg.countAndamentoByOficina(oficinaAtual));
		model.addAttribute("agendaConcluido", repoAg.countConcluidosbyOficina(oficinaAtual));
		model.addAttribute("agendaConcluidoHoje", repoAg.countConcluidosHojeByOficina(oficinaAtual));
		
		return verificaUsuario(session, "dashboard", slug);
	}
	
    @GetMapping("/{slug}/funcionarios")
    public String funcionarios(@PathVariable String slug, HttpSession session, Model model) {
    	if(!s.verificaAcesso(session, "admin")) return "acesso-negado";
    	
    	Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
    	model.addAttribute("funcionarios", repoFunc.findByOficinaAndIdFuncionarioNot(
            logado.getOficina(), 
            logado.getIdFuncionario()
        ));
    	return verificaUsuario(session, "funcionarios", slug);
    }
    
    @GetMapping("/{slug}/perfil")
    public String perfil(@PathVariable String slug, HttpSession session, Model model) {
        if(!s.loginAtivo(session)) return "redirect:/" + slug + "/login";
        
        Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
        Funcionario atual = repoFunc.findById(logado.getIdFuncionario()).get();
        
        List<Agendamento> historico = repoAg.findHistoricoDoFuncionario(atual);
        
        // Cálculo das métricas
        long concluidos = historico.stream().filter(a -> a.getStatusAgendamento().equalsIgnoreCase("Concluido") || a.getStatusAgendamento().equalsIgnoreCase("Concluído")).count();
        long cancelados = historico.stream().filter(a -> a.getStatusAgendamento().equalsIgnoreCase("Cancelado")).count();
        long andamento = historico.stream().filter(a -> a.getStatusAgendamento().equalsIgnoreCase("Em Andamento")).count();
        
        model.addAttribute("historico", historico);
        model.addAttribute("usuarioLogado", atual);
        model.addAttribute("metricaTotal", historico.size());
        model.addAttribute("metricaConcluidos", concluidos);
        model.addAttribute("metricaCancelados", cancelados);
        model.addAttribute("metricaAndamento", andamento);
        return verificaUsuario(session, "perfil", slug);
    }
    
    @GetMapping("/{slug}/clientes")
    public String clientes(@PathVariable String slug, HttpSession session, Model model) {
    	if(!s.verificaAcesso(session, "admin")) return "acesso-negado";
    	
    	Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
    	model.addAttribute("clientes", repoCl.findByOficinaAndAtivoTrue(oficinaAtual));
    	return verificaUsuario(session, "clientes", slug);
    }
	
    @GetMapping("/{slug}/servicos")
    public String servicos(@PathVariable String slug, HttpSession session, Model model) {
    	Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
    	model.addAttribute("servicos", repoServ.findByOficinaAndAtivoTrue(oficinaAtual));
    	return verificaUsuario(session, "servicos", slug);
    }
    
    @GetMapping("/{slug}/produtos")
    public String produtos(@PathVariable String slug, HttpSession session, Model model) {
    	Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
    	
    	model.addAttribute("totalProdutos", repoProd.countByOficina(oficinaAtual));
    	model.addAttribute("estoqueBaixo", repoProd.countEstoqueBaixoByOficina(oficinaAtual));
    	model.addAttribute("estoqueZerado", repoProd.countByEstoqueAtualAndOficina(0, oficinaAtual));
		model.addAttribute("precoEstoque", somaPrecoCusto(oficinaAtual));
    	model.addAttribute("produtos", repoProd.findByOficina(oficinaAtual));
    	return verificaUsuario(session, "produtos", slug);
    }
    
    @GetMapping("/{slug}/agendamentos")
    public String agendamentos(@PathVariable String slug, HttpSession session, Model model) {
    	Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
    	
    	model.addAttribute("servicos", repoServ.findByOficinaAndAtivoTrue(oficinaAtual));
    	model.addAttribute("agendamentos", repoAg.findByOficinaAndAtivoTrue(oficinaAtual));
    	model.addAttribute("clientes", repoCl.findByOficinaAndAtivoTrue(oficinaAtual));
    	model.addAttribute("veiculos", repoVe.findByCliente_OficinaAndAtivoTrue(oficinaAtual));
		model.addAttribute("agendaPendente", repoAg.countPendentesByOficina(oficinaAtual));
		model.addAttribute("agendaConcluido", repoAg.countConcluidosbyOficina(oficinaAtual));
		model.addAttribute("agendaAndamento", repoAg.countAndamentoByOficina(oficinaAtual));
		model.addAttribute("agendaConcluidoHoje", repoAg.countConcluidosHojeByOficina(oficinaAtual));
    	return verificaUsuario(session, "agendamentos", slug);
    }
    
    @GetMapping("/configurar_login")
    public String configurar(HttpSession session, Model model) {
    	return "configurar_login";
    }
    
    @GetMapping("/{slug}/painel")
    public String painel(@PathVariable String slug, HttpSession session, Model model) {
        if(!s.loginAtivo(session)) return "redirect:/" + slug + "/login";

        Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
        if (oficinaAtual == null) return "redirect:/erro/oficina-nao-encontrada";
        if(!s.verificaAcesso(session, "admin")) return "acesso-negado";
        
        model.addAttribute("oficina", oficinaAtual);
        model.addAttribute("statusEmail", true); 
        
        return verificaUsuario(session, "painel", slug);
    }
    
	@GetMapping("/{slug}/recuperar-senha")
	public String recuperarSenha(@PathVariable String slug, HttpSession session) { 
		if(s.loginAtivo(session)) { return "redirect:/"; }
		return "esqueciasenha"; 
	}
}
