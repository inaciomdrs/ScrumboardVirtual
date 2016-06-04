package br.ufrn.imd.dao.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import br.ufrn.imd.dao.GenericGitDAO;
import br.ufrn.imd.dominio.Papel;
import br.ufrn.imd.dominio.Participacao;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.git.UserServiceExtended;
import br.ufrn.imd.translation.ProjetoTranslator;
import br.ufrn.imd.translation.UsuarioTranslator;

public class UsuarioGitDAO extends GenericGitDAO<Usuario, Projeto, User, UserServiceExtended> {

	private ProjetoTranslator projetoTranslator;

	public UsuarioGitDAO(Usuario usuario) {
		super(usuario);
		service = new UserServiceExtended(getGitHubClient());
		translator = new UsuarioTranslator();
		projetoTranslator = new ProjetoTranslator();
	}
	
	public UsuarioGitDAO() {}

	@Override
	public Usuario salvar(Usuario object) {
		throw new NotImplementedException("Esta classe não implementa persistência de Usuário");
	}

	/**
	 * Lista os usuários de um projeto especificado
	 * 
	 * @param objetoFiltro
	 *            Projeto do qual se busca extrair os usuários participantes
	 */
	@Override
	public List<Usuario> listarComBaseEm(Projeto objetoFiltro) {
		Repository repository = projetoTranslator.convertToGitObject(objetoFiltro);

		String repositoryOwnerName = objetoFiltro.getCoordenador().getLogin();

		List<User> gitUsers = service.getCollaboratorsOfRepository(repository);

		if (gitUsers == null) {
			return null;
		}

		List<Usuario> usuarios = new ArrayList<Usuario>();

		Usuario usuario;
		Participacao participacao;

		for (User gitUser : gitUsers) {
			usuario = translator.convertToObject(gitUser);

			participacao = new Participacao();
			participacao.setProjeto(objetoFiltro);
			participacao.setUsuario(usuario);

			if (usuarioEhCoordenadorDoProjeto(usuario, repositoryOwnerName)) {
				participacao.setPapel(Papel.ADMIN);
			} else {
				participacao.setPapel(Papel.DEVELOPER);
			}

			usuario.setParticipacoes(new ArrayList<Participacao>());
			usuario.getParticipacoes().add(participacao);

			usuarios.add(usuario);
		}

		return usuarios;
	}

	private boolean usuarioEhCoordenadorDoProjeto(Usuario usuario, String repositoryOwner) {
		return usuario.getLogin().equals(repositoryOwner);
	}

	public Usuario buscarUsuario(String login) {
		User gitUser;

		try {
			gitUser = service.getUser(login);
		} catch (IOException e) {
			return null;
		}

		Usuario usuario = translator.convertToObject(gitUser);

		return usuario;
	}

}
