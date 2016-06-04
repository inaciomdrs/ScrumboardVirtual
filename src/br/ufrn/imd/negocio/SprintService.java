package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.SprintJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;

@Stateless
public class SprintService {

	@Inject
	private SprintJPADAO sprintJPADAO;

	public Sprint cadastrarSprint(Sprint sprint){
		if(sprint != null){
			sprint = sprintJPADAO.salvar(sprint);	
		}
		return sprint;
	}
	
	public List<Sprint> listarSprintsDoProjeto(Projeto projeto){
		List<Sprint> sprints = sprintJPADAO.listarComBaseEm(projeto);
		
		return sprints;
	}
	
	public Sprint buscaPorId(long id){
		return sprintJPADAO.listarPorId(id);
	}
	
}
