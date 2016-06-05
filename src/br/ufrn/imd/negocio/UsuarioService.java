package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.git.UsuarioGitDAO;
import br.ufrn.imd.dao.jpa.UsuarioJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

@Stateless
public class UsuarioService {

	private UsuarioGitDAO usuarioGitDAO;

	@Inject
	private UsuarioJPADAO usuarioJPADAO;

	public UsuarioService() {
		usuarioGitDAO = new UsuarioGitDAO();
	}
	
	public Usuario cadastrarUsuario(Usuario usuario){
		if(usuario == null){
			return null;
		}
		
		List<Usuario> usuariosExistentes = usuariosComLogin(usuario.getLogin());
		
		if((usuariosExistentes != null) && (usuariosExistentes.size() > 0)){
			return null;
		}
		
		usuario = usuarioJPADAO.salvar(usuario);
		
		return usuario;
	}

	public Usuario logar(Usuario usuario) {
		String loginUsuario = usuario.getLogin();
		String senha;
		Usuario usuarioBanco = usuarioJPADAO.buscaPorLogin(loginUsuario);

		if (usuarioBanco != null) {
			senha = usuarioBanco.getSenha();
			if (senha.equals(usuario.getSenha())) {
				return usuarioBanco;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public List<Usuario> usuariosComLogin(String login){
		List<Usuario> usuariosEncontrados = usuarioJPADAO.buscaUsuariosComLogin(login);
		return usuariosEncontrados;
	}

	public Usuario logarComGit(Usuario usuario) {
		String loginUsuario = usuario.getLogin();

		usuarioGitDAO = new UsuarioGitDAO(usuario);

		Usuario usuarioGit = usuarioGitDAO.buscarUsuario(loginUsuario);

		if (usuarioGit != null) {
			usuarioGitDAO = new UsuarioGitDAO(usuarioGit);
		}

		return usuarioGit;
	}
	
	public List<Usuario> usuariosDoProjeto(Projeto projeto){
		if(projeto.getId() >= 0){
			return usuarioJPADAO.listarComBaseEm(projeto);	
		} else {
			return null;	
		}
	}

}
