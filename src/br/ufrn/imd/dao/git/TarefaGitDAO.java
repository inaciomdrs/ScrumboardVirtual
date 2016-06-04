package br.ufrn.imd.dao.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;

import br.ufrn.imd.dao.GenericGitDAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.git.IssueServiceExtended;
import br.ufrn.imd.translation.ProjetoTranslator;
import br.ufrn.imd.translation.SprintTranslator;
import br.ufrn.imd.translation.TarefaTranslator;

public class TarefaGitDAO extends GenericGitDAO<Tarefa, Sprint, Issue, IssueServiceExtended> {

	private ProjetoTranslator projetoTranslator;
	private SprintTranslator sprintTranslator;
	
	public TarefaGitDAO(Usuario usuario) {
		super(usuario);
		this.service = new IssueServiceExtended(getGitHubClient());
		this.translator = new TarefaTranslator(getGitHubClient());
		this.projetoTranslator = new ProjetoTranslator();
		this.sprintTranslator = new SprintTranslator(getGitHubClient());
	}

	@Override
	public Tarefa salvar(Tarefa object) {
		Projeto projeto = object.getSprint().getProjeto();
		Repository repository = projetoTranslator.convertToGitObject(projeto);
		
		Issue issue = translator.convertToGitObject(object);
						
		try {
			issue = service.createIssue(repository, issue);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
				
		return object;
	}

	/**
	 * Lista as tarefas de uma sprint
	 */
	@Override
	public List<Tarefa> listarComBaseEm(Sprint objetoFiltro) {
		Milestone milestone = sprintTranslator.convertToGitObject(objetoFiltro);
		Repository repository = projetoTranslator.convertToGitObject(objetoFiltro.getProjeto());
		
		List<Issue> issues = service.getIssuesFromMilestone(milestone, repository);
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		Tarefa tarefa;
		for(Issue issue : issues){
			tarefa = translator.convertToObject(issue);
			tarefas.add(tarefa);
		}
		
		return tarefas;
	}
	
	public Tarefa atualizarTarefa(Tarefa tarefa){
		Projeto projeto = tarefa.getSprint().getProjeto();
		Repository repository = projetoTranslator.convertToGitObject(projeto);
		
		Issue issue = translator.convertToGitObject(tarefa);
						
		try {
			issue = service.editIssue(repository, issue);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
				
		return tarefa;		
	}

}
