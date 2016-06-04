package br.ufrn.imd.translation;

import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;

import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.git.MilestoneServiceExtended;

public class SprintTranslator implements ITranslator<Milestone, Sprint> {
	
	private MilestoneServiceExtended milestoneServiceExtended;
	private ProjetoTranslator projetoTranslator;
	
	public SprintTranslator(GitHubClient client) {
		milestoneServiceExtended = new MilestoneServiceExtended(client);
		projetoTranslator = new ProjetoTranslator();
	}
	
	@Override
	public Sprint convertToObject(Milestone gitObject) {
		Repository repository = milestoneServiceExtended.getRepositoryOfMilestone(gitObject);
		Projeto projeto;
		
		if(repository != null){
			projeto = projetoTranslator.convertToObject(repository);
		} else {
			projeto = null;
		}
		
		Sprint sprint = new Sprint();
		sprint.setTitulo(gitObject.getTitle());
		sprint.setDescricao(gitObject.getDescription());
		sprint.setDataFinalizacao(gitObject.getDueOn());
		sprint.setNumero(gitObject.getNumber());
		sprint.setProjeto(projeto);
						
		return sprint;
	}

	@Override
	public Milestone convertToGitObject(Sprint object) {
		Milestone milestone = new Milestone();
		milestone.setTitle(object.getTitulo());
		milestone.setDescription(object.getDescricao());
		milestone.setDueOn(object.getDataFinalizacao());
		milestone.setState("open");
		milestone.setNumber(object.getNumero());
		
		return milestone;
	}

}
