package br.ufrn.imd.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.TarefaJPADAO;
import br.ufrn.imd.dominio.Estado;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.otimizacao.DistribuidorTarefas;

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
	
	public Tarefa fecharTarefa(Tarefa tarefa){
		if(tarefa.getEstado() == Estado.OPEN){
			tarefa.setEstado(Estado.CLOSED);
			tarefa = atualizarTarefa(tarefa);
		}
		return tarefa;
	}
	
	public Tarefa assumirTarefa(Usuario usuario, Tarefa tarefa){
		if(usuario != null){
			tarefa.setUsuario(usuario);
			tarefa = atualizarTarefa(tarefa);
		}
		return tarefa;
	}
	
	public void distribuirTarefas(List<Usuario> participantes, List<Tarefa> tarefas){
		// TODO Implement this method
		
//		if((participantes == null) || (tarefasLivres == null) || (tarefasLivres.size() == 0)) {
//			return;
//		}
//		
//		DistribuidorTarefas distribuidor = DistribuidorTarefas.getInstance();
//		distribuidor.distribuirTarefas(tarefasLivres, participantes);
//		
//		for(Tarefa tarefa : tarefasLivres){
//			tarefa
//		}
	}
	
}
