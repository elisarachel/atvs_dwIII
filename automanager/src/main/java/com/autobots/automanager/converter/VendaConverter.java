package com.autobots.automanager.converter;

import com.autobots.automanager.dto.VendaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.dto.EmpresaDTO;

public class VendaConverter {
	public VendaDTO toDTO(Venda venda) {
		VendaDTO dto = new VendaDTO();
		dto.setId(venda.getId());
		dto.setIdentificacao(venda.getIdentificacao());
		return dto;
	}

	private EmpresaDTO toEmpresaDTO(Empresa empresa) {
		EmpresaDTO dto = new EmpresaDTO();
		dto.setId(empresa.getId());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		return dto;
	}
}
