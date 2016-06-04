package br.ufrn.imd.dao.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.MilestoneService;

import br.ufrn.imd.dao.GenericGitDAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.translation.ProjetoTranslator;
import br.ufrn.imd.translation.SprintTranslator;

public class SprintGitDAO extends GenericGitDAO<Sprint, Projeto, Milestone, MilestoneService> {

	private ProjetoTranslator projetoTranslator;
	
	public SprintGitDAO(Usuario usuario) {
		super(usuario);
		service = new MilestoneService(getGitHubClient());
		translator = new SprintTranslator(getGitHubClient());
		projetoTranslator = new ProjetoTranslator();
	}

	@Override
	public Sprint salvar(Sprint object) {
		Projeto projeto = object.getProjeto();
		
		Repository sprintRepo = projetoTranslator.convertToGitObject(projeto);
				
		Milestone milestone = translator.convertToGitObject(object);
						
		try {
			milestone = service.createMilestone(sprintRepo, milestone);
		} catch (IOException e) {
			return null;
		}
		
		return object;
	}

	/**
	 * Lista as sprints de um projeto
	 */
	@Override
	public List<Sprint> listarComBaseEm(Projeto objetoFiltro) {
		Repository sprintRepo = projetoTranslator.convertToGitObject(objetoFiltro);
				
		String OPEN_STATE = "open";
		List<Milestone> milestones = null;
		List<Sprint> sprints = new ArrayList<Sprint>();
		
		try {
			milestones = service.getMilestones(sprintRepo, OPEN_STATE);
		} catch (IOException e) {
			return null;
		}
		
		Sprint sprint;
		for(Milestone milestone : milestones){
			sprint = translator.convertToObject(milestone);
			sprints.add(sprint);
		}
		
		return sprints;
	}

}
