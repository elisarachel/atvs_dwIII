package com.autobots.automanager.converter;

import com.autobots.automanager.dto.VeiculoDTO;
import com.autobots.automanager.entidades.Veiculo;

public class VeiculoConverter {
	public VeiculoDTO toDTO(Veiculo veiculo) {
		VeiculoDTO dto = new VeiculoDTO();
		dto.setId(veiculo.getId());
		dto.setTipo(veiculo.getTipo().toString());
		dto.setModelo(veiculo.getModelo());
		dto.setPlaca(veiculo.getPlaca());
		return dto;
	}
}
