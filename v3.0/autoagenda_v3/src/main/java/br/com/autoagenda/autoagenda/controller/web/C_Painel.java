package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.service.SetupService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/{slug}/painel-api")
public class C_Painel {
    @Autowired private SetupService service;

    @PostMapping("/salvar-dados")
    public String salvarDados(@PathVariable("slug") String slug, Oficina oficina, RedirectAttributes ra, HttpSession session) {
        try {
            service.atualizarDadosCadastrais(oficina);
            session.removeAttribute("oficinaAtual");
            
            ra.addFlashAttribute("msgSucesso", "Dados atualizados com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", "Erro ao salvar: " + e.getMessage());
        }
        return "redirect:/"+ slug +"/painel";
    }

    @PostMapping("/salvar-features")
    public String salvarFeatures(@PathVariable("slug") String slug, @RequestParam Integer idOficina,
                                 @RequestParam(defaultValue = "false") boolean usarProdutos,
                                 @RequestParam(defaultValue = "false") boolean usarFinanceiro,
                                 @RequestParam(defaultValue = "false") boolean usarAuth,
                                 RedirectAttributes ra) {
        try {
            service.atualizarFuncionalidades(idOficina, usarProdutos, usarFinanceiro, usarAuth);
            ra.addFlashAttribute("msgSucesso", "Funcionalidades atualizadas!");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", "Erro: " + e.getMessage());
        }
        return "redirect:/"+ slug +"/painel";
    }

    @PostMapping("/salvar-branding")
    public String salvarBranding(@PathVariable("slug") String slug, @RequestParam Integer idOficina,
                         @RequestParam(value = "arquivoLogo", required = false) MultipartFile logo,
                         @RequestParam(value = "arquivoFavicon", required = false) MultipartFile favicon,
                         RedirectAttributes ra) {
        try {
            service.atualizarBranding(idOficina, logo, favicon);
            ra.addFlashAttribute("msgSucesso", "Imagens atualizadas! Atualize a página se necessário.");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", "Erro ao subir imagem: " + e.getMessage());
        }
        return "redirect:/"+ slug +"/painel";
    }
}