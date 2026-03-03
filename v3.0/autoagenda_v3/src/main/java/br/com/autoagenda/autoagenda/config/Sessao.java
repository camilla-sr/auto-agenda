package br.com.autoagenda.autoagenda.config;

import org.springframework.stereotype.Component;
import br.com.autoagenda.autoagenda.model.Funcionario;
import jakarta.servlet.http.HttpSession;

@Component
public class Sessao {
    public void logout(HttpSession session) { session.invalidate(); }
    
    public boolean loginAtivo(HttpSession session) {
        return session.getAttribute("usuarioLogado") != null;
    }
    
    public boolean verificaAcesso(HttpSession session, String acessoNecessario) {
        if (!loginAtivo(session)) return false;
        return ((Funcionario) session.getAttribute("usuarioLogado")).getAcesso().equals(acessoNecessario);
    }
}