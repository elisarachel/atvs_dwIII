package com.autobots.automanager.repositorios;

import com.autobots.automanager.enumeracoes.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entidades.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	@Query("SELECT u FROM Usuario u JOIN u.credenciais c WHERE TYPE(c) = CredencialUsuarioSenha AND TREAT(c AS CredencialUsuarioSenha).nomeUsuario = :nomeUsuario")
	Optional<Usuario> findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);

	@Query("SELECT u FROM Usuario u WHERE u.empresa.id = :empresaId AND u.perfis IN :perfis")
	List<Usuario> findByEmpresaIdAndPerfis(@Param("empresaId") Long empresaId, @Param("perfis") Set<Perfil> perfis);
}
