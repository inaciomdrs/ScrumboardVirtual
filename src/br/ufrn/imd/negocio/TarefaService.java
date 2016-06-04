package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.TarefaJPADAO;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;

@Stateless
public class TarefaService {

	@Inject
	private TarefaJPADAO tarefaJPADAO;
	
	public Tarefa cadastrarTarefa(Tarefa tarefa){
		tarefa = tarefaJPADAO.salvar(tarefa);
		return tarefa;
	}
	
	public Tarefa atualizarTarefa(Tarefa tarefa){
		tarefa = tarefaJPADAO.atualizar(tarefa);
		return tarefa;
	}
	
	public List<Tarefa> tarefasDaSprint(Sprint sprint){
		if(sprint.getId() > 0){
			return tarefaJPADAO.listarComBaseEm(sprint);
		}
		return null;
	}
	
	public Tarefa buscaPorId(long id){
		return tarefaJPADAO.listarPorId(id);
	}
	
}
