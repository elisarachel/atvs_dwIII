package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.enumeracoes.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.configuracao.AuthenticationFacade;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public List<Usuario> listarTodos() {
		return usuarioRepositorio.findAll();
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepositorio.findById(id);
	}

	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	public void excluirUsuario(Long id) {
		usuarioRepositorio.deleteById(id);
	}

	@Autowired
	public AuthenticationFacade authenticationFacade;

	public boolean temPermissaoParaAlterar(Usuario usuarioAlvo) {
		Usuario usuarioAutenticado = authenticationFacade.getUsuarioAutenticado();

		// Administrador pode alterar qualquer usuário
		if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_ADMIN)) {
			return true;
		}

		// Gerente pode alterar vendedores e clientes
		if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_GERENTE)) {
			return usuarioAlvo.getPerfis().stream().allMatch(perfil ->
					perfil == Perfil.ROLE_VENDEDOR || perfil == Perfil.ROLE_CLIENTE);
		}

		// Vendedor pode alterar apenas clientes
		if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
			return usuarioAlvo.getPerfis().contains(Perfil.ROLE_CLIENTE);
		}

		// Cliente pode alterar apenas seus próprios dados
		return usuarioAlvo.getId().equals(usuarioAutenticado.getId());
	}

	public void atualizarDadosPermitidos(Usuario usuarioAtual, Usuario dadosAtualizados) {
		Usuario usuarioAutenticado = authenticationFacade.getUsuarioAutenticado();

		// Cliente pode alterar apenas dados pessoais limitados
		if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_CLIENTE)) {
			usuarioAtual.setNome(dadosAtualizados.getNome());
			usuarioAtual.setEmails(dadosAtualizados.getEmails());
		} else {
			// Outros perfis podem alterar todos os campos
			usuarioAtual.setNome(dadosAtualizados.getNome());
			usuarioAtual.setEmails(dadosAtualizados.getEmails());
			usuarioAtual.setPerfis(dadosAtualizados.getPerfis());
		}
	}

	public Usuario getUsuarioAutenticado() {
        return authenticationFacade.getUsuarioAutenticado();
	}
}
