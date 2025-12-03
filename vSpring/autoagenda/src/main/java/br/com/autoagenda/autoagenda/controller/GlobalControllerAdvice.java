package br.com.autoagenda.autoagenda.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * GlobalControllerAdvice - Intercepta todas as requisições e injeta dados globais no modelo.
 * 
 * Esta classe resolve a limitação do Thymeleaf 3.1+ onde objetos como #request não estão
 * mais disponíveis por padrão. Ao usar @ModelAttribute, disponibilizamos a URI atual
 * para todos os templates, permitindo lógica condicional (ex: destacar item ativo na navbar).
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Injeta a URI atual da requisição como atributo "currentUri" no modelo.
     * 
     * @param request O objeto HttpServletRequest da requisição atual
     * @return A URI da requisição (ex: "/agendamentos", "/servicos")
     */
    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
