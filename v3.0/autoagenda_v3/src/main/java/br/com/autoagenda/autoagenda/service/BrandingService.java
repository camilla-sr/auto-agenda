package br.com.autoagenda.autoagenda.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autoagenda.autoagenda.dto.BrandingDto;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;

@Service
public class BrandingService {
    @Autowired private OficinaRepository oficinaRepo;

    private static final String LOGO_PADRAO = "/img/padrao_logo.png";
    private static final String NOME_PADRAO = "AutoAgenda";

    public BrandingDto obterBrandingAtual() {
        List<Oficina> oficinas = oficinaRepo.findAll();
        if (oficinas.isEmpty()) {
            return new BrandingDto(LOGO_PADRAO, NOME_PADRAO);
        }

        Oficina oficina = oficinas.get(0);

        // Lógica: Se tiver logo no banco, usa ele (com o caminho /img/). Se não, usa o padrão.
        String urlLogo = (oficina.getLogotipo() != null && !oficina.getLogotipo().isEmpty()) 
                ? "/img/" + oficina.getLogotipo() 
                : LOGO_PADRAO;

        String nomeEmpresa = (oficina.getNomeFantasia() != null && !oficina.getNomeFantasia().isEmpty())
                ? oficina.getNomeFantasia()
                : NOME_PADRAO;

        return new BrandingDto(urlLogo, nomeEmpresa);
    }
}