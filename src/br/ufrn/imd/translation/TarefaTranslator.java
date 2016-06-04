package br.ufrn.imd.translation;

import java.util.Collections;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;

import br.ufrn.imd.dominio.Estado;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;

public class TarefaTranslator implements ITranslator<Issue, Tarefa> {

	private SprintTranslator sprintTranslator;
	private UsuarioTranslator usuarioTranslator;

	private final int POSITION_PARAMETER_VALUE;
	private final String CHAR_SEPARATOR;
	
	private final String PONTUACAO_TERM;

	public TarefaTranslator(GitHubClient client) {
		POSITION_PARAMETER_VALUE = 1;
		CHAR_SEPARATOR = ":";
		
		PONTUACAO_TERM = "Pontos";

		sprintTranslator = new SprintTranslator(client);
		usuarioTranslator = new UsuarioTranslator();
	}

	@Override
	public Tarefa convertToObject(Issue gitObject) {
		List<Label> labels = gitObject.getLabels();
		Label pontuacaoLabel = null;
						
		for(Label label : labels){
			if(label.getName().startsWith(PONTUACAO_TERM)){
				pontuacaoLabel = label;
				break;
			}
		}
		
		int pontuacao = 0;
		String pontos;
		if(pontuacaoLabel != null){
			pontos = pontuacaoLabel.getName();
			pontos = pontos.split(CHAR_SEPARATOR)[POSITION_PARAMETER_VALUE];
			pontuacao = Integer.parseInt(pontos);
		}
		
		String estadoTarefa = gitObject.getState(); 
		
		Estado estado = estadoTarefa.equals("open") ? Estado.OPEN : Estado.CLOSED;

		Milestone milestone = gitObject.getMilestone();
		Sprint sprint = null;
		if (milestone != null) {
			sprint = sprintTranslator.convertToObject(milestone);
		}

		User assignedUser = gitObject.getAssignee();
		Usuario usuario = null;
		if (assignedUser != null) {
			usuario = usuarioTranslator.convertToObject(gitObject.getAssignee());
		}
				
		Tarefa tarefa = new Tarefa();
		tarefa.setId(gitObject.getId());
		tarefa.setNumero(gitObject.getNumber());
		tarefa.setTitulo(gitObject.getTitle());
		tarefa.setDescricao(gitObject.getBody());
		tarefa.setPontuacao(pontuacao);
		tarefa.setEstado(estado);
		tarefa.setSprint(sprint);
		tarefa.setUsuario(usuario);

		return tarefa;
	}

	@Override
	public Issue convertToGitObject(Tarefa object) {
		Label pontuacao = new Label();
		pontuacao.setName("Pontos:" + object.getPontuacao());

		List<Label> labels = Collections.singletonList(pontuacao);

		Sprint sprint = object.getSprint();
		Milestone milestone = null;
		if(sprint != null){
			milestone = sprintTranslator.convertToGitObject(sprint);	
		}
		
		Usuario usuario = object.getUsuario();
		User user = null;
		if(usuario != null){
			user = usuarioTranslator.convertToGitObject(object.getUsuario());	
		}
		
		String estado = (object.getEstado() == Estado.OPEN) ? "open" : "closed";

		Issue issue = new Issue();
		issue.setId(object.getId());
		issue.setNumber(object.getNumero());
		issue.setTitle(object.getTitulo());
		issue.setBody(object.getDescricao());
		issue.setLabels(labels);
		issue.setAssignee(user);
		issue.setState(estado);
		issue.setMilestone(milestone);

		return issue;
	}

}
