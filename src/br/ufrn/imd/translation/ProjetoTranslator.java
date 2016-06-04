package br.ufrn.imd.translation;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

public class ProjetoTranslator implements ITranslator<Repository, Projeto> {
	
	private UsuarioTranslator usuarioTranslator;
	
	public ProjetoTranslator() {
		usuarioTranslator = new UsuarioTranslator();
	}
	
	@Override
	public Projeto convertToObject(Repository gitObject) {
		Usuario coordenador = null;
		User owner = gitObject.getOwner();
		
		if(owner != null){
			coordenador = usuarioTranslator.convertToObject(owner);	
		}
		
		Projeto projeto = new Projeto();
		projeto.setTitulo(gitObject.getName());
		projeto.setDescricao(gitObject.getDescription());
		projeto.setId(gitObject.getId());
		projeto.setCoordenador(coordenador);
		
		return projeto;
	}

	@Override
	public Repository convertToGitObject(Projeto object) {
		Usuario coordenador = object.getCoordenador();
		User owner = null;
		
		if(coordenador != null){
			owner = usuarioTranslator.convertToGitObject(coordenador);
		}
		
		Repository repository = new Repository();
		repository.setId(object.getId());
		repository.setName(object.getTitulo());
		repository.setDescription(object.getDescricao());
		repository.setOwner(owner);
		
		return repository;
	}

}
