package br.com.autoagenda.autoagenda.dto.mobile;

import java.util.List;

public record ClienteDto(Integer idCliente, OficinaResumo oficina, String nomeCliente, 
        				String telefone, String email, boolean ativo, List<VeiculoResumo> veiculos) {
	    
	public record OficinaResumo(Integer idOficina) {}
    public record VeiculoResumo(Integer idVeiculo, String placa, String modelo, String marca, boolean ativo) {} 
}