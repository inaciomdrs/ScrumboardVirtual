package br.ufrn.imd.dao.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import br.ufrn.imd.dao.GenericGitDAO;
import br.ufrn.imd.dominio.Papel;
import br.ufrn.imd.dominio.Participacao;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.git.RepositoryServiceExtended;
import br.ufrn.imd.translation.ProjetoTranslator;
import br.ufrn.imd.translation.UsuarioTranslator;

public class ProjetoGitDAO extends GenericGitDAO<Projeto, Usuario, Repository, RepositoryServiceExtended> {

	private UsuarioTranslator usuarioTranslator;

	public ProjetoGitDAO(Usuario usuario) {
		super(usuario);
		service = new RepositoryServiceExtended(getGitHubClient());
		translator = new ProjetoTranslator();
		usuarioTranslator = new UsuarioTranslator();
	}

	@Override
	public Projeto salvar(Projeto object) {
		Repository repository = translator.convertToGitObject(object);
				
		try {
			repository = service.createRepository(repository);
		} catch (IOException e) {
			return null;
		}

		return object;
	}

	/**
	 * Lista os projetos de um usuário
	 */
	@Override
	public List<Projeto> listarComBaseEm(Usuario objetoFiltro) {
		List<Repository> repositories = service.getRepositoriesUserParticipate(); 
		
		if(repositories == null){
			return null;
		}
		
		List<Projeto> projetos = new ArrayList<Projeto>();
		
		Projeto projeto;
		Participacao participacao;
		Usuario coordenador;
		for(Repository repository : repositories){
			projeto = translator.convertToObject(repository);
			
			participacao = new Participacao();
			participacao.setProjeto(projeto);
			participacao.setUsuario(getUsuario());
			
			coordenador = projeto.getCoordenador();
			if(usuarioEhCoordenadorDoProjeto(coordenador.getLogin())){
				participacao.setPapel(Papel.ADMIN);
			} else {
				participacao.setPapel(Papel.DEVELOPER);
			}

			projeto.setParticipacoes(Collections.singletonList(participacao));
			
			projetos.add(projeto);
		}

		return projetos;
	}
	
	private boolean usuarioEhCoordenadorDoProjeto(String repositoryOwner){
		return getUsuario().getLogin().equals(repositoryOwner);
	}
	
	public boolean adicionarUsuarioAoProjeto(Usuario usuario, Projeto projeto){
		Repository repository = translator.convertToGitObject(projeto);
		
		User colaborador = usuarioTranslator.convertToGitObject(usuario);
		
		return service.addCollaboratorToRepository(colaborador, repository);
	}

}
