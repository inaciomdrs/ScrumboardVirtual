package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.ParticipacaoJPADAO;
import br.ufrn.imd.dao.jpa.ProjetoJPADAO;
import br.ufrn.imd.dominio.Papel;
import br.ufrn.imd.dominio.Participacao;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

@Stateless
public class ProjetoService {

	@Inject
	private ProjetoJPADAO projetoJPADAO;
	
	@Inject
	private ParticipacaoJPADAO participacaoJPADAO;
	
	//private ProjetoGitDAO projetoGitDAO;
	
	public Projeto cadastrarProjeto(Projeto projeto, Usuario usuario){
		if(usuario != null){
			projeto.setCoordenador(usuario);
		}
		
		Participacao participacao = new Participacao();
		participacao.setUsuario(usuario);
		participacao.setProjeto(projeto);
		participacao.setPapel(Papel.ADMIN);
		
		projeto = projetoJPADAO.salvar(projeto);
		participacao = participacaoJPADAO.salvar(participacao);
		
		return projeto;
	}
	
	public List<Projeto> listarProjetosDeUsuario(Usuario usuario){
		List<Projeto> projetos = projetoJPADAO.listarComBaseEm(usuario);
		
		return projetos;
	}
	
}
