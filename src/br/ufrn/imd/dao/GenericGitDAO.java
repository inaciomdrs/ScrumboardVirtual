
package br.ufrn.imd.dao;

import org.eclipse.egit.github.core.client.GitHubClient;

import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.translation.ITranslator;

/**
 * Implementa um DAO genérico para persistência e listagem em/de repositório GitHub.
 * @author inacio-medeiros
 *
 * @param <T> Tipo de objeto base para as operações
 * @param <G> Tipo de objeto GIT base para as operações
 * @param <F> Objeto de filtro para a operação de listagem
 * @param <Service> Serviço GitHub que executa operações de persistência e recuperação de dados
 * @param <Converter> Conversor GitHub-Objeto e vice-verse
 */
public abstract class GenericGitDAO<T, F, G, Service> implements IDataAcess<T, F> {
	
	private static Usuario usuario;
	private static GitHubClient gitHubClient;
	protected Service service;
	protected ITranslator<G, T> translator;
	
	public GenericGitDAO(){
		usuario = null;
		gitHubClient = new GitHubClient();
	}
	
	public GenericGitDAO(Usuario user){
		usuario = user;
		gitHubClient = new GitHubClient();
		gitHubClient.setCredentials(usuario.getLogin(), usuario.getSenha());
	}
	
	public static GitHubClient getGitHubClient() {
		return gitHubClient;
	}

	public static Usuario getUsuario() {
		return usuario;
	}	
	
}
