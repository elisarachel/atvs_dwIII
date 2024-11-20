package com.autobots.automanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTO {
	private Long id;
	private String identificacao;
	private EmpresaDTO empresa;
}
