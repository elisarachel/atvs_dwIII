package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
	List<Veiculo> findByEmpresaId(Long empresaId);
}
