package br.com.autoagenda.autoagenda.controller.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.service.LogService;
import br.com.autoagenda.autoagenda.service.SetupService;

@Controller
@RequestMapping("/setup")
public class C_Setup {
    @Autowired private SetupService setupService;
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private LogService log;

    @GetMapping("/oficina")
    public String formOficina(Model model) {
        model.addAttribute("oficina", new Oficina());
        return "setup/oficina"; 
    }

    @PostMapping("/oficina")
    public String salvarOficina(Oficina oficina,
                @RequestParam(value = "imgLogo", required = false) MultipartFile logo,
                @RequestParam(value = "imgFavicon", required = false) MultipartFile favicon,
                RedirectAttributes ra) {
        
        Oficina salva = setupService.salvarOficinaInicial(oficina, logo, favicon);
        
        log.registrar("Setup", "Oficina", salva.getIdOficina(), "Oficina inicial criada: " + salva.getNomeFantasia(), false);
        ra.addFlashAttribute("idOficina", salva.getIdOficina());
        return "redirect:/setup/admin";
    }

    @GetMapping("/admin")
    public String formAdmin(Model model) {
        if (!model.containsAttribute("idOficina")) {
            List<Oficina> lista = oficinaRepo.findAll();
            if (lista.isEmpty()) { return "redirect:/setup/oficina"; }
            model.addAttribute("idOficina", lista.get(0).getIdOficina());
        }
        model.addAttribute("funcionario", new Funcionario());
        return "setup/admin"; 
    }

    @PostMapping("/admin")
    public String salvarAdmin(Funcionario admin, @RequestParam Integer idOficina, Model model) { // SEM @PathVariable AQUI
        try {
            setupService.criarAdminInicial(admin, idOficina);
            log.registrar("Setup", "Funcionário", admin.getIdFuncionario(), "Admin inicial criado: " + admin.getUsuario() + " (Vinculado à Oficina ID: " + idOficina + ")", false);
            
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow();
            return "redirect:/" + oficina.getSlug() + "/login?setupSuccess=true"; 
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("funcionario", admin);
            model.addAttribute("idOficina", idOficina);
            return "setup/admin"; 
        }
    }
}