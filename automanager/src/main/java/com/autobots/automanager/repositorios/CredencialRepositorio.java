package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entidades.Credencial;

@Repository
public interface CredencialRepositorio extends JpaRepository<Credencial, Long> {
}
