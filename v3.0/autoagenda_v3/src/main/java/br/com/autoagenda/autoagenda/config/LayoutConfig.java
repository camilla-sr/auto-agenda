package br.com.autoagenda.autoagenda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import br.com.autoagenda.autoagenda.dto.BrandingDto;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class LayoutConfig {
    @Autowired private OficinaRepository oficinaRepo;

    @ModelAttribute("branding")
    public BrandingDto carregarIdentidadeVisual(HttpServletRequest request, HttpSession session) {
        Oficina oficina = extrairOficinaDaRequisicao(request, session);
        
        if (oficina != null) {
            String logo = oficina.getLogotipo() != null ? "/img/" + oficina.getLogotipo() : "/img/padrao_logo.png";
            String fav = oficina.getFavicon() != null ? "/img/" + oficina.getFavicon() : "/img/padrao_favicon.ico";
            
            return new BrandingDto(logo, fav, oficina.getNomeFantasia());
        }
        return new BrandingDto(
            "/img/padrao_logo.png",
            "/img/padrao_favicon.ico",
            "AutoAgenda"
        );
    }
    
    @ModelAttribute("configuracao")
    public Oficina carregarConfiguracao(HttpServletRequest request, HttpSession session) {
        Oficina oficina = extrairOficinaDaRequisicao(request, session);
        return oficina != null ? oficina : new Oficina(); 
    }

    private Oficina extrairOficinaDaRequisicao(HttpServletRequest request, HttpSession session) {
        Oficina oficina = (Oficina) request.getAttribute("oficinaContexto");
        
        if (oficina == null) oficina = (Oficina) request.getAttribute("oficinaAtual");
        if (oficina != null) return oficina;

        String uri = request.getRequestURI(); 
        String[] partes = uri.split("/");
        
        if (partes.length > 1) {
            String slugUrl = partes[1];
            if (!slugUrl.equals("superadmin-api") && !slugUrl.equals("superadmin") 
                && !slugUrl.equals("setup") && !slugUrl.equals("error")) {
            	
                return oficinaRepo.findBySlug(slugUrl).orElse(null);
            }
        }
        return (Oficina) session.getAttribute("oficinaAtual");
    }
}