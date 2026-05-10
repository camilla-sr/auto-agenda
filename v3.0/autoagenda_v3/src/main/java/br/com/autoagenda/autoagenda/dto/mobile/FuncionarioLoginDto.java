package br.com.autoagenda.autoagenda.dto.mobile;

public record FuncionarioLoginDto(Integer idFuncionario, String nomeFuncionario, 
        					String usuario, String email, String acesso, OficinaResumo oficina) {
	
    public record OficinaResumo(Integer idOficina) {}
}