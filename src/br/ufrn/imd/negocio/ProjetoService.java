package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.ProjetoJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

@Stateless
public class ProjetoService {

	@Inject
	private ProjetoJPADAO projetoJPADAO;
	
	//private ProjetoGitDAO projetoGitDAO;
				
	public List<Projeto> listarProjetosDeUsuario(Usuario usuario){
		List<Projeto> projetos = projetoJPADAO.listarComBaseEm(usuario);
		
		return projetos;
	}
	
}
