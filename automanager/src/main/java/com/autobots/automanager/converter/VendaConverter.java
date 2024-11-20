package com.autobots.automanager.converter;

import com.autobots.automanager.dto.VendaDTO;
import com.autobots.automanager.entidades.Venda;

public class VendaConverter {
	public VendaDTO toDTO(Venda venda) {
		VendaDTO dto = new VendaDTO();
		dto.setId(venda.getId());
		dto.setIdentificacao(venda.getIdentificacao());
		return dto;
	}
}
