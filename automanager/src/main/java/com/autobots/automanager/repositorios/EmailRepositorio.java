package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entidades.Email;

@Repository
public interface EmailRepositorio extends JpaRepository<Email, Long> {
}
