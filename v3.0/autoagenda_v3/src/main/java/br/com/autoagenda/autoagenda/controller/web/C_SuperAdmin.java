package br.com.autoagenda.autoagenda.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.LogSistema;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.LogSistemaRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import br.com.autoagenda.autoagenda.repositorios.SuperAdminRepository;
import br.com.autoagenda.autoagenda.service.FuncionarioService;
import br.com.autoagenda.autoagenda.service.LogService;
import br.com.autoagenda.autoagenda.service.SuperAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;

@Controller
@RequestMapping("/superadmin-api")
public class C_SuperAdmin {
    @Autowired SuperAdminService service;
    @Autowired FuncionarioService funcService;
    @Autowired SuperAdminRepository repo;
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private FuncionarioRepository funcRepo;
    @Autowired private LogSistemaRepository logRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private AgendamentoRepository agRepo;
    @Autowired private ProdutoRepository prodRepo;
    @Autowired private LogService log;
    
    @PostMapping(value = "/logar")
    public String logar(@RequestParam String usuario, @RequestParam String senha, HttpSession session) {
        SuperAdmin admin = service.autenticar(usuario, senha);
        if(admin == null) return "redirect:/superadmin/login?erro=true";
        session.setAttribute("primeiroLogin", admin.isPrimeiroLogin());
        session.setAttribute("superAdminLogado", admin);
        
        log.registrar("Login", "Sistema", null, "Acesso ao painel por superadmin", false);
        return "redirect:/autoagenda";
    }
    
    @PostMapping(value = "/salvar") @ResponseBody
    public ResponseEntity<?> salvar(@ModelAttribute SuperAdmin admin, @RequestParam(required = false) String novaSenha,
                                    @RequestParam(required = false, defaultValue = "false") boolean cadastroInicial) {
        try {
        	String desc = cadastroInicial 
                    ? "Criou novo super usuário: '" + admin.getNome() + "', E-mail: '" + admin.getEmail() + "'"
                    : "Editou dados do super usuário: '" + admin.getNome() + "'";
        	
            service.salvarOuAtualizar(admin, novaSenha, cadastroInicial);
            log.registrar(cadastroInicial ? "Criação" : "Edição", "SuperAdmin", admin.getIdSuperadmin(), desc, false);
            return ResponseEntity.ok(Map.of("mensagem", "Dados salvos com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro interno."));
        }
    }
    
    @GetMapping("/listar-admins") @ResponseBody
    public ResponseEntity<Iterable<SuperAdmin>> listarSuperAdmins() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/admin/status/{id}") @ResponseBody
    public ResponseEntity<?> toggleStatusAdmin(@PathVariable Integer id, HttpSession session) {
        SuperAdmin logado = (SuperAdmin) session.getAttribute("superAdminLogado");
        if (logado.getIdSuperadmin().equals(id)) {
            return ResponseEntity.badRequest().body("Não é possível desativar a própria conta.");
        }
        SuperAdmin admin = repo.findById(id).orElseThrow();
        admin.setAtivo(!admin.isAtivo());
        repo.save(admin);
        
        String desc = admin.isAtivo() 
                ? admin.getUsuario() + " está ativo no sistema e pode fazer login no sistema" 
                : admin.getUsuario() + " foi desativado no sistema";
                
        log.registrar("Alterar Status", "SuperAdmin", id, desc, false);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.registrar("Logout", "Sistema", null, "Saída do painel mestre", false);
        session.removeAttribute("superAdminLogado");
        return "redirect:/superadmin/login";
    }
    
    @GetMapping("/acessar-oficina/{slug}")
    public String acessarOficinaDireto(@PathVariable String slug, HttpSession session) {
        SuperAdmin admin = (SuperAdmin) session.getAttribute("superAdminLogado");
        Oficina oficina = oficinaRepo.findBySlug(slug).orElse(null);
        if (oficina == null) return "redirect:/autoagenda";

        session.setAttribute("oficinaAtual", oficina);
        session.setAttribute("acessoMestre", true);
        
        log.registrar("Acesso Remoto", "Oficina", oficina.getIdOficina(), "Acesso administrativo à oficina: " + oficina.getNomeFantasia(), false);
        return "redirect:/" + slug;
    }
    
    @GetMapping("/oficina/estatisticas/{id}") @ResponseBody
    public Map<String, Long> getStats(@PathVariable Integer id) {
        Oficina of = oficinaRepo.findById(id).orElseThrow();
        Map<String, Long> stats = new HashMap<>();
        
        stats.put("totalClientes", clienteRepo.countByOficinaAndAtivoTrue(of));
        stats.put("totalProdutos", prodRepo.countByOficina(of));
        stats.put("totalAgendamentos", agRepo.countByOficinaAndAtivoTrue(of));
        return stats;
    }
    
    @PostMapping("/oficina/status/{id}") @ResponseBody
    public ResponseEntity<?> toggleStatus(@PathVariable Integer id, @RequestParam String senhaConfirmacao, HttpSession session) {
        SuperAdmin logado = (SuperAdmin) session.getAttribute("superAdminLogado");
        if (logado == null || !logado.getSenha().equals(senhaConfirmacao)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha mestre incorreta.");
        }
        Oficina of = oficinaRepo.findById(id).orElseThrow();
        of.setAtivo(!of.getAtivo());
        oficinaRepo.save(of);
        
        log.registrar("Alterar Status", "Oficina", id, "Oficina bloqueada/desbloqueada: " + of.getNomeFantasia(), false);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/funcionario/alterar-nivel") @ResponseBody
    public ResponseEntity<?> alterarNivel(@RequestParam Integer idFuncionario, @RequestParam String nivel) {
        Funcionario f = funcRepo.findById(idFuncionario).orElseThrow();
        f.setAcesso(nivel);
        funcRepo.save(f);
        
        log.registrar("Alterar Acesso", "Funcionário", idFuncionario, "Nível alterado para " + nivel + ": " + f.getUsuario(), false);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/oficina/modulos/{id}") @ResponseBody
    public Map<String, Boolean> getModulos(@PathVariable Integer id) {
        Oficina of = oficinaRepo.findById(id).orElseThrow();
        Map<String, Boolean> modulos = new HashMap<>();
        modulos.put("usarProdutos", of.isUsarProdutos());
        modulos.put("usarFinanceiro", of.isUsarFinanceiro());
        return modulos;
    }

    @PostMapping("/salvar-modulos") @ResponseBody
    public ResponseEntity<?> salvarModulos(@RequestParam Integer idOficina, @RequestParam(defaultValue = "false") boolean usarProdutos) {
        try {
            Oficina of = oficinaRepo.findById(idOficina).orElseThrow();
            of.setUsarProdutos(usarProdutos);
            oficinaRepo.save(of);
            
            log.registrar("Edição Módulos", "Oficina", idOficina, "Módulos atualizados. Produtos: " + usarProdutos, false);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar os módulos.");
        }
    }
    
    @GetMapping("/oficina/funcionarios/{id}") @ResponseBody
    public ResponseEntity<List<Funcionario>> listarFuncionariosDaOficina(@PathVariable("id") Integer id, 
            @RequestParam(name = "nivel", defaultValue = "todos") String nivel) {
        
        Oficina of = oficinaRepo.findById(id).orElse(null);
        if (of == null) return ResponseEntity.notFound().build();
        List<Funcionario> todos = funcRepo.findByOficina(of);
        
        if ("todos".equalsIgnoreCase(nivel)) {
            return ResponseEntity.ok(todos);
        } else {
            List<Funcionario> filtrados = todos.stream().filter(f -> f.getAcesso() != null && nivel.equalsIgnoreCase(f.getAcesso().trim()))
                .collect(Collectors.toList());
            return ResponseEntity.ok(filtrados);
        }
    }

    @PostMapping("/funcionario/status/{id}") @ResponseBody
    public ResponseEntity<?> alterarStatusFuncionario(@PathVariable Integer id) {
        Funcionario func = funcRepo.findById(id).orElseThrow();
        func.setAtivo(!func.isAtivo());
        funcRepo.save(func);
        
        String desc = func.isAtivo() 
                ? func.getUsuario() + " está ativo no sistema e pode fazer login na oficina " + func.getOficina().getNomeFantasia()
                : func.getUsuario() + " foi desativado na oficina " + func.getOficina().getNomeFantasia();
                
        log.registrar("Alterar Status", "Funcionário", id, desc, false);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/funcionario/logout/{id}") @ResponseBody
    public ResponseEntity<?> forcarLogout(@PathVariable Integer id) {
        log.registrar("Forçar Logout", "Funcionário", id, "Sessão derrubada pelo SuperAdmin", false);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/funcionario/reset-senha/{id}") @ResponseBody
    public ResponseEntity<?> resetarSenhaFuncionario(@PathVariable Integer id) {
        try {
            funcService.resetarSenha(id);
            log.registrar("Reset Senha", "Funcionário", id, "Envio de senha provisória via painel mestre", false);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao resetar senha: " + e.getMessage());
        }
    }
    
    @PostMapping("/funcionario/salvar") @ResponseBody
    public ResponseEntity<?> salvarFuncionarioPeloAdmin(@RequestParam Integer idOficina, @ModelAttribute Funcionario func,
            @RequestParam(required = false) String novaSenha,
            @RequestParam(required = false, defaultValue = "false") boolean cadastroInicial) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina)
                .orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada para o ID: " + idOficina));
            funcService.salvarOuAtualizar(func, novaSenha, cadastroInicial, oficina);
            
            log.registrar(cadastroInicial ? "Criação" : "Edição", "Funcionário", func.getIdFuncionario(), "Gerenciado via SuperAdmin: " + func.getUsuario(), false);
            return ResponseEntity.ok().build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body("O CPF informado é inválido.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Este e-mail ou nome de usuário já está em uso.");
        } catch (Exception e) {
            e.printStackTrace(); 
            String erroReal = e.getClass().getSimpleName() + ": " + e.getMessage();
            return ResponseEntity.status(500).body("Erro interno: " + erroReal);
        }
    }
    
    @GetMapping("/listar-logs") @ResponseBody
    public ResponseEntity<Page<LogSistema>> listarLogsFiltrados(
    		@RequestParam(required = false) String dataInicio, @RequestParam(required = false) String dataFim,
    		@RequestParam(required = false) String acao, @RequestParam(required = false) String busca,
    		@RequestParam(required = false) String origem, @RequestParam(required = false) Integer idOficina,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){

        LocalDateTime inicio = (dataInicio != null && !dataInicio.isEmpty()) ? LocalDate.parse(dataInicio).atStartOfDay() : null;
        LocalDateTime fim = (dataFim != null && !dataFim.isEmpty()) ? LocalDate.parse(dataFim).atTime(23, 59, 59) : null;

        Pageable pageable = PageRequest.of(page, size);
        Page<LogSistema> logsPage = logRepo.filtrarLogs(inicio, fim, acao, busca, origem, idOficina, pageable);
        
        return ResponseEntity.ok(logsPage);
    }
    
    @GetMapping("/status-servicos") @ResponseBody
    public Map<String, String> statusServicos() {
        Map<String, String> status = new HashMap<>();
        RestTemplate rest = new RestTemplate();
        status.put("email", checkHealth(rest, "http://localhost:8081/actuator/health"));
        status.put("auth", checkHealth(rest, "http://localhost:8082/actuator/health"));
        return status;
    }

    private String checkHealth(RestTemplate rest, String url) {
        try {
            ResponseEntity<String> res = rest.getForEntity(url, String.class);
            return res.getStatusCode().is2xxSuccessful() ? "Online" : "Offline";
        } catch (Exception e) {
            return "Offline";
        }
    }
}