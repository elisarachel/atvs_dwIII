package com.autobots.automanager.entidades;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "documentos")
public class Documento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String tipo;
	@Column(unique = true)
	private String numero;
}