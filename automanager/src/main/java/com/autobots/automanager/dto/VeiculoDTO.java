package com.autobots.automanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO {
	private Long id;
	private String tipo;
	private String modelo;
	private String placa;
	private EmpresaDTO empresa;
}
