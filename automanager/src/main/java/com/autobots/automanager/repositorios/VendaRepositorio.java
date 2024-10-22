package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entidades.Venda;
import java.util.List;

@Repository
public interface VendaRepositorio extends JpaRepository<Venda, Long> {
	// Metodo para buscar vendas por ID do vendedor (funcionário)
	List<Venda> findByFuncionarioId(Long vendedorId);

	// Metodo para buscar vendas por ID do cliente
	List<Venda> findByClienteId(Long clienteId);
}
